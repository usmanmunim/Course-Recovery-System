/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package crs.gui;

import crs.models.User;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import crs.services.ReportService;
import crs.services.EmailService;

public class ReportFrame extends javax.swing.JFrame {

        private java.nio.file.Path generatedPdfPath;
        private User currentUser; // ✅ ADD THIS

        private static final java.util.logging.Logger logger = java.util.logging.Logger
                        .getLogger(ReportFrame.class.getName());

        private static final java.awt.Color HEADER = new java.awt.Color(18, 45, 110);
        private static final java.awt.Color PRIMARY = new java.awt.Color(28, 66, 150);
        private static final java.awt.Color PRIMARY_DARK = new java.awt.Color(20, 50, 120);
        private final ReportService reportService = new ReportService();
        private final EmailService emailService = new EmailService();

        public ReportFrame() {
                initComponents();
                setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
                btnViewPdf.setVisible(false);
                applyStyles();
                loadStudentsIntoCombo();
        }

        public ReportFrame(User user) {
                this.currentUser = user; // store session
                initComponents();
                setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
                btnViewPdf.setVisible(false);
                applyStyles();
                loadStudentsIntoCombo();
        }

        private void applyStyles() {
                // Example: frame settings
                setTitle("Course Recovery System | Reports");
                setLocationRelativeTo(null); // center
                getContentPane().setBackground(java.awt.Color.WHITE);
        }

        private static final String STUDENTS_CSV = "student_information.csv";

        private void loadStudentsIntoCombo() {
                try {
                        cmbStudents.removeAllItems();
                        cmbStudents.addItem("Select a student...");

                        for (String item : reportService.getStudentDisplayList()) {
                                cmbStudents.addItem(item);
                        }
                } catch (Exception ex) {
                        logger.log(java.util.logging.Level.SEVERE, "Failed to load students", ex);
                        JOptionPane.showMessageDialog(this, "Failed to load students: " + ex.getMessage());
                }
        }

        // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jPanel1 = new javax.swing.JPanel();
                jLabel2 = new javax.swing.JLabel();
                jLabel9 = new javax.swing.JLabel();
                btnBack = new javax.swing.JButton();
                jPanel2 = new javax.swing.JPanel();
                lblStudentId = new javax.swing.JLabel();
                txtStudentId = new javax.swing.JTextField();
                btnLoadStudent = new javax.swing.JButton();
                lblStudentNameValue = new javax.swing.JLabel();
                lblProgrammeTitle = new javax.swing.JLabel();
                lblProgrammeValue = new javax.swing.JLabel();
                jLabel5 = new javax.swing.JLabel();
                lblCgpaTitle = new javax.swing.JLabel();
                btnGenerateTxt = new javax.swing.JButton();
                btnExportPdf = new javax.swing.JButton();
                scrollTable = new javax.swing.JScrollPane();
                tblReport = new javax.swing.JTable();
                cmbStudents = new javax.swing.JComboBox<>();
                btnViewPdf = new javax.swing.JButton();
                lblEmail = new javax.swing.JLabel();
                txtEmail = new javax.swing.JTextField();
                btnEmailReport = new javax.swing.JButton();

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                setTitle("Course Recovery System - Academic Reports");

                jPanel1.setBackground(new java.awt.Color(18, 45, 109));

                jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 28));
                jLabel2.setForeground(new java.awt.Color(255, 255, 255));
                jLabel2.setText("Academic Reports");

                jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 12));
                jLabel9.setForeground(new java.awt.Color(200, 200, 200));
                jLabel9.setText("Generate and export student academic performance reports");

                btnBack.setBackground(new java.awt.Color(204, 0, 0));
                btnBack.setFont(new java.awt.Font("Segoe UI", 1, 14));
                btnBack.setForeground(new java.awt.Color(255, 255, 255));
                btnBack.setText("← RETURN");
                btnBack.setBorderPainted(false);
                btnBack.setFocusPainted(false);
                btnBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                btnBack.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnBackActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(20, 20, 20)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jLabel2)
                                                                                .addComponent(jLabel9))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(btnBack)
                                                                .addGap(20, 20, 20)));
                jPanel1Layout.setVerticalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(15, 15, 15)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.CENTER)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel2)
                                                                                                .addGap(2, 2, 2)
                                                                                                .addComponent(jLabel9))
                                                                                .addComponent(btnBack))
                                                                .addGap(15, 15, 15)));

                jPanel2.setBackground(new java.awt.Color(255, 255, 255));
                jPanel2.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)),
                                javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20)));

                lblStudentId.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
                lblStudentId.setText("Student ID:");

                txtStudentId.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtStudentIdActionPerformed(evt);
                        }
                });

                btnLoadStudent.setBackground(new java.awt.Color(18, 45, 110));
                btnLoadStudent.setFont(new java.awt.Font("Segoe UI", 1, 14));
                btnLoadStudent.setForeground(new java.awt.Color(255, 255, 255));
                btnLoadStudent.setText("Load");
                btnLoadStudent.setBorderPainted(false);
                btnLoadStudent.setFocusPainted(false);
                btnLoadStudent.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                btnLoadStudent.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnLoadStudentActionPerformed(evt);
                        }
                });

                lblStudentNameValue.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
                lblStudentNameValue.setForeground(new java.awt.Color(0, 0, 51));

                lblProgrammeTitle.setBackground(new java.awt.Color(0, 0, 51));
                lblProgrammeTitle.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
                lblProgrammeTitle.setForeground(new java.awt.Color(0, 0, 51));
                lblProgrammeTitle.setText("Programme:");

                lblProgrammeValue.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                lblProgrammeValue.setForeground(new java.awt.Color(0, 0, 51));

                jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
                jLabel5.setForeground(new java.awt.Color(0, 0, 51));
                jLabel5.setText("CGPA:");

                lblCgpaTitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                lblCgpaTitle.setForeground(new java.awt.Color(0, 0, 51));

                btnGenerateTxt.setBackground(new java.awt.Color(46, 125, 50));
                btnGenerateTxt.setFont(new java.awt.Font("Segoe UI", 1, 14));
                btnGenerateTxt.setForeground(new java.awt.Color(255, 255, 255));
                btnGenerateTxt.setText("Generate TXT");
                btnGenerateTxt.setBorderPainted(false);
                btnGenerateTxt.setFocusPainted(false);
                btnGenerateTxt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                btnGenerateTxt.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnGenerateTxtActionPerformed(evt);
                        }
                });

                // <Year / Semester UI>
                lblYear = new javax.swing.JLabel();
                lblYear.setFont(new java.awt.Font("Segoe UI", 0, 14));
                lblYear.setText("Year:");

                cmbYear = new javax.swing.JComboBox<>();
                cmbYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All" }));
                cmbYear.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                filterData();
                        }
                });

                lblSemester = new javax.swing.JLabel();
                lblSemester.setFont(new java.awt.Font("Segoe UI", 0, 14));
                lblSemester.setText("Sem:");

                cmbSemester = new javax.swing.JComboBox<>();
                cmbSemester.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All" }));
                cmbSemester.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                filterData();
                        }
                });
                // </Year / Semester UI>

                btnExportPdf.setBackground(new java.awt.Color(18, 45, 110));
                btnExportPdf.setFont(new java.awt.Font("Segoe UI", 1, 14));
                btnExportPdf.setForeground(new java.awt.Color(255, 255, 255));
                btnExportPdf.setText("Export PDF");
                btnExportPdf.setBorderPainted(false);
                btnExportPdf.setFocusPainted(false);
                btnExportPdf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                btnExportPdf.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnExportPdfActionPerformed(evt);
                        }
                });

                scrollTable.setBackground(new java.awt.Color(255, 255, 255));
                scrollTable.setForeground(new java.awt.Color(255, 255, 255));

                tblReport.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                tblReport.setForeground(new java.awt.Color(18, 45, 109));
                tblReport.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {
                                                { null, null, null },
                                                { null, null, null },
                                                { null, null, null },
                                                { null, null, null }
                                },
                                new String[] {
                                                "Course ID", "Grade", "Credits", "Term", "Year"
                                }) { // Added Term/Year columns for clarity, optional but helpful
                        Class[] types = new Class[] {
                                        java.lang.String.class, java.lang.String.class, java.lang.Double.class,
                                        java.lang.String.class, java.lang.String.class
                        };
                        boolean[] canEdit = new boolean[] {
                                        false, false, false, false, false
                        };

                        public Class getColumnClass(int columnIndex) {
                                return types[columnIndex];
                        }

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit[columnIndex];
                        }
                });
                tblReport.setGridColor(new java.awt.Color(18, 45, 109));
                scrollTable.setViewportView(tblReport);

                cmbStudents.setBackground(new java.awt.Color(18, 45, 109));
                cmbStudents.setForeground(new java.awt.Color(255, 255, 255));
                cmbStudents.setModel(
                                new javax.swing.DefaultComboBoxModel<>(
                                                new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
                cmbStudents.addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                cmbStudentsItemStateChanged(evt);
                        }
                });
                cmbStudents.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                cmbStudentsActionPerformed(evt);
                        }
                });

                btnViewPdf.setBackground(new java.awt.Color(18, 45, 110));
                btnViewPdf.setFont(new java.awt.Font("Segoe UI", 1, 14));
                btnViewPdf.setForeground(new java.awt.Color(255, 255, 255));
                btnViewPdf.setText("View PDF");
                btnViewPdf.setBorderPainted(false);
                btnViewPdf.setFocusPainted(false);
                btnViewPdf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                btnViewPdf.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnViewPdfActionPerformed(evt);
                        }
                });

                lblEmail.setText("Recipient Email:");

                btnEmailReport.setBackground(new java.awt.Color(28, 66, 150));
                btnEmailReport.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                btnEmailReport.setForeground(new java.awt.Color(255, 255, 255));
                btnEmailReport.setText("Email Report");
                btnEmailReport.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnEmailReportActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel2Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(lblProgrammeTitle,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                118,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(lblProgrammeValue,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                138,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(jPanel2Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(lblStudentId,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                99,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(txtStudentId,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                75,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(cmbStudents,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                161,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addComponent(btnLoadStudent,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                75,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGroup(jPanel2Layout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                false)
                                                                                                .addComponent(btnViewPdf,
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(btnExportPdf,
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(btnGenerateTxt,
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(lblEmail,
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(txtEmail,
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(btnEmailReport,
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addGroup(jPanel2Layout
                                                                                                                .createSequentialGroup()
                                                                                                                .addComponent(jLabel5,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                118,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addPreferredGap(
                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                .addComponent(lblCgpaTitle,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                138,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                .addComponent(lblStudentNameValue,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                256,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                /* Add Year Select Group */
                                                                                .addGroup(jPanel2Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(lblYear)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(cmbYear,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                80,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(18, 18, 18)
                                                                                                .addComponent(lblSemester)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(cmbSemester,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                80,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                18,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(scrollTable,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                475,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));
                jPanel2Layout.setVerticalGroup(
                                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(scrollTable,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                440,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(25, Short.MAX_VALUE))
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addGap(31, 31, 31)
                                                                .addGroup(jPanel2Layout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                false)
                                                                                .addGroup(jPanel2Layout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(txtStudentId,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                29,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(cmbStudents))
                                                                                .addComponent(lblStudentId,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                29,
                                                                                                Short.MAX_VALUE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnLoadStudent,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                31,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(27, 27, 27)
                                                                .addComponent(lblStudentNameValue,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                37,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel2Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(45, 45, 45)
                                                                                                .addGroup(jPanel2Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                .addComponent(jLabel5,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                33,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(lblCgpaTitle,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                33,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                .addGroup(jPanel2Layout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(lblProgrammeTitle,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                33,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(lblProgrammeValue,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                33,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                /* Year Selection Row */
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(lblYear)
                                                                                .addComponent(cmbYear,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(lblSemester)
                                                                                .addComponent(cmbSemester,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(btnGenerateTxt,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                47,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnExportPdf,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                47,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)

                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnViewPdf,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                47,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(lblEmail)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(txtEmail,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnEmailReport,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                47,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(20, Short.MAX_VALUE)));

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addGap(50, 50, 50)
                                                                .addComponent(jPanel2,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addGap(50, 50, 50)));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jPanel1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(30, 30, 30)
                                                                .addComponent(jPanel2,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addGap(30, 30, 30)));
                pack();
        }// </editor-fold>//GEN-END:initComponents

        private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnBackActionPerformed
                if (currentUser == null) {
                        new MainMenuFrame().setVisible(true); // fallback
                } else {
                        new MainMenuFrame(currentUser).setVisible(true); // ✅ return with session
                }
                this.dispose();
        }// GEN-LAST:event_btnBackActionPerformed

        private void txtStudentIdActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtStudentIdActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_txtStudentIdActionPerformed

        private void btnLoadStudentActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnLoadStudentActionPerformed

                String studentId = txtStudentId.getText().trim();

                if (studentId.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please enter a Student ID.");
                        return;
                }

                if (!reportService.doesStudentExist(studentId)) {
                        JOptionPane.showMessageDialog(this, "Student with ID " + studentId + " not found.",
                                        "No Result Found", JOptionPane.WARNING_MESSAGE);
                        clearStudentData();
                        return;
                }

                try {
                        // Labels
                        lblStudentNameValue.setText(reportService.getStudentName(studentId));
                        lblProgrammeValue.setText(reportService.getStudentProgramme(studentId));
                        lblCgpaTitle.setText(String.format("%.2f", reportService.calculateGpa(studentId)));

                        loadFilters(studentId);
                        filterData(); // Loads table with "All"

                        // Auto-fill email with actual student email from CSV
                        String studentEmail = reportService.getStudentEmail(studentId);
                        txtEmail.setText(studentEmail.isEmpty() ? studentId + "@crs.edu" : studentEmail);

                } catch (Exception ex) {
                        logger.log(java.util.logging.Level.SEVERE, "Load failed", ex);
                        JOptionPane.showMessageDialog(this, "Error loading: " + ex.getMessage());
                }
        }// GEN-LAST:event_btnLoadStudentActionPerformed

        private void btnGenerateTxtActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnGenerateTxtActionPerformed
                String studentId = txtStudentId.getText().trim();

                if (studentId.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Enter Student ID first.");
                        return;
                }

                if (!reportService.doesStudentExist(studentId)) {
                        JOptionPane.showMessageDialog(this, "Student with ID " + studentId + " not found.",
                                        "No Result Found", JOptionPane.WARNING_MESSAGE);
                        return;
                }

                // Get filters
                Object yObj = cmbYear.getSelectedItem();
                Object tObj = cmbSemester.getSelectedItem();
                String year = (yObj == null) ? "All" : yObj.toString();
                String term = (tObj == null) ? "All" : tObj.toString();

                try {
                        java.nio.file.Path out = reportService.exportToTXT(studentId, year, term);

                        if (out != null) {
                                JOptionPane.showMessageDialog(this, "Report saved to: " + out.toAbsolutePath());
                                try {
                                        java.awt.Desktop.getDesktop().open(out.toFile());
                                } catch (Exception e) {
                                        // ignore if desktop not supported
                                }
                        } else {
                                JOptionPane.showMessageDialog(this, "No data found to export.");
                        }
                } catch (Exception ex) {
                        logger.log(java.util.logging.Level.SEVERE, "TXT Export failed", ex);
                        JOptionPane.showMessageDialog(this, "Error exporting TXT: " + ex.getMessage());
                }
        }// GEN-LAST:event_btnGenerateTxtActionPerformed

        private void btnEmailReportActionPerformed(java.awt.event.ActionEvent evt) {
                String studentId = txtStudentId.getText().trim();
                String email = txtEmail.getText().trim();

                if (studentId.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please load a student first.");
                        return;
                }

                if (!reportService.doesStudentExist(studentId)) {
                        JOptionPane.showMessageDialog(this, "Student with ID " + studentId + " not found.",
                                        "No Result Found", JOptionPane.WARNING_MESSAGE);
                        return;
                }

                if (email.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please enter an email address.");
                        return;
                }

                // Get filters
                Object yObj = cmbYear.getSelectedItem();
                Object tObj = cmbSemester.getSelectedItem();
                String year = (yObj == null) ? "All" : yObj.toString();
                String term = (tObj == null) ? "All" : tObj.toString();

                try {
                        String reportContent = reportService.generateReport(studentId, year, term);
                        if (reportContent.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "No report data to send.");
                                return;
                        }

                        emailService.sendReportEmail(email, reportContent);
                        JOptionPane.showMessageDialog(this, "Email sent successfully to " + email);

                } catch (Exception ex) {
                        logger.log(java.util.logging.Level.SEVERE, "Email failed", ex);
                        JOptionPane.showMessageDialog(this, "Error sending email: " + ex.getMessage());
                }
        }

        private void btnExportPdfActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnExportPdfActionPerformed
                String studentId = txtStudentId.getText().trim();

                if (studentId.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Enter Student ID first.");
                        return;
                }

                if (!reportService.doesStudentExist(studentId)) {
                        JOptionPane.showMessageDialog(this, "Student with ID " + studentId + " not found.",
                                        "No Result Found", JOptionPane.WARNING_MESSAGE);
                        return;
                }

                // Get filters
                Object yObj = cmbYear.getSelectedItem();
                Object tObj = cmbSemester.getSelectedItem();
                String year = (yObj == null) ? "All" : yObj.toString();
                String term = (tObj == null) ? "All" : tObj.toString();

                try {
                        // export filtered
                        java.nio.file.Path out = reportService.exportToPDF(studentId, year, term);

                        if (out != null) {
                                generatedPdfPath = out;
                                btnViewPdf.setVisible(true);

                                JOptionPane.showMessageDialog(this,
                                                "PDF exported successfully!\n" + out.toString());
                        } else {
                                JOptionPane.showMessageDialog(this, "PDF export failed.");
                        }

                } catch (Exception ex) {
                        logger.log(java.util.logging.Level.SEVERE, "PDF export failed", ex);
                        JOptionPane.showMessageDialog(this, "Failed to export PDF: " + ex.getMessage());
                }
        }// GEN-LAST:event_btnExportPdfActionPerformed

        private void cmbStudentsItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_cmbStudentsItemStateChanged
                if (evt.getStateChange() != java.awt.event.ItemEvent.SELECTED)
                        return;

                Object obj = cmbStudents.getSelectedItem();
                if (obj == null)
                        return;

                String selected = obj.toString().trim();
                if (selected.startsWith("Select"))
                        return;

                String[] parts = selected.split(" - ", 2);
                if (parts.length < 1)
                        return;

                String studentId = parts[0].trim();
                txtStudentId.setText(studentId);
        }// GEN-LAST:event_cmbStudentsItemStateChanged

        private void cmbStudentsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cmbStudentsActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_cmbStudentsActionPerformed

        private void btnViewPdfActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnViewPdfActionPerformed
                openPdf(generatedPdfPath);
        }// GEN-LAST:event_btnViewPdfActionPerformed

        private void openPdf(java.nio.file.Path pdfPath) {
                try {
                        if (pdfPath == null) {
                                JOptionPane.showMessageDialog(this, "No PDF available to view.");
                                return;
                        }

                        java.io.File file = pdfPath.toFile();
                        if (!file.exists()) {
                                JOptionPane.showMessageDialog(this, "PDF file not found:\n" + file.getAbsolutePath());
                                return;
                        }

                        // Try normal Desktop open
                        if (java.awt.Desktop.isDesktopSupported()) {
                                java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
                                if (desktop.isSupported(java.awt.Desktop.Action.OPEN)) {
                                        desktop.open(file);
                                        return;
                                }
                        }

                        // Windows fallback: start "" "<file>"
                        new ProcessBuilder("cmd", "/c", "start", "", file.getAbsolutePath())
                                        .start();

                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this,
                                        "Unable to open PDF: " + e.getMessage() +
                                                        "\n\nFix: Set a default PDF app in Windows (Settings → Apps → Default apps → .pdf)");
                }
        }

        // New helper methods
        private boolean isUpdating = false;

        private void filterData() {
                if (isUpdating)
                        return;
                String studentId = txtStudentId.getText().trim();
                if (studentId.isEmpty())
                        return;

                Object yObj = cmbYear.getSelectedItem();
                Object tObj = cmbSemester.getSelectedItem();

                String year = (yObj == null) ? "All" : yObj.toString();
                String term = (tObj == null) ? "All" : tObj.toString();

                loadTable(studentId, year, term);
        }

        private void loadFilters(String studentId) {
                isUpdating = true;
                try {
                        cmbYear.removeAllItems();
                        cmbSemester.removeAllItems();

                        cmbYear.addItem("All");
                        cmbSemester.addItem("All");

                        // Load Years
                        List<String> years = reportService.getAvailableAcademicYears(studentId);
                        for (String y : years)
                                cmbYear.addItem(y);

                        // Load Terms (All initially)
                        List<String> terms = reportService.getAvailableTerms(studentId, "");
                        for (String t : terms)
                                cmbSemester.addItem(t);

                        cmbYear.setSelectedIndex(0);
                        cmbSemester.setSelectedIndex(0);

                } finally {
                        isUpdating = false;
                }
        }

        private void loadTable(String studentId, String year, String term) {
                try {
                        DefaultTableModel model = (DefaultTableModel) tblReport.getModel();
                        model.setRowCount(0);

                        List<ReportService.ReportRow> rows = reportService.getReportRows(studentId, year, term);

                        for (ReportService.ReportRow r : rows) {
                                model.addRow(new Object[] {
                                                r.courseId,
                                                (r.grade == null || r.grade.isBlank()) ? "-" : r.grade,
                                                r.credits,
                                                r.term == null ? "" : r.term,
                                                r.year == null ? "" : r.year
                                });
                        }

                        if (rows.isEmpty()) {
                                model.addRow(new Object[] { "No results", "-", "-", "-", -1 });
                        }

                } catch (Exception ex) {
                        logger.log(java.util.logging.Level.SEVERE, "Table load failed", ex);
                }
        }

        /**
         * @param args the command line arguments
         */
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
                        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
                                        .getInstalledLookAndFeels()) {
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
                java.awt.EventQueue.invokeLater(() -> {
                        JOptionPane.showMessageDialog(null,
                                        "ReportFrame should be opened from Main Menu only");
                });
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton btnBack;
        private javax.swing.JButton btnEmailReport;
        private javax.swing.JButton btnExportPdf;
        private javax.swing.JButton btnGenerateTxt;
        private javax.swing.JButton btnLoadStudent;
        private javax.swing.JButton btnViewPdf;
        private javax.swing.JComboBox<String> cmbSemester;
        private javax.swing.JComboBox<String> cmbStudents;
        private javax.swing.JComboBox<String> cmbYear;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JLabel jLabel9;
        private javax.swing.JLabel lblEmail;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JLabel lblCgpaTitle;
        private javax.swing.JLabel lblProgrammeTitle;
        private javax.swing.JLabel lblProgrammeValue;
        private javax.swing.JLabel lblSemester;
        private javax.swing.JLabel lblStudentId;
        private javax.swing.JLabel lblStudentNameValue;
        private javax.swing.JLabel lblYear;
        private javax.swing.JScrollPane scrollTable;
        private javax.swing.JTable tblReport;
        private javax.swing.JTextField txtStudentId;
        private javax.swing.JTextField txtEmail;

        // End of variables declaration//GEN-END:variables
        private void clearStudentData() {
                lblStudentNameValue.setText("Student Name");
                lblProgrammeValue.setText("Programme");
                lblCgpaTitle.setText("0.00");
                txtEmail.setText("");
                if (tblReport.getModel() instanceof javax.swing.table.DefaultTableModel) {
                        ((javax.swing.table.DefaultTableModel) tblReport.getModel()).setRowCount(0);
                }
        }
}
