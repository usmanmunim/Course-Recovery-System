package crs.services;

import crs.utils.CsvUtil;
import crs.utils.ValidationUtil;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class EligibilityService {
    private static final String STUDENTS = "student_information.csv";
    private static final String RESULTS = "results.csv";
    private static final String COURSES = "course_assessment_information.csv";
    private static final String ENROL = "enrolments.csv";

    public static class EligibilityResult {
        public String studentId;
        public String fullName;
        public String major;
        public String year;
        public String email;
        public double cgpa;
        public int failed;
        public boolean eligible;

        public EligibilityResult(String id, String name, String m, String y, String e,
                double c, int f, boolean el) {
            studentId = id;
            fullName = name;
            major = m;
            year = y;
            email = e;
            cgpa = c;
            failed = f;
            eligible = el;
        }
    }

    public List<EligibilityResult> listAll() {
        Map<String, Map<String, String>> students = mapByUpper("StudentID", CsvUtil.readCsv(STUDENTS));
        List<EligibilityResult> out = new ArrayList<>();
        for (String sid : students.keySet()) {
            EligibilityResult r = checkStudent(sid);
            if (r != null)
                out.add(r);
        }
        out.sort(Comparator.comparing(a -> a.studentId));
        return out;
    }

    public EligibilityResult checkStudent(String studentId) {
        final String sid = safe(studentId).toUpperCase();
        Map<String, Map<String, String>> students = mapByUpper("StudentID", CsvUtil.readCsv(STUDENTS));
        if (!students.containsKey(sid))
            return null;
        Map<String, Integer> creditsByCourse = new HashMap<>();
        for (Map<String, String> c : CsvUtil.readCsv(COURSES)) {
            String cid = safe(c.get("CourseID"));
            if (ValidationUtil.isBlank(cid))
                continue;
            creditsByCourse.put(cid,
                    ValidationUtil.safeInt(c.get("Credits"), 0));
        }
        List<Map<String, String>> results = CsvUtil.readCsv(RESULTS);
        Map<String, Map<String, String>> latest = results.stream()
                .filter(r -> sid.equalsIgnoreCase(
                        safe(r.get("studentId")).toUpperCase()))
                .filter(r -> !ValidationUtil.isBlank(r.get("courseId")))
                .collect(Collectors.toMap(
                        r -> safe(r.get("courseId")),
                        r -> r,
                        (a, b) -> {
                            int aa = ValidationUtil.safeInt(a.get("attemptNo"), 1);
                            int bb = ValidationUtil.safeInt(b.get("attemptNo"), 1);
                            return bb >= aa ? b : a;
                        }));
        double totalWeighted = 0.0;
        int totalCredits = 0;
        int failed = 0;
        for (Map<String, String> r : latest.values()) {
            String courseId = safe(r.get("courseId"));
            double gp = ValidationUtil.safeDouble(r.get("gradePoint"), 0.0);
            String grade = safe(r.get("grade"));
            int cr = creditsByCourse.getOrDefault(courseId, 0);
            totalWeighted += gp * cr;
            totalCredits += cr;
            if (gp <= 0.0 || "F".equalsIgnoreCase(grade)) {
                failed++;
            }
        }
        double cgpa = (totalCredits == 0)
                ? 0.0
                : round2(totalWeighted / totalCredits);
        boolean eligible = (cgpa >= 2.0) && (failed <= 3);
        Map<String, String> s = students.get(sid);
        String fullName = (safe(s.get("FirstName")) + " " +
                safe(s.get("LastName"))).trim();
        return new EligibilityResult(
                sid,
                fullName,
                safe(s.get("Major")),
                safe(s.get("Year")),
                safe(s.get("Email")),
                cgpa,
                failed,
                eligible);
    }

    public Set<String> registeredIds() {
        Set<String> set = new HashSet<>();
        for (Map<String, String> r : CsvUtil.readCsv(ENROL)) {
            String id = safe(r.get("studentId")).toUpperCase();
            if (!id.isEmpty())
                set.add(id);
        }
        return set;
    }

    public void register(String studentId) {
        final String sid = safe(studentId).toUpperCase();
        CsvUtil.appendLine(
                ENROL,
                "studentId,timestamp",
                sid + "," + LocalDateTime.now());
    }

    private Map<String, Map<String, String>> mapByUpper(
            String key, List<Map<String, String>> rows) {
        Map<String, Map<String, String>> map = new HashMap<>();
        for (Map<String, String> r : rows) {
            String id = safe(r.get(key)).toUpperCase();
            if (!id.isEmpty())
                map.put(id, r);
        }
        return map;
    }

    private String safe(String s) {
        return s == null ? "" : s.trim();
    }

    private double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}
