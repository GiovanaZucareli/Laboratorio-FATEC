package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Classes.Carro;
import DataBase.Conexao;

import java.sql.Connection;

public class CarroDAO {

    public void criar(Carro carro) {
        String sql = "INSERT INTO Carro(marca, cor) VALUES (?, ?)";

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, carro.getMarca());
            stmt.setString(2, carro.getCor());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao gravar carro: " + e.getMessage());
        }
    }

}
