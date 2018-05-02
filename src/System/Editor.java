package System;

public class Editor extends javax.swing.JFrame {
	private static Editor singleton;

	private Editor() {
		initComponents();
	}
	
	public static void setTitleS(String title) {
		singleton.setTitle(title);
	}

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanel1 = new javax.swing.JPanel();
        canvas = new java.awt.Canvas();
        PropertiesModule = new javax.swing.JToolBar();
        PropertiesScroll = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Test");
        setMinimumSize(new java.awt.Dimension(640, 480));

        jLayeredPane1.setMinimumSize(new java.awt.Dimension(640, 480));
        jLayeredPane1.setPreferredSize(new java.awt.Dimension(640, 480));

        jPanel1.setEnabled(false);
        jPanel1.setFocusable(false);
        jPanel1.setMinimumSize(new java.awt.Dimension(640, 480));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(640, 480));
        jPanel1.setRequestFocusEnabled(false);
        jPanel1.setVerifyInputWhenFocusTarget(false);

        canvas.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(canvas, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(canvas, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
        );

        jPanel1.setBounds(0, 0, 640, 480);
        jLayeredPane1.add(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        PropertiesModule.setRollover(true);
        PropertiesModule.setMinimumSize(new java.awt.Dimension(200, 200));

        PropertiesScroll.setMinimumSize(new java.awt.Dimension(200, 200));

        jEditorPane1.setText("TESTESTESTESTESTESTESTESTESTESTES\nTESTESTESTESTESTESTESTESTESTESETE\nTESTESTESTESTESTESTESTESTESTESTES\nTESTESTESTESTESTESTESTESTESTESETE\nTESTESTESTESTESTESTESTESTESTESTES\nTESTESTESTESTESTESTESTESTESTESETE\nTESTESTESTESTESTESTESTESTESTESTES\nTESTESTESTESTESTESTESTESTESTESETE\nTESTESTESTESTESTESTESTESTESTESTES\nTESTESTESTESTESTESTESTESTESTESETE");
        PropertiesScroll.setViewportView(jEditorPane1);

        PropertiesModule.add(PropertiesScroll);

        PropertiesModule.setBounds(0, 0, 140, 130);
        jLayeredPane1.add(PropertiesModule, javax.swing.JLayeredPane.POPUP_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	public static void main(String args[]) {
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				singleton=new Editor();
				new Thread(Main.get(singleton.canvas), "MasterThread").start();
				singleton.setVisible(true);
			}
		});
	}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar PropertiesModule;
    private javax.swing.JScrollPane PropertiesScroll;
    private java.awt.Canvas canvas;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
