package com.example.atividade2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TelaCiclista extends Application{

    private Ciclista ciclista;

    public static void main(String[] args) {
        launch(args);
    }

    @Override

    public void start (Stage stage) {

        TextField txtNome = new TextField();
        txtNome.setPromptText("Nome");

        TextField txtIdade = new TextField();
        txtIdade.setPromptText("Idade");

        TextField txtTipoBike = new TextField();
        txtTipoBike.setPromptText("Tipo da bicicleta");

        Button btnCriar = new Button ("Criar ciclista");
        Button btnPedalar = new Button ("Pedalar");
        Button btnFrear = new Button ("Frear");
        Button btnVirar = new Button ("Virar");

        btnPedalar.setDisable(true);
        btnFrear.setDisable(true);
        btnVirar.setDisable(true);

        TextArea resultado = new TextArea();
        resultado.setEditable(false);
        resultado.setPrefHeight(100);

        btnCriar.setOnAction(e  -> {
            try {
                String nome = txtNome.getText();
                int idade = Integer.parseInt(txtIdade.getText());
                String tipoBike = txtTipoBike.getText();
                ciclista = new Ciclista(nome, idade, tipoBike);

                resultado.setText("Ciclista criado com sucesso!");
                btnPedalar.setDisable(false);
                btnFrear.setDisable(false);
                btnVirar.setDisable(false);
            }catch (NumberFormatException ex) {
                resultado.setText("Idade inválida. Digite um número.");
            }
        });

        btnPedalar.setOnAction(e -> resultado.setText(ciclista.pedalar()));
        btnFrear.setOnAction(e -> resultado.setText(ciclista.frear()));
        btnVirar.setOnAction(e -> resultado.setText(ciclista.virar()));

        VBox layout = new VBox(10,
                new Label ("Nome: "), txtNome,
                new Label ("Idade: "), txtIdade,
                new Label ("Tipo da bicicleta: "), txtTipoBike,
                btnCriar, new HBox(10, btnPedalar, btnFrear, btnVirar),
                resultado
        );

        layout.setPadding(new javafx.geometry.Insets(15));

        stage.setScene(new Scene(layout, 400, 400));
        stage.setTitle("Cadastro de Ciclista");
        stage.show();
    }

}
