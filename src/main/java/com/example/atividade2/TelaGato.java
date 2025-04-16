package com.example.atividade2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class TelaGato extends Application {

    private Gato gato;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        // Criando os campos de entrada
        TextField txtNome = new TextField();
        txtNome.setPromptText("Nome do gato");

        TextField txtCor = new TextField();
        txtCor.setPromptText("Cor do gato");

        TextField txtIdade = new TextField();
        txtIdade.setPromptText("Idade do gato");

        // Criando os botões
        Button btnCriar = new Button("Criar gato");
        Button btnPular = new Button("Pular");
        Button btnMiar = new Button("Miar");
        Button btnCorrer = new Button("Correr");

        // Desabilitando os botões de ação até o gato ser criado
        btnPular.setDisable(true);
        btnMiar.setDisable(true);
        btnCorrer.setDisable(true);

        // Área para exibição dos resultados
        TextArea resultado = new TextArea();
        resultado.setEditable(false);
        resultado.setPrefHeight(100);

        // Carregando a imagem do gato
        Image gatoImage = new Image(getClass().getResourceAsStream("/gatos.jpg"));
        ImageView gatoImageView = new ImageView(gatoImage);

        // Redimensionando e arredondando a imagem
        double imageWidth = 200;
        double imageHeight = 200;
        gatoImageView.setFitWidth(imageWidth);
        gatoImageView.setFitHeight(imageHeight);

        Rectangle clip = new Rectangle(imageWidth, imageHeight);
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        gatoImageView.setClip(clip);

        // Evento de ação para criar o gato
        btnCriar.setOnAction(e -> {
            String nome = txtNome.getText();
            String cor = txtCor.getText();
            String idade = txtIdade.getText();

            if (nome.isEmpty() || cor.isEmpty() || idade.isEmpty()) {
                resultado.setText("Preencha todos os campos corretamente.");
            } else {
                gato = new Gato(nome, cor, idade);
                resultado.setText("Gato criado com sucesso!");
                btnPular.setDisable(false);
                btnMiar.setDisable(false);
                btnCorrer.setDisable(false);
            }
        });

        // Eventos para as ações de pular, miar e correr
        btnPular.setOnAction(e -> resultado.setText(gato.pular()));
        btnMiar.setOnAction(e -> resultado.setText(gato.miar()));
        btnCorrer.setOnAction(e -> resultado.setText(gato.correr()));

        // Layout principal com VBox
        VBox layout = new VBox(10,
                gatoImageView,  // Adicionando a imagem do gato no topo
                new Label("Nome:"), txtNome,
                new Label("Cor:"), txtCor,
                new Label("Idade:"), txtIdade,
                btnCriar, new HBox(10, btnPular, btnMiar, btnCorrer),
                resultado
        );

        // Aplicando o padding no layout
        layout.setPadding(new javafx.geometry.Insets(15));

        // Criando a cena e adicionando o arquivo de estilo CSS
        Scene scene = new Scene(layout, 300, 700);
        scene.getStylesheets().add(getClass().getResource("/estiloGato.css").toExternalForm());

        // Configurando o palco
        stage.setScene(scene);
        stage.setTitle("Cadastro de Gato");
        stage.show();
    }
}
