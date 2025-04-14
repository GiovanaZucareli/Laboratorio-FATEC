package com.example.atividade2;

public class Carro {

    private String marca;
    private String cor;
    private int velocidadeAtual; // Em km/h

    public Carro(String marca, String cor, int velocidadeAtual) {
        this.marca = marca;
        this.cor = cor;
        this.velocidadeAtual = velocidadeAtual;
    }

    public String acelerar() {
        velocidadeAtual += 10;
        return "O carro acelerou. Velocidade atual: " + velocidadeAtual + " km/h.";
    }

    public String frear() {
        velocidadeAtual = 0;
        return "O carro freou e parou.";
    }

    public String virar() {
        return "O carro virou para outra direção.";
    }

    // Getters (opcional)
    public String getMarca() {
        return marca;
    }

    public String getCor() {
        return cor;
    }

    public int getVelocidadeAtual() {
        return velocidadeAtual;
    }
}
