package com.example.atividade2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class TelaPassaro extends Application {

    private Passaro passaro;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        // Criando os campos de entrada
        TextField txtEspecie = new TextField();
        txtEspecie.setPromptText("Espécie");

        TextField txtTamanho = new TextField();
        txtTamanho.setPromptText("Tamanho (cm)");

        TextField txtCor = new TextField();
        txtCor.setPromptText("Cor");

        // Criando os botões
        Button btnCriar = new Button("Criar pássaro");
        Button btnVoar = new Button("Voar");
        Button btnCantar = new Button("Cantar");
        Button btnPousar = new Button("Pousar");

        // Desabilitando os botões de ação até o pássaro ser criado
        btnVoar.setDisable(true);
        btnCantar.setDisable(true);
        btnPousar.setDisable(true);

        // Área para exibição dos resultados
        TextArea resultado = new TextArea();
        resultado.setEditable(false);
        resultado.setPrefHeight(100);

        // Carregando a imagem do pássaro
        Image passaroImage = new Image(getClass().getResourceAsStream("/passaro.jpg"));
        ImageView passaroImageView = new ImageView(passaroImage);

        // Redimensionando e arredondando a imagem
        double imageWidth = 200;
        double imageHeight = 200;
        passaroImageView.setFitWidth(imageWidth);
        passaroImageView.setFitHeight(imageHeight);

        Rectangle clip = new Rectangle(imageWidth, imageHeight);
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        passaroImageView.setClip(clip);

        // Evento de ação para criar o pássaro
        btnCriar.setOnAction(e -> {
            String especie = txtEspecie.getText();
            String tamanho = txtTamanho.getText();
            String cor = txtCor.getText();

            if (especie.isEmpty() || tamanho.isEmpty() || cor.isEmpty()) {
                resultado.setText("Preencha todos os campos corretamente.");
                return;
            }

            passaro = new Passaro(especie, tamanho, cor);
            resultado.setText("Pássaro criado com sucesso!");
            btnVoar.setDisable(false);
            btnCantar.setDisable(false);
            btnPousar.setDisable(false);
        });

        // Eventos das ações
        btnVoar.setOnAction(e -> resultado.setText(passaro.voar()));
        btnCantar.setOnAction(e -> resultado.setText(passaro.cantar()));
        btnPousar.setOnAction(e -> resultado.setText(passaro.pousar()));

        // Layout
        VBox layout = new VBox(10,
                passaroImageView,
                new Label("Espécie:"), txtEspecie,
                new Label("Tamanho (cm):"), txtTamanho,
                new Label("Cor:"), txtCor,
                btnCriar,
                new HBox(10, btnVoar, btnCantar, btnPousar),
                resultado
        );
        layout.setPadding(new javafx.geometry.Insets(15));

        // Cena e estilo
        Scene scene = new Scene(layout, 300, 700);
        scene.getStylesheets().add(getClass().getResource("/estiloPassaro.css").toExternalForm());

        // Palco
        stage.setScene(scene);
        stage.setTitle("Cadastro de Pássaro");
        stage.show();
    }
}
