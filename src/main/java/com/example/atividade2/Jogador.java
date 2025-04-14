package com.example.atividade2;

public class Jogador {

    private String nome;
    private String time;
    private String posicao;

    public Jogador(String nome, String time, String posicao) {
        this.nome = nome;
        this.time = time;
        this.posicao = posicao;
    }

    public String chutar() {
        return nome + " chutou a bola.";
    }

    public String driblar() {
        return nome + " driblou.";
    }

    public String correr() {
        return nome + " correu.";
    }

    // Getters (opcionais)
    public String getNome() {
        return nome;
    }

    public String getTime() {
        return time;
    }

    public String getPosicao() {
        return posicao;
    }
}
