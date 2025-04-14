package com.example.atividade2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TelaJogador extends Application {

    private Jogador jogador;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        TextField txtNome = new TextField();
        txtNome.setPromptText("Nome do jogador");

        TextField txtTime = new TextField();
        txtTime.setPromptText("Time do jogador");

        TextField txtPosicao = new TextField();
        txtPosicao.setPromptText("Posição do jogador");

        Button btnCriar = new Button("Criar jogador");
        Button btnChutar = new Button("Chutar");
        Button btnDriblar = new Button("Driblar");
        Button btnCorrer = new Button("Correr");

        btnChutar.setDisable(true);
        btnDriblar.setDisable(true);
        btnCorrer.setDisable(true);

        TextArea resultado = new TextArea();
        resultado.setEditable(false);
        resultado.setPrefHeight(100);

        btnCriar.setOnAction(e -> {
            String nome = txtNome.getText();
            String time = txtTime.getText();
            String posicao = txtPosicao.getText();

            if (nome.isEmpty() || time.isEmpty() || posicao.isEmpty()) {
                resultado.setText("Preencha todos os campos corretamente.");
            } else {
                jogador = new Jogador(nome, time, posicao);
                resultado.setText("Jogador criado com sucesso!");
                btnChutar.setDisable(false);
                btnDriblar.setDisable(false);
                btnCorrer.setDisable(false);
            }
        });

        btnChutar.setOnAction(e -> resultado.setText(jogador.chutar()));
        btnDriblar.setOnAction(e -> resultado.setText(jogador.driblar()));
        btnCorrer.setOnAction(e -> resultado.setText(jogador.correr()));

        VBox layout = new VBox(10,
                new Label("Nome:"), txtNome,
                new Label("Time:"), txtTime,
                new Label("Posição:"), txtPosicao,
                btnCriar, new HBox(10, btnChutar, btnDriblar, btnCorrer),
                resultado
        );

        layout.setPadding(new javafx.geometry.Insets(15));

        stage.setScene(new Scene(layout, 400, 400));
        stage.setTitle("Cadastro de Jogador");
        stage.show();
    }
}
