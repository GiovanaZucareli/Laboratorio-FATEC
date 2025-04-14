package com.example.atividade2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TelaPintores extends Application {

    private Pintores pintores;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        TextField txtNome = new TextField();
        txtNome.setPromptText("Nome do pintor");

        TextField txtCorDaParede = new TextField();
        txtCorDaParede.setPromptText("Cor da parede");

        TextField txtTipoDeTinta = new TextField();
        txtTipoDeTinta.setPromptText("Tipo de tinta");

        Button btnCriar = new Button("Criar Pintores");
        Button btnAdicionarPintor = new Button("Adicionar Pintor");
        Button btnMisturarCores = new Button("Misturar Cores");
        Button btnPintar = new Button("Pintar");
        Button btnLimparPincel = new Button("Limpar Pincel");

        btnAdicionarPintor.setDisable(true);
        btnMisturarCores.setDisable(true);
        btnPintar.setDisable(true);
        btnLimparPincel.setDisable(true);

        TextArea resultado = new TextArea();
        resultado.setEditable(false);
        resultado.setPrefHeight(100);

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

        btnMisturarCores.setOnAction(e -> resultado.setText(pintores.misturarCores()));
        btnPintar.setOnAction(e -> resultado.setText(pintores.pintar()));
        btnLimparPincel.setOnAction(e -> resultado.setText(pintores.limparPincel()));

        VBox layout = new VBox(10,
                new Label("Nome do Pintor:"), txtNome,
                new Label("Cor da Parede:"), txtCorDaParede,
                new Label("Tipo de Tinta:"), txtTipoDeTinta,
                btnCriar, new HBox(10, btnAdicionarPintor),
                new HBox(10, btnMisturarCores, btnPintar, btnLimparPincel),
                resultado
        );

        layout.setPadding(new javafx.geometry.Insets(15));

        stage.setScene(new Scene(layout, 400, 400));
        stage.setTitle("Cadastro de Pintores");
        stage.show();
    }
}
