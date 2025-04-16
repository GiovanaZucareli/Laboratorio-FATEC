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

public class TelaCiclista extends Application {

    private Ciclista ciclista;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        // Campos de entrada
        TextField txtNome = new TextField();
        txtNome.setPromptText("Nome");

        TextField txtIdade = new TextField();
        txtIdade.setPromptText("Idade");

        TextField txtTipoBike = new TextField();
        txtTipoBike.setPromptText("Tipo da bicicleta");

        Button btnCriar = new Button("Criar ciclista");
        Button btnPedalar = new Button("Pedalar");
        Button btnFrear = new Button("Frear");
        Button btnVirar = new Button("Virar");

        // Desabilita os botões de ação até o ciclista ser criado
        btnPedalar.setDisable(true);
        btnFrear.setDisable(true);
        btnVirar.setDisable(true);

        TextArea resultado = new TextArea();
        resultado.setEditable(false);
        resultado.setPrefHeight(100);

        // Carregando a imagem do ciclista
        Image ciclistaImage = new Image(getClass().getResourceAsStream("/ciclista.jpg"));
        ImageView ciclistaImageView = new ImageView(ciclistaImage);

        // Redimensionando e arredondando a imagem
        double imageWidth = 200;
        double imageHeight = 200;
        ciclistaImageView.setFitWidth(imageWidth);
        ciclistaImageView.setFitHeight(imageHeight);

        Rectangle clip = new Rectangle(imageWidth, imageHeight);
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        ciclistaImageView.setClip(clip);

        // Botão Criar Ciclista
        btnCriar.setOnAction(e -> {
            try {
                String nome = txtNome.getText();
                int idade = Integer.parseInt(txtIdade.getText());
                String tipoBike = txtTipoBike.getText();
                ciclista = new Ciclista(nome, idade, tipoBike);

                resultado.setText("Ciclista criado com sucesso!");
                btnPedalar.setDisable(false);
                btnFrear.setDisable(false);
                btnVirar.setDisable(false);
            } catch (NumberFormatException ex) {
                resultado.setText("Idade inválida. Digite um número.");
            }
        });

        // Botões de ação
        btnPedalar.setOnAction(e -> resultado.setText(ciclista.pedalar()));
        btnFrear.setOnAction(e -> resultado.setText(ciclista.frear()));
        btnVirar.setOnAction(e -> resultado.setText(ciclista.virar()));

        // Organizando o layout
        VBox layout = new VBox(15,
                ciclistaImageView,  // Adicionando a imagem do ciclista no topo
                new Label("Nome:"), txtNome,
                new Label("Idade:"), txtIdade,
                new Label("Tipo da bicicleta:"), txtTipoBike,
                btnCriar, new HBox(10, btnPedalar, btnFrear, btnVirar),
                resultado
        );
        layout.setPadding(new javafx.geometry.Insets(15));
        layout.setAlignment(Pos.CENTER);

        // Aplicando a folha de estilo para a tela Ciclista
        Scene scene = new Scene(layout, 300, 700);
        scene.getStylesheets().add(getClass().getResource("/estiloCiclista.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Cadastro de Ciclista");
        stage.show();
    }
}
