package com.example.atividade2;

public class Ciclista {

    private String nome;
    private int idade;
    private String tipo_de_bike;

    public Ciclista (String nome, int idade, String tipo_de_bike) {
        this.nome = nome;
        this.idade = idade;
        this.tipo_de_bike = tipo_de_bike;
    }

    public String pedalar() {
        return "O ciclista está pedalando";
    }

    public String frear() {
        return "O ciclista está freando";
    }

    public String virar() {
        return "O ciclista está virando";
    }

}
