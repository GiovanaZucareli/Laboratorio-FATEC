package com.example.atividade2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TelaCarro extends Application {

    private Carro carro;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        TextField txtMarca = new TextField();
        txtMarca.setPromptText("Marca");

        TextField txtCor = new TextField();
        txtCor.setPromptText("Cor");

        TextField txtVelocidade = new TextField();
        txtVelocidade.setPromptText("Velocidade atual (km/h)");

        Button btnCriar = new Button("Criar carro");
        Button btnAcelerar = new Button("Acelerar");
        Button btnFrear = new Button("Frear");
        Button btnVirar = new Button("Virar");

        btnAcelerar.setDisable(true);
        btnFrear.setDisable(true);
        btnVirar.setDisable(true);

        TextArea resultado = new TextArea();
        resultado.setEditable(false);
        resultado.setPrefHeight(100);

        btnCriar.setOnAction(e -> {
            try {
                String marca = txtMarca.getText();
                String cor = txtCor.getText();
                int velocidade = Integer.parseInt(txtVelocidade.getText());
                carro = new Carro(marca, cor, velocidade);

                resultado.setText("Carro criado com sucesso!");
                btnAcelerar.setDisable(false);
                btnFrear.setDisable(false);
                btnVirar.setDisable(false);
            } catch (NumberFormatException ex) {
                resultado.setText("Velocidade inválida. Digite um número.");
            }
        });

        btnAcelerar.setOnAction(e -> resultado.setText(carro.acelerar()));
        btnFrear.setOnAction(e -> resultado.setText(carro.frear()));
        btnVirar.setOnAction(e -> resultado.setText(carro.virar()));

        VBox layout = new VBox(10,
                new Label("Marca:"), txtMarca,
                new Label("Cor:"), txtCor,
                new Label("Velocidade atual (km/h):"), txtVelocidade,
                btnCriar, new HBox(10, btnAcelerar, btnFrear, btnVirar),
                resultado
        );

        layout.setPadding(new javafx.geometry.Insets(15));

        stage.setScene(new Scene(layout, 400, 400));
        stage.setTitle("Cadastro de Carro");
        stage.show();
    }
}
