package com.example.atividade2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class TelaPintores extends Application {

    private Pintores pintores;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        // Criando os campos de entrada
        TextField txtNome = new TextField();
        txtNome.setPromptText("Nome do pintor");

        TextField txtCorDaParede = new TextField();
        txtCorDaParede.setPromptText("Cor da parede");

        TextField txtTipoDeTinta = new TextField();
        txtTipoDeTinta.setPromptText("Tipo de tinta");

        // Criando os botões
        Button btnCriar = new Button("Criar Pintores");
        Button btnAdicionarPintor = new Button("Adicionar Pintor");
        Button btnMisturarCores = new Button("Misturar Cores");
        Button btnPintar = new Button("Pintar");
        Button btnLimparPincel = new Button("Limpar Pincel");

        // Desabilitando os botões de ação até a criação dos pintores
        btnAdicionarPintor.setDisable(true);
        btnMisturarCores.setDisable(true);
        btnPintar.setDisable(true);
        btnLimparPincel.setDisable(true);

        // Área para exibição dos resultados
        TextArea resultado = new TextArea();
        resultado.setEditable(false);
        resultado.setPrefHeight(100);

        // Carregando a imagem dos pintores
        Image pinturaImage = new Image(getClass().getResourceAsStream("/pintores.jpg"));
        ImageView pinturaImageView = new ImageView(pinturaImage);

        // Redimensionando a imagem
        double imageWidth = 200;  // Novo tamanho da largura
        double imageHeight = 200; // Novo tamanho da altura
        pinturaImageView.setFitWidth(imageWidth);
        pinturaImageView.setFitHeight(imageHeight);

        // Aplicando o border-radius sem perder a resolução usando setClip
        Rectangle clip = new Rectangle(imageWidth, imageHeight); // Ajustando o recorte para a nova dimensão
        clip.setArcWidth(30);  // Arredondando mais as bordas
        clip.setArcHeight(30); // Arredondando mais as bordas
        pinturaImageView.setClip(clip); // Aplica o recorte

        // Evento para criar os pintores
        btnCriar.setOnAction(e -> {
            String corDaParede = txtCorDaParede.getText();
            String tipoDeTinta = txtTipoDeTinta.getText();

            if (corDaParede.isEmpty() || tipoDeTinta.isEmpty()) {
                resultado.setText("Preencha todos os campos corretamente.");
            } else {
                pintores = new Pintores(corDaParede, tipoDeTinta);
                resultado.setText("Pintores criados com sucesso!");
                btnAdicionarPintor.setDisable(false);
                btnMisturarCores.setDisable(false);
                btnPintar.setDisable(false);
                btnLimparPincel.setDisable(false);
            }
        });

        // Evento para adicionar um pintor à equipe
        btnAdicionarPintor.setOnAction(e -> {
            String nome = txtNome.getText();
            if (!nome.isEmpty()) {
                pintores.adicionarPintor(nome);
                resultado.setText(nome + " foi adicionado à equipe de pintores.");
                txtNome.clear();
            } else {
                resultado.setText("Digite o nome do pintor.");
            }
        });

        // Eventos para misturar cores, pintar e limpar o pincel
        btnMisturarCores.setOnAction(e -> resultado.setText(pintores.misturarCores()));
        btnPintar.setOnAction(e -> resultado.setText(pintores.pintar()));
        btnLimparPincel.setOnAction(e -> resultado.setText(pintores.limparPincel()));

        // Layout principal com VBox
        VBox layout = new VBox(10,
                pinturaImageView,  // Adicionando a imagem antes dos campos de texto
                new Label("Nome do Pintor:"), txtNome,
                new Label("Cor da Parede:"), txtCorDaParede,
                new Label("Tipo de Tinta:"), txtTipoDeTinta,
                btnCriar, new HBox(10, btnAdicionarPintor),
                new HBox(10, btnMisturarCores, btnPintar, btnLimparPincel),
                resultado
        );

        // Aplicando o padding no layout
        layout.setPadding(new javafx.geometry.Insets(15));

        // Criando a cena e adicionando o arquivo de estilo CSS
        Scene scene = new Scene(layout, 300, 700);
        scene.getStylesheets().add(getClass().getResource("/estiloPintores.css").toExternalForm());

        // Configurando o palco
        stage.setScene(scene);
        stage.setTitle("Cadastro de Pintores");
        stage.show();
    }
}
