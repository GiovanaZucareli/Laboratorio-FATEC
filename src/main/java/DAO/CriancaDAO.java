package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Classes.Crianca;
import DataBase.Conexao;

import java.sql.Connection;


public class CriancaDAO {

    public void criar (Crianca crianca) {
        String sql = "INSERT INTO Crianca (nome, idade) VALUES (?, ?)";

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, crianca.getNome());
            stmt.setInt(2, crianca.getIdade());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao gravar crianca: " + e.getMessage());
        }

    }

}
