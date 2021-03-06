package windows;

import javax.swing.JOptionPane;

public class StudentLogIn extends javax.swing.JFrame {

    private StudentDashboard stDashboard;
    private StartupForm startForm;

    /**
     * Creates new form StudentLogIn
     */
    public StudentLogIn() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        studentLogInLbl = new javax.swing.JLabel();
        studentIDLbl = new javax.swing.JLabel();
        passwordLbl = new javax.swing.JLabel();
        studentIDTxtFld = new javax.swing.JTextField();
        studentPassFld = new javax.swing.JPasswordField();
        cancelBttn = new javax.swing.JButton();
        logInBttn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Student Log-In");
        setIconImage(new javax.swing.ImageIcon("C:\\Users\\Gelnn Spencre\\Documents\\NetBeansProjects\\LibraryManagementSystem\\src\\resources\\studentDashboardIcon.png").getImage()
        );
        setUndecorated(true);

        studentLogInLbl.setFont(new java.awt.Font("Segoe UI Black", 0, 40)); // NOI18N
        studentLogInLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        studentLogInLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/studentLogo.png"))); // NOI18N
        studentLogInLbl.setText("  STUDENT LOG-IN");

        studentIDLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        studentIDLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        studentIDLbl.setText("Student ID:");

        passwordLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        passwordLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        passwordLbl.setText("Password:");

        studentIDTxtFld.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        studentIDTxtFld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentIDTxtFldActionPerformed(evt);
            }
        });

        studentPassFld.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        studentPassFld.setEchoChar((char)8226);
        studentPassFld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentPassFldActionPerformed(evt);
            }
        });

        cancelBttn.setBackground(java.awt.Color.red);
        cancelBttn.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        cancelBttn.setForeground(new java.awt.Color(255, 255, 255));
        cancelBttn.setText("Cancel");
        cancelBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBttnActionPerformed(evt);
            }
        });

        logInBttn.setBackground(new java.awt.Color(0, 153, 255));
        logInBttn.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        logInBttn.setForeground(java.awt.Color.white);
        logInBttn.setText("Log-In");
        logInBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logInBttnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(studentIDLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(studentIDTxtFld, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(passwordLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(studentPassFld))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(cancelBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(logInBttn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(studentLogInLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(studentLogInLbl)
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(studentIDLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(studentIDTxtFld, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(studentPassFld, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(logInBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56))
        );

        setSize(new java.awt.Dimension(650, 450));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cancelBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBttnActionPerformed
        this.startForm.getStudentBttn().setEnabled(true);
        studentIDTxtFld.setText("");
        studentPassFld.setText("");
        this.dispose();
    }//GEN-LAST:event_cancelBttnActionPerformed

    private void logInBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logInBttnActionPerformed
        javax.swing.SwingUtilities.invokeLater(() -> {
            stDashboard = new StudentDashboard();
            if (studentIDTxtFld.getText().isEmpty() || studentPassFld.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(null, "Please all fields", "Library Management System", JOptionPane.ERROR_MESSAGE);
                } else if (!stDashboard.verifyStudentID(studentIDTxtFld.getText())) {
                    JOptionPane.showMessageDialog(null, "The Student ID you entered does not exist", "Library Management System", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (stDashboard.verifyLogIn(studentIDTxtFld.getText(), studentPassFld.getPassword())) {
                        stDashboard.studentLogged(stDashboard.getPsdb().getStudent(studentIDTxtFld.getText()));
                        stDashboard.setStartForm(startForm);
                        stDashboard.setVisible(true);
                        JOptionPane.showMessageDialog(null, "Welcome", "Library Management System", JOptionPane.INFORMATION_MESSAGE);
                        studentIDTxtFld.setText("");
                        studentPassFld.setText("");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect Password", "Library Management System", JOptionPane.ERROR_MESSAGE);
                    }
                }
        });
    }//GEN-LAST:event_logInBttnActionPerformed

    private void studentPassFldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentPassFldActionPerformed
        logInBttnActionPerformed(evt);
    }//GEN-LAST:event_studentPassFldActionPerformed

    private void studentIDTxtFldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentIDTxtFldActionPerformed
        logInBttnActionPerformed(evt);
    }//GEN-LAST:event_studentIDTxtFldActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Metal".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(StudentLogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(StudentLogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(StudentLogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(StudentLogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new StudentLogIn().setVisible(true);
//            }
//        });
//    }

    protected void setStartForm(StartupForm startForm) {
        this.startForm = startForm;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBttn;
    private javax.swing.JButton logInBttn;
    private javax.swing.JLabel passwordLbl;
    private javax.swing.JLabel studentIDLbl;
    private javax.swing.JTextField studentIDTxtFld;
    private javax.swing.JLabel studentLogInLbl;
    private javax.swing.JPasswordField studentPassFld;
    // End of variables declaration//GEN-END:variables
}
