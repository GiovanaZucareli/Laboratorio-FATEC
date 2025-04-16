package com.example.atividade2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class TelaPessoa extends Application {

    private Pessoa pessoa;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        // Criando os campos de entrada
        TextField txtNome = new TextField();
        txtNome.setPromptText("Nome");

        TextField txtIdade = new TextField();
        txtIdade.setPromptText("Idade");

        TextField txtProfissao = new TextField();
        txtProfissao.setPromptText("Profissão");

        // Criando os botões
        Button btnCriar = new Button("Criar pessoa");
        Button btnDigitar = new Button("Digitar");
        Button btnPensar = new Button("Pensar");
        Button btnDescansar = new Button("Descansar");

        // Desabilitando os botões de ação até a pessoa ser criada
        btnDigitar.setDisable(true);
        btnPensar.setDisable(true);
        btnDescansar.setDisable(true);

        // Área para exibição dos resultados
        TextArea resultado = new TextArea();
        resultado.setEditable(false);
        resultado.setPrefHeight(100);

        // Carregando a imagem da pessoa
        Image pessoaImage = new Image(getClass().getResourceAsStream("/pessoa.jpg"));
        ImageView pessoaImageView = new ImageView(pessoaImage);

        // Redimensionando e arredondando a imagem
        double imageWidth = 200;
        double imageHeight = 200;
        pessoaImageView.setFitWidth(imageWidth);
        pessoaImageView.setFitHeight(imageHeight);

        Rectangle clip = new Rectangle(imageWidth, imageHeight);
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        pessoaImageView.setClip(clip);

        // Evento de ação para criar a pessoa
        btnCriar.setOnAction(e -> {
            try {
                String nome = txtNome.getText();
                int idade = Integer.parseInt(txtIdade.getText());
                String profissao = txtProfissao.getText();

                if (nome.isEmpty() || txtIdade.getText().isEmpty() || profissao.isEmpty()) {
                    resultado.setText("Por favor, preencha todos os campos.");
                    return;
                }

                pessoa = new Pessoa(nome, idade, profissao);

                resultado.setText("Pessoa criada com sucesso!");
                btnDigitar.setDisable(false);
                btnPensar.setDisable(false);
                btnDescansar.setDisable(false);
            } catch (NumberFormatException ex) {
                resultado.setText("Idade inválida. Digite um número.");
            }
        });

        // Eventos para as ações de digitar, pensar e descansar
        btnDigitar.setOnAction(e -> resultado.setText(pessoa.digitar()));
        btnPensar.setOnAction(e -> resultado.setText(pessoa.pensar()));
        btnDescansar.setOnAction(e -> resultado.setText(pessoa.descansar()));

        // Layout principal
        VBox layout = new VBox(10,
                pessoaImageView, // Adiciona a imagem antes dos campos
                new Label("Nome:"), txtNome,
                new Label("Idade:"), txtIdade,
                new Label("Profissão:"), txtProfissao,
                btnCriar,
                new HBox(10, btnDigitar, btnPensar, btnDescansar),
                resultado
        );
        layout.setPadding(new javafx.geometry.Insets(15));

        // Criando a cena com CSS
        Scene scene = new Scene(layout, 300, 700);
        scene.getStylesheets().add(getClass().getResource("/estiloPessoa.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Cadastro de Pessoa");
        stage.show();
    }
}
