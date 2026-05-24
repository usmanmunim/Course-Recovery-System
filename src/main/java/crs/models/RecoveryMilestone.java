package crs.models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class RecoveryMilestone {
    public String planId;
    public String week;
    public String task;
    private static final String FILE = "milestones.txt";

    public RecoveryMilestone(String planId, String week, String task) {
        this.planId = planId;
        this.week = week;
        this.task = task;
    }

    public RecoveryMilestone(String week, String task) {
        this(null, week, task);
    }

    @Override
    public String toString() {
        return week + " : " + task;
    }

    public String toCSV() {
        return planId + "," + week + "," + task;
    }

    public static ArrayList<RecoveryMilestone> loadByPlan(String planId) {
        ArrayList<RecoveryMilestone> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length == 3 && p[0].equals(planId)) {
                    list.add(new RecoveryMilestone(p[0], p[1], p[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("No milestone file yet or read error: " + e.getMessage());
        }
        return list;
    }

    public static void addMilestone(RecoveryMilestone m) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE, true))) {
            pw.println(m.toCSV());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteMilestone(String planId, String week) {
        ArrayList<RecoveryMilestone> all = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length == 3) {
                    if (p[0].equals(planId) && p[1].equals(week)) {
                        continue;
                    }
                    all.add(new RecoveryMilestone(p[0], p[1], p[2]));
                }
            }
        } catch (IOException e) {
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            for (RecoveryMilestone m : all) {
                pw.println(m.toCSV());
            }
        } catch (IOException e) {
        }
    }
}
