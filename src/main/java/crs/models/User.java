package crs.models;

public class User {
    private final String username;
    private final String role;
    private final boolean active;
    private final String email;

    public User(String username, String role, boolean active, String email) {
        this.username = username;
        this.role = role;
        this.active = active;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public boolean isActive() {
        return active;
    }

    public String getEmail() {
        return email;
    }
}
