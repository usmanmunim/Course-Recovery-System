package crs.services;

import crs.models.User;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

public class UserService {
    private static final String USERS_FILE = "users.csv";

    private Path getUsersDataPath() throws IOException {
        Path dir = Paths.get("data");
        Files.createDirectories(dir);
        return dir.resolve(USERS_FILE);
    }

    private void ensureUsersFileExists(Path dataPath) throws IOException {
        if (Files.exists(dataPath))
            return;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(USERS_FILE)) {
            if (is == null) {
                throw new FileNotFoundException("Cannot find " + USERS_FILE + " in src/main/resources");
            }
            Files.copy(is, dataPath);
        }
    }

    public User authenticate(String username, String password) throws IOException {
        username = (username == null) ? "" : username.trim();
        password = (password == null) ? "" : password.trim();
        if (username.isEmpty() || password.isEmpty())
            return null;
        Path dataPath = getUsersDataPath();
        ensureUsersFileExists(dataPath);
        List<String> lines = Files.readAllLines(dataPath, StandardCharsets.UTF_8);
        for (String line : lines) {
            if (line == null)
                continue;
            line = line.trim();
            if (line.isEmpty())
                continue;
            if (line.toLowerCase().startsWith("id,username,password,role,active"))
                continue;
            String[] p = line.split(",", -1);
            if (p.length < 6)
                continue;
            String u = p[1].trim();
            String pass = p[2].trim();
            String role = p[3].trim().isEmpty() ? "ACADEMIC_OFFICER" : p[3].trim();
            boolean active = Boolean.parseBoolean(p[4].trim());
            String email = p.length > 5 ? p[5].trim() : "";
            if (u.equalsIgnoreCase(username) && pass.equals(password)) {
                return new User(u, role, active, email);
            }
        }
        return null;
    }

    public boolean isUsernameTaken(String username) throws IOException {
        Path dataPath = getUsersDataPath();
        ensureUsersFileExists(dataPath);
        return Files.lines(dataPath, StandardCharsets.UTF_8)
                .skip(1)
                .map(line -> line.split(",", -1))
                .filter(p -> p.length >= 2)
                .anyMatch(p -> p[1].trim().equalsIgnoreCase(username));
    }

    public boolean updatePassword(String username, String newPassword) throws IOException {
        Path dataPath = getUsersDataPath();
        ensureUsersFileExists(dataPath);
        List<String> lines = Files.readAllLines(dataPath, StandardCharsets.UTF_8);
        List<String> out = new java.util.ArrayList<>();
        boolean updated = false;
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty() || trimmed.toLowerCase().startsWith("id,username")) {
                out.add(line);
                continue;
            }
            String[] p = trimmed.split(",", -1);
            if (p.length >= 3 && p[1].trim().equalsIgnoreCase(username)) {
                p[2] = newPassword;
                out.add(String.join(",", p));
                updated = true;
            } else {
                out.add(line);
            }
        }
        if (updated) {
            Files.write(dataPath, out, StandardCharsets.UTF_8);
        }
        return updated;
    }

    public boolean userExists(String username) throws IOException {
        return isUsernameTaken(username);
    }

    public String getUserEmail(String username) throws IOException {
        Path dataPath = getUsersDataPath();
        ensureUsersFileExists(dataPath);
        List<String> lines = Files.readAllLines(dataPath, StandardCharsets.UTF_8);
        for (String line : lines) {
            if (line == null || line.trim().isEmpty())
                continue;
            if (line.toLowerCase().startsWith("id,username"))
                continue;
            String[] p = line.split(",", -1);
            if (p.length < 6)
                continue;
            String u = p[1].trim();
            if (u.equalsIgnoreCase(username)) {
                return p[5].trim();
            }
        }
        return null;
    }

    public synchronized String generateNextUserId(String role) throws IOException {
        int maxId = 0;
        String prefix;
        String normalizedRole = role.trim().toUpperCase().replace(" ", "_");
        if ("COURSE_ADMINISTRATOR".equals(normalizedRole) || "COURSE ADMINISTRATOR".equalsIgnoreCase(role))
            prefix = "CA-";
        else if ("ACADEMIC_OFFICER".equals(normalizedRole) || "ACADEMIC OFFICER".equalsIgnoreCase(role))
            prefix = "AO-";
        else
            throw new IllegalArgumentException(
                    "Invalid role: " + role + ". Must be 'Academic Officer' or 'Course Administrator'");
        Path dataPath = getUsersDataPath();
        ensureUsersFileExists(dataPath);
        List<String> lines = Files.readAllLines(dataPath, StandardCharsets.UTF_8);
        for (String line : lines) {
            if (line == null || line.trim().isEmpty())
                continue;
            String[] p = line.split(",", -1);
            if (p.length < 1)
                continue;
            String id = p[0].trim();
            if (id.startsWith(prefix)) {
                try {
                    int val = Integer.parseInt(id.substring(3));
                    if (val > maxId)
                        maxId = val;
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return String.format("%s%04d", prefix, maxId + 1);
    }

    public void registerUser(String username, String password, String role, String email) throws IOException {
        String newId = generateNextUserId(role);
        Path dataPath = getUsersDataPath();
        try (BufferedWriter bw = Files.newBufferedWriter(dataPath, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            if (Files.size(dataPath) > 0) {
            }
            String line = String.join(",",
                    newId,
                    username,
                    password,
                    role.toUpperCase(),
                    "true",
                    email);
            bw.newLine();
            bw.write(line);
        }
    }
}
