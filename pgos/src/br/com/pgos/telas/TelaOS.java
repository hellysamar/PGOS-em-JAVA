/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pgos.telas;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import br.com.pgos.dal.ModuloConexao;
import java.time.temporal.IsoFields;
import javax.swing.JOptionPane;

/**
 *
 * @author Hellysamar
 */
public class TelaOS extends javax.swing.JInternalFrame {
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    
            
    private String tipo;
    /**
     * Creates new form TelaOS
     */
    public TelaOS() {
        initComponents();
        
        conexao = ModuloConexao.conector();
        
        btnCreateOS.setEnabled(true);
        btnUpdateOS.setEnabled(false);
        btnDeleteOS.setEnabled(false);
        btnImprimirOS.setEnabled(false);
    }
    
    private void consultarCliente() {
        String sql = "SELECT idCliente AS ID_Cliente, nomeCliente AS Nome, foneCliente AS Telefone FROM tblClientes WHERE nomeCliente LIKE ?";
        
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtBuscaClienteOS.getText() + "%");
            
            rs = pst.executeQuery();
            
            tblClientesOS.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void preencherCampo() {
        int linhaTabela = tblClientesOS.getSelectedRow();
        txtIdClienteOS.setText(tblClientesOS.getModel().getValueAt(linhaTabela, 0).toString());
        txtClienteOS.setText(tblClientesOS.getModel().getValueAt(linhaTabela, 1).toString());
        
    }

    // CRUD
    // CREATE
    private void emitirOS() {
        String sql = "INSERT INTO tblOS (tipo, situacaoOS, equipamento, defeito, servico, tecnico, valor, idCliente) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            pst = conexao.prepareStatement(sql);
            
            pst.setString(1, tipo);
            pst.setString(2, cmbSituacaoOS.getSelectedItem().toString());
            pst.setString(3, txtEquipamentoOS.getText());
            pst.setString(4, txtDefeitoOS.getText());
            pst.setString(5, txtServicoOS.getText());
            pst.setString(6, txtTecnicoOS.getText());
            if (txtValorOS.getText().isEmpty()) {
                txtValorOS.setText("0.00");
            } else {
            }
            pst.setString(7, txtValorOS.getText().replace(",", "."));
            pst.setString(8, txtIdClienteOS.getText());
            
            if ( /*CAMPOS OBRG VAZIOS*/ (txtEquipamentoOS.getText().isEmpty()) || (txtDefeitoOS.getText().isEmpty()) || (txtIdClienteOS.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha os campos *obrigatório!");
            } else {
                int osEmitida = pst.executeUpdate();
                
                if (osEmitida > 0) {
                    
                    if (tipo == "OS") {
                        JOptionPane.showMessageDialog(null, "Ordem de Serviço emitida com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Orçamento emitido com sucesso!");
                    }
                    
                    //Limpo todos os campos após emitida a OS
                    //limparCampos();                    
                    btnImprimirOS.setEnabled(true);
                    btnCreateOS.setEnabled(false);
                    btnReadOS.setEnabled(false);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    // READ
    private void consultarOS() {
        String numeroOS = JOptionPane.showInputDialog("Informe o número da Ordem de Serviço:");
        String sql = "SELECT O.os, date_format(dataOS, '%d/%m/%Y - %H:%i'), tipo, situacaoOS, equipamento, defeito, servico, tecnico, valor, O.idCliente, C.nomeCliente FROM tblos AS O INNER JOIN tblclientes AS C ON (O.idCliente = C.idCliente) WHERE O.os = " + numeroOS;
//        String sql = "SELECT * FROM tblOS WHERE os = " + numeroOS;
        
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                txtNumeroOS.setText(rs.getString(1));
                txtDataOS.setText(rs.getString(2));
                String rbtTipo = rs.getString(3);
                if (rbtTipo.equals("OS")) {
                    rbtOS.setSelected(true);
                    tipo = "OS";
                } else {
                    rbtOrcamentoOS.setSelected(true);
                    tipo = "OC";    
                }
                cmbSituacaoOS.setSelectedItem(rs.getString(4));
                txtEquipamentoOS.setText(rs.getString(5));
                txtDefeitoOS.setText(rs.getString(6));
                txtServicoOS.setText(rs.getString(7));
                txtTecnicoOS.setText(rs.getString(8));
                txtValorOS.setText(rs.getString(9));
                txtIdClienteOS.setText(rs.getString(10));                
                txtClienteOS.setText(rs.getString(11));
                
                btnCreateOS.setEnabled(false);
                btnUpdateOS.setEnabled(true);
                btnDeleteOS.setEnabled(true);
                
            } else {
                JOptionPane.showMessageDialog(null, "Ordem de serviço Nr " + numeroOS + " não encontrada!");
                
                //Limpo todos os campos após emitida a OS
                limparCampos();
            }
                   
        } catch (java.sql.SQLSyntaxErrorException letterNum) {
            JOptionPane.showMessageDialog(null, "Ordem de Serviço Inválida!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    // UPDATE
    private void atualizarOS() {
        String sql = "UPDATE tblOS SET tipo = ?, situacaoOS = ?, equipamento = ?, defeito = ?, servico = ?, tecnico = ?, valor = ?, idCliente = ? WHERE os = ?";
        
        if ((txtEquipamentoOS.getText().isEmpty()) || (txtDefeitoOS.getText().isEmpty()) || (txtIdClienteOS.getText().isEmpty())) {
            JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios!");
        } else {
            try {
                pst = conexao.prepareStatement(sql);

                pst.setString(2, cmbSituacaoOS.getSelectedItem().toString());
                pst.setString(3, txtEquipamentoOS.getText());
                pst.setString(4, txtDefeitoOS.getText());
                pst.setString(5, txtServicoOS.getText());
                pst.setString(6, txtTecnicoOS.getText());
                pst.setString(7, txtValorOS.getText());
                pst.setString(8, txtIdClienteOS.getText());
                pst.setString(9, txtNumeroOS.getText());
                // recupera o rbt que foi selecionado para a OS pesquisada
                pst.setString(1, tipo);

                int atualizou = pst.executeUpdate();
                
                //se atualizou
                if (atualizou > 0) {
                // apresenta mensagem de sucesso e limpa os campos
                    if (tipo == "OC") {
                        JOptionPane.showMessageDialog(null, "Orçamento " + txtNumeroOS.getText() + " atualizado com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Ordem de Serviço " + txtNumeroOS.getText() + " atualizada com sucesso!");
                    }
                    
                    limparCampos();
                    
                } else {
                // se não, apresenta mensagem de erro
                    JOptionPane.showMessageDialog(null, "Houve algo de errado e não foi possível atualizar a OS Nr " + txtNumeroOS.getText() + "!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    
    // DELETE
    private void excluirOS() {
        int confirmDelete;
        if (tipo == "OC") {
            confirmDelete = JOptionPane.showConfirmDialog(null, "Confirma exclusão da Ordem de Serviço Nr" + txtNumeroOS.getText() + "?", "Atenção!", JOptionPane.YES_NO_OPTION);
        } else {
            confirmDelete = JOptionPane.showConfirmDialog(null, "Confirma exclusão do Orçamento Nr" + txtNumeroOS.getText() + "?", "Atenção!", JOptionPane.YES_NO_OPTION);
        }
                
        String sql = "DELETE FROM tblOS WHERE os = ?";
        
        if (confirmDelete == JOptionPane.YES_OPTION) {
            try {
                pst = conexao.prepareStatement(sql);                
                pst.setString(1, txtNumeroOS.getText());
                
                int deletado = pst.executeUpdate();
                
                if (deletado > 0) {                            
                    JOptionPane.showMessageDialog(null, "Exclusão realizada com sucesso!");
                    
                    limparCampos();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        
        
    }
    
    private void limparCampos() {
        txtNumeroOS.setText(null);
        txtDataOS.setText(null);
        rbtOrcamentoOS.setSelected(true);
        tipo = "OC";
        txtBuscaClienteOS.setText(null);
        txtClienteOS.setText(null);
        txtIdClienteOS.setText(null);
        cmbSituacaoOS.setSelectedIndex(0);
        txtEquipamentoOS.setText(null);
        txtServicoOS.setText(null);
        txtDefeitoOS.setText(null);
        txtTecnicoOS.setText(null);
        txtValorOS.setText(null);
        
        btnCreateOS.setEnabled(true);
        btnUpdateOS.setEnabled(false);
        btnDeleteOS.setEnabled(false);
        btnImprimirOS.setEnabled(false);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupTpDoc = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtDataOS = new javax.swing.JTextField();
        txtNumeroOS = new javax.swing.JTextField();
        rbtOrcamentoOS = new javax.swing.JRadioButton();
        rbtOS = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        cmbSituacaoOS = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtBuscaClienteOS = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtIdClienteOS = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientesOS = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        txtClienteOS = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtEquipamentoOS = new javax.swing.JTextField();
        txtServicoOS = new javax.swing.JTextField();
        txtDefeitoOS = new javax.swing.JTextField();
        txtTecnicoOS = new javax.swing.JTextField();
        txtValorOS = new javax.swing.JTextField();
        btnCreateOS = new javax.swing.JButton();
        btnReadOS = new javax.swing.JButton();
        btnUpdateOS = new javax.swing.JButton();
        btnDeleteOS = new javax.swing.JButton();
        btnImprimirOS = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("PGOS - Ordens de Serviço");
        setMaximumSize(new java.awt.Dimension(740, 587));
        setMinimumSize(new java.awt.Dimension(128, 34));
        setPreferredSize(new java.awt.Dimension(740, 587));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("OS N°");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Data");

        txtDataOS.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        txtDataOS.setEnabled(false);

        txtNumeroOS.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtNumeroOS.setEnabled(false);

        btnGroupTpDoc.add(rbtOrcamentoOS);
        rbtOrcamentoOS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rbtOrcamentoOS.setText("Orçamento");
        rbtOrcamentoOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOrcamentoOSActionPerformed(evt);
            }
        });

        btnGroupTpDoc.add(rbtOS);
        rbtOS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rbtOS.setText("Ordem de Serviço");
        rbtOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rbtOrcamentoOS)
                        .addGap(18, 18, 18)
                        .addComponent(rbtOS))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtNumeroOS, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDataOS)
                            .addComponent(jLabel2))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDataOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumeroOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtOrcamentoOS)
                    .addComponent(rbtOS))
                .addContainerGap())
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Situação");

        cmbSituacaoOS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbSituacaoOS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Entregue na Assistência", "Orçamento REPROVADO", "Aguardando Aprovação", "Aguardando Peças", "Abandonado pelo Cliente", "Pronto para Retirada", "Retornou a Assitencia" }));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Buscar cliente:");

        txtBuscaClienteOS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtBuscaClienteOS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscaClienteOSKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("ID*");

        txtIdClienteOS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtIdClienteOS.setEnabled(false);

        tblClientesOS = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tblClientesOS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID Cliente", "Nome", "Teleone"
            }
        ));
        tblClientesOS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesOSMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientesOS);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Cliente");

        txtClienteOS.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtClienteOS.setEnabled(false);

        jLabel13.setFont(new java.awt.Font("Tahoma", 2, 8)); // NOI18N
        jLabel13.setText("Após buscar o cliente, selecione-o.");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscaClienteOS))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtClienteOS, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtIdClienteOS, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtBuscaClienteOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtClienteOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtIdClienteOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(328, 328, 328))
        );

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Equipamento*");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Defeito*");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Serviço");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Valor");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Técnico");

        txtEquipamentoOS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtServicoOS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtDefeitoOS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtTecnicoOS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtValorOS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btnCreateOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pgos/icons/OS Create.png"))); // NOI18N
        btnCreateOS.setToolTipText("Adicionar");
        btnCreateOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCreateOS.setMaximumSize(new java.awt.Dimension(97, 73));
        btnCreateOS.setMinimumSize(new java.awt.Dimension(97, 73));
        btnCreateOS.setPreferredSize(new java.awt.Dimension(90, 90));
        btnCreateOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateOSActionPerformed(evt);
            }
        });

        btnReadOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pgos/icons/OS Read.png"))); // NOI18N
        btnReadOS.setToolTipText("Pesquisar");
        btnReadOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReadOS.setMaximumSize(new java.awt.Dimension(97, 73));
        btnReadOS.setMinimumSize(new java.awt.Dimension(97, 73));
        btnReadOS.setPreferredSize(new java.awt.Dimension(90, 90));
        btnReadOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReadOSActionPerformed(evt);
            }
        });

        btnUpdateOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pgos/icons/OS Update.png"))); // NOI18N
        btnUpdateOS.setToolTipText("Editar");
        btnUpdateOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdateOS.setMaximumSize(new java.awt.Dimension(97, 73));
        btnUpdateOS.setMinimumSize(new java.awt.Dimension(97, 73));
        btnUpdateOS.setPreferredSize(new java.awt.Dimension(90, 90));
        btnUpdateOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateOSActionPerformed(evt);
            }
        });

        btnDeleteOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pgos/icons/OS Delete.png"))); // NOI18N
        btnDeleteOS.setToolTipText("Apagar");
        btnDeleteOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeleteOS.setMaximumSize(new java.awt.Dimension(97, 73));
        btnDeleteOS.setMinimumSize(new java.awt.Dimension(97, 73));
        btnDeleteOS.setPreferredSize(new java.awt.Dimension(90, 90));
        btnDeleteOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteOSActionPerformed(evt);
            }
        });

        btnImprimirOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/pgos/icons/OS Print.png"))); // NOI18N
        btnImprimirOS.setToolTipText("Imprimir OS");
        btnImprimirOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImprimirOS.setMaximumSize(new java.awt.Dimension(97, 73));
        btnImprimirOS.setMinimumSize(new java.awt.Dimension(97, 73));
        btnImprimirOS.setPreferredSize(new java.awt.Dimension(90, 90));

        jLabel11.setText("* Campos obrigatórios");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbSituacaoOS, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtTecnicoOS, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel9)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtValorOS, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtServicoOS, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEquipamentoOS, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDefeitoOS, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel11)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnCreateOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(37, 37, 37)
                                        .addComponent(btnReadOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(44, 44, 44)
                                        .addComponent(btnUpdateOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(42, 42, 42)
                                        .addComponent(btnDeleteOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(41, 41, 41)
                                        .addComponent(btnImprimirOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jSeparator1))))
                        .addGap(49, 49, 49)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cmbSituacaoOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtEquipamentoOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtServicoOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtDefeitoOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtTecnicoOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtValorOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreateOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReadOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdateOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImprimirOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        setBounds(0, 0, 740, 593);
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscaClienteOSKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscaClienteOSKeyReleased
        // TODO add your handling code here:
        consultarCliente();
    }//GEN-LAST:event_txtBuscaClienteOSKeyReleased

    private void tblClientesOSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesOSMouseClicked
        // TODO add your handling code here:
        preencherCampo();
    }//GEN-LAST:event_tblClientesOSMouseClicked

    private void rbtOrcamentoOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOrcamentoOSActionPerformed
        // TODO add your handling code here:
        tipo = "OC";
    }//GEN-LAST:event_rbtOrcamentoOSActionPerformed

    private void rbtOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOSActionPerformed
        // TODO add your handling code here:
        tipo = "OS";
    }//GEN-LAST:event_rbtOSActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        rbtOrcamentoOS.setSelected(true);
        tipo = "OC";
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnCreateOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateOSActionPerformed
        // TODO add your handling code here:
        emitirOS();
    }//GEN-LAST:event_btnCreateOSActionPerformed

    private void btnReadOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReadOSActionPerformed
        // TODO add your handling code here:
        consultarOS();
    }//GEN-LAST:event_btnReadOSActionPerformed

    private void btnUpdateOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateOSActionPerformed
        // TODO add your handling code here:
        atualizarOS();
    }//GEN-LAST:event_btnUpdateOSActionPerformed

    private void btnDeleteOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteOSActionPerformed
        // TODO add your handling code here:
        excluirOS();
    }//GEN-LAST:event_btnDeleteOSActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreateOS;
    private javax.swing.JButton btnDeleteOS;
    private javax.swing.ButtonGroup btnGroupTpDoc;
    private javax.swing.JButton btnImprimirOS;
    private javax.swing.JButton btnReadOS;
    private javax.swing.JButton btnUpdateOS;
    private javax.swing.JComboBox<String> cmbSituacaoOS;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JRadioButton rbtOS;
    private javax.swing.JRadioButton rbtOrcamentoOS;
    private javax.swing.JTable tblClientesOS;
    private javax.swing.JTextField txtBuscaClienteOS;
    private javax.swing.JTextField txtClienteOS;
    private javax.swing.JTextField txtDataOS;
    private javax.swing.JTextField txtDefeitoOS;
    private javax.swing.JTextField txtEquipamentoOS;
    private javax.swing.JTextField txtIdClienteOS;
    private javax.swing.JTextField txtNumeroOS;
    private javax.swing.JTextField txtServicoOS;
    private javax.swing.JTextField txtTecnicoOS;
    private javax.swing.JTextField txtValorOS;
    // End of variables declaration//GEN-END:variables
}
