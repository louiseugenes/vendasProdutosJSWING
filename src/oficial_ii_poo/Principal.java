/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package oficial_ii_poo;

/**
 *
 * @author louis
 */
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Principal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Point initialClick;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
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
	public Principal() {
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
		
		JButton btnClient = new JButton("Cliente");
		btnClient.setBounds(10, 137, 116, 38);
		btnClient.setFont(new Font("Tahoma", Font.BOLD, 13));
		contentPane.add(btnClient);
		
		btnClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClienteView clienteView = new ClienteView();
				clienteView.setVisible(true);
			}
		});
		
		JButton btnProduct = new JButton("Produtos");
		btnProduct.setBounds(167, 137, 116, 38);
		btnProduct.setFont(new Font("Tahoma", Font.BOLD, 13));
		contentPane.add(btnProduct);
		
		btnProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProdutoView produtoView = new ProdutoView();
				produtoView.setVisible(true);
			}
		});

		
		JButton btnSale = new JButton("Vendas");
		btnSale.setBounds(324, 137, 116, 38);
		btnSale.setFont(new Font("Tahoma", Font.BOLD, 13));
		contentPane.add(btnSale);
		
		btnSale.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    VendasProduto vendasProduto = new VendasProduto();
                    vendasProduto.setVisible(true);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao abrir a tela de vendas: " + ex.getMessage());
                }
            }
        });
	}

}

