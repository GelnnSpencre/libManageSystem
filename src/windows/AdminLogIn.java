package windows;

import javax.swing.JOptionPane;

public class AdminLogIn extends javax.swing.JFrame {

    private AdminDashboard adminDashboard;
    private StartupForm startForm;

    /**
     * Creates new form AdminLogIn
     */
    public AdminLogIn() {

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

        adminLogInLbl = new javax.swing.JLabel();
        adminIDTxtFld = new javax.swing.JTextField();
        cancelBttn = new javax.swing.JButton();
        passwordLbl = new javax.swing.JLabel();
        adminIDLbl = new javax.swing.JLabel();
        logInBttn = new javax.swing.JButton();
        adminPassFld = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Admin Log-In");
        setIconImage(new javax.swing.ImageIcon("C:\\Users\\Gelnn Spencre\\Documents\\NetBeansProjects\\LibraryManagementSystem\\src\\resources\\adminDashboardIcon.png").getImage()
        );
        setName("adminLogInFrame"); // NOI18N
        setUndecorated(true);

        adminLogInLbl.setFont(new java.awt.Font("Segoe UI Black", 0, 40)); // NOI18N
        adminLogInLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        adminLogInLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/adminLogo.png"))); // NOI18N
        adminLogInLbl.setText("  ADMIN LOG-IN");

        adminIDTxtFld.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        adminIDTxtFld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminIDTxtFldActionPerformed(evt);
            }
        });

        cancelBttn.setBackground(java.awt.Color.red);
        cancelBttn.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        cancelBttn.setForeground(new java.awt.Color(255, 255, 255));
        cancelBttn.setText("Cancel");
        cancelBttn.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cancelBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBttnActionPerformed(evt);
            }
        });

        passwordLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        passwordLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        passwordLbl.setText("Password:");

        adminIDLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        adminIDLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        adminIDLbl.setText("Admin ID:");

        logInBttn.setBackground(new java.awt.Color(0, 153, 255));
        logInBttn.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        logInBttn.setForeground(new java.awt.Color(255, 255, 255));
        logInBttn.setText("Log-In");
        logInBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logInBttnActionPerformed(evt);
            }
        });

        adminPassFld.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        adminPassFld.setEchoChar((char)8226);
        adminPassFld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminPassFldActionPerformed(evt);
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
                                .addComponent(adminIDLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(adminIDTxtFld, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(passwordLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(adminPassFld))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(cancelBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(logInBttn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(adminLogInLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(adminLogInLbl)
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(adminIDLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(adminIDTxtFld, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(adminPassFld, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        this.startForm.getAdminBttn().setEnabled(true);
        adminIDTxtFld.setText("");
        adminPassFld.setText("");
        this.dispose();
    }//GEN-LAST:event_cancelBttnActionPerformed

    private void logInBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logInBttnActionPerformed
        adminDashboard = new AdminDashboard();
        javax.swing.SwingUtilities.invokeLater(() -> {
            if (adminIDTxtFld.getText().isEmpty() || adminPassFld.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields", "Library Management System", JOptionPane.ERROR_MESSAGE);
                } else if (!adminDashboard.verifyAdminID(adminIDTxtFld.getText())) {
                    JOptionPane.showMessageDialog(null, "The Admin ID you entered does not exist", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (adminDashboard.getPsdb().getAdmin(adminIDTxtFld.getText()).isCurrentlyLogged()) {
                        JOptionPane.showMessageDialog(null, "This admin has is currently logged in", "Library Management System", JOptionPane.INFORMATION_MESSAGE);
                    } else if (adminDashboard.verifyLogIn(adminIDTxtFld.getText(), adminPassFld.getPassword())) {
                        javax.swing.SwingUtilities.invokeLater(() -> {
                            startForm.getAdminBttn().setEnabled(true);
                            adminDashboard.adminLogged(adminDashboard.getPsdb().getAdmin(adminIDTxtFld.getText()));
                            adminDashboard.setStartForm(startForm);
                            adminDashboard.setVisible(true);
                            JOptionPane.showMessageDialog(null, "Welcome", "Library Management System", JOptionPane.INFORMATION_MESSAGE);
                            adminIDTxtFld.setText("");
                            adminPassFld.setText("");
                        });
                        
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect Password", "Library Management System", JOptionPane.ERROR_MESSAGE);
                    }
                }
        });
    }//GEN-LAST:event_logInBttnActionPerformed

    private void adminPassFldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminPassFldActionPerformed
        logInBttnActionPerformed(evt);
    }//GEN-LAST:event_adminPassFldActionPerformed

    private void adminIDTxtFldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminIDTxtFldActionPerformed
        logInBttnActionPerformed(evt);
    }//GEN-LAST:event_adminIDTxtFldActionPerformed

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
//            java.util.logging.Logger.getLogger(AdminLogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(AdminLogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(AdminLogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(AdminLogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new AdminLogIn().setVisible(true);
//            }
//        });
//    }

    protected void setStartForm(StartupForm startForm) {
        this.startForm = startForm;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel adminIDLbl;
    private javax.swing.JTextField adminIDTxtFld;
    private javax.swing.JLabel adminLogInLbl;
    private javax.swing.JPasswordField adminPassFld;
    private javax.swing.JButton cancelBttn;
    private javax.swing.JButton logInBttn;
    private javax.swing.JLabel passwordLbl;
    // End of variables declaration//GEN-END:variables
}
