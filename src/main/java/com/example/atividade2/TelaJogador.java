package com.example.atividade2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class TelaJogador extends Application {

    private Jogador jogador;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        // Criando os campos de entrada
        TextField txtNome = new TextField();
        txtNome.setPromptText("Nome do jogador");

        TextField txtTime = new TextField();
        txtTime.setPromptText("Time do jogador");

        TextField txtPosicao = new TextField();
        txtPosicao.setPromptText("Posição do jogador");

        // Criando os botões
        Button btnCriar = new Button("Criar jogador");
        Button btnChutar = new Button("Chutar");
        Button btnDriblar = new Button("Driblar");
        Button btnCorrer = new Button("Correr");

        // Desabilitando os botões de ação até o jogador ser criado
        btnChutar.setDisable(true);
        btnDriblar.setDisable(true);
        btnCorrer.setDisable(true);

        // Área para exibição dos resultados
        TextArea resultado = new TextArea();
        resultado.setEditable(false);
        resultado.setPrefHeight(100);

        // Carregando a imagem do jogador
        Image jogadorImage = new Image(getClass().getResourceAsStream("/jogador.jpg"));
        ImageView jogadorImageView = new ImageView(jogadorImage);

        // Redimensionando e arredondando a imagem
        double imageWidth = 200;
        double imageHeight = 200;
        jogadorImageView.setFitWidth(imageWidth);
        jogadorImageView.setFitHeight(imageHeight);

        Rectangle clip = new Rectangle(imageWidth, imageHeight);
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        jogadorImageView.setClip(clip);

        // Evento de ação para criar o jogador
        btnCriar.setOnAction(e -> {
            String nome = txtNome.getText();
            String time = txtTime.getText();
            String posicao = txtPosicao.getText();

            if (nome.isEmpty() || time.isEmpty() || posicao.isEmpty()) {
                resultado.setText("Preencha todos os campos corretamente.");
                return;
            }

            jogador = new Jogador(nome, time, posicao);
            resultado.setText("Jogador criado com sucesso!");
            btnChutar.setDisable(false);
            btnDriblar.setDisable(false);
            btnCorrer.setDisable(false);
        });

        // Eventos das ações
        btnChutar.setOnAction(e -> resultado.setText(jogador.chutar()));
        btnDriblar.setOnAction(e -> resultado.setText(jogador.driblar()));
        btnCorrer.setOnAction(e -> resultado.setText(jogador.correr()));

        // Layout
        VBox layout = new VBox(10,
                jogadorImageView, // Adiciona a imagem no topo
                new Label("Nome:"), txtNome,
                new Label("Time:"), txtTime,
                new Label("Posição:"), txtPosicao,
                btnCriar,
                new HBox(10, btnChutar, btnDriblar, btnCorrer),
                resultado
        );
        layout.setPadding(new javafx.geometry.Insets(15));

        // Criando a cena e adicionando o arquivo de estilo CSS
        Scene scene = new Scene(layout, 300, 700);
        scene.getStylesheets().add(getClass().getResource("/estiloJogador.css").toExternalForm());

        // Configurando o palco
        stage.setScene(scene);
        stage.setTitle("Cadastro de Jogador");
        stage.show();
    }
}
