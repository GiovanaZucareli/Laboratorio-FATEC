package com.example.atividade2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class TelaCrianca extends Application {

    private Crianca crianca;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        // Criando os campos de entrada
        TextField txtIdade = new TextField();
        txtIdade.setPromptText("Idade");

        TextField txtPeso = new TextField();
        txtPeso.setPromptText("Peso (kg)");

        TextField txtAltura = new TextField();
        txtAltura.setPromptText("Altura (m)");

        // Criando os botões
        Button btnCriar = new Button("Criar criança");
        Button btnPular = new Button("Pular");
        Button btnCorrer = new Button("Correr");
        Button btnBrincar = new Button("Brincar");

        // Desabilitando os botões de ação até a criança ser criada
        btnPular.setDisable(true);
        btnCorrer.setDisable(true);
        btnBrincar.setDisable(true);

        // Área para exibição dos resultados
        TextArea resultado = new TextArea();
        resultado.setEditable(false);
        resultado.setPrefHeight(100);

        // Carregando a imagem da criança
        Image criancaImage = new Image(getClass().getResourceAsStream("/crianca.jpg"));
        ImageView criancaImageView = new ImageView(criancaImage);

        // Redimensionando e arredondando a imagem
        double imageWidth = 200;
        double imageHeight = 200;
        criancaImageView.setFitWidth(imageWidth);
        criancaImageView.setFitHeight(imageHeight);

        Rectangle clip = new Rectangle(imageWidth, imageHeight);
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        criancaImageView.setClip(clip);

        // Evento de ação para criar a criança
        btnCriar.setOnAction(e -> {
            try {
                int idade = Integer.parseInt(txtIdade.getText());
                double peso = Double.parseDouble(txtPeso.getText());
                double altura = Double.parseDouble(txtAltura.getText());
                crianca = new Crianca(idade, peso, altura);

                resultado.setText("Criança criada com sucesso!");
                btnPular.setDisable(false);
                btnCorrer.setDisable(false);
                btnBrincar.setDisable(false);
            } catch (NumberFormatException ex) {
                resultado.setText("Verifique se os valores inseridos são válidos.");
            }
        });

        // Eventos para as ações de pular, correr e brincar
        btnPular.setOnAction(e -> resultado.setText(crianca.pular()));
        btnCorrer.setOnAction(e -> resultado.setText(crianca.correr()));
        btnBrincar.setOnAction(e -> resultado.setText(crianca.brincar()));

        // Layout principal com VBox
        VBox layout = new VBox(10,
                criancaImageView,  // Adicionando a imagem da criança no topo
                new Label("Idade:"), txtIdade,
                new Label("Peso (kg):"), txtPeso,
                new Label("Altura (m):"), txtAltura,
                btnCriar, new HBox(10, btnPular, btnCorrer, btnBrincar),
                resultado
        );

        // Aplicando o padding no layout
        layout.setPadding(new javafx.geometry.Insets(15));

        // Criando a cena e adicionando o arquivo de estilo CSS
        Scene scene = new Scene(layout, 300, 700);
        scene.getStylesheets().add(getClass().getResource("/estiloCrianca.css").toExternalForm());

        // Configurando o palco
        stage.setScene(scene);
        stage.setTitle("Cadastro de Criança");
        stage.show();
    }
}
