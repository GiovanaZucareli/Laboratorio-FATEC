package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Classes.Jogador;
import DataBase.Conexao;

import java.sql.Connection;

public class JogadorDAO {

    public void criar (Jogador jogador) {
        String sql = "INSERT INTO Jogador (nome, idade, esporte) VALUES (?, ?, ?)";

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, jogador.getNome());
            stmt.setInt(2, jogador.getIdade());
            stmt.setString(3, jogador.getEsporte());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao gravar jogador: " + e.getMessage());
        }
    }

}
