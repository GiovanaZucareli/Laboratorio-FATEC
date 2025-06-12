package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Classes.Robo;
import DataBase.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class RoboDAO {

    public void criar(Robo robo) {
        String sql = "INSERT INTO Robo (nome, modelo, anoFabricacao) VALUES (?, ?, ?)";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, robo.getNome());
            stmt.setString(2, robo.getModelo());
            stmt.setInt(3, robo.getAnoFabricacao());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Robo> listarTodos() {
        List<Robo> lista = new ArrayList<>();
        String sql = "SELECT * FROM Robo";
        try (Connection con = Conexao.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Robo robo = new Robo(
                        rs.getInt("idRobo"),
                        rs.getString("nome"),
                        rs.getString("modelo"),
                        rs.getInt("anoFabricacao")
                );
                lista.add(robo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void atualizar(Robo robo) {
        String sql = "UPDATE Robo SET nome=?, modelo=?, anoFabricacao=? WHERE idRobo=?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, robo.getNome());
            stmt.setString(2, robo.getModelo());
            stmt.setInt(3, robo.getAnoFabricacao());
            stmt.setInt(4, robo.getIdRobo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluir(int idRobo) {
        String sql = "DELETE FROM Robo WHERE idRobo=?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idRobo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
