package crs.utils;

public class ValidationUtil {

    public static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static int safeInt(String s, int def) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return def; }
    }

    public static double safeDouble(String s, double def) {
        try { return Double.parseDouble(s.trim()); } catch (Exception e) { return def; }
    }
}
