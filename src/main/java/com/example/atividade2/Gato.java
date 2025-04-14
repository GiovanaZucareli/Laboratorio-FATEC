package com.example.atividade2;

public class Gato {

    private String nome;
    private String cor;
    private String idade;

    public Gato(String nome, String cor, String idade) {
        this.nome = nome;
        this.cor = cor;
        this.idade = idade;
    }

    public String pular() {
        return nome + " saltou.";
    }

    public String miar() {
        return nome + " miou.";
    }

    public String correr() {
        return nome + " correu.";
    }

    // Getters (opcionais)
    public String getNome() {
        return nome;
    }

    public String getCor() {
        return cor;
    }

    public String getIdade() {
        return idade;
    }
}
