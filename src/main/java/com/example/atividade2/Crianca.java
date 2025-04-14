package com.example.atividade2;

public class Crianca {

    private int idade;
    private double peso;
    private double altura;

    public Crianca(int idade, double peso, double altura) {
        this.idade = idade;
        this.peso = peso;
        this.altura = altura;
    }

    public String pular() {
        return "A criança pulou";
    }

    public String correr() {
        return "A criança está correndo.";
    }

    public String brincar() {
        return "A criança está brincando.";
    }

    // Getters (opcionais)
    public int getIdade() {
        return idade;
    }

    public double getPeso() {
        return peso;
    }

    public double getAltura() {
        return altura;
    }
}
