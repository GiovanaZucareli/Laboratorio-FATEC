package com.example.atividade2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class TelaRobo extends Application {

    private Robo robo;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        // Criando os campos de entrada
        TextField txtModelo = new TextField();
        txtModelo.setPromptText("Modelo");

        TextField txtEnergia = new TextField();
        txtEnergia.setPromptText("Energia (%)");

        TextField txtCor = new TextField();
        txtCor.setPromptText("Cor");

        // Criando os botões
        Button btnCriar = new Button("Criar robô");
        Button btnAndar = new Button("Andar");
        Button btnFalar = new Button("Falar");
        Button btnCarregar = new Button("Carregar Bateria");

        // Desabilitando os botões de ação até a criação do robô
        btnAndar.setDisable(true);
        btnFalar.setDisable(true);
        btnCarregar.setDisable(true);

        // Área para exibição dos resultados
        TextArea resultado = new TextArea();
        resultado.setEditable(false);
        resultado.setPrefHeight(100);

        // Carregando a imagem do robô
        Image roboImage = new Image(getClass().getResourceAsStream("/robo.jpg"));
        ImageView roboImageView = new ImageView(roboImage);

        // Aumentando o tamanho da imagem
        double imageWidth = 200;  // Novo tamanho da largura
        double imageHeight = 200; // Novo tamanho da altura
        roboImageView.setFitWidth(imageWidth);
        roboImageView.setFitHeight(imageHeight);

        // Aplicando o border-radius sem perder a resolução usando setClip
        Rectangle clip = new Rectangle(imageWidth, imageHeight); // Ajustando o recorte para a nova dimensão
        clip.setArcWidth(30);  // Arredondando mais as bordas
        clip.setArcHeight(30); // Arredondando mais as bordas
        roboImageView.setClip(clip); // Aplica o recorte

        // Evento para criar o robô
        btnCriar.setOnAction(e -> {
            try {
                String modelo = txtModelo.getText();
                int energia = Integer.parseInt(txtEnergia.getText());
                String cor = txtCor.getText();

                if (energia < 0 || energia > 100) {
                    resultado.setText("Energia inválida. Digite um valor entre 0 e 100.");
                    return;
                }

                robo = new Robo(modelo, energia, cor);

                resultado.setText("Robô criado com sucesso!");
                btnAndar.setDisable(false);
                btnFalar.setDisable(false);
                btnCarregar.setDisable(false);
            } catch (NumberFormatException ex) {
                resultado.setText("Energia inválida. Digite um número.");
            }
        });

        // Eventos para as ações do robô
        btnAndar.setOnAction(e -> resultado.setText(robo.andar()));
        btnFalar.setOnAction(e -> resultado.setText(robo.falar()));
        btnCarregar.setOnAction(e -> resultado.setText(robo.carregarBateria()));

        // Layout principal com VBox
        VBox layout = new VBox(10,
                roboImageView,  // Adicionando a imagem antes dos campos de texto
                new Label("Modelo:"), txtModelo,
                new Label("Energia (%):"), txtEnergia,
                new Label("Cor:"), txtCor,
                btnCriar, new HBox(10, btnAndar, btnFalar, btnCarregar),
                resultado
        );

        // Aplicando o padding no layout
        layout.setPadding(new javafx.geometry.Insets(15));

        // Criando a cena e adicionando o arquivo de estilo CSS
        Scene scene = new Scene(layout, 300, 700);
        scene.getStylesheets().add(getClass().getResource("/estiloRobo.css").toExternalForm());

        // Configurando o palco
        stage.setScene(scene);
        stage.setTitle("Cadastro de Robô");
        stage.show();
    }
}
