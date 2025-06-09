package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Classes.Gato;
import DataBase.Conexao;

import java.sql.Connection;

public class GatoDAO {

    public void criar (Gato gato) {
        String sql = "INSERT INTO Gato (nome, idade, raca, cor) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, gato.getNome());
            stmt.setInt(2, gato.getIdade());
            stmt.setString(3, gato.getRaca());
            stmt.setString(4, gato.getCor());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao gravar gato: " + e.getMessage());
        }
    }

}
