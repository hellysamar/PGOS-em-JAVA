/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pgos.telas;

import java.sql.*;
import br.com.pgos.dal.ModuloConexao;
import java.awt.Color;
import javax.swing.JOptionPane;
/**
 *
 * @author Hellysamar
 */
public class TelaLogin extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    public void logar() {
        String sql = "SELECT * FROM tblusuarios WHERE login = ? AND senha = ?";
        
        try {
            // É Preparado a consulta ao banco substituindo as ? pelos dados
            // inseridos nas caixas de texto, as ? (interrogações) são 
            // substituidas pelas variaveis.
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUserName.getText());
            // Captura caracter por caracter mantendo mais seguro a informação da senha
            String captura = new String(txtPassword.getPassword());
            pst.setString(2, captura);
            
            // É feita a execução da Query (Consulta)
            rs = pst.executeQuery();
            
            
            if (rs.next()) {
                // instancia a tela principal para chama-la
                TelaPrincipal principal = new TelaPrincipal();
                // permite acessao a tela principal chamando-a
                // acessa a tela principal e fecha a tela de Login
                principal.setVisible(true);
                // captura do dado do campo nr 6, que é o campo Perfil
                String perfil = rs.getString(6);            
                
                // Valida se o dado do perfil é Admin, sendo assim ele acessa o
                // bloco if e libera o acesso aos items de menu "cadastro de usuário" e "relatorio"
                if (perfil.equals("admin")){
                    TelaPrincipal.menuCadUsuario.setEnabled(true);
                    TelaPrincipal.menuRel.setEnabled(true);
                    TelaPrincipal.lblUseuarioPrincipal.setForeground(Color.red);
                }
                
                TelaPrincipal.lblUseuarioPrincipal.setText(rs.getString(2));
                
                this.dispose();
                conexao.close();
                
            } else {
                JOptionPane.showMessageDialog(null, "Usuário e(ou) Senha inválido(s)!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    /**
     * Creates new form TelaLogin
     */
    public TelaLogin() {
        initComponents();
        
        conexao = ModuloConexao.conector();
        //a linha abaixo serve de apoio para o Dev saber o estatus de conexão
        System.out.println(conexao);
        
        if (conexao != null) {
            lblStatusConn.setText("");
            lblStatusConn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pgos/icons/dbAccess.png")));
        } else {
            lblStatusConn.setText("");
            lblStatusConn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pgos/icons/dbDeined.png")));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        txtUserName = new javax.swing.JTextField();
        btnLogin = new javax.swing.JButton();
        lblStatusConn = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Programa Gerenciador de Ordem de Serviço");
        setResizable(false);

        jLabel1.setText("USUÁRIO");

        jLabel2.setText("SENHA");

        btnLogin.setText("Acessar");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        lblStatusConn.setText("STATUS");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(txtPassword)
                                .addComponent(txtUserName)
                                .addComponent(lblStatusConn))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 204, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addComponent(btnLogin)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(77, 77, 77))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel1)
                .addGap(7, 7, 7)
                .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel2)
                .addGap(3, 3, 3)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addComponent(lblStatusConn)
                .addGap(65, 65, 65))
        );

        setSize(new java.awt.Dimension(416, 356));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // chama o método logar
        logar();
    }//GEN-LAST:event_btnLoginActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblStatusConn;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}
