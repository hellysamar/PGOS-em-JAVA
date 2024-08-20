/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pgos.telas;

import br.com.pgos.dal.ModuloConexao;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
// Import de recursos da Biblioteca sqlitejdbc-v056.jar
import net.proteanit.sql.DbUtils;
/**
 *
 * @author Hellysamar
 */
public class TelaCliente extends javax.swing.JInternalFrame {

    // Variáveis de conexão com o banco;
    // CONECTOR
    Connection conexao = null;
    // PAPARAÇÃO
    PreparedStatement pst = null;
    // RESULTADO
    ResultSet rs = null;
    
    /**
     * Creates new form TelaCliente
     */
    public TelaCliente() {
        initComponents();
        conexao = ModuloConexao.conector();
        btnDeleteCliente.setEnabled(false);
    }
    
    // Métodos CRUD 
    // Método CREATE
    private void adicionar() {
        String sql = "INSERT INTO tblClientes (nomeCliente, enderecoCliente, foneCliente, emailCliente) VALUES (?, ?, ?, ?);";
        
        // IF para verificar campos obrigatórios
        if(/*Campo Obrigatório vazío*/ (txtNomeCliente.getText().isEmpty()) || (txtFoneCliente.getText().isEmpty())) {
            JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios!");
        } else {
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtNomeCliente.getText());
                pst.setString(2, txtEnderecoCliente.getText());
                pst.setString(3, txtFoneCliente.getText());
                pst.setString(4, txtEmailCliente.getText());

                int foiAdicionado = pst.executeUpdate();

                if (foiAdicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente " + txtNomeCliente.getText() + " adiconado com sucesso!");
                    
                    limparCampos();                    
                    buscar();
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Houve algum problema ao tentar adiconar o clinte " + txtNomeCliente.getText());
                }
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, e);
            }
        }
    }
    
    // Método READ
    private void buscar() {
        String sql = "SELECT idCliente AS ID, nomeCliente AS NOME, enderecoCliente AS ENDEREÇO, foneCliente AS TELEFONE, emailCliente AS EMAIL FROM tblClientes WHERE nomeCliente LIKE ?";
        
        try {
            pst = conexao.prepareStatement(sql);
            
            pst.setString(1, txtBuscaCliente.getText() + "%");
            
            rs = pst.executeQuery();
            
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
   
    // Método UPDATE
    private void alterar() {
        // Armazeno o Script numa variavel.
        String sql = "UPDATE tblClientes SET nomeCliente = ?, enderecoCliente = ?, foneCliente = ?, emailCliente = ? WHERE idCliente = ?;";
        
        // Verifico se há campos obrigatórios vazios
        if((txtNomeCliente.getText().isEmpty()) || (txtFoneCliente.getText().isEmpty())) {
            JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios!");
        } else {
            // TRY - Atualizo o Script com os dados inseridos para alteração no banco
            try {
                pst = conexao.prepareStatement(sql);
                
                pst.setString(1, txtNomeCliente.getText());
                pst.setString(2, txtEnderecoCliente.getText());
                pst.setString(3, txtFoneCliente.getText());
                pst.setString(4, txtEmailCliente.getText());
                pst.setString(5, txtIdCliente.getText());
                
                btnCreateCliente.setEnabled(true);
                btnDeleteCliente.setEnabled(false);
                
                // Executo o Update no banco com executeUpdate e armazena em um inteiro 1 se conseguir atualizar
                int atualizou = pst.executeUpdate();
                
                // Verifica se foi feito o Update, se SIM, apresenta mensagem de Sucesso e apaga os campos de Texto,
                if (atualizou > 0) {
                    JOptionPane.showMessageDialog(null, "Alteração realizada com sucesso!");
                
                    limparCampos();
                    
                    buscar();
                    
                // Caso o update tenha dado erro, apresenta mensagem no Else
                } else {
                    JOptionPane.showMessageDialog(null, "Houve algum problema ao tentar atualizar os dados do Cliente!");
                }
            // Apresento mensagem de erro CASO haja algum erro || Excessão
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    
    // Método DELETE
    private void deletar() {
        String mensagemDel = "Deseja realmente excluir o cliente " + txtNomeCliente.getText() + "?";        
        int confirma = JOptionPane.showConfirmDialog(null, mensagemDel, "Atenção", JOptionPane.YES_NO_OPTION);
        
        String sql = "DELETE FROM tblClientes WHERE idCliente = ?; ";
        
        if (confirma == JOptionPane.YES_NO_OPTION) {
            try {
                pst = conexao.prepareStatement(sql);

                pst.setString(1, txtIdCliente.getText());

                int excluido = pst.executeUpdate();

                if (excluido > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente " + txtNomeCliente.getText() + " excluido com sucesso!");

                    limparCampos();
                    
                    buscar();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Houve algo de errado na Exclusão do Cliente!");
            }
        }        
    }
    
    public void preencherCampos(){
        int linha = tblClientes.getSelectedRow();
        
        txtIdCliente.setText(tblClientes.getModel().getValueAt(linha, 0).toString());
        txtNomeCliente.setText(tblClientes.getModel().getValueAt(linha, 1).toString());
        txtEnderecoCliente.setText(tblClientes.getModel().getValueAt(linha, 2).toString());
        txtFoneCliente.setText(tblClientes.getModel().getValueAt(linha, 3).toString());
        txtEmailCliente.setText(tblClientes.getModel().getValueAt(linha, 4).toString());
        
        btnCreateCliente.setEnabled(false);
        btnDeleteCliente.setEnabled(true);
    }
    
    public void limparCampos() {
        txtNomeCliente.setText(null);
        txtIdCliente.setText(null);
        txtEnderecoCliente.setText(null);
        txtFoneCliente.setText(null);
        txtEmailCliente.setText(null);
        
        //buscar();
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
        txtNomeCliente = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtEnderecoCliente = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtFoneCliente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtEmailCliente = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        btnCreateCliente = new javax.swing.JButton();
        btnUpdateCliente = new javax.swing.JButton();
        btnDeleteCliente = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtBuscaCliente = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        txtIdCliente = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("PGOS - Gerenciamento de Clientes");
        setMaximumSize(new java.awt.Dimension(740, 587));
        setPreferredSize(new java.awt.Dimension(740, 587));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Nome*");

        txtNomeCliente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Endereço");

        txtEnderecoCliente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Telefone*");

        txtFoneCliente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("e-mail");

        txtEmailCliente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel5.setText("* Campos obrigatórios");

        btnCreateCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pgos/icons/createUser.png"))); // NOI18N
        btnCreateCliente.setToolTipText("Adicionar cliente");
        btnCreateCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCreateCliente.setPreferredSize(new java.awt.Dimension(90, 90));
        btnCreateCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateClienteActionPerformed(evt);
            }
        });

        btnUpdateCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pgos/icons/updateUser.png"))); // NOI18N
        btnUpdateCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdateCliente.setPreferredSize(new java.awt.Dimension(90, 90));
        btnUpdateCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateClienteActionPerformed(evt);
            }
        });

        btnDeleteCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pgos/icons/deleteUser.png"))); // NOI18N
        btnDeleteCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeleteCliente.setPreferredSize(new java.awt.Dimension(90, 90));
        btnDeleteCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteClienteActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Pesquisar Cliente");

        txtBuscaCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscaClienteKeyReleased(evt);
            }
        });

        tblClientes = new javax.swing.JTable() {
            public boolean  isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "NOME", "ENDEREÇO", "TELEFONE", "EMAIL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblClientes.setFocusable(false);
        tblClientes.getTableHeader().setReorderingAllowed(false);
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        txtIdCliente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtIdCliente.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(154, 154, 154)
                .addComponent(btnCreateCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addComponent(btnUpdateCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73)
                .addComponent(btnDeleteCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(152, 156, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(469, 469, 469))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(183, 183, 183))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addComponent(txtNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtFoneCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtEnderecoCliente, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtEmailCliente, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtBuscaCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGap(32, 32, 32)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtBuscaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFoneCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEnderecoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmailCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUpdateCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCreateCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        setBounds(0, 0, 740, 593);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCreateClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateClienteActionPerformed
        // TODO add your handling code here:
        adicionar();
    }//GEN-LAST:event_btnCreateClienteActionPerformed

    private void txtBuscaClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscaClienteKeyReleased
        // TODO add your handling code here:
        buscar();
        
        if (txtBuscaCliente.getText().isEmpty()) {
            ((DefaultTableModel) tblClientes.getModel()).setRowCount(0);
        }
    }//GEN-LAST:event_txtBuscaClienteKeyReleased

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // TODO add your handling code here:
        preencherCampos();
    }//GEN-LAST:event_tblClientesMouseClicked

    private void btnUpdateClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateClienteActionPerformed
        // TODO add your handling code here:
        alterar();
    }//GEN-LAST:event_btnUpdateClienteActionPerformed

    private void btnDeleteClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteClienteActionPerformed
        // TODO add your handling code here:
        deletar();
    }//GEN-LAST:event_btnDeleteClienteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreateCliente;
    private javax.swing.JButton btnDeleteCliente;
    private javax.swing.JButton btnUpdateCliente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtBuscaCliente;
    private javax.swing.JTextField txtEmailCliente;
    private javax.swing.JTextField txtEnderecoCliente;
    private javax.swing.JTextField txtFoneCliente;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtNomeCliente;
    // End of variables declaration//GEN-END:variables
}
