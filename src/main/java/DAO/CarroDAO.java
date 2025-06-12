package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Classes.Carro;
import DataBase.Conexao;

public class CarroDAO {

    public boolean criar(Carro carro) {
        String sql = "INSERT INTO Carro(marca, cor) VALUES (?, ?)";

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, carro.getMarca());
            stmt.setString(2, carro.getCor());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao criar carro: " + e.getMessage());
            return false;
        }
    }

    public List<Carro> listarTodos() {
        List<Carro> lista = new ArrayList<>();
        String sql = "SELECT * FROM Carro";

        try (Connection con = Conexao.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Carro c = new Carro(
                        rs.getString("marca"),
                        rs.getString("cor"),
                        rs.getInt("id_Carro") // Usando o padrão com underscore
                );
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar carros: " + e.getMessage());
        }
        return lista;
    }

    public boolean atualizar(Carro carro) {
        String sql = "UPDATE Carro SET marca=?, cor=? WHERE id_Carro=?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, carro.getMarca());
            ps.setString(2, carro.getCor());
            ps.setInt(3, carro.getIdCarro());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar carro: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM Carro WHERE id_Carro=?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao excluir carro: " + e.getMessage());
            return false;
        }
    }

    public Carro buscarPorId(int id) {
        String sql = "SELECT * FROM Carro WHERE id_Carro = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Carro(
                            rs.getString("marca"),
                            rs.getString("cor"),
                            rs.getInt("id_Carro")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar carro: " + e.getMessage());
        }
        return null;
    }

    public List<Carro> buscarPorMarca(String marca) {
        List<Carro> lista = new ArrayList<>();
        String sql = "SELECT * FROM Carro WHERE marca LIKE ?";

        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + marca + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Carro c = new Carro(
                            rs.getString("marca"),
                            rs.getString("cor"),
                            rs.getInt("id_Carro")
                    );
                    lista.add(c);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar carros por marca: " + e.getMessage());
        }
        return lista;
    }

    // Método para verificar a estrutura da tabela (útil para debug)
    public void verificarEstruturaTabelaCarro() {
        String sql = "SELECT * FROM Carro LIMIT 1";
        try (Connection con = Conexao.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            System.out.println("Colunas encontradas na tabela Carro:");
            for (int i = 1; i <= columnCount; i++) {
                System.out.println("Coluna " + i + ": " + metaData.getColumnName(i));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar estrutura da tabela: " + e.getMessage());
        }
    }
}
