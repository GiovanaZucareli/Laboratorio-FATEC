package DAO;

import Classes.Crianca;
import DataBase.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CriancaDAO {

    public boolean criar(Crianca crianca) {
        String sql = "INSERT INTO Crianca (nome, idade, turma) VALUES (?, ?, ?)";

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, crianca.getNome());
            stmt.setInt(2, crianca.getIdade());
            stmt.setString(3, crianca.getTurma());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao criar criança: " + e.getMessage());
            return false;
        }
    }

    public List<Crianca> listarTodos() {
        List<Crianca> lista = new ArrayList<>();
        String sql = "SELECT * FROM Crianca";

        try (Connection con = Conexao.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Crianca crianca = new Crianca(
                        rs.getInt("id"), // Usando apenas "id"
                        rs.getString("nome"),
                        rs.getInt("idade"),
                        rs.getString("turma")
                );
                lista.add(crianca);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar crianças: " + e.getMessage());
        }
        return lista;
    }

    public boolean atualizar(Crianca crianca) {
        String sql = "UPDATE Crianca SET nome=?, idade=?, turma=? WHERE id=?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, crianca.getNome());
            ps.setInt(2, crianca.getIdade());
            ps.setString(3, crianca.getTurma());
            ps.setInt(4, crianca.getIdCrianca());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar criança: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM Crianca WHERE id=?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao excluir criança: " + e.getMessage());
            return false;
        }
    }

    public Crianca buscarPorId(int id) {
        String sql = "SELECT * FROM Crianca WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Crianca(
                            rs.getInt("id"), // Usando apenas "id"
                            rs.getString("nome"),
                            rs.getInt("idade"),
                            rs.getString("turma")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar criança: " + e.getMessage());
        }
        return null;
    }

    public List<Crianca> buscarPorTurma(String turma) {
        List<Crianca> lista = new ArrayList<>();
        String sql = "SELECT * FROM Crianca WHERE turma LIKE ?";

        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + turma + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Crianca crianca = new Crianca(
                            rs.getInt("id"), // Usando apenas "id"
                            rs.getString("nome"),
                            rs.getInt("idade"),
                            rs.getString("turma")
                    );
                    lista.add(crianca);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar crianças por turma: " + e.getMessage());
        }
        return lista;
    }

    // Método para verificar a estrutura da tabela (útil para debug)
    public void verificarEstruturaTabelaCrianca() {
        String sql = "SELECT * FROM Crianca LIMIT 1";
        try (Connection con = Conexao.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            System.out.println("Colunas encontradas na tabela Crianca:");
            for (int i = 1; i <= columnCount; i++) {
                System.out.println("Coluna " + i + ": " + metaData.getColumnName(i));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar estrutura da tabela: " + e.getMessage());
        }
    }
}
