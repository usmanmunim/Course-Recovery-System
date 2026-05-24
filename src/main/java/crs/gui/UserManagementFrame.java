
package crs.gui;

import crs.models.User;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.io.*;

public class UserManagementFrame extends javax.swing.JFrame {

    private java.nio.file.Path getUsersDataPath() throws IOException {
        java.nio.file.Path dir = java.nio.file.Paths.get("data");
        java.nio.file.Files.createDirectories(dir);
        return dir.resolve(USERS_FILE); // data/users.csv
    }

    private DefaultTableModel model;
    private static final String USERS_FILE = "users.csv";

    private static class UserRow {
        String id, username, password, role, email;
        boolean active;

        UserRow(String id, String username, String password, String role, boolean active, String email) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.role = role;
            this.active = active;
            this.email = (email == null) ? "" : email;
        }
    }

    private java.util.List<UserRow> users = new java.util.ArrayList<>();

    private User currentUser; // logged in user session

    public UserManagementFrame(User user) {
        initComponents();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        this.currentUser = user;

        model = (DefaultTableModel) usersTable.getModel();
        usersTable.setDefaultEditor(Object.class, null);

        loadUsersFromCsv();
    }

    private static final java.util.logging.Logger logger = java.util.logging.Logger
            .getLogger(UserManagementFrame.class.getName());

    public UserManagementFrame() {
        initComponents();
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        model = (DefaultTableModel) usersTable.getModel();
        usersTable.setDefaultEditor(Object.class, null);

        loadUsersFromCsv();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        MenuTitle = new javax.swing.JLabel();
        lblSubtitle = new javax.swing.JLabel();
        btnReturn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        usersTable = new javax.swing.JTable();
        btnAddUser = new javax.swing.JButton();
        btnToggleActive = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnChangeUsername = new javax.swing.JButton();
        btnChangePassword = new javax.swing.JButton();
        btnChangeRole = new javax.swing.JButton();
        btnDeleteUser = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(18, 45, 110));

        MenuTitle.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        MenuTitle.setForeground(new java.awt.Color(255, 255, 255));
        MenuTitle.setText("User Management");

        lblSubtitle.setForeground(new java.awt.Color(200, 200, 200));
        lblSubtitle.setText("Manage system users, roles, and access permissions");

        btnReturn.setBackground(new java.awt.Color(204, 0, 0));
        btnReturn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnReturn.setForeground(new java.awt.Color(255, 255, 255));
        btnReturn.setText("RETURN");
        btnReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(MenuTitle)
                                        .addComponent(lblSubtitle))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnReturn)
                                .addGap(20, 20, 20)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(MenuTitle)
                                                .addGap(2, 2, 2)
                                                .addComponent(lblSubtitle))
                                        .addComponent(btnReturn))
                                .addGap(15, 15, 15)));

        usersTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null, null },
                        { null, null, null, null, null },
                        { null, null, null, null, null },
                        { null, null, null, null, null }
                },
                new String[] {
                        "ID", "Username", "Password", "Role", "Active"
                }));
        jScrollPane2.setViewportView(usersTable);

        btnAddUser.setBackground(new java.awt.Color(18, 45, 110));
        btnAddUser.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAddUser.setForeground(new java.awt.Color(255, 255, 255));
        btnAddUser.setText("+ Add New User");
        btnAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUserActionPerformed(evt);
            }
        });

        btnToggleActive.setBackground(new java.awt.Color(18, 45, 110));
        btnToggleActive.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnToggleActive.setForeground(new java.awt.Color(255, 255, 255));
        btnToggleActive.setText("Toggle Active Status");
        btnToggleActive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToggleActiveActionPerformed(evt);
            }
        });

        btnRefresh.setBackground(new java.awt.Color(46, 125, 50));
        btnRefresh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnChangeUsername.setBackground(new java.awt.Color(18, 45, 110));
        btnChangeUsername.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnChangeUsername.setForeground(new java.awt.Color(255, 255, 255));
        btnChangeUsername.setText("Change Username");
        btnChangeUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeUsernameActionPerformed(evt);
            }
        });

        btnChangePassword.setBackground(new java.awt.Color(18, 45, 110));
        btnChangePassword.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnChangePassword.setForeground(new java.awt.Color(255, 255, 255));
        btnChangePassword.setText("Change Password");
        btnChangePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangePasswordActionPerformed(evt);
            }
        });

        btnChangeRole.setBackground(new java.awt.Color(18, 45, 110));
        btnChangeRole.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnChangeRole.setForeground(new java.awt.Color(255, 255, 255));
        btnChangeRole.setText("Change Role");
        btnChangeRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeRoleActionPerformed(evt);
            }
        });

        btnDeleteUser.setBackground(new java.awt.Color(198, 40, 40));
        btnDeleteUser.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDeleteUser.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteUser.setText("Delete User");
        btnDeleteUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteUserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(btnAddUser, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnToggleActive, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        298, Short.MAX_VALUE))
                                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 298,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnChangeUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 298,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnChangePassword, javax.swing.GroupLayout.PREFERRED_SIZE, 298,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnChangeRole, javax.swing.GroupLayout.PREFERRED_SIZE, 298,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnDeleteUser, javax.swing.GroupLayout.PREFERRED_SIZE, 298,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 433,
                                                        Short.MAX_VALUE)
                                                .addContainerGap())
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(80, 80, 80)
                                                .addComponent(btnAddUser)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnToggleActive)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnChangeUsername)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnChangePassword)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnChangeRole)
                                                .addGap(52, 52, 52)
                                                .addComponent(btnDeleteUser)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnRefresh)
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        Short.MAX_VALUE)))));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void UpdateMailBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_UpdateMailBtnActionPerformed
        int row = usersTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user.");
            return;
        }
        String id = (String) model.getValueAt(row, 0);
        String newMail = JOptionPane.showInputDialog(this, "Enter new email address:");
        if (newMail != null && !newMail.trim().isEmpty()) {
            for (UserRow u : users) {
                if (u.id.equals(id)) {
                    u.email = newMail.trim();
                    break;
                }
            }
            saveUsersToCsv();
            refreshTable();
        }
    }// GEN-LAST:event_UpdateMailBtnActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnRefreshActionPerformed
        loadUsersFromCsv();
        refreshTable();
    }// GEN-LAST:event_btnRefreshActionPerformed

    private void btnToggleActiveActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnToggleActiveActionPerformed
        int row = usersTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user.");
            return;
        }

        // Find user by ID shown in table (col 0)
        String id = (String) model.getValueAt(row, 0);
        for (UserRow u : users) {
            if (u.id.equals(id)) {
                u.active = !u.active;
                break;
            }
        }
        saveUsersToCsv();
        refreshTable();
    }// GEN-LAST:event_btnToggleActiveActionPerformed

    private void btnAddUserActionPerformed(java.awt.event.ActionEvent evt) {
        // 1. Select Role first (needed for ID generation)
        String[] roles = { "Academic Officer", "Course Administrator" };
        String role = (String) JOptionPane.showInputDialog(this, "Select Role:", "Add New User",
                JOptionPane.QUESTION_MESSAGE, null, roles, roles[0]);
        if (role == null)
            return;

        // 2. Enter Username
        String user = JOptionPane.showInputDialog(this, "Enter Username:");
        if (user == null || user.trim().isEmpty())
            return;

        // Check if username exists (in memory list to avoid file read)
        for (UserRow u : users) {
            if (u.username.equalsIgnoreCase(user.trim())) {
                JOptionPane.showMessageDialog(this, "Username '" + user + "' is already taken.");
                return;
            }
        }

        // 3. Enter Password
        String pass = JOptionPane.showInputDialog(this, "Enter Password:");
        if (pass == null || pass.trim().isEmpty())
            return;

        // 4. Ask for Email (Gmail)
        String email = JOptionPane.showInputDialog(this, "Enter Gmail address (or leave empty to skip verification):");
        String verifiedEmail = "-"; // Default if not verified

        if (email != null && !email.trim().isEmpty()) {
            email = email.trim();

            // Validate email format
            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                JOptionPane.showMessageDialog(this, "Invalid email format. User will be created with email '-'.");
            } else {
                try {
                    // Generate and send OTP
                    String otp = crs.services.EmailService.generateOTP();
                    crs.services.EmailService.sendOTP(email, otp);

                    JOptionPane.showMessageDialog(this, "OTP sent to " + email);

                    // Ask user to enter OTP
                    String enteredOtp = JOptionPane.showInputDialog(this, "Enter the OTP sent to your email:");

                    if (enteredOtp != null && enteredOtp.trim().equals(otp)) {
                        verifiedEmail = email; // OTP verified
                        JOptionPane.showMessageDialog(this, "Email verified successfully!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid OTP. User will be created with email '-'.");
                    }
                } catch (Exception e) {
                    logger.log(java.util.logging.Level.WARNING, "Email verification failed", e);
                    JOptionPane.showMessageDialog(this,
                            "Email verification failed. User will be created with email '-'.");
                }
            }
        }

        // 5. Auto-Generate ID and Save
        try {
            crs.services.UserService us = new crs.services.UserService();
            String newId = us.generateNextUserId(role);

            // Add user with verified email or "-"
            users.add(new UserRow(newId, user.trim(), pass, role.toUpperCase().replace(" ", "_"), true, verifiedEmail));
            saveUsersToCsv();
            refreshTable();

            // Send welcome email if verified
            if (!verifiedEmail.equals("-")) {
                crs.services.EmailService.sendWelcomeEmail(verifiedEmail, user);
            }

            JOptionPane.showMessageDialog(this,
                    "User added successfully!\nAssigned ID: " + newId + "\nEmail: " + verifiedEmail);

        } catch (IOException e) {
            logger.log(java.util.logging.Level.SEVERE, "ID Generation failed", e);
            JOptionPane.showMessageDialog(this, "Error generating ID: " + e.getMessage());
        }
    }

    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnReturnActionPerformed
        if (currentUser == null) {
            new MainMenuFrame().setVisible(true);
        } else {
            new MainMenuFrame(currentUser).setVisible(true);
        }
        this.dispose();
    }// GEN-LAST:event_btnReturnActionPerformed

    private void btnChangeUsernameActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnChangeUsernameActionPerformed
        int row = usersTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user.");
            return;
        }
        String id = (String) model.getValueAt(row, 0);
        String newName = JOptionPane.showInputDialog(this, "Enter new username:");
        if (newName != null && !newName.trim().isEmpty()) {
            String trimmedName = newName.trim();
            // Check for duplicate username
            boolean exists = false;
            for (UserRow u : users) {
                if (!u.id.equals(id) && u.username.equalsIgnoreCase(trimmedName)) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                JOptionPane.showMessageDialog(this, "Username '" + trimmedName + "' is already taken.",
                        "Duplicate Username", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (UserRow u : users) {
                if (u.id.equals(id)) {
                    u.username = trimmedName;
                    break;
                }
            }
            saveUsersToCsv();
            refreshTable();
        }
    }// GEN-LAST:event_btnChangeUsernameActionPerformed

    private void btnChangePasswordActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnChangePasswordActionPerformed
        int row = usersTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user.");
            return;
        }
        String id = (String) model.getValueAt(row, 0);
        String newPass = JOptionPane.showInputDialog(this, "Enter new password:");
        if (newPass != null && !newPass.trim().isEmpty()) {
            for (UserRow u : users) {
                if (u.id.equals(id)) {
                    u.password = newPass.trim();
                    break;
                }
            }
            saveUsersToCsv();
            refreshTable();
        }
    }// GEN-LAST:event_btnChangePasswordActionPerformed

    private void btnChangeRoleActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnChangeRoleActionPerformed
        int row = usersTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user.");
            return;
        }
        String id = (String) model.getValueAt(row, 0);
        String[] roles = { "Academic Officer", "Course Administrator" };
        String newRole = (String) JOptionPane.showInputDialog(this, "Select Role:", "Role",
                JOptionPane.QUESTION_MESSAGE, null, roles, roles[0]);

        if (newRole != null) {
            for (UserRow u : users) {
                if (u.id.equals(id)) {
                    u.role = newRole.toUpperCase().replace(" ", "_");
                    break;
                }
            }
            saveUsersToCsv();
            refreshTable();
        }
    }// GEN-LAST:event_btnChangeRoleActionPerformed

    private void btnDeleteUserActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDeleteUserActionPerformed
        int row = usersTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user.");
            return;
        }
        String id = (String) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Delete user " + id + "?", "Confirm",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            users.removeIf(u -> u.id.equals(id));
            saveUsersToCsv();
            refreshTable();
        }
    }// GEN-LAST:event_btnDeleteUserActionPerformed

    private UserRow getSelectedUserOrWarn() {
        int row = usersTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a user row first.");
            return null;
        }
        String selectedId = String.valueOf(model.getValueAt(row, 0));
        for (UserRow u : users) {
            if (u.id.equals(selectedId))
                return u;
        }
        JOptionPane.showMessageDialog(this, "Selected user not found in list.");
        return null;
    }

    private boolean usernameExistsExcept(String username, String exceptId) {
        for (UserRow u : users) {
            if (!u.id.equals(exceptId) && u.username.equalsIgnoreCase(username))
                return true;
        }
        return false;
    }

    private boolean isValidRole(String role) {
        if (role == null)
            return false;
        role = role.trim().toUpperCase().replace(" ", "_");
        return role.equals("ACADEMIC_OFFICER") || role.equals("COURSE_ADMINISTRATOR");
    }

    private void loadUsersFromCsv() {
        users.clear();
        java.io.File file = new java.io.File("data/users.csv");

        // If data/users.csv does not exist, copy from resources
        if (!file.exists()) {
            try {
                java.nio.file.Files.createDirectories(java.nio.file.Paths.get("data"));
                try (java.io.InputStream is = getClass().getClassLoader().getResourceAsStream("users.csv")) {
                    if (is != null) {
                        java.nio.file.Files.copy(is, file.toPath());
                    }
                }
            } catch (Exception e) {
                logger.log(java.util.logging.Level.WARNING, "Could not copy users.csv from resources", e);
            }
        }

        if (!file.exists())
            return;

        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty())
                    continue;
                String[] p = line.split(",", -1);
                if (p.length >= 5) {
                    String email = (p.length >= 6) ? p[5].trim() : "";
                    users.add(new UserRow(
                            p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim().toUpperCase(),
                            Boolean.parseBoolean(p[4].trim()), email));
                }
            }
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, "Load error", ex);
        }
        refreshTable();
    }

    private void saveUsersToCsv() {
        try (java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.FileWriter("data/users.csv"))) {
            for (UserRow u : users) {
                pw.println(u.id + "," + u.username + "," + u.password + "," + u.role + "," + u.active + "," + u.email);
            }
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, "Save error", ex);
        }
    }

    private void refreshTable() {
        model.setRowCount(0);
        for (UserRow u : users) {
            model.addRow(new Object[] { u.id, u.username, u.password, u.role, u.active, u.email });
        }
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new UserManagementFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel MenuTitle;
    private javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnChangePassword;
    private javax.swing.JButton btnChangeRole;
    private javax.swing.JButton btnChangeUsername;
    private javax.swing.JButton btnDeleteUser;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnReturn;
    private javax.swing.JButton btnToggleActive;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblSubtitle;
    private javax.swing.JTable usersTable;
    // End of variables declaration//GEN-END:variables
}
