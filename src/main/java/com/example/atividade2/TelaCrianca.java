package com.example.atividade2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TelaCrianca extends Application {

    private Crianca crianca;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        TextField txtIdade = new TextField();
        txtIdade.setPromptText("Idade");

        TextField txtPeso = new TextField();
        txtPeso.setPromptText("Peso (kg)");

        TextField txtAltura = new TextField();
        txtAltura.setPromptText("Altura (m)");

        Button btnCriar = new Button("Criar criança");
        Button btnPular = new Button("Pular");
        Button btnCorrer = new Button("Correr");
        Button btnBrincar = new Button("Brincar");

        btnPular.setDisable(true);
        btnCorrer.setDisable(true);
        btnBrincar.setDisable(true);

        TextArea resultado = new TextArea();
        resultado.setEditable(false);
        resultado.setPrefHeight(100);

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

        btnPular.setOnAction(e -> resultado.setText(crianca.pular()));
        btnCorrer.setOnAction(e -> resultado.setText(crianca.correr()));
        btnBrincar.setOnAction(e -> resultado.setText(crianca.brincar()));

        VBox layout = new VBox(10,
                new Label("Idade:"), txtIdade,
                new Label("Peso (kg):"), txtPeso,
                new Label("Altura (m):"), txtAltura,
                btnCriar, new HBox(10, btnPular, btnCorrer, btnBrincar),
                resultado
        );

        layout.setPadding(new javafx.geometry.Insets(15));

        stage.setScene(new Scene(layout, 400, 400));
        stage.setTitle("Cadastro de Criança");
        stage.show();
    }
}
