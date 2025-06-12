package DAO;

import Classes.Pintores;
import DataBase.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PintoresDAO {

    public boolean criar(Pintores pintor) {
        String sql = "INSERT INTO Pintores (nome, nacionalidade, idade) VALUES (?, ?, ?)";

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, pintor.getNome());
            stmt.setString(2, pintor.getNacionalidade());
            stmt.setInt(3, pintor.getIdade());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao criar pintor: " + e.getMessage());
            return false;
        }
    }

    public List<Pintores> listarTodos() {
        List<Pintores> lista = new ArrayList<>();
        String sql = "SELECT * FROM Pintores";

        try (Connection con = Conexao.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Pintores pintor = new Pintores(
                        rs.getInt("idPintor"),
                        rs.getString("nome"),
                        rs.getString("nacionalidade"),
                        rs.getInt("idade")
                );
                lista.add(pintor);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar pintores: " + e.getMessage());
        }
        return lista;
    }

    public boolean atualizar(Pintores pintor) {
        String sql = "UPDATE Pintores SET nome=?, nacionalidade=?, idade=? WHERE idPintor=?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pintor.getNome());
            ps.setString(2, pintor.getNacionalidade());
            ps.setInt(3, pintor.getIdade());
            ps.setInt(4, pintor.getIdPintor());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar pintor: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM Pintores WHERE idPintor=?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao excluir pintor: " + e.getMessage());
            return false;
        }
    }

    public Pintores buscarPorId(int id) {
        String sql = "SELECT * FROM Pintores WHERE idPintor = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Pintores(
                            rs.getInt("idPintor"),
                            rs.getString("nome"),
                            rs.getString("nacionalidade"),
                            rs.getInt("idade")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pintor: " + e.getMessage());
        }
        return null;
    }

    public List<Pintores> buscarPorNacionalidade(String nacionalidade) {
        List<Pintores> lista = new ArrayList<>();
        String sql = "SELECT * FROM Pintores WHERE nacionalidade LIKE ?";

        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + nacionalidade + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Pintores pintor = new Pintores(
                            rs.getInt("idPintor"),
                            rs.getString("nome"),
                            rs.getString("nacionalidade"),
                            rs.getInt("idade")
                    );
                    lista.add(pintor);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pintores por nacionalidade: " + e.getMessage());
        }
        return lista;
    }

    public List<Pintores> buscarPorNome(String nome) {
        List<Pintores> lista = new ArrayList<>();
        String sql = "SELECT * FROM Pintores WHERE nome LIKE ?";

        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + nome + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Pintores pintor = new Pintores(
                            rs.getInt("idPintor"),
                            rs.getString("nome"),
                            rs.getString("nacionalidade"),
                            rs.getInt("idade")
                    );
                    lista.add(pintor);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pintores por nome: " + e.getMessage());
        }
        return lista;
    }

    public List<Pintores> buscarPorPeriodo(String periodo) {
        List<Pintores> lista = new ArrayList<>();
        String sql = "SELECT * FROM Pintores WHERE periodo LIKE ?";

        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + periodo + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Pintores pintor = new Pintores(
                            rs.getInt("idPintor"),
                            rs.getString("nome"),
                            rs.getString("nacionalidade"),
                            rs.getInt("idade")
                    );
                    lista.add(pintor);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pintores por período: " + e.getMessage());
        }
        return lista;
    }

    // Método para verificar a estrutura da tabela (útil para debug)
    public void verificarEstruturaTabelaPintores() {
        String sql = "SELECT * FROM Pintores LIMIT 1";
        try (Connection con = Conexao.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            System.out.println("Colunas encontradas na tabela Pintores:");
            for (int i = 1; i <= columnCount; i++) {
                System.out.println("Coluna " + i + ": " + metaData.getColumnName(i));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar estrutura da tabela: " + e.getMessage());
        }
    }
}
