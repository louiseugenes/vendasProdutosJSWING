package oficial_ii_poo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Item {
    private int id;
    private int pedidoId;
    private int produtoId;
    private double preco;
    private int quantidade;

    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }

    public int getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    // Método para gravar o item
    public boolean gravaItem(Connection conn, int produtoId, int pedidoId, double preco, int quantidade) throws SQLException {
        String sql = "INSERT INTO minhaloja.Item (ProdutoId, PedidoId, preco, quantidade) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, produtoId);
            pstmt.setInt(2, pedidoId);
            pstmt.setDouble(3, preco);
            pstmt.setInt(4, quantidade);
            return pstmt.executeUpdate() > 0;
        }
    }
}
