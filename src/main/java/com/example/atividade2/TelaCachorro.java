package com.example.atividade2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
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

        VBox layout = new VBox(10,
                new Label("Raça:"), txtRaca,
                new Label("Idade:"), txtIdade,
                new Label("Cor:"), txtCor,
                btnCriar, new HBox(10, btnCorrer, btnLatir, btnAbanar),
                resultado
        );
        layout.setPadding(new javafx.geometry.Insets(15));

        stage.setScene(new Scene(layout, 400, 400));
        stage.setTitle("Cadastro de Cachorro");
        stage.show();
    }
}
