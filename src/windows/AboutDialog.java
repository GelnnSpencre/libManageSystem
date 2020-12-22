/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windows;

/**
 *
 * @author Gelnn Spencre
 */
public class AboutDialog extends javax.swing.JDialog {

    /**
     * Creates new form AboutDialog
     */
    public AboutDialog(javax.swing.JFrame parent, boolean modal) {
        super(parent, modal);
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

        aboutPanel = new javax.swing.JPanel();
        projectInLbl = new javax.swing.JLabel();
        profNameLbl = new javax.swing.JLabel();
        daphLbl = new javax.swing.JLabel();
        byLbl = new javax.swing.JLabel();
        projectTitleLbl = new javax.swing.JLabel();
        profLbl = new javax.swing.JLabel();
        dsaLbl = new javax.swing.JLabel();
        gabLbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("About");

        projectInLbl.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        projectInLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        projectInLbl.setText("PROJECT IN");

        profNameLbl.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        profNameLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        profNameLbl.setText("MR. EARL PASCO");

        daphLbl.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        daphLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        daphLbl.setText("ANGELES, DAPHNEY");

        byLbl.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        byLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        byLbl.setText("BY:");

        projectTitleLbl.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        projectTitleLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        projectTitleLbl.setText("LIBRARY MANAGEMENT SYSTEM V1.0");

        profLbl.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        profLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        profLbl.setText("PROFESSOR:");

        dsaLbl.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        dsaLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dsaLbl.setText("DATA STRUCTURES & ALGORITHMS");

        gabLbl.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        gabLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gabLbl.setText("EXCIJA, GABRIEL EMMANUEL");

        javax.swing.GroupLayout aboutPanelLayout = new javax.swing.GroupLayout(aboutPanel);
        aboutPanel.setLayout(aboutPanelLayout);
        aboutPanelLayout.setHorizontalGroup(
            aboutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aboutPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(aboutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(projectInLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dsaLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
                    .addComponent(byLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
                    .addComponent(gabLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
                    .addComponent(daphLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
                    .addComponent(profLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
                    .addComponent(profNameLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
                    .addComponent(projectTitleLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE))
                .addContainerGap())
        );
        aboutPanelLayout.setVerticalGroup(
            aboutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aboutPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(projectTitleLbl)
                .addGap(40, 40, 40)
                .addComponent(projectInLbl)
                .addGap(20, 20, 20)
                .addComponent(dsaLbl)
                .addGap(40, 40, 40)
                .addComponent(byLbl)
                .addGap(20, 20, 20)
                .addComponent(gabLbl)
                .addGap(20, 20, 20)
                .addComponent(daphLbl)
                .addGap(40, 40, 40)
                .addComponent(profLbl)
                .addGap(20, 20, 20)
                .addComponent(profNameLbl)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(aboutPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(26, 26, 26))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(aboutPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(800, 500));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
//            java.util.logging.Logger.getLogger(AboutDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(AboutDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(AboutDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(AboutDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel aboutPanel;
    private javax.swing.JLabel byLbl;
    private javax.swing.JLabel daphLbl;
    private javax.swing.JLabel dsaLbl;
    private javax.swing.JLabel gabLbl;
    private javax.swing.JLabel profLbl;
    private javax.swing.JLabel profNameLbl;
    private javax.swing.JLabel projectInLbl;
    private javax.swing.JLabel projectTitleLbl;
    // End of variables declaration//GEN-END:variables
}
