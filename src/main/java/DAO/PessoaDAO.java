package DAO;

import Classes.Pessoa;
import DataBase.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {

    public boolean criar(Pessoa pessoa) {
        String sql = "INSERT INTO Pessoa (nome, idade, cpf) VALUES (?, ?, ?)";

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, pessoa.getNome());
            stmt.setInt(2, pessoa.getIdade());
            stmt.setString(3, pessoa.getCpf());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao criar pessoa: " + e.getMessage());
            return false;
        }
    }

    public List<Pessoa> listarTodos() {
        List<Pessoa> lista = new ArrayList<>();
        String sql = "SELECT * FROM Pessoa";

        try (Connection con = Conexao.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Pessoa pessoa = new Pessoa(
                        rs.getInt("idPessoa"),
                        rs.getString("nome"),
                        rs.getInt("idade"),
                        rs.getString("cpf")
                );
                lista.add(pessoa);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar pessoas: " + e.getMessage());
        }
        return lista;
    }

    public boolean atualizar(Pessoa pessoa) {
        String sql = "UPDATE Pessoa SET nome=?, idade=?, cpf=? WHERE idPessoa=?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pessoa.getNome());
            ps.setInt(2, pessoa.getIdade());
            ps.setString(3, pessoa.getCpf());
            ps.setInt(4, pessoa.getIdPessoa());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar pessoa: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM Pessoa WHERE idPessoa=?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao excluir pessoa: " + e.getMessage());
            return false;
        }
    }

    public Pessoa buscarPorId(int id) {
        String sql = "SELECT * FROM Pessoa WHERE idPessoa = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Pessoa(
                            rs.getInt("idPessoa"),
                            rs.getString("nome"),
                            rs.getInt("idade"),
                            rs.getString("cpf")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pessoa: " + e.getMessage());
        }
        return null;
    }

    public Pessoa buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM Pessoa WHERE cpf = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Pessoa(
                            rs.getInt("idPessoa"),
                            rs.getString("nome"),
                            rs.getInt("idade"),
                            rs.getString("cpf")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pessoa por CPF: " + e.getMessage());
        }
        return null;
    }

    public List<Pessoa> buscarPorNome(String nome) {
        List<Pessoa> lista = new ArrayList<>();
        String sql = "SELECT * FROM Pessoa WHERE nome LIKE ?";

        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + nome + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Pessoa pessoa = new Pessoa(
                            rs.getInt("idPessoa"),
                            rs.getString("nome"),
                            rs.getInt("idade"),
                            rs.getString("cpf")
                    );
                    lista.add(pessoa);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pessoas por nome: " + e.getMessage());
        }
        return lista;
    }

    public boolean cpfJaExiste(String cpf, int idExcluir) {
        String sql = "SELECT COUNT(*) FROM Pessoa WHERE cpf = ? AND idPessoa != ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cpf);
            ps.setInt(2, idExcluir);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar CPF: " + e.getMessage());
        }
        return false;
    }

    // Método para verificar a estrutura da tabela (útil para debug)
    public void verificarEstruturaTabelaPessoa() {
        String sql = "SELECT * FROM Pessoa LIMIT 1";
        try (Connection con = Conexao.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            System.out.println("Colunas encontradas na tabela Pessoa:");
            for (int i = 1; i <= columnCount; i++) {
                System.out.println("Coluna " + i + ": " + metaData.getColumnName(i));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar estrutura da tabela: " + e.getMessage());
        }
    }
}
