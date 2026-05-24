package crs.services;

import crs.models.User;
import java.io.IOException;

public class AuthService {
    private final UserService userService = new UserService();

    public User login(String username, String password) {
        username = (username == null) ? "" : username.trim();
        password = (password == null) ? "" : password.trim();
        if (username.isEmpty() || password.isEmpty())
            return null;
        try {
            User u = userService.authenticate(username, password);
            if (u == null)
                return null;
            try {
                if (!u.isActive())
                    return null;
            } catch (Exception ignored) {
            }
            return u;
        } catch (IOException e) {
            System.out.println("AuthService login IO error: " + e.getMessage());
            return null;
        }
    }
}
