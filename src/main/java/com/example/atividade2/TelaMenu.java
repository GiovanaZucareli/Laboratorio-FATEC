package com.example.atividade2;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TelaMenu extends Application {

    private void abrirTela(Class<? extends Application> telaClass) {
        try {
            Application tela = telaClass.getDeclaredConstructor().newInstance();
            tela.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // Criando o VBox
        VBox vbox = new VBox(15);  // Espaçamento de 15 entre os botões
        vbox.setAlignment(Pos.CENTER);
        vbox.getStyleClass().add("grid");

        // Adicionando emojis antes dos nomes
        String[] nomes = {
                "🐶 Cachorro", "🚗 Carro", "🚴 Ciclista", "👶 Crianca", "🐱 Gato",
                "⚽ Jogador", "🦅 Passaro", "👤 Pessoa", "🎨 Pintores", "🤖 Robo"
        };

        Class<?>[] classes = {
                TelaCachorro.class, TelaCarro.class, TelaCiclista.class, TelaCrianca.class, TelaGato.class,
                TelaJogador.class, TelaPassaro.class, TelaPessoa.class, TelaPintores.class, TelaRobo.class
        };

        // Criando os botões e adicionando os emojis antes dos textos
        for (int i = 0; i < nomes.length; i++) {
            Button btn = new Button(nomes[i]);
            btn.getStyleClass().add("botao-menu");
            btn.setPrefWidth(300);  // Define a largura fixa para todos os botões
            btn.setPrefHeight(50);  // Define a altura fixa para todos os botões
            final Class<? extends Application> tela = (Class<? extends Application>) classes[i];
            btn.setOnAction(e -> abrirTela(tela));
            vbox.getChildren().add(btn); // Adiciona os botões no VBox
        }

        // Adicionando o VBox à cena
        Scene scene = new Scene(vbox, 400, 600);
        scene.getStylesheets().add(getClass().getResource("/estilo.css").toExternalForm());

        // Configurando o palco
        primaryStage.setScene(scene);
        primaryStage.setTitle("Menu Principal");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
