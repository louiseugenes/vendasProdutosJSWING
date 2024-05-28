package oficial_ii_poo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.sql.Statement;


public class Pedido {
    private int id;
    private String dtCadastro;
    private int clienteId;

    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(String dtCadastro) {
        this.dtCadastro = dtCadastro;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int gravaPedidoKey(Connection conn, Cliente cliente) throws SQLException {
        String sql = "INSERT INTO minhaloja.Pedido (ClienteId, DtCadastro) VALUES (?, NOW())";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, cliente.getId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating pedido failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating pedido failed, no ID obtained.");
                }
            }
        }
    }
    
    public static List<Object[]> listarPedidosDataCorrente(Connection conn) throws SQLException {
        String sql = "SELECT p.id, p.dtCadastro, c.Nome AS clienteNome, pr.descricao AS produtoNome, i.preco, i.quantidade, (i.preco * i.quantidade) AS total " +
                     "FROM minhaloja.Pedido p " +
                     "JOIN minhaloja.Cliente c ON p.ClienteId = c.id " +
                     "JOIN minhaloja.Item i ON p.id = i.PedidoId " +
                     "JOIN minhaloja.Produto pr ON i.ProdutoId = pr.id " +
                     "WHERE DATE(p.dtCadastro) = CURDATE() " +
                     "ORDER BY p.dtCadastro, pr.descricao, c.nome, i.preco, i.quantidade";

        List<Object[]> pedidos = new ArrayList<>();
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Object[] pedido = new Object[7];
                pedido[0] = rs.getInt("id");
                pedido[1] = rs.getString("dtCadastro");
                pedido[2] = rs.getString("clienteNome");
                pedido[3] = rs.getString("produtoNome");
                pedido[4] = rs.getDouble("preco");
                pedido[5] = rs.getInt("quantidade");
                pedido[6] = rs.getDouble("total");
                pedidos.add(pedido);
            }
        }
        return pedidos;
    }
}