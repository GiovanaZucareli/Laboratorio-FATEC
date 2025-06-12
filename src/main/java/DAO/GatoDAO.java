package DAO;

import Classes.Gato;
import DataBase.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GatoDAO {

    public boolean criar(Gato gato) {
        String sql = "INSERT INTO Gato (nome, idade, raca, cor) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, gato.getNome());
            stmt.setInt(2, gato.getIdade());
            stmt.setString(3, gato.getRaca());
            stmt.setString(4, gato.getCor());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao criar gato: " + e.getMessage());
            return false;
        }
    }

    public List<Gato> listarTodos() {
        List<Gato> lista = new ArrayList<>();
        String sql = "SELECT * FROM Gato";

        try (Connection con = Conexao.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Gato gato = new Gato(
                        rs.getInt("id_Gato"),
                        rs.getString("nome"),
                        rs.getInt("idade"),
                        rs.getString("raca"),
                        rs.getString("cor")
                );
                lista.add(gato);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar gatos: " + e.getMessage());
        }
        return lista;
    }

    public boolean atualizar(Gato gato) {
        String sql = "UPDATE Gato SET nome=?, idade=?, raca=?, cor=? WHERE id_Gato=?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, gato.getNome());
            ps.setInt(2, gato.getIdade());
            ps.setString(3, gato.getRaca());
            ps.setString(4, gato.getCor());
            ps.setInt(5, gato.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar gato: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM Gato WHERE id_Gato=?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao excluir gato: " + e.getMessage());
            return false;
        }
    }

    public Gato buscarPorId(int id) {
        String sql = "SELECT * FROM Gato WHERE id_Gato = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Gato(
                            rs.getInt("id_Gato"),
                            rs.getString("nome"),
                            rs.getInt("idade"),
                            rs.getString("raca"),
                            rs.getString("cor")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar gato: " + e.getMessage());
        }
        return null;
    }

    public List<Gato> buscarPorRaca(String raca) {
        List<Gato> lista = new ArrayList<>();
        String sql = "SELECT * FROM Gato WHERE raca LIKE ?";

        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + raca + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Gato gato = new Gato(
                            rs.getInt("id_Gato"),
                            rs.getString("nome"),
                            rs.getInt("idade"),
                            rs.getString("raca"),
                            rs.getString("cor")
                    );
                    lista.add(gato);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar gatos por raça: " + e.getMessage());
        }
        return lista;
    }

    // Método para verificar a estrutura da tabela (útil para debug)
    public void verificarEstruturaTabelaGato() {
        String sql = "SELECT * FROM Gato LIMIT 1";
        try (Connection con = Conexao.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            System.out.println("Colunas encontradas na tabela Gato:");
            for (int i = 1; i <= columnCount; i++) {
                System.out.println("Coluna " + i + ": " + metaData.getColumnName(i));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar estrutura da tabela: " + e.getMessage());
        }
    }
}
