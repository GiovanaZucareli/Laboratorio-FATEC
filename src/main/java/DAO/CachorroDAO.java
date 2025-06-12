package DAO;

import Classes.Cachorro;
import DataBase.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CachorroDAO {

    public boolean criar(Cachorro cachorro) {
        String sql = "INSERT INTO Cachorro (raca, cor, nome, idade) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cachorro.getRaca());
            ps.setString(2, cachorro.getCor());
            ps.setString(3, cachorro.getNome());
            ps.setInt(4, cachorro.getIdade());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao criar cachorro: " + e.getMessage());
            return false;
        }
    }

    public List<Cachorro> listarTodos() {
        List<Cachorro> lista = new ArrayList<>();
        String sql = "SELECT * FROM Cachorro";

        try (Connection con = Conexao.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Cachorro c = new Cachorro(
                        rs.getString("raca"),
                        rs.getString("cor"),
                        rs.getString("nome"),
                        rs.getInt("id_Cachorro"), // Usando o nome correto da coluna
                        rs.getInt("idade")
                );
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar cachorros: " + e.getMessage());
        }
        return lista;
    }

    public boolean atualizar(Cachorro cachorro) {
        String sql = "UPDATE Cachorro SET raca=?, cor=?, nome=?, idade=? WHERE id_Cachorro=?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cachorro.getRaca());
            ps.setString(2, cachorro.getCor());
            ps.setString(3, cachorro.getNome());
            ps.setInt(4, cachorro.getIdade());
            ps.setInt(5, cachorro.getIdCachorro());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar cachorro: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM Cachorro WHERE id_Cachorro=?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao excluir cachorro: " + e.getMessage());
            return false;
        }
    }

    public Cachorro buscarPorId(int id) {
        String sql = "SELECT * FROM Cachorro WHERE id_Cachorro = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Cachorro(
                            rs.getString("raca"),
                            rs.getString("cor"),
                            rs.getString("nome"),
                            rs.getInt("id_Cachorro"), // Usando o nome correto da coluna
                            rs.getInt("idade")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cachorro: " + e.getMessage());
        }
        return null;
    }

    public List<Cachorro> buscarPorRaca(String raca) {
        List<Cachorro> lista = new ArrayList<>();
        String sql = "SELECT * FROM Cachorro WHERE raca LIKE ?";

        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + raca + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cachorro c = new Cachorro(
                            rs.getString("raca"),
                            rs.getString("cor"),
                            rs.getString("nome"),
                            rs.getInt("id_Cachorro"), // Usando o nome correto da coluna
                            rs.getInt("idade")
                    );
                    lista.add(c);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cachorros por raça: " + e.getMessage());
        }
        return lista;
    }

    // Método para verificar a estrutura da tabela (útil para debug)
    public void verificarEstruturaTabelaCachorro() {
        String sql = "SELECT * FROM Cachorro LIMIT 1";
        try (Connection con = Conexao.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            System.out.println("Colunas encontradas na tabela Cachorro:");
            for (int i = 1; i <= columnCount; i++) {
                System.out.println("Coluna " + i + ": " + metaData.getColumnName(i));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar estrutura da tabela: " + e.getMessage());
        }
    }
}
