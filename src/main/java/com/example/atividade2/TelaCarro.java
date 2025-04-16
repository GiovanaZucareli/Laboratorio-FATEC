package com.example.atividade2;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class TelaCarro extends Application {

    private Carro carro;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        // Campos de entrada
        TextField txtMarca = new TextField();
        txtMarca.setPromptText("Marca");

        TextField txtCor = new TextField();
        txtCor.setPromptText("Cor");

        TextField txtVelocidade = new TextField();
        txtVelocidade.setPromptText("Velocidade atual (km/h)");

        Button btnCriar = new Button("Criar carro");
        Button btnAcelerar = new Button("Acelerar");
        Button btnFrear = new Button("Frear");
        Button btnVirar = new Button("Virar");

        // Desabilita os botões de ação até o carro ser criado
        btnAcelerar.setDisable(true);
        btnFrear.setDisable(true);
        btnVirar.setDisable(true);

        TextArea resultado = new TextArea();
        resultado.setEditable(false);
        resultado.setPrefHeight(100);

        // Carregando a imagem do carro
        Image carroImage = new Image(getClass().getResourceAsStream("/carro.jpg"));
        ImageView carroImageView = new ImageView(carroImage);

        // Redimensionando e arredondando a imagem
        double imageWidth = 250;
        double imageHeight = 150;
        carroImageView.setFitWidth(imageWidth);
        carroImageView.setFitHeight(imageHeight);

        Rectangle clip = new Rectangle(imageWidth, imageHeight);
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        carroImageView.setClip(clip);

        // Botão Criar Carro
        btnCriar.setOnAction(e -> {
            try {
                String marca = txtMarca.getText();
                String cor = txtCor.getText();
                int velocidade = Integer.parseInt(txtVelocidade.getText());
                carro = new Carro(marca, cor, velocidade);

                resultado.setText("Carro criado com sucesso!");
                btnAcelerar.setDisable(false);
                btnFrear.setDisable(false);
                btnVirar.setDisable(false);
            } catch (NumberFormatException ex) {
                resultado.setText("Velocidade inválida. Digite um número.");
            }
        });

        // Botões de ação
        btnAcelerar.setOnAction(e -> resultado.setText(carro.acelerar()));
        btnFrear.setOnAction(e -> resultado.setText(carro.frear()));
        btnVirar.setOnAction(e -> resultado.setText(carro.virar()));

        // Organizando o layout
        VBox layout = new VBox(15,
                carroImageView,  // Adicionando a imagem do carro no topo
                new Label("Marca:"), txtMarca,
                new Label("Cor:"), txtCor,
                new Label("Velocidade atual (km/h):"), txtVelocidade,
                btnCriar, new HBox(10, btnAcelerar, btnFrear, btnVirar),
                resultado
        );
        layout.setPadding(new javafx.geometry.Insets(15));
        layout.setAlignment(Pos.CENTER);

        // Aplicando a folha de estilo para a tela Carro
        Scene scene = new Scene(layout, 600, 600);
        scene.getStylesheets().add(getClass().getResource("/estiloCarro.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Cadastro de Carro");
        stage.show();
    }
}
