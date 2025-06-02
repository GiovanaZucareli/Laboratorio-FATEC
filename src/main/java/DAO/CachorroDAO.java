package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Classes.Cachorro;
import DataBase.Conexao;

import java.sql.Connection;

public class CachorroDAO {

    public void criar(Cachorro cachorro) {
        String sql = "INSERT INTO Cachorro (raca, cor, nome, idade) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, cachorro.getRaca());
            stmt.setString(2, cachorro.getCor());
            stmt.setString(3, cachorro.getNome());
            stmt.setInt(4, cachorro.getIdade());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao gravar curso: " + e.getMessage());
        }
    }
}
