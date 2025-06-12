package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Classes.Ciclista;
import DataBase.Conexao;

public class CiclistaDAO {

    public boolean criar(Ciclista ciclista) {
        String sql = "INSERT INTO Ciclista (nome, equipe, nacionalidade, idade) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, ciclista.getNome());
            stmt.setString(2, ciclista.getEquipe());
            stmt.setString(3, ciclista.getNacionalidade());
            stmt.setInt(4, ciclista.getIdade());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao criar ciclista: " + e.getMessage());
            return false;
        }
    }

    public List<Ciclista> listarTodos() {
        List<Ciclista> lista = new ArrayList<>();
        String sql = "SELECT * FROM Ciclista";

        try (Connection con = Conexao.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Ciclista ciclista = new Ciclista(
                        rs.getString("nome"),
                        rs.getString("equipe"),
                        rs.getString("nacionalidade"),
                        rs.getInt("idade")
                );
                ciclista.setIdCiclista(rs.getInt("id_Ciclista")); // Usando o padrão com underscore
                lista.add(ciclista);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar ciclistas: " + e.getMessage());
        }
        return lista;
    }

    public boolean atualizar(Ciclista ciclista) {
        String sql = "UPDATE Ciclista SET nome=?, equipe=?, nacionalidade=?, idade=? WHERE id_Ciclista=?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ciclista.getNome());
            ps.setString(2, ciclista.getEquipe());
            ps.setString(3, ciclista.getNacionalidade());
            ps.setInt(4, ciclista.getIdade());
            ps.setInt(5, ciclista.getIdCiclista());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar ciclista: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM Ciclista WHERE id_Ciclista=?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao excluir ciclista: " + e.getMessage());
            return false;
        }
    }

    public Ciclista buscarPorId(int id) {
        String sql = "SELECT * FROM Ciclista WHERE id_Ciclista = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Ciclista ciclista = new Ciclista(
                            rs.getString("nome"),
                            rs.getString("equipe"),
                            rs.getString("nacionalidade"),
                            rs.getInt("idade")
                    );
                    ciclista.setIdCiclista(rs.getInt("id_Ciclista"));
                    return ciclista;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar ciclista: " + e.getMessage());
        }
        return null;
    }

    // Método para verificar a estrutura da tabela (útil para debug)
    public void verificarEstruturaTabelaCiclista() {
        String sql = "SELECT * FROM Ciclista LIMIT 1";
        try (Connection con = Conexao.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            System.out.println("Colunas encontradas na tabela Ciclista:");
            for (int i = 1; i <= columnCount; i++) {
                System.out.println("Coluna " + i + ": " + metaData.getColumnName(i));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar estrutura da tabela: " + e.getMessage());
        }
    }
}
