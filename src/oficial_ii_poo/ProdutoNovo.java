package oficial_ii_poo;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Point;

public class ProdutoNovo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txDescricao;
	private JTextField txPreco;
	private Connection objConexao;
	private Produto prod=new Produto();
	private JPanel contentPane;
	private Point initialClick;
	protected Object textField;

	/**
	 * Launch the application.
	 */
	
	public void setConexao(Connection obj)
	{  
		
			this.objConexao=obj;
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProdutoNovo frame = new ProdutoNovo();
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
	public ProdutoNovo() {
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
		
		JLabel lblTitle = new JLabel("Cadastrar novo Produto");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setBounds(10, 5, 214, 30);
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
		
		JLabel lblNewLabel = new JLabel("Descrição");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(23, 132, 96, 14);
		getContentPane().add(lblNewLabel);
		
		txDescricao = new JTextField();
		txDescricao.setBounds(23, 157, 335, 30);
		getContentPane().add(txDescricao);
		txDescricao.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Preço");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(23, 53, 46, 14);
		getContentPane().add(lblNewLabel_1);
		
		txPreco = new JTextField();
		txPreco.setBounds(23, 78, 133, 30);
		getContentPane().add(txPreco);
		txPreco.setColumns(10);
		
		JButton btnNew = new JButton("Gravar");
		btnNew.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNew.setBackground(Color.GREEN);
		contentPane.add(btnNew);
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					prod.gravarProduto(objConexao, txDescricao.getText(), 
							 Double.parseDouble(txPreco.getText())
							);
					txDescricao.setText(null);
					txPreco.setText(null);
					JOptionPane.showMessageDialog(null,"Produto gravado com sucesso!");
				} catch (NumberFormatException | SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNew.setBounds(268, 72, 90, 40);
		getContentPane().add(btnNew);

	}
}
