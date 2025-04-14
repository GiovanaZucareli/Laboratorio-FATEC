package com.example.atividade2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TelaGato extends Application {

    private Gato gato;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        TextField txtNome = new TextField();
        txtNome.setPromptText("Nome do gato");

        TextField txtCor = new TextField();
        txtCor.setPromptText("Cor do gato");

        TextField txtIdade = new TextField();
        txtIdade.setPromptText("Idade do gato");

        Button btnCriar = new Button("Criar gato");
        Button btnPular = new Button("Pular");
        Button btnMiar = new Button("Miar");
        Button btnCorrer = new Button("Correr");

        btnPular.setDisable(true);
        btnMiar.setDisable(true);
        btnCorrer.setDisable(true);

        TextArea resultado = new TextArea();
        resultado.setEditable(false);
        resultado.setPrefHeight(100);

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

        btnPular.setOnAction(e -> resultado.setText(gato.pular()));
        btnMiar.setOnAction(e -> resultado.setText(gato.miar()));
        btnCorrer.setOnAction(e -> resultado.setText(gato.correr()));

        VBox layout = new VBox(10,
                new Label("Nome:"), txtNome,
                new Label("Cor:"), txtCor,
                new Label("Idade:"), txtIdade,
                btnCriar, new HBox(10, btnPular, btnMiar, btnCorrer),
                resultado
        );

        layout.setPadding(new javafx.geometry.Insets(15));

        stage.setScene(new Scene(layout, 400, 400));
        stage.setTitle("Cadastro de Gato");
        stage.show();
    }
}
