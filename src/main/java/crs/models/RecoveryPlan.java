package crs.models;

import java.io.*;
import java.util.ArrayList;

public class RecoveryPlan {
    public Student student;
    public Course course;
    public String recommendation;
    public ArrayList<RecoveryMilestone> milestones = new ArrayList<>();
    public String planId;
    public String studentId;
    public String courseId;
    public int attemptNo;
    public String status;
    public String grade = "";
    private static final String FILE_NAME = "recovery_plans.txt";

    public RecoveryPlan(Student student, Course course, String recommendation) {
        this.student = student;
        this.course = course;
        this.recommendation = recommendation;
    }

    public RecoveryPlan(String planId, String studentId, String courseId, int attemptNo, String status,
            String recommendation, String grade) {
        this.planId = planId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.attemptNo = attemptNo;
        this.status = status;
        this.recommendation = (recommendation == null) ? "" : recommendation;
        this.grade = (grade == null) ? "" : grade;
    }

    public void addMilestone(RecoveryMilestone m) {
        milestones.add(m);
    }

    public String toCSV() {
        return planId + "," + studentId + "," + courseId + "," + attemptNo + "," + status + "," + recommendation + ","
                + grade;
    }

    @Override
    public String toString() {
        if (student == null && course == null) {
            return "Recovery Plan [" + planId + "]\n" +
                    "Student ID: " + studentId + "\n" +
                    "Course ID: " + courseId + "\n" +
                    "Attempt: " + attemptNo + "\n" +
                    "Status: " + status;
        }
        String output = "Recovery Plan\n" +
                "Student: " + student + "\n" +
                "Course: " + course + "\n" +
                "Recommendation: " + recommendation + "\n" +
                "Milestones:\n";
        for (RecoveryMilestone m : milestones) {
            output += "  - " + m.toString() + "\n";
        }
        return output;
    }

    public static ArrayList<RecoveryPlan> loadPlans() {
        ArrayList<RecoveryPlan> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length >= 5) {
                    String rec = (p.length > 5) ? p[5] : "";
                    String grd = (p.length > 6) ? p[6] : "";
                    RecoveryPlan recovery = new RecoveryPlan(
                            p[0],
                            p[1],
                            p[2],
                            Integer.parseInt(p[3]),
                            p[4],
                            rec,
                            grd);
                    list.add(recovery);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
        return list;
    }

    public static void savePlans(ArrayList<RecoveryPlan> list) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (RecoveryPlan r : list) {
                pw.println(r.toCSV());
            }
        } catch (Exception e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public static String generatePlanID() {
        ArrayList<RecoveryPlan> list = loadPlans();
        int max = 0;
        for (RecoveryPlan r : list) {
            try {
                if (r.planId != null && r.planId.startsWith("RP")) {
                    int num = Integer.parseInt(r.planId.substring(2));
                    if (num > max) {
                        max = num;
                    }
                }
            } catch (Exception e) {
            }
        }
        int next = max + 1;
        return String.format("RP%03d", next);
    }

    public static void addPlan(RecoveryPlan r) {
        ArrayList<RecoveryPlan> list = loadPlans();
        for (RecoveryPlan rec : list) {
            if (rec.planId != null && rec.planId.equalsIgnoreCase(r.planId)) {
                System.out.println("Duplicate PlanID " + r.planId + " ignored.");
                return;
            }
        }
        list.add(r);
        savePlans(list);
    }

    public static RecoveryPlan findByPlanID(String planId) {
        for (RecoveryPlan r : loadPlans()) {
            if (r.planId != null && r.planId.equalsIgnoreCase(planId)) {
                return r;
            }
        }
        return null;
    }

    public static ArrayList<RecoveryPlan> findByStudent(String studentId) {
        ArrayList<RecoveryPlan> result = new ArrayList<>();
        for (RecoveryPlan r : loadPlans()) {
            if (r.studentId != null && r.studentId.equalsIgnoreCase(studentId)) {
                result.add(r);
            }
        }
        return result;
    }

    public static void deletePlan(String planId) {
        ArrayList<RecoveryPlan> list = loadPlans();
        ArrayList<RecoveryPlan> newList = new ArrayList<>();
        for (RecoveryPlan r : list) {
            if (r.planId != null && !r.planId.equalsIgnoreCase(planId)) {
                newList.add(r);
            }
        }
        savePlans(newList);
    }
}
