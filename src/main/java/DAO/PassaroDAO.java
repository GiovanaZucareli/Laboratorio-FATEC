package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Classes.Passaro;
import DataBase.Conexao;

import java.sql.Connection;


public class PassaroDAO {

    public void criar (Passaro passaro) {
        String sql = "INSERT INTO Passaro (cor, especie) VALUES (?, ?)";

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, passaro.getcor());
            stmt.setString(2, passaro.getespecie());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao gravar passaro: " + e.getMessage());
        }
    }

    
}
