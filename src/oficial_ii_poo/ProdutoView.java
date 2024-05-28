package oficial_ii_poo;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JScrollPane;

public class ProdutoView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Connection objConexaoBanco;
	private Produto produto;
	private JTable tabProduto;
	private ProdutoNovo novocad;
	private ProdutoNovo novoprod;
	private JTextField textField;
	private Point initialClick;

	/**
	 * Launch the application.
	 */
	
	public void setConexao(Connection obj)
	{  
		
			this.objConexaoBanco=obj;
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProdutoView frame = new ProdutoView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProdutoView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		setUndecorated(true);
		getContentPane().setLayout(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(Color.GRAY);
		setContentPane(contentPane);
		
		JPanel titleBar = new JPanel();
		titleBar.setBackground(Color.DARK_GRAY);
		titleBar.setBounds(0, 0, 450, 40);
		titleBar.setLayout(null);
		contentPane.add(titleBar);
		
		JLabel lblTitle = new JLabel("Listagem de Produtos");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setBounds(10, 5, 200, 30);
		titleBar.add(lblTitle);
		
		JButton btnClose = new JButton("X");
		btnClose.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnClose.setForeground(Color.WHITE);
		btnClose.setBackground(Color.RED);
		btnClose.setBounds(384, 7, 56, 28);
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
		
		String[] colunasTabela = new String[]{ "Id", "Descrição", "Preço" };
                DefaultTableModel modeloTabela = new DefaultTableModel(null, colunasTabela);
                tabProduto = new JTable(modeloTabela);
                tabProduto.setFont(new Font("Tahoma", Font.BOLD, 12));
                JScrollPane scrollPane = new JScrollPane(tabProduto);
                scrollPane.setBounds(10, 160, 414, 129);
                getContentPane().add(scrollPane);
		
		JButton btnNew = new JButton("Novo");
		btnNew.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNew.setBackground(Color.GREEN);
		btnNew.setBounds(10, 50, 90, 40);
		contentPane.add(btnNew);
		
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (novocad == null) {
					novocad = new ProdutoNovo();
					novocad.setConexao(objConexaoBanco);
					novocad.setLocationRelativeTo(null);
					novocad.setDefaultCloseOperation(HIDE_ON_CLOSE);
				}
				novocad.setVisible(true);
			}
		});
		
		JButton btnDelete = new JButton("Excluir");
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnDelete.setBackground(Color.RED);
		btnDelete.setBounds(350, 50, 90, 40);
		contentPane.add(btnDelete);
		
		btnDelete.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int row = tabProduto.getSelectedRow();
                        if (row >= 0) {
                            int confirm = JOptionPane.showConfirmDialog(
                                null, 
                                "Tem certeza de que deseja excluir o produto selecionado?", 
                                "Confirmar Exclusão", 
                                JOptionPane.YES_NO_OPTION
                            );
                            if (confirm == JOptionPane.YES_OPTION) {
                                try {
                                    int idCliente = Integer.parseInt(tabProduto.getValueAt(row, 0).toString());
                                    produto.excluirProduto(idCliente, objConexaoBanco);
                                    produto.listarProduto(objConexaoBanco, tabProduto);
                                } catch (SQLException ex) {
                                    JOptionPane.showMessageDialog(null, "Erro ao deletar produto: " + ex.getMessage());
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Selecione um produto para excluir.");
                        }
                    }
                });
		
		JButton btnUpdate = new JButton("Atualizar");
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnUpdate.setBackground(Color.YELLOW);
		btnUpdate.setBounds(110, 50, 100, 40);
		contentPane.add(btnUpdate);
		
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					produto.listarProduto(objConexaoBanco, tabProduto);
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + ex.getMessage());
				}
				
			}
		});
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.BOLD, 12));
		textField.setBounds(10, 129, 157, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnSearch = new JButton("Pesquisar");
		btnSearch.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSearch.setBackground(Color.BLUE);
		btnSearch.setBounds(177, 114, 102, 35);
		contentPane.add(btnSearch);
		
		btnSearch.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            String texto = textField.getText();
		            produto.pesquisarProduto(texto, objConexaoBanco, tabProduto);
		        } catch (SQLException ex) {
		            JOptionPane.showMessageDialog(null, "Erro ao pesquisar produtos: " + ex.getMessage());
		        }
		    }
		});

		
		JButton btnCleanSearch = new JButton("Limpar");
		btnCleanSearch.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnCleanSearch.setBackground(Color.YELLOW);
		btnCleanSearch.setBounds(289, 114, 90, 35);
		contentPane.add(btnCleanSearch);
		
		btnCleanSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					produto.listarProduto(objConexaoBanco, tabProduto);
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + ex.getMessage());
				}
				
			}
		});
		
		JLabel lblNewLabel = new JLabel("Digite o Id ou Descrição");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(10, 111, 160, 14);
		contentPane.add(lblNewLabel);
		
		Conexao con = new Conexao();
		objConexaoBanco = con.conectar();
		if (objConexaoBanco == null) {
			JOptionPane.showMessageDialog(null, "Conexão não realizada");
		}
		produto = new Produto();
		
		try {
			produto.listarProduto(objConexaoBanco, tabProduto);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + ex.getMessage());
		}
	}
}
