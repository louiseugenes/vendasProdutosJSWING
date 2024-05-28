/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package oficial_ii_poo;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Label;
import java.awt.Point;
import java.awt.TextField;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

/**
 *
 * @author louis
 */
public class VendasProduto extends javax.swing.JFrame {

        private static final long serialVersionUID = 1L;
	private JComboBox<Cliente> comboCliente = new JComboBox<>();
	private JComboBox<Produto> comboProd = new JComboBox<>();
	private JTable table;
	private Conexao con=new Conexao();
	private Connection objbanco=con.conectar();
	private final JLabel lblNewLabel = new JLabel("Produto");
	private JTextField textQuant;
	private Point initialClick;
	private JPanel contentPane;
        private Produto produto;
        private Cliente cliente;
        private JButton btnFinalizar;
        private DefaultTableModel modeloTabela;
        private JLabel lblClienteId;
        private JPanel panelClienteId;

    /**
     * Creates new form VendasProduto
     */
    public VendasProduto() throws SQLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		
		setUndecorated(true);
		getContentPane().setLayout(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(Color.GRAY);
		setContentPane(contentPane);
		
		JPanel titleBar = new JPanel();
		titleBar.setBackground(Color.DARK_GRAY);
		titleBar.setBounds(0, 0, 700, 40);
		titleBar.setLayout(null);
		contentPane.add(titleBar);
		
		JLabel lblTitle = new JLabel("Vendas de Produtos");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setBounds(10, 4, 200, 30);
		titleBar.add(lblTitle);
		
		JButton btnClose = new JButton("X");
		btnClose.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnClose.setForeground(Color.WHITE);
		btnClose.setBackground(Color.RED);
		btnClose.setBounds(634, 7, 56, 28);
		btnClose.setFocusPainted(false);
		btnClose.setBorderPainted(false);
		btnClose.setContentAreaFilled(false);
		btnClose.setOpaque(true);
		titleBar.add(btnClose);
		
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		titleBar.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				initialClick = e.getPoint();
				getComponentAt(initialClick);
			}
		});
		
		titleBar.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				int thisX = getLocation().x;
				int thisY = getLocation().y;

				int xMoved = e.getX() - initialClick.x;
				int yMoved = e.getY() - initialClick.y;

				int x = thisX + xMoved;
				int y = thisY + yMoved;
				setLocation(x, y);
			}
		});
		
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		comboCliente = new JComboBox();
		comboCliente.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboCliente.setBounds(192, 107, 417, 31);
		getContentPane().add(comboCliente);
                
		
                panelClienteId = new JPanel();
                panelClienteId.setLayout(null);
                panelClienteId.setBackground(Color.WHITE);
                panelClienteId.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                panelClienteId.setBounds(62, 107, 109, 31);
                getContentPane().add(panelClienteId);

                lblClienteId = new JLabel();
                lblClienteId.setFont(new Font("Tahoma", Font.PLAIN, 16));
                lblClienteId.setBounds(0, 0, 109, 31);
                panelClienteId.add(lblClienteId);
                
                
                comboCliente.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Cliente cli = (Cliente) comboCliente.getSelectedItem();
                        if (cli != null) {
                            lblClienteId.setText("ID: " + cli.getId());
                        }
                    }
                });
		
		JLabel txCliente = new JLabel("Cliente");
		txCliente.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txCliente.setBounds(192, 89, 62, 14);
		getContentPane().add(txCliente);
		
		JButton btnNewButton = new JButton("Adicionar");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Cliente cli = (Cliente) comboCliente.getSelectedItem();
                        Pedido pedido = new Pedido();
                        try {
                            int retorno = pedido.gravaPedidoKey(objbanco, cli);
                            if (retorno > 0) {
                                Item itm = new Item();
                                Produto pd = (Produto) comboProd.getSelectedItem();
                                double preco = pd.getPreco();
                                int quantidade = Integer.parseInt(textQuant.getText());
                                itm.gravaItem(objbanco, pd.getId(), retorno, preco, quantidade);
                            }
                        } catch (SQLException e1) {
                            try {
                                objbanco.rollback();
                            } catch (SQLException e2) {
                                e2.printStackTrace();
                            }
                            e1.printStackTrace();
                        }
                        // Atualizar a tabela após adicionar um pedido
                        atualizarTabelaPedidos();
                    }
                });
		btnNewButton.setBounds(444, 170, 165, 31);
		getContentPane().add(btnNewButton);
                
                btnFinalizar = new JButton("Finalizar Compras");
                btnFinalizar.setFont(new Font("Tahoma", Font.PLAIN, 16));
                btnFinalizar.setBounds(444, 205, 165, 31);
                getContentPane().add(btnFinalizar);
                
                btnFinalizar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            objbanco.setAutoCommit(false); // Desativar o autocommit
                            objbanco.commit();
                            JOptionPane.showMessageDialog(null, "Compra finalizada com sucesso!");
                        } catch (SQLException e1) {
                            try {
                                objbanco.rollback();
                                JOptionPane.showMessageDialog(null, "Erro ao finalizar compra. Transação revertida.");
                            } catch (SQLException e2) {
                                e2.printStackTrace();
                            }
                            e1.printStackTrace();
                        } finally {
                            try {
                                objbanco.setAutoCommit(true); // Ativar o autocommit novamente
                            } catch (SQLException e3) {
                                e3.printStackTrace();
                            }
                        }
                    }
                });
	
                
                String[] colunasTabela = new String[]{ "Id", "Data", "Cliente", "Produto", "Preço", "Quantidade", "Total" };
                modeloTabela = new DefaultTableModel(null, colunasTabela);
                table = new JTable(modeloTabela);
                table.setFont(new Font("Tahoma", Font.BOLD, 12));
                JScrollPane scrollPane = new JScrollPane(table); // Adiciona um painel de rolagem
                scrollPane.setBounds(62, 244, 547, 228); // Define as dimensões do painel de rolagem
                getContentPane().add(scrollPane);

                atualizarTabelaPedidos();
                
		comboProd.setEditable(true);
		comboProd.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		
		comboProd.setBounds(65, 190, 225, 31);
		getContentPane().add(comboProd);
		lblNewLabel.setEnabled(false);
		lblNewLabel.setBounds(68, 168, 109, 22);
		getContentPane().add(lblNewLabel);
		
		JLabel lblQuant = new JLabel("Quant");
		lblQuant.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblQuant.setBounds(320, 166, 109, 22);
		getContentPane().add(lblQuant);
		
		textQuant = new JTextField();
		textQuant.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textQuant.setBounds(320, 190, 99, 31);
		getContentPane().add(textQuant);
		textQuant.setColumns(10);
		
                Produto produto = new Produto();
                try {
                    produto.listarProdutosEmComboBox(objbanco, comboProd);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + ex.getMessage());
                }
                
                Cliente cliente = new Cliente();
                try {
                    cliente.listarClientesEmComboBox(objbanco, comboCliente);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao listar clientes: " + ex.getMessage());
                }

	}
        private void atualizarTabelaPedidos() {
            try {
                List<Object[]> pedidos = Pedido.listarPedidosDataCorrente(objbanco);
                modeloTabela.setRowCount(0); // Limpar tabela
                for (Object[] pedido : pedidos) {
                    modeloTabela.addRow(pedido);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao listar pedidos: " + ex.getMessage());
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 404, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(VendasProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VendasProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VendasProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VendasProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new VendasProduto().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(VendasProduto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
