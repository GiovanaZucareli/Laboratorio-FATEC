package com.example.atividade2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TelaPassaro extends Application {

    private Passaro passaro;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        TextField txtEspecie = new TextField();
        txtEspecie.setPromptText("Espécie");

        TextField txtTamanho = new TextField();
        txtTamanho.setPromptText("Tamanho (cm)");

        TextField txtCor = new TextField();
        txtCor.setPromptText("Cor");

        Button btnCriar = new Button("Criar pássaro");
        Button btnVoar = new Button("Voar");
        Button btnCantar = new Button("Cantar");
        Button btnPousar = new Button("Pousar");

        btnVoar.setDisable(true);
        btnCantar.setDisable(true);
        btnPousar.setDisable(true);

        TextArea resultado = new TextArea();
        resultado.setEditable(false);
        resultado.setPrefHeight(100);

        btnCriar.setOnAction(e -> {
            String especie = txtEspecie.getText();
            String tamanho = txtTamanho.getText();
            String cor = txtCor.getText();
            passaro = new Passaro(especie, tamanho, cor);

            resultado.setText("Pássaro criado com sucesso!");
            btnVoar.setDisable(false);
            btnCantar.setDisable(false);
            btnPousar.setDisable(false);
        });

        btnVoar.setOnAction(e -> resultado.setText(passaro.voar()));
        btnCantar.setOnAction(e -> resultado.setText(passaro.cantar()));
        btnPousar.setOnAction(e -> resultado.setText(passaro.pousar()));

        VBox layout = new VBox(10,
                new Label("Espécie:"), txtEspecie,
                new Label("Tamanho (cm):"), txtTamanho,
                new Label("Cor:"), txtCor,
                btnCriar, new HBox(10, btnVoar, btnCantar, btnPousar),
                resultado
        );

        layout.setPadding(new javafx.geometry.Insets(15));

        stage.setScene(new Scene(layout, 400, 400));
        stage.setTitle("Cadastro de Pássaro");
        stage.show();
    }
}
