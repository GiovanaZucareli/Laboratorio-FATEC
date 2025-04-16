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

public class TelaCachorro extends Application {

    private Cachorro cachorro;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        // Campos de entrada
        TextField txtRaca = new TextField();
        txtRaca.setPromptText("Raça");

        TextField txtIdade = new TextField();
        txtIdade.setPromptText("Idade");

        TextField txtCor = new TextField();
        txtCor.setPromptText("Cor");

        Button btnCriar = new Button("Criar Cachorro");
        Button btnCorrer = new Button("Correr");
        Button btnLatir = new Button("Latir");
        Button btnAbanar = new Button("Abanar Rabo");

        // Desabilita os botões de ação até o cachorro ser criado
        btnCorrer.setDisable(true);
        btnLatir.setDisable(true);
        btnAbanar.setDisable(true);

        TextArea resultado = new TextArea();
        resultado.setEditable(false);
        resultado.setPrefHeight(100);

        // Carregando a imagem do cachorro
        Image cachorroImage = new Image(getClass().getResourceAsStream("/cachorro.jpg"));
        ImageView cachorroImageView = new ImageView(cachorroImage);

        // Redimensionando e arredondando a imagem
        double imageWidth = 250;
        double imageHeight = 150;
        cachorroImageView.setFitWidth(imageWidth);
        cachorroImageView.setFitHeight(imageHeight);

        Rectangle clip = new Rectangle(imageWidth, imageHeight);
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        cachorroImageView.setClip(clip);

        // Botão Criar Cachorro
        btnCriar.setOnAction(e -> {
            try {
                String raca = txtRaca.getText();
                int idade = Integer.parseInt(txtIdade.getText());
                String cor = txtCor.getText();
                cachorro = new Cachorro(raca, idade, cor);

                resultado.setText("Cachorro criado com sucesso!");
                btnCorrer.setDisable(false);
                btnLatir.setDisable(false);
                btnAbanar.setDisable(false);
            } catch (NumberFormatException ex) {
                resultado.setText("Idade inválida. Digite um número.");
            }
        });

        // Botões de ação
        btnCorrer.setOnAction(e -> resultado.setText(cachorro.correr()));
        btnLatir.setOnAction(e -> resultado.setText(cachorro.latir()));
        btnAbanar.setOnAction(e -> resultado.setText(cachorro.abanarRabo()));

        // Organizando o layout
        VBox layout = new VBox(15,
                cachorroImageView,  // Adicionando a imagem do cachorro no topo
                new Label("Raça:"), txtRaca,
                new Label("Idade:"), txtIdade,
                new Label("Cor:"), txtCor,
                btnCriar, new HBox(10, btnCorrer, btnLatir, btnAbanar),
                resultado
        );
        layout.setPadding(new javafx.geometry.Insets(15));
        layout.setAlignment(Pos.CENTER);

        // Aplicando a folha de estilo para a tela Cachorro
        Scene scene = new Scene(layout, 300, 700);
        scene.getStylesheets().add(getClass().getResource("/estiloCachorro.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Cadastro de Cachorro");
        stage.show();
    }
}
