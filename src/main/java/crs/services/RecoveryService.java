package crs.services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class RecoveryService {
    private static final Path DATA_FILE = Paths.get("data", "recovery_plans.txt");
    private static final String RESOURCE_FILE = "recovery_plans.txt";

    public List<String> loadAllLines() {
        try {
            ensureDataFileExists();
            if (Files.exists(DATA_FILE)) {
                List<String> lines = Files.readAllLines(DATA_FILE, StandardCharsets.UTF_8);
                if (lines.isEmpty()) {
                    List<String> resourceLines = readResourceLinesOrEmpty();
                    if (!resourceLines.isEmpty()) {
                        Files.write(DATA_FILE, resourceLines, StandardCharsets.UTF_8,
                                StandardOpenOption.TRUNCATE_EXISTING);
                        return resourceLines;
                    }
                }
                return lines;
            }
        } catch (Exception e) {
            System.out.println("RecoveryService load error: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public void appendLine(String line) {
        if (line == null)
            return;
        line = line.trim();
        if (line.isEmpty())
            return;
        try {
            ensureDataFileExists();
            Files.writeString(
                    DATA_FILE,
                    line + System.lineSeparator(),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (Exception e) {
            System.out.println("RecoveryService save error: " + e.getMessage());
        }
    }

    public void overwriteAllLines(List<String> lines) {
        try {
            ensureDataFileExists();
            if (lines == null)
                lines = new ArrayList<>();
            Files.write(DATA_FILE, lines, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            System.out.println("RecoveryService overwrite error: " + e.getMessage());
        }
    }

    public String getStoragePath() {
        return DATA_FILE.toAbsolutePath().toString();
    }

    private void ensureDataFileExists() throws Exception {
        Files.createDirectories(DATA_FILE.getParent());
        if (!Files.exists(DATA_FILE)) {
            Files.createFile(DATA_FILE);
        }
    }

    private List<String> readResourceLinesOrEmpty() {
        List<String> out = new ArrayList<>();
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(RESOURCE_FILE);
            if (is == null)
                return out;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty())
                        out.add(line);
                }
            }
        } catch (Exception ignored) {
        }
        return out;
    }
}
