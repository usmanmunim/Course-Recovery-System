package crs.services;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class EmailService {
    private final String senderEmail = "war676654@gmail.com";
    private final String appPassword = "ckpjvddvrbsxakgd";

    private boolean sendEmail(String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "*");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, appPassword);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail, "CRS Notification System", "UTF-8"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            System.out.println("✅ Email sent to: " + to);
            return true;
        } catch (UnsupportedEncodingException e) {
            System.out.println("❌ Encoding error: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (MessagingException e) {
            System.out.println("❌ Email sending failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static void sendWelcomeEmail(String email, String username) {
        new EmailService().sendAccountCreatedEmail(email, username, "USER");
    }

    public void sendAccountCreatedEmail(String email, String username, String role) {
        String subject = "CRS Account Created";
        String body = "Hello " + username + ",\n\n" +
                "Your CRS account has been created successfully.\n" +
                "Role: " + role + "\n\n" +
                "Regards,\nCRS Notification System";
        sendEmail(email, subject, body);
    }

    public void sendRegistrationConfirmation(String email, String username, String role, String password) {
        String subject = "Welcome to CRS - Account Details";
        String body = "Hello " + username + ",\n\n" +
                "Your CRS account has been created successfully.\n\n" +
                "Here are your login details:\n" +
                "Username: " + username + "\n" +
                "Role: " + role + "\n" +
                "Password: " + password + "\n\n" +
                "Please keep this information credentials safe.\n\n" +
                "Regards,\nCRS Notification System";
        sendEmail(email, subject, body);
    }

    public static void sendPasswordReset(String email, String tempPassword) {
        new EmailService().sendPasswordResetEmail(email, "User", tempPassword);
    }

    public void sendPasswordResetEmail(String email, String username, String tempPassword) {
        String subject = "CRS Password Reset";
        String body = "Hello " + username + ",\n\n" +
                "Your password has been reset.\n" +
                "Temporary Password: " + tempPassword + "\n\n" +
                "Please change it after login.\n\n" +
                "Regards,\nCRS Notification System";
        sendEmail(email, subject, body);
    }

    public void sendEligibilityResultEmail(String email, String studentId, double cgpa, int failedCourses,
            boolean eligible) {
        String subject = "CRS Eligibility Result";
        String body = "Student ID: " + studentId + "\n" +
                "GPA: " + cgpa + "\n" +
                "Failed Courses: " + failedCourses + "\n" +
                "Eligibility: " + (eligible ? "Eligible" : "Not Eligible") + "\n\n" +
                "Regards,\nCRS Admin";
        sendEmail(email, subject, body);
    }

    public void sendReportEmail(String email, String reportContent) {
        String subject = "Your Academic Report";
        String body = "Please find below your requested academic report:\n\n" +
                reportContent + "\n\n" +
                "Regards,\nCRS Notification System";
        sendEmail(email, subject, body);
    }

    public static void sendRecoveryPlanAlert(String email, String planDetails) {
        new EmailService().sendRecoveryPlanEmail(email, "Student", planDetails);
    }

    public void sendRecoveryPlanEmail(String email, String studentId, String planSummary) {
        String subject = "CRS Recovery Plan Update";
        String body = "Student ID: " + studentId + "\n\n" +
                "Recovery Plan Details:\n" +
                planSummary + "\n\n" +
                "Regards,\nCRS Notification System";
        sendEmail(email, subject, body);
    }

    public static void sendReportReady(String email, String studentId) {
        new EmailService().sendReportEmail(email, studentId, "Academic Report");
    }

    public void sendReportEmail(String email, String studentId, String reportFile) {
        String subject = "CRS Academic Performance Report";
        String body = "Student ID: " + studentId + "\n" +
                "Report Generated: " + reportFile + "\n\n" +
                "Regards,\nCRS Notification System";
        sendEmail(email, subject, body);
    }

    public static String generateOTP() {
        // Generate 6-digit OTP
        return String.format("%06d", (int) (Math.random() * 1000000));
    }

    public static void sendOTP(String email, String otp) {
        sendOtp(email, otp);
    }

    public static void sendOtp(String email, String otp) {
        new EmailService().sendOtpEmail(email, otp);
    }

    public void sendOtpEmail(String email, String otp) {
        String subject = "CRS Registration OTP";
        String body = "Your verification code is: " + otp + "\n\n" +
                "This code will expire in 10 minutes.\n" +
                "If you did not request this, please ignore this email.\n\n" +
                "Regards,\nCRS Notification System";
        boolean success = sendEmail(email, subject, body);
        if (!success) {
            throw new RuntimeException("Failed to send OTP email. Please check internet or credentials.");
        }
    }
}