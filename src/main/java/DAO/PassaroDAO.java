package DAO;

import Classes.Passaro;
import DataBase.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassaroDAO {

    public boolean criar(Passaro passaro) {
        String sql = "INSERT INTO Passaro (nome, idade, especie, cor) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, passaro.getNome());
            stmt.setInt(2, passaro.getIdade());
            stmt.setString(3, passaro.getEspecie());
            stmt.setString(4, passaro.getCor());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao criar pássaro: " + e.getMessage());
            return false;
        }
    }

    public List<Passaro> listarTodos() {
        List<Passaro> lista = new ArrayList<>();
        String sql = "SELECT * FROM Passaro";

        try (Connection con = Conexao.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Passaro passaro = new Passaro(
                        rs.getInt("idPassaro"),
                        rs.getString("nome"),
                        rs.getInt("idade"),
                        rs.getString("especie"),
                        rs.getString("cor")
                );
                lista.add(passaro);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar pássaros: " + e.getMessage());
        }
        return lista;
    }

    public boolean atualizar(Passaro passaro) {
        String sql = "UPDATE Passaro SET nome=?, idade=?, especie=?, cor=? WHERE idPassaro=?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, passaro.getNome());
            ps.setInt(2, passaro.getIdade());
            ps.setString(3, passaro.getEspecie());
            ps.setString(4, passaro.getCor());
            ps.setInt(5, passaro.getIdPassaro());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar pássaro: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM Passaro WHERE idPassaro=?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao excluir pássaro: " + e.getMessage());
            return false;
        }
    }

    public Passaro buscarPorId(int id) {
        String sql = "SELECT * FROM Passaro WHERE idPassaro = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Passaro(
                            rs.getInt("idPassaro"),
                            rs.getString("nome"),
                            rs.getInt("idade"),
                            rs.getString("especie"),
                            rs.getString("cor")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pássaro: " + e.getMessage());
        }
        return null;
    }

    public List<Passaro> buscarPorEspecie(String especie) {
        List<Passaro> lista = new ArrayList<>();
        String sql = "SELECT * FROM Passaro WHERE especie LIKE ?";

        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + especie + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Passaro passaro = new Passaro(
                            rs.getInt("idPassaro"),
                            rs.getString("nome"),
                            rs.getInt("idade"),
                            rs.getString("especie"),
                            rs.getString("cor")
                    );
                    lista.add(passaro);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pássaros por espécie: " + e.getMessage());
        }
        return lista;
    }

    public List<Passaro> buscarPorCor(String cor) {
        List<Passaro> lista = new ArrayList<>();
        String sql = "SELECT * FROM Passaro WHERE cor LIKE ?";

        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + cor + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Passaro passaro = new Passaro(
                            rs.getInt("idPassaro"),
                            rs.getString("nome"),
                            rs.getInt("idade"),
                            rs.getString("especie"),
                            rs.getString("cor")
                    );
                    lista.add(passaro);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pássaros por cor: " + e.getMessage());
        }
        return lista;
    }

    // Método para verificar a estrutura da tabela (útil para debug)
    public void verificarEstruturaTabelaPassaro() {
        String sql = "SELECT * FROM Passaro LIMIT 1";
        try (Connection con = Conexao.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            System.out.println("Colunas encontradas na tabela Passaro:");
            for (int i = 1; i <= columnCount; i++) {
                System.out.println("Coluna " + i + ": " + metaData.getColumnName(i));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar estrutura da tabela: " + e.getMessage());
        }
    }
}
