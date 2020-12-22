/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windows;

import commons.Admin;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class RegisterAdmin extends javax.swing.JFrame {
    
    private AdminDashboard adminDashboard;
    private StartupForm startForm;
    
    /**
     * Creates new form RegisterAdmin
     */
    public RegisterAdmin() {
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

        adminRegisterLbl = new javax.swing.JLabel();
        adminRegisterIDTxtFld = new javax.swing.JTextField();
        adminRegisterNameLbl = new javax.swing.JLabel();
        adminRegisterPasswordLbl = new javax.swing.JLabel();
        adminRegisterPasswordFld = new javax.swing.JPasswordField();
        adminRegisterBttn = new javax.swing.JButton();
        adminRegisterCancelBttn = new javax.swing.JButton();
        adminRegisterIDLbl1 = new javax.swing.JLabel();
        adminRegisterNameTxtFld = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon("C:\\Users\\Gelnn Spencre\\Documents\\NetBeansProjects\\LibraryManagementSystem\\src\\resources\\adminDashboardIcon.png").getImage());
        setUndecorated(true);

        adminRegisterLbl.setFont(new java.awt.Font("Segoe UI Black", 0, 40)); // NOI18N
        adminRegisterLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        adminRegisterLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/adminLogo.png"))); // NOI18N
        adminRegisterLbl.setText("  ADMIN REGISTER");

        adminRegisterIDTxtFld.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        adminRegisterIDTxtFld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminRegisterIDTxtFldActionPerformed(evt);
            }
        });

        adminRegisterNameLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        adminRegisterNameLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        adminRegisterNameLbl.setText("Admin Name:");

        adminRegisterPasswordLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        adminRegisterPasswordLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        adminRegisterPasswordLbl.setText("Admin Password:");

        adminRegisterPasswordFld.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        adminRegisterPasswordFld.setEchoChar((char)8226);
        adminRegisterPasswordFld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminRegisterPasswordFldActionPerformed(evt);
            }
        });

        adminRegisterBttn.setBackground(new java.awt.Color(0, 153, 255));
        adminRegisterBttn.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        adminRegisterBttn.setForeground(new java.awt.Color(255, 255, 255));
        adminRegisterBttn.setText("Register");
        adminRegisterBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminRegisterBttnActionPerformed(evt);
            }
        });

        adminRegisterCancelBttn.setBackground(java.awt.Color.red);
        adminRegisterCancelBttn.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        adminRegisterCancelBttn.setForeground(new java.awt.Color(255, 255, 255));
        adminRegisterCancelBttn.setText("Cancel");
        adminRegisterCancelBttn.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        adminRegisterCancelBttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminRegisterCancelBttnActionPerformed(evt);
            }
        });

        adminRegisterIDLbl1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        adminRegisterIDLbl1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        adminRegisterIDLbl1.setText("Admin ID:");

        adminRegisterNameTxtFld.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        adminRegisterNameTxtFld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminRegisterNameTxtFldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(adminRegisterLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(adminRegisterIDLbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(adminRegisterIDTxtFld, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(adminRegisterCancelBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(adminRegisterBttn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(adminRegisterNameLbl)
                                    .addComponent(adminRegisterPasswordLbl))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(adminRegisterPasswordFld, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(adminRegisterNameTxtFld, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(adminRegisterLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(adminRegisterIDTxtFld, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(adminRegisterIDLbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(adminRegisterNameTxtFld, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(adminRegisterNameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(adminRegisterPasswordFld, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(adminRegisterPasswordLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(adminRegisterBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(adminRegisterCancelBttn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(634, 411));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void adminRegisterBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminRegisterBttnActionPerformed
        adminDashboard = new AdminDashboard();
        if ((adminRegisterIDTxtFld.getText().isEmpty() || adminRegisterNameTxtFld.getText().isEmpty())
        || (adminRegisterPasswordFld.getPassword().length == 0)){
            JOptionPane.showMessageDialog(null, "Please fill all fields", "Library Management System", JOptionPane.ERROR_MESSAGE);
        } else if (adminRegisterPasswordFld.getPassword().length < 10) {
            JOptionPane.showMessageDialog(null, "Password length must be longer than (9) characters", "Library Management System", JOptionPane.ERROR_MESSAGE);
        } else {
            Admin newAdmin = new Admin(adminRegisterIDTxtFld.getText(),
                    adminRegisterNameTxtFld.getText().trim(),
                    adminRegisterPasswordFld.getPassword());
            adminDashboard.getPsdb().registerNewAdmin(newAdmin);
            new SwingWorker<Void, Void>(){
                @Override
                protected Void doInBackground() {
                    writePseudoDatabaseSer();
                    return null;
                }
                @Override
                protected void done() {
                    JOptionPane.showMessageDialog(null, "Successful Registration", "Library Management System", JOptionPane.INFORMATION_MESSAGE);
                    adminRegisterIDTxtFld.setText("");
                    adminRegisterNameTxtFld.setText("");
                    adminRegisterPasswordFld.setText("");
                                startForm.getAdminBttn().setEnabled(true);
                    dispose();
                }
            }.execute();
            
        }
            
    }//GEN-LAST:event_adminRegisterBttnActionPerformed

    private void adminRegisterCancelBttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminRegisterCancelBttnActionPerformed
        adminRegisterIDTxtFld.setText("");
        adminRegisterNameTxtFld.setText("");
        adminRegisterPasswordFld.setText("");
        startForm.getAdminBttn().setEnabled(true);
        dispose();
    }//GEN-LAST:event_adminRegisterCancelBttnActionPerformed

    private void adminRegisterPasswordFldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminRegisterPasswordFldActionPerformed
        adminRegisterBttnActionPerformed(evt);
    }//GEN-LAST:event_adminRegisterPasswordFldActionPerformed

    private void adminRegisterNameTxtFldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminRegisterNameTxtFldActionPerformed
        adminRegisterBttnActionPerformed(evt);
    }//GEN-LAST:event_adminRegisterNameTxtFldActionPerformed

    private void adminRegisterIDTxtFldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminRegisterIDTxtFldActionPerformed
        adminRegisterBttnActionPerformed(evt);
    }//GEN-LAST:event_adminRegisterIDTxtFldActionPerformed

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
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(RegisterAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(RegisterAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(RegisterAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(RegisterAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new RegisterAdmin().setVisible(true);
//            }
//        });
//    }
    
    private void writePseudoDatabaseSer() {
        try {
            FileOutputStream fileOut = new FileOutputStream("psdb.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(adminDashboard.getPsdb());
            objectOut.flush();
            objectOut.close();
            fileOut.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StudentDashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StudentDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void setStartForm(StartupForm startForm) {
        this.startForm = startForm;
    }
    
    protected commons.PseudoDatabase getPsdb() {
        return new AdminDashboard().getPsdb();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adminRegisterBttn;
    private javax.swing.JButton adminRegisterCancelBttn;
    private javax.swing.JLabel adminRegisterIDLbl1;
    private javax.swing.JTextField adminRegisterIDTxtFld;
    private javax.swing.JLabel adminRegisterLbl;
    private javax.swing.JLabel adminRegisterNameLbl;
    private javax.swing.JTextField adminRegisterNameTxtFld;
    private javax.swing.JPasswordField adminRegisterPasswordFld;
    private javax.swing.JLabel adminRegisterPasswordLbl;
    // End of variables declaration//GEN-END:variables
}