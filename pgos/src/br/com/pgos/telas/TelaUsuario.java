/*
 * The MIT License
 *
 * Copyright 2024 Hellysamar.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.    
 */
package br.com.pgos.telas;

/**
 * 
 * @author Hellysamar
 */

import java.sql.*;
import br.com.pgos.dal.ModuloConexao;
import javax.swing.JOptionPane;

public class TelaUsuario extends javax.swing.JInternalFrame {
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    /**
     * Creates new form TelaUsuario
     */
    public TelaUsuario() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    /**
     * Método responsável por criar um usuário
     * CREATE do CRUD
     */
    private void adicionar() {
        String sql = "INSERT INTO tblUsuarios (idUser, usuario, fone, login, senha, perfil) VALUES (?, ?, ?, ?, ?, ?);";
        
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtIdUser.getText());
            pst.setString(2, txtNomeUser.getText());
            pst.setString(3, txtFoneUser.getText());
            pst.setString(4, txtLoginUser.getText());
            pst.setString(5, txtSenhaUser.getText());
            pst.setString(6, cmbPerfilUser.getSelectedItem().toString());
            
            if ((txtIdUser.getText().isEmpty()) || (txtNomeUser.getText().isEmpty()) || (txtLoginUser.getText().isEmpty()) || (txtSenhaUser.getText().isEmpty()) || (cmbPerfilUser.getSelectedItem().toString().isEmpty())){
                JOptionPane.showMessageDialog(null, "Preencha os campos *Obrigatórios!");
            } else {
                int userAdicionado = pst.executeUpdate();
            
                if (userAdicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário cadastrado com Sucesso!");

                    txtIdUser.setText(null);
                    txtNomeUser.setText(null);
                    txtFoneUser.setText(null);
                    txtLoginUser.setText(null);
                    txtSenhaUser.setText(null);
                    cmbPerfilUser.setSelectedItem(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }        
    }
    
    /**
     * Método responsável por buscar um usuário preenchendo seus dados apartir da tabela
     * READ do CRUD
     */
    private void consultar() {
        String sql = "SELECT * FROM tblUsuarios WHERE idUser = ?;";
        
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtIdUser.getText());
            rs = pst.executeQuery();
            
            if (rs.next()) {
                txtNomeUser.setText(rs.getString(2));
                txtFoneUser.setText(rs.getString(3));
                txtLoginUser.setText(rs.getString(4));
                txtSenhaUser.setText(rs.getString(5));
                
                cmbPerfilUser.setSelectedItem(rs.getString(6));
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não cadastrado!");
                
                txtNomeUser.setText(null);
                txtFoneUser.setText(null);
                txtLoginUser.setText(null);
                txtSenhaUser.setText(null);
                cmbPerfilUser.setSelectedItem(null);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /**
     * Método responsável por atualizar dados do Usuário
     * UPDATE do CRUD
     */
    private void alterar() {
        String sql = "UPDATE tblUsuarios SET usuario = ?, fone = ?, login = ?, senha = ?, perfil = ? WHERE idUser = ?;";
        
        try {
            pst = conexao.prepareStatement(sql);
            
            pst.setString(6, txtIdUser.getText());            
            pst.setString(1, txtNomeUser.getText());
            pst.setString(2, txtFoneUser.getText());
            pst.setString(3, txtLoginUser.getText());
            pst.setString(4, txtSenhaUser.getText());
            pst.setString(5, cmbPerfilUser.getSelectedItem().toString());
            
            if ((txtIdUser.getText().isEmpty()) || (txtNomeUser.getText().isEmpty()) || (txtLoginUser.getText().isEmpty()) || (txtSenhaUser.getText().isEmpty()) || (cmbPerfilUser.getSelectedItem().toString().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha os Campos Obrigatórios!");
            } else {
                int atualizado = pst.executeUpdate();

                if (atualizado > 0) {
                   JOptionPane.showMessageDialog(null, "Dados do usuário " + txtNomeUser.getText() + " alterados com sucesso!");
                } else {
                   JOptionPane.showMessageDialog(null, "Houve algum problema" + atualizado);
                }   
            }            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }        
    }
    
    /**
     * Método responsável por Excluir um Usuário do Sistema
     * DELETE do CRUD
     */
    private void remover() {
        String sql = "DELETE FROM tblUsuarios WHERE idUser = ?;";
        int confirma = JOptionPane.showConfirmDialog(null, "Confirma remoção do usuário " + txtNomeUser.getText() + "?", "Atenção!", JOptionPane.YES_NO_OPTION);
        
        if(confirma == JOptionPane.YES_OPTION){
            try {
                pst = conexao.prepareStatement(sql);
                
                pst.setString(1, txtIdUser.getText());
                
                int remocao = pst.executeUpdate();
                
                if (remocao > 0) {
                    txtNomeUser.setText(null);
                    txtFoneUser.setText(null);
                    txtLoginUser.setText(null);
                    txtSenhaUser.setText(null);
                    cmbPerfilUser.setSelectedItem(null);
                    
                    JOptionPane.showMessageDialog(null, "Usuário " + txtNomeUser.getText() + " removido com Sucesso!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnCreate = new javax.swing.JButton();
        btnRead = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        txtIdUser = new javax.swing.JTextField();
        txtNomeUser = new javax.swing.JTextField();
        txtFoneUser = new javax.swing.JTextField();
        txtLoginUser = new javax.swing.JTextField();
        txtSenhaUser = new javax.swing.JTextField();
        cmbPerfilUser = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("PGOS - Gerenciamento de Usuários");
        setMaximumSize(new java.awt.Dimension(740, 587));
        setPreferredSize(new java.awt.Dimension(740, 587));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("iD*");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Login*");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Perfil*");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Telefone");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Senha*");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Nome*");

        btnCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pgos/icons/createUser.png"))); // NOI18N
        btnCreate.setToolTipText("Adicionar");
        btnCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCreate.setPreferredSize(new java.awt.Dimension(90, 90));
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        btnRead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pgos/icons/readUser.png"))); // NOI18N
        btnRead.setToolTipText("Pesquisar");
        btnRead.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRead.setPreferredSize(new java.awt.Dimension(90, 90));
        btnRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReadActionPerformed(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pgos/icons/updateUser.png"))); // NOI18N
        btnUpdate.setToolTipText("Editar");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.setPreferredSize(new java.awt.Dimension(90, 90));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pgos/icons/deleteUser.png"))); // NOI18N
        btnDelete.setToolTipText("Deletar");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.setPreferredSize(new java.awt.Dimension(90, 90));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        txtIdUser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtIdUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdUserActionPerformed(evt);
            }
        });

        txtNomeUser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtFoneUser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtLoginUser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtSenhaUser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        cmbPerfilUser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbPerfilUser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "user", "admin" }));

        jLabel7.setText("* Campos obrigatórios.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtNomeUser)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtLoginUser, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel2)
                                                    .addComponent(jLabel6)
                                                    .addComponent(txtIdUser, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(64, 64, 64)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtSenhaUser)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel5)
                                                        .addGap(0, 0, Short.MAX_VALUE)))))
                                        .addGap(70, 70, 70))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(71, 71, 71)
                                        .addComponent(btnRead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(48, 48, 48)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtFoneUser, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(cmbPerfilUser, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(23, 23, 23)
                                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(74, 74, 74))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtIdUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNomeUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFoneUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLoginUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSenhaUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbPerfilUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70))
        );

        setBounds(0, 0, 740, 587);
    }// </editor-fold>//GEN-END:initComponents

    private void txtIdUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdUserActionPerformed

    private void btnReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReadActionPerformed
        consultar();
    }//GEN-LAST:event_btnReadActionPerformed

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        adicionar();
    }//GEN-LAST:event_btnCreateActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        alterar();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        remover();
    }//GEN-LAST:event_btnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRead;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbPerfilUser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField txtFoneUser;
    private javax.swing.JTextField txtIdUser;
    private javax.swing.JTextField txtLoginUser;
    private javax.swing.JTextField txtNomeUser;
    private javax.swing.JTextField txtSenhaUser;
    // End of variables declaration//GEN-END:variables
}
