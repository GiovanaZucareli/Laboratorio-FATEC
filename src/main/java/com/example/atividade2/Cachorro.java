package com.example.atividade2;

public class Cachorro {

    private String raca;
    private int idade;
    private String cor;

    public Cachorro(String raca, int idade, String cor) {
        this.raca = raca;
        this.idade = idade;
        this.cor = cor;
    }

    public String correr() {
        return "O cachorro está correndo";
    }

    public String latir() {
        return "O cachorro está latindo";
    }

    public String abanarRabo() {
        return "O cachorro está abanando o rabo";
    }

}
