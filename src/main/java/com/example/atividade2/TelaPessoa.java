package com.example.atividade2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TelaPessoa extends Application{

    private Pessoa pessoa;

    public static void main(String[] args) {
        launch(args);
    }

    @Override

    public void start (Stage stage) {

        TextField txtNome = new TextField();
        txtNome.setPromptText("Nome");

        TextField txtIdade = new TextField();
        txtIdade.setPromptText("Idade");

        TextField txtProfissao = new TextField();
        txtProfissao.setPromptText("Profissão");

        Button btnCriar = new Button ("Criar pessoa");
        Button btnDigitar = new Button ("Digitar");
        Button btnPensar = new Button ("Pensar");
        Button btnDescansar = new Button ("Descansar");

        btnDigitar.setDisable(true);
        btnPensar.setDisable(true);
        btnDescansar.setDisable(true);

        TextArea resultado = new TextArea();
        resultado.setEditable(false);
        resultado.setPrefHeight(100);

        btnCriar.setOnAction(e  -> {
            try {
                String nome = txtNome.getText();
                int idade = Integer.parseInt(txtIdade.getText());
                String profissao = txtProfissao.getText();
                pessoa = new Pessoa(nome, idade, profissao);

                resultado.setText("Pessoa criada com sucesso!");
                btnDigitar.setDisable(false);
                btnPensar.setDisable(false);
                btnDescansar.setDisable(false);
            }catch (NumberFormatException ex) {
                resultado.setText("Idade inválida. Digite um número.");
            }
        });

        btnDigitar.setOnAction(e -> resultado.setText(pessoa.digitar()));
        btnPensar.setOnAction(e -> resultado.setText(pessoa.pensar()));
        btnDescansar.setOnAction(e -> resultado.setText(pessoa.descansar()));

        VBox layout = new VBox(10,
                new Label ("Nome: "), txtNome,
                new Label ("Idade: "), txtIdade,
                new Label ("Profissão: "), txtProfissao,
                btnCriar, new HBox(10, btnDigitar, btnPensar, btnDescansar),
                resultado
        );

        layout.setPadding(new javafx.geometry.Insets(15));

        stage.setScene(new Scene(layout, 400, 400));
        stage.setTitle("Cadastro de Pessoa");
        stage.show();
    }

}
