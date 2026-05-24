package crs.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class CsvUtil {
    public static List<Map<String, String>> readCsv(String fileName) {
        List<Map<String, String>> rows = new ArrayList<>();
        try (BufferedReader br = openCsv(fileName)) {
            String headerLine = br.readLine();
            if (headerLine == null)
                return rows;
            String[] headers = splitCsv(headerLine);
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;
                String[] parts = splitCsv(line);
                Map<String, String> map = new HashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    String key = headers[i].trim();
                    String val = (i < parts.length) ? parts[i].trim() : "";
                    map.put(key, val);
                }
                rows.add(map);
            }
        } catch (IOException e) {
            System.out.println("csv not found: " + fileName +
                    " (running from: " + Paths.get(".").toAbsolutePath() + ")");
        }
        return rows;
    }

    public static void appendLine(String fileName, String header, String line) {
        try {
            Path dir = Paths.get("data");
            Files.createDirectories(dir);
            Path p = dir.resolve(fileName);
            boolean writeHeader = !Files.exists(p) || Files.size(p) == 0;
            try (BufferedWriter bw = Files.newBufferedWriter(
                    p,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND)) {
                if (writeHeader) {
                    bw.write(header);
                    bw.newLine();
                }
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write CSV: " + fileName, e);
        }
    }

    private static BufferedReader openCsv(String fileName) throws IOException {
        Path dataDir = Paths.get("data");
        if (!Files.exists(dataDir)) {
            Files.createDirectories(dataDir);
        }
        Path dataPath = dataDir.resolve(fileName);
        if (!Files.exists(dataPath)) {
            try (InputStream is = CsvUtil.class.getClassLoader().getResourceAsStream(fileName)) {
                if (is != null) {
                    Files.copy(is, dataPath);
                    System.out.println("Migrated " + fileName + " to data/" + fileName);
                }
            } catch (Exception e) {
                System.err.println("Failed to migrate " + fileName + ": " + e.getMessage());
            }
        }
        if (Files.exists(dataPath)) {
            return Files.newBufferedReader(dataPath, StandardCharsets.UTF_8);
        }
        InputStream is = CsvUtil.class.getClassLoader().getResourceAsStream(fileName);
        if (is != null) {
            return new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        }
        throw new IOException("CSV not found: " + fileName);
    }

    private static String[] splitCsv(String line) {
        List<String> out = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;
        for (char ch : line.toCharArray()) {
            if (ch == '"') {
                inQuotes = !inQuotes;
            } else if (ch == ',' && !inQuotes) {
                out.add(cur.toString());
                cur.setLength(0);
            } else {
                cur.append(ch);
            }
        }
        out.add(cur.toString());
        return out.toArray(new String[0]);
    }
}
