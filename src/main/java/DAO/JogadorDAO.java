package DAO;

import Classes.Jogador;
import DataBase.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JogadorDAO {

    public boolean criar(Jogador jogador) {
        String sql = "INSERT INTO Jogador (nome, idade, posicao, time) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, jogador.getNome());
            stmt.setInt(2, jogador.getIdade());
            stmt.setString(3, jogador.getPosicao());
            stmt.setString(4, jogador.getTime());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao criar jogador: " + e.getMessage());
            return false;
        }
    }

    public List<Jogador> listarTodos() {
        List<Jogador> lista = new ArrayList<>();
        String sql = "SELECT * FROM Jogador";

        try (Connection con = Conexao.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Jogador jogador = new Jogador(
                        rs.getInt("idJogador"),
                        rs.getString("nome"),
                        rs.getInt("idade"),
                        rs.getString("posicao"),
                        rs.getString("time")
                );
                lista.add(jogador);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar jogadores: " + e.getMessage());
        }
        return lista;
    }

    public boolean atualizar(Jogador jogador) {
        String sql = "UPDATE Jogador SET nome=?, idade=?, posicao=?, time=? WHERE idJogador=?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, jogador.getNome());
            ps.setInt(2, jogador.getIdade());
            ps.setString(3, jogador.getPosicao());
            ps.setString(4, jogador.getTime());
            ps.setInt(5, jogador.getIdJogador());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar jogador: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM Jogador WHERE idJogador=?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao excluir jogador: " + e.getMessage());
            return false;
        }
    }

    public Jogador buscarPorId(int id) {
        String sql = "SELECT * FROM Jogador WHERE idJogador = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Jogador(
                            rs.getInt("idJogador"),
                            rs.getString("nome"),
                            rs.getInt("idade"),
                            rs.getString("posicao"),
                            rs.getString("time")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar jogador: " + e.getMessage());
        }
        return null;
    }

    public List<Jogador> buscarPorTime(String time) {
        List<Jogador> lista = new ArrayList<>();
        String sql = "SELECT * FROM Jogador WHERE time LIKE ?";

        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + time + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Jogador jogador = new Jogador(
                            rs.getInt("idJogador"),
                            rs.getString("nome"),
                            rs.getInt("idade"),
                            rs.getString("posicao"),
                            rs.getString("time")
                    );
                    lista.add(jogador);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar jogadores por time: " + e.getMessage());
        }
        return lista;
    }

    public List<Jogador> buscarPorPosicao(String posicao) {
        List<Jogador> lista = new ArrayList<>();
        String sql = "SELECT * FROM Jogador WHERE posicao LIKE ?";

        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + posicao + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Jogador jogador = new Jogador(
                            rs.getInt("idJogador"),
                            rs.getString("nome"),
                            rs.getInt("idade"),
                            rs.getString("posicao"),
                            rs.getString("time")
                    );
                    lista.add(jogador);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar jogadores por posição: " + e.getMessage());
        }
        return lista;
    }

    // Método para verificar a estrutura da tabela (útil para debug)
    public void verificarEstruturaTabelaJogador() {
        String sql = "SELECT * FROM Jogador LIMIT 1";
        try (Connection con = Conexao.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            System.out.println("Colunas encontradas na tabela Jogador:");
            for (int i = 1; i <= columnCount; i++) {
                System.out.println("Coluna " + i + ": " + metaData.getColumnName(i));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar estrutura da tabela: " + e.getMessage());
        }
    }
}
