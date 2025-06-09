package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Classes.Ciclista;
import DataBase.Conexao;

import java.sql.Connection;

public class CiclistaDAO {

    public void criar (Ciclista ciclista) {
        String sql = "INSERT INTO Ciclista (nome, bicliceta, idade) VALUES (?, ?, ?)";

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, ciclista.getNome());
            stmt.setString(2, ciclista.getBicicleta());
            stmt.setInt(3, ciclista.getIdade());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao gravar ciclista: " + e.getMessage());
        }
    }


}
