package crs.services;

import javax.swing.JOptionPane;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

public class ReportService {
    private static final String STUDENTS_CSV = "student_information.csv";
    private static final String COURSES_CSV = "course_assessment_information.csv";
    private static final String RESULTS_CSV = "results.csv";
    private static final String OUTPUT_DIR = "data/reports";

    public static class ReportRow {
        public final String courseId;
        public final String grade;
        public final String year; // Added
        public final String term; // Added
        public final double credits;
        public final double gradePoint;

        public ReportRow(String courseId, String grade, double credits, double gradePoint, String year, String term) {
            this.courseId = courseId;
            this.grade = grade;
            this.credits = credits;
            this.gradePoint = gradePoint;
            this.year = year;
            this.term = term;
        }
    }

    private static class StudentInfo {
        final String fullName;
        final String programme;

        StudentInfo(String fullName, String programme) {
            this.fullName = fullName;
            this.programme = programme;
        }
    }

    private static class CourseMeta {
        final String title;
        final double credits;

        CourseMeta(String title, double credits) {
            this.title = title;
            this.credits = credits;
        }
    }

    public String generateReport(String studentId) {
        return generateReport(studentId, "", "");
    }

    public String generateReport(String studentId, String year, String term) {
        studentId = (studentId == null) ? "" : studentId.trim();
        if (studentId.isEmpty())
            return "";
        String safeYear = (year == null || year.equalsIgnoreCase("All")) ? "" : year.trim();
        String safeTerm = (term == null || term.equalsIgnoreCase("All")) ? "" : term.trim();
        Map<String, String> studentNames = loadStudentNames();
        Map<String, CourseMeta> courseInfo = loadCourseInfo();
        List<ResultRow> rows = loadResultsForStudent(studentId);
        List<ResultRow> filtered = new ArrayList<>();
        for (ResultRow r : rows) {
            if (!safeYear.isEmpty() && !safeYear.equalsIgnoreCase(r.academicYear))
                continue;
            if (!safeTerm.isEmpty() && !safeTerm.equalsIgnoreCase(r.term))
                continue;
            filtered.add(r);
        }
        if (filtered.isEmpty()) {
            String msg = "No results found for Student ID: " + studentId +
                    (safeYear.isEmpty() ? "" : " Year:" + safeYear) +
                    (safeTerm.isEmpty() ? "" : " Term:" + safeTerm);
            writeTxt(studentId, msg);
            return msg;
        }
        String name = studentNames.getOrDefault(studentId, "N/A");
        int total = filtered.size();
        int passed = 0, failed = 0;
        double totalQualityPoints = 0.0;
        double totalCredits = 0.0;
        StringBuilder table = new StringBuilder();
        table.append(String.format("%-12s | %-6s | %-7s%n", "Course ID", "Grade", "Credits"));
        table.append("------------------------------------------------------\n");
        for (ResultRow r : filtered) {
            CourseMeta meta = courseInfo.get(r.courseId);
            double credits = (meta != null) ? meta.credits : 3.0;
            boolean isFail = isFailGrade(r.grade);
            if (isFail)
                failed++;
            else
                passed++;
            double gp = (r.gradePoint >= 0) ? r.gradePoint : gradeToPoint(r.grade);
            if (gp >= 0) {
                totalQualityPoints += gp * credits;
                totalCredits += credits;
            }
            table.append(String.format(
                    "%-12s | %-6s | %-7.1f%n",
                    r.courseId,
                    (r.grade == null || r.grade.isBlank()) ? "-" : r.grade,
                    credits));
        }
        double gpa = (totalCredits > 0) ? (totalQualityPoints / totalCredits) : 0.0;
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        StringBuilder report = new StringBuilder();
        report.append("ACADEMIC PERFORMANCE REPORT\n");
        report.append("Generated: ").append(now).append("\n\n");
        report.append("Student ID: ").append(studentId).append("\n");
        report.append("Student Name: ").append(name).append("\n");
        report.append("Semester: ").append(safeTerm.isEmpty() ? "All" : safeTerm)
                .append("   Year: ").append(safeYear.isEmpty() ? "All" : safeYear).append("\n\n");
        report.append(table).append("\n");
        report.append("Summary\n");
        report.append("-------\n");
        report.append("Total Courses: ").append(total).append("\n");
        report.append("Passed: ").append(passed).append("\n");
        report.append("Failed: ").append(failed).append("\n");
        report.append(String.format(Locale.ROOT, "GPA: %.2f%n", gpa)).append("\n");
        writeTxt(studentId, report.toString());
        return report.toString();
    }

    public java.nio.file.Path exportToTXT(String studentId, String year, String term)
            throws java.io.IOException {
        java.nio.file.Path out = java.nio.file.Paths.get("reports", "report_" + studentId + ".txt");
        java.nio.file.Files.createDirectories(out.getParent());
        String reportText = generateReport(studentId, year, term);
        java.nio.file.Files.writeString(out, reportText, java.nio.charset.StandardCharsets.UTF_8);
        return out;
    }

    public Path exportToPDF(String studentId, String academicYear, String term) {
        studentId = (studentId == null) ? "" : studentId.trim();
        academicYear = (academicYear == null) ? "" : academicYear.trim();
        term = (term == null) ? "" : term.trim();
        if (studentId.isEmpty())
            return null;
        Map<String, String> studentNames = loadStudentNames();
        Map<String, CourseMeta> courseInfo = loadCourseInfo();
        String name = studentNames.getOrDefault(studentId, "N/A");
        List<ResultRow> all = loadResultsForStudent(studentId);
        String safeYear = (academicYear.equalsIgnoreCase("All")) ? "" : academicYear;
        String safeTerm = (term.equalsIgnoreCase("All")) ? "" : term;
        List<ResultRow> rows = new ArrayList<>();
        for (ResultRow r : all) {
            if (!safeYear.isEmpty() && !safeYear.equalsIgnoreCase(r.academicYear)) {
                continue;
            }
            if (!safeTerm.isEmpty() && !safeTerm.equalsIgnoreCase(r.term)) {
                continue;
            }
            rows.add(r);
        }
        String displayYear = safeYear.isEmpty() ? "All" : safeYear;
        String displayTerm = safeTerm.isEmpty() ? "All" : safeTerm;
        try {
            Path dir = Paths.get(OUTPUT_DIR);
            if (!Files.exists(dir))
                Files.createDirectories(dir);
            Path pdfOut = dir.resolve("report_" + studentId + "_" + safeYear + "_T" + safeTerm + ".pdf");
            PdfWriter writer = new PdfWriter(pdfOut.toString());
            PdfDocument pdf = new PdfDocument(writer);
            Document doc = new Document(pdf, PageSize.A4);
            doc.setMargins(36, 36, 36, 36);
            doc.add(new Paragraph("ACADEMIC PERFORMANCE REPORT").setFontSize(14));
            doc.add(new Paragraph("Student Name: " + name));
            doc.add(new Paragraph("Student ID: " + studentId));
            doc.add(new Paragraph("Semester: " + (safeTerm.equals("ALL") ? "All" : safeTerm)
                    + "   Year: " + (safeYear.equals("ALL") ? "All" : safeYear)));
            doc.add(new Paragraph(" "));
            if (rows.isEmpty()) {
                doc.add(new Paragraph("No results found for the selected semester/year."));
                doc.close();
                return pdfOut;
            }
            Table table = new Table(new float[] { 2, 4, 2, 2, 2 });
            table.setWidth(UnitValue.createPercentValue(100));
            table.addHeaderCell(new com.itextpdf.layout.element.Cell()
                    .add(new Paragraph("Course Code")));
            table.addHeaderCell(new com.itextpdf.layout.element.Cell()
                    .add(new Paragraph("Course Title")));
            table.addHeaderCell(new com.itextpdf.layout.element.Cell()
                    .add(new Paragraph("Credit Hours")));
            table.addHeaderCell(new com.itextpdf.layout.element.Cell()
                    .add(new Paragraph("Grade")));
            table.addHeaderCell(new com.itextpdf.layout.element.Cell()
                    .add(new Paragraph("Grade Point")));
            double totalCredits = 0.0;
            double totalQuality = 0.0;
            for (ResultRow r : rows) {
                CourseMeta meta = courseInfo.get(r.courseId);
                double credits = (meta != null) ? meta.credits : 3.0;
                String title = (meta != null) ? meta.title : "Unknown Course";
                double gp = (r.gradePoint >= 0) ? r.gradePoint : gradeToPoint(r.grade);
                table.addCell(r.courseId);
                table.addCell(title);
                table.addCell(String.valueOf(credits));
                table.addCell((r.grade == null || r.grade.isBlank()) ? "-" : r.grade);
                table.addCell(gp < 0 ? "-" : String.format(Locale.ROOT, "%.2f", gp));
                if (gp >= 0) {
                    totalCredits += credits;
                    totalQuality += gp * credits;
                }
            }
            doc.add(table);
            doc.add(new Paragraph(" "));
            double gpa = (totalCredits > 0) ? (totalQuality / totalCredits) : 0.0;
            doc.add(new Paragraph(
                    String.format(Locale.ROOT, "Cumulative GPA (CGPA): %.2f", gpa)));
            doc.close();
            return pdfOut;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getStudentDisplayList() {
        Map<String, StudentInfo> info = loadStudentInfo();
        List<String> out = new ArrayList<>();
        for (Map.Entry<String, StudentInfo> e : info.entrySet()) {
            out.add(e.getKey() + " - " + e.getValue().fullName);
        }
        out.sort(String.CASE_INSENSITIVE_ORDER);
        return out;
    }

    public String getStudentName(String studentId) {
        if (studentId == null)
            return "N/A";
        StudentInfo info = loadStudentInfo().get(studentId.trim());
        return (info == null) ? "N/A" : info.fullName;
    }

    public List<String> getAvailableAcademicYears(String studentId) {
        studentId = (studentId == null) ? "" : studentId.trim();
        if (studentId.isEmpty())
            return Collections.emptyList();
        List<ResultRow> rows = loadResultsForStudent(studentId);
        Set<String> years = new HashSet<>();
        for (ResultRow r : rows) {
            if (r.academicYear != null && !r.academicYear.isBlank()) {
                years.add(r.academicYear.trim());
            }
        }
        List<String> sorted = new ArrayList<>(years);
        sorted.sort(Collections.reverseOrder()); // newest first
        return sorted;
    }

    public List<String> getAvailableTerms(String studentId, String year) {
        studentId = (studentId == null) ? "" : studentId.trim();
        year = (year == null) ? "" : year.trim();
        if (studentId.isEmpty())
            return Collections.emptyList();
        List<ResultRow> rows = loadResultsForStudent(studentId);
        Set<String> terms = new HashSet<>();
        for (ResultRow r : rows) {
            String rYear = (r.academicYear == null) ? "" : r.academicYear.trim();
            if (year.isEmpty() || year.equalsIgnoreCase(rYear)) {
                if (r.term != null && !r.term.isBlank()) {
                    terms.add(r.term.trim());
                }
            }
        }
        List<String> sorted = new ArrayList<>(terms);
        sorted.sort(String.CASE_INSENSITIVE_ORDER);
        return sorted;
    }

    public List<ReportRow> getReportRows(String studentId, String year, String term) {
        studentId = (studentId == null) ? "" : studentId.trim();
        year = (year == null) ? "" : year.trim();
        term = (term == null) ? "" : term.trim();
        String safeYear = year.equalsIgnoreCase("All") ? "" : year;
        String safeTerm = term.equalsIgnoreCase("All") ? "" : term;
        if (studentId.isEmpty())
            return Collections.emptyList();
        Map<String, CourseMeta> courseInfo = loadCourseInfo();
        List<ResultRow> rows = loadResultsForStudent(studentId);
        List<ReportRow> out = new ArrayList<>();
        for (ResultRow r : rows) {
            if (!safeYear.isEmpty() && !safeYear.equalsIgnoreCase(r.academicYear))
                continue;
            if (!safeTerm.isEmpty() && !safeTerm.equalsIgnoreCase(r.term))
                continue;
            CourseMeta meta = courseInfo.get(r.courseId);
            double credits = (meta != null) ? meta.credits : 3.0;
            double gp = (r.gradePoint >= 0) ? r.gradePoint : gradeToPoint(r.grade);
            out.add(new ReportRow(
                    r.courseId,
                    r.grade,
                    credits,
                    gp,
                    r.academicYear,
                    r.term));
        }
        return out;
    }

    public List<ReportRow> getReportRows(String studentId) {
        return getReportRows(studentId, "", "");
    }

    public double calculateGpa(String studentId) {
        List<ReportRow> rows = getReportRows(studentId);
        double totalCredits = 0.0;
        double totalQuality = 0.0;
        for (ReportRow r : rows) {
            if (r.gradePoint >= 0) {
                totalCredits += r.credits;
                totalQuality += r.gradePoint * r.credits;
            }
        }
        return totalCredits > 0 ? (totalQuality / totalCredits) : 0.0;
    }

    private Map<String, StudentInfo> loadStudentInfo() {
        Map<String, StudentInfo> map = new HashMap<>();
        try (BufferedReader br = openResourceCsv(STUDENTS_CSV)) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank())
                    continue;
                List<String> parts = parseCsvLine(line);
                if (parts.size() < 4)
                    continue; // id, first, last, major
                String id = parts.get(0).trim();
                String first = parts.get(1).trim();
                String last = parts.get(2).trim();
                String major = parts.get(3).trim(); // ✅ Programme/Major
                String fullName = (first + " " + last).trim();
                if (!id.isEmpty()) {
                    map.put(id, new StudentInfo(fullName, major.isEmpty() ? "N/A" : major));
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load student info: " + e.getMessage());
        }
        return map;
    }

    private Map<String, String> loadStudentNames() {
        Map<String, String> map = new HashMap<>();
        try (BufferedReader br = openResourceCsv(STUDENTS_CSV)) {
            String header = br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank())
                    continue;
                List<String> parts = parseCsvLine(line); // use your existing CSV parser
                if (parts.size() < 3)
                    continue;
                String id = parts.get(0).trim();
                String first = parts.get(1).trim();
                String last = parts.get(2).trim();
                String fullName = (first + " " + last).trim();
                if (!id.isEmpty()) {
                    map.put(id, fullName);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load student names: " + e.getMessage());
        }
        return map;
    }

    public String getStudentProgramme(String studentId) {
        if (studentId == null)
            return "N/A";
        return loadStudentProgrammeMap().getOrDefault(studentId.trim(), "N/A");
    }

    public String getStudentEmail(String studentId) {
        if (studentId == null)
            return "";
        return loadStudentEmailMap().getOrDefault(studentId.trim(), "");
    }

    private Map<String, String> loadStudentEmailMap() {
        Map<String, String> map = new HashMap<>();
        try (BufferedReader br = openResourceCsv(STUDENTS_CSV)) {
            String headerLine = br.readLine(); // header
            if (headerLine == null)
                return map;
            List<String> header = parseCsvLine(headerLine);
            int idCol = findCol(header, "studentid", "student_id", "id");
            int emailCol = findCol(header, "email", "e-mail", "emailaddress", "email_address");
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank())
                    continue;
                List<String> row = parseCsvLine(line);
                String id = (idCol >= 0) ? getCell(row, idCol) : getCell(row, 0);
                String email = (emailCol >= 0) ? getCell(row, emailCol) : "";
                if (!id.isBlank()) {
                    map.put(id.trim(), email.isBlank() ? "" : email.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load student emails: " + e.getMessage());
        }
        return map;
    }

    private Map<String, String> loadStudentProgrammeMap() {
        Map<String, String> map = new HashMap<>();
        try (BufferedReader br = openResourceCsv(STUDENTS_CSV)) {
            String headerLine = br.readLine(); // header
            if (headerLine == null)
                return map;
            List<String> header = parseCsvLine(headerLine);
            int idCol = findCol(header, "studentid", "student_id", "id");
            int progCol = findCol(header, "programme", "program", "program_name", "course", "major");
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank())
                    continue;
                List<String> row = parseCsvLine(line);
                String id = (idCol >= 0) ? getCell(row, idCol) : getCell(row, 0);
                String prog = (progCol >= 0) ? getCell(row, progCol) : "";
                if (!id.isBlank()) {
                    map.put(id.trim(), prog.isBlank() ? "N/A" : prog.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load programme: " + e.getMessage());
        }
        return map;
    }

    private Map<String, CourseMeta> loadCourseInfo() {
        Map<String, CourseMeta> map = new HashMap<>();
        try (BufferedReader br = openResourceCsv(COURSES_CSV)) {
            List<String> header = readCsvRow(br);
            if (header == null)
                return map;
            int courseIdCol = findCol(header, "courseid", "course_id", "course", "code", "course_code");
            int creditsCol = findCol(header, "credit", "credits", "credit_hours", "credithours");
            int titleCol = findCol(header, "title", "course_title", "name", "course_name");
            List<String> row;
            while ((row = readCsvRow(br)) != null) {
                String cid = (courseIdCol >= 0) ? getCell(row, courseIdCol) : getCell(row, 0);
                if (cid.isBlank())
                    continue;
                double cr = (creditsCol >= 0) ? parseDoubleSafe(getCell(row, creditsCol), 3.0) : 3.0;
                String title = (titleCol >= 0) ? getCell(row, titleCol) : "";
                map.put(cid, new CourseMeta(title, cr));
            }
        } catch (IOException e) {
            System.err.println("Failed to load course info: " + e.getMessage());
        }
        return map;
    }

    private List<ResultRow> loadResultsForStudent(String studentId) {
        List<ResultRow> list = new ArrayList<>();
        try (BufferedReader br = openResourceCsv(RESULTS_CSV)) {
            List<String> header = readCsvRow(br);
            if (header == null)
                return list;
            int sidCol = findCol(header, "studentid", "student_id", "id");
            int courseCol = findCol(header, "courseid", "course_id", "course", "code");
            int yearCol = findCol(header, "academicyear", "academic_year", "year");
            int termCol = findCol(header, "term", "semester");
            int gradeCol = findCol(header, "gradeletter", "grade_letter", "grade");
            int gpCol = findCol(header, "gradepoint", "grade_point", "gp");
            int marksCol = findCol(header, "marks", "mark", "score", "finalmark", "final_mark");
            if (sidCol < 0 || courseCol < 0)
                return list;
            List<String> row;
            while ((row = readCsvRow(br)) != null) {
                String sid = getCell(row, sidCol);
                if (!studentId.equalsIgnoreCase(sid))
                    continue;
                String cid = getCell(row, courseCol);
                String year = (yearCol >= 0) ? getCell(row, yearCol) : "";
                String term = (termCol >= 0) ? getCell(row, termCol) : "";
                String grd = (gradeCol >= 0) ? getCell(row, gradeCol) : "";
                double gp = (gpCol >= 0) ? parseDoubleSafe(getCell(row, gpCol), -1) : -1;
                Double marks = null;
                if (marksCol >= 0) {
                    String m = getCell(row, marksCol);
                    if (!m.isBlank())
                        marks = parseDoubleSafe(m, -1);
                    if (marks != null && marks < 0)
                        marks = null;
                }
                if (!cid.isBlank())
                    list.add(new ResultRow(sid, cid, year, term, grd, gp));
            }
        } catch (IOException e) {
            System.err.println("Failed to read " + RESULTS_CSV + ": " + e.getMessage());
        }
        return list;
    }

    private void writeTxt(String studentId, String content) {
        try {
            Path dir = Paths.get(OUTPUT_DIR);
            if (!Files.exists(dir))
                Files.createDirectories(dir);
            Path out = dir.resolve("report_" + studentId + ".txt");
            Files.write(out, content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private BufferedReader openResourceCsv(String fileName) throws IOException {
        Path dataDir = Paths.get("data");
        if (!Files.exists(dataDir)) {
            Files.createDirectories(dataDir);
        }
        Path dataPath = dataDir.resolve(fileName);
        if (!Files.exists(dataPath)) {
            try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName)) {
                if (is != null) {
                    Files.copy(is, dataPath);
                    System.out.println("Initialized " + dataPath);
                } else {
                    System.err.println("Warning: " + fileName + " not found in resources to initialize.");
                }
            }
        }
        if (Files.exists(dataPath)) {
            return Files.newBufferedReader(dataPath, StandardCharsets.UTF_8);
        }
        throw new IOException("Missing CSV: " + fileName + " (Checked " + dataPath.toAbsolutePath() + ")");
    }

    private static List<String> readCsvRow(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty())
                continue;
            return parseCsvLine(line);
        }
        return null;
    }

    private static List<String> parseCsvLine(String line) {
        List<String> out = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (ch == '"') {
                inQuotes = !inQuotes;
                continue;
            }
            if (ch == ',' && !inQuotes) {
                out.add(cur.toString().trim());
                cur.setLength(0);
            } else {
                cur.append(ch);
            }
        }
        out.add(cur.toString().trim());
        return out;
    }

    private static int findCol(List<String> header, String... names) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < header.size(); i++)
            map.put(norm(header.get(i)), i);
        for (String n : names) {
            Integer idx = map.get(norm(n));
            if (idx != null)
                return idx;
        }
        for (int i = 0; i < header.size(); i++) {
            String h = norm(header.get(i));
            for (String n : names) {
                if (!n.isBlank() && h.contains(norm(n)))
                    return i;
            }
        }
        return -1;
    }

    private static String norm(String s) {
        return (s == null) ? "" : s.trim().toLowerCase(Locale.ROOT).replace(" ", "_");
    }

    private static String getCell(List<String> row, int idx) {
        if (row == null || idx < 0 || idx >= row.size())
            return "";
        String v = row.get(idx);
        return (v == null) ? "" : v.trim();
    }

    private static double parseDoubleSafe(String s, double def) {
        try {
            if (s == null)
                return def;
            s = s.trim();
            if (s.isEmpty())
                return def;
            return Double.parseDouble(s);
        } catch (Exception e) {
            return def;
        }
    }

    private static boolean isFailGrade(String grade) {
        if (grade == null)
            return false;
        String g = grade.trim().toUpperCase(Locale.ROOT);
        return g.equals("F") || g.equals("FAIL");
    }

    private static double gradeToPoint(String grade) {
        if (grade == null)
            return -1;
        String g = grade.trim().toUpperCase(Locale.ROOT);
        switch (g) {
            case "A":
                return 4.00;
            case "A-":
                return 3.67;
            case "B+":
                return 3.33;
            case "B":
                return 3.00;
            case "B-":
                return 2.67;
            case "C+":
                return 2.33;
            case "C":
                return 2.00;
            case "C-":
                return 1.67;
            case "D+":
                return 1.33;
            case "D":
                return 1.00;
            case "F":
                return 0.00;
            default:
                return -1;
        }
    }

    public boolean doesStudentExist(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            return false;
        }
        Map<String, StudentInfo> students = loadStudentInfo();
        return students.containsKey(studentId.trim());
    }

    private static class ResultRow {
        final String studentId;
        final String courseId;
        final String academicYear;
        final String term;
        final String grade;
        final double gradePoint;

        ResultRow(String studentId, String courseId, String academicYear, String term, String grade,
                double gradePoint) {
            this.studentId = studentId;
            this.courseId = courseId;
            this.academicYear = academicYear;
            this.term = term;
            this.grade = grade;
            this.gradePoint = gradePoint;
        }
    }

    public static void main(String[] args) {
        ReportService rs = new ReportService();
        System.out.println("--- Full Report ---");
        System.out.println(rs.generateReport("S001"));
        System.out.println("\n--- Filtered Report (2024, Spring) ---");
        System.out.println(rs.generateReport("S001", "2024", "Spring"));
        System.out.println("\n--- Filtered Report (2024, Fall) ---");
        System.out.println(rs.generateReport("S001", "2024", "Fall"));
    }
}
