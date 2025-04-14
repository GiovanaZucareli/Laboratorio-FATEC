package com.example.atividade2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TelaRobo extends Application {

    private Robo robo;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        TextField txtModelo = new TextField();
        txtModelo.setPromptText("Modelo");

        TextField txtEnergia = new TextField();
        txtEnergia.setPromptText("Energia (%)");

        TextField txtCor = new TextField();
        txtCor.setPromptText("Cor");

        Button btnCriar = new Button("Criar robô");
        Button btnAndar = new Button("Andar");
        Button btnFalar = new Button("Falar");
        Button btnCarregar = new Button("Carregar Bateria");

        btnAndar.setDisable(true);
        btnFalar.setDisable(true);
        btnCarregar.setDisable(true);

        TextArea resultado = new TextArea();
        resultado.setEditable(false);
        resultado.setPrefHeight(100);

        btnCriar.setOnAction(e -> {
            try {
                String modelo = txtModelo.getText();
                int energia = Integer.parseInt(txtEnergia.getText());
                String cor = txtCor.getText();
                robo = new Robo(modelo, energia, cor);

                resultado.setText("Robô criado com sucesso!");
                btnAndar.setDisable(false);
                btnFalar.setDisable(false);
                btnCarregar.setDisable(false);
            } catch (NumberFormatException ex) {
                resultado.setText("Energia inválida. Digite um número.");
            }
        });

        btnAndar.setOnAction(e -> resultado.setText(robo.andar()));
        btnFalar.setOnAction(e -> resultado.setText(robo.falar()));
        btnCarregar.setOnAction(e -> resultado.setText(robo.carregarBateria()));

        VBox layout = new VBox(10,
                new Label("Modelo:"), txtModelo,
                new Label("Energia (%):"), txtEnergia,
                new Label("Cor:"), txtCor,
                btnCriar, new HBox(10, btnAndar, btnFalar, btnCarregar),
                resultado
        );

        layout.setPadding(new javafx.geometry.Insets(15));

        stage.setScene(new Scene(layout, 400, 400));
        stage.setTitle("Cadastro de Robô");
        stage.show();
    }
}
