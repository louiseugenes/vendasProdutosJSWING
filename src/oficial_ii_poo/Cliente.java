package oficial_ii_poo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Cliente {
	private int id;
	private String nome;
	
	public Cliente()
	{
		super();
	}
	public Cliente(int id, String nome)
	{   super();
	    this.id=id;
		this.nome=nome;
	}
        
        @Override
        public String toString() {
            return this.nome;
        }

	public void listarCliente(Connection conn, JTable tabProduto) throws SQLException {
		DefaultTableModel modeloTabela = (DefaultTableModel) tabProduto.getModel();
		modeloTabela.setRowCount(0);

		try (Statement stmt = conn.createStatement()) {
			String sql = "SELECT * FROM minhaloja.cliente";
			try (ResultSet rs = stmt.executeQuery(sql)) {
				while (rs.next()) {
					modeloTabela.addRow(new Object[] { 
						rs.getInt("Id"), 
						rs.getString("Nome")
					});
				}
			}
		}
	}
        
        public void listarClientesEmComboBox(Connection conn, JComboBox<Cliente> comboBox) throws SQLException {
            String sql = "SELECT id, nome FROM minhaloja.Cliente";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getInt("id"));
                    cliente.setNome(rs.getString("nome"));
                    comboBox.addItem(cliente); // Adiciona instâncias de Cliente ao JComboBox
                }
            }
        }

	
	public void excluirCliente(int id, Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		String sql = "DELETE FROM minhaloja.cliente WHERE Id = " + id;
		stmt.executeUpdate(sql);
		stmt.close();
	}
	
	public void gravarCliente(Connection conn, String nome) throws SQLException {
            try {
                conn.setAutoCommit(false);
                Statement stmt = conn.createStatement();
                // Inclua a função NOW() para definir a data e hora atuais no campo dtCadastro
                String query = "INSERT INTO minhaloja.cliente(Nome, dtCadastro) VALUES('" + nome + "', NOW())";
                stmt.executeUpdate(query);
                conn.commit();
            } catch (Exception ex) {
                conn.rollback();
                JOptionPane.showMessageDialog(null, "Não foi possível gravar. " + ex.getMessage());
            }
        }
	
	public void pesquisarCliente(String texto, Connection conn, JTable tabCliente) throws SQLException {
		DefaultTableModel modeloTabela = (DefaultTableModel) tabCliente.getModel();
		modeloTabela.setRowCount(0);

		try (Statement stmt = conn.createStatement()) {
			String sql = "SELECT * FROM cliente WHERE Id LIKE '%" + texto + "%' OR Nome LIKE '%" + texto + "%'";
			try (ResultSet rs = stmt.executeQuery(sql)) {
				while (rs.next()) {
					modeloTabela.addRow(new Object[] { 
						rs.getInt("Id"), 
						rs.getString("Nome"), 
					});
				}
			}
		}
	}
	

	public String getNome()
	{  return this.nome; }
	
	public int getId()
	{  return this.id; }
	
	public void setId(int id)
	{   this.id=id; }

	public void setNome(String nome)
	{
	   this.nome=nome;	
	}
	
}
