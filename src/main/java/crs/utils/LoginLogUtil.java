package crs.utils;

import java.io.*;

public class LoginLogUtil {
    private static final String LOG_FILE = "login_log.bin";

    public static void logLogin(String username) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(LOG_FILE, true))) {
            dos.writeUTF(username);
            dos.writeLong(System.currentTimeMillis());
            dos.writeLong(0L);
        } catch (IOException e) {
            System.out.println("error writing login_log.bin: " + e.getMessage());
        }
    }
}
