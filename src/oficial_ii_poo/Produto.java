package oficial_ii_poo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;

public class Produto {
	public int id;
	public String descricao;
        public double preco;
	
        @Override
        public String toString() {
            return this.descricao;
        }
	
	public Produto()
	{
		super();
	}
        
        public int getId() {
        return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

        public double getPreco() {
            return preco;
        }

        public void setPreco(double preco) {
            this.preco = preco;
        }
	
	
	public Produto(int id, String descricao, double preco)
	{   super();
	    this.id=id;
            this.descricao=descricao;
            this.preco=preco;
	}
	
	public void listarProduto(Connection conn, JTable tabProduto) throws SQLException {
		DefaultTableModel modeloTabela = (DefaultTableModel) tabProduto.getModel();
		modeloTabela.setRowCount(0);

		try (Statement stmt = conn.createStatement()) {
			String sql = "SELECT * FROM minhaloja.produto";
			try (ResultSet rs = stmt.executeQuery(sql)) {
				while (rs.next()) {
					modeloTabela.addRow(new Object[] { 
						rs.getInt("Id"), 
						rs.getString("Descricao"), 
						rs.getFloat("Preco")
					});
				}
			}
		}
	}
        
       public void listarProdutosEmComboBox(Connection conn, JComboBox<Produto> comboBox) throws SQLException {
            String sql = "SELECT id, descricao, preco FROM minhaloja.produto";
            DefaultComboBoxModel<Produto> model = new DefaultComboBoxModel<>();
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Produto produto = new Produto();
                    produto.setId(rs.getInt("id"));
                    produto.setDescricao(rs.getString("descricao"));
                    produto.setPreco(rs.getDouble("preco"));
                    comboBox.addItem(produto);
                    model.addElement(produto);
                }
            }
            comboBox.setModel(model);
        }

	
	public void excluirProduto(int id, Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		String sql = "DELETE FROM minhaloja.produto WHERE Id = " + id;
		stmt.executeUpdate(sql);
		stmt.close();
	}
	
	public void gravarProduto(Connection conn,String descricao,double preco) throws SQLException
	{  try
	   {
		conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();
	        String query="insert into minhaloja.produto(Descricao,Preco) values('"+descricao+"',"+preco+")";
	        stmt.executeUpdate(query);
	        conn.commit();
	   }
		catch(Exception ex)
		{
			conn.rollback();
			JOptionPane.showMessageDialog(null,"Não foi possível gravar. "+ex.getMessage());
		}
	
   	}
	
	public void pesquisarProduto(String texto, Connection conn, JTable tabProduto) throws SQLException {
		DefaultTableModel modeloTabela = (DefaultTableModel) tabProduto.getModel();
		modeloTabela.setRowCount(0);

		try (Statement stmt = conn.createStatement()) {
			String sql = "SELECT * FROM produto WHERE Id LIKE '%" + texto + "%' OR Descricao LIKE '%" + texto + "%'";
			try (ResultSet rs = stmt.executeQuery(sql)) {
				while (rs.next()) {
					modeloTabela.addRow(new Object[] { 
						rs.getInt("Id"), 
						rs.getString("Descricao"), 
						rs.getFloat("Preco")
					});
				}
			}
		}
	}
}
