package com.example.atividade2;

public class Robo {

    private String modelo;
    private int energia; // Representada em porcentagem (0 a 100)
    private String cor;

    public Robo(String modelo, int energia, String cor) {
        this.modelo = modelo;
        this.energia = energia;
        this.cor = cor;
    }

    public String andar() {
        return "O robô " + modelo + " está andando para frente.";
    }

    public String falar() {
        return "O robô " + modelo + " diz: Olá, humano!";
    }

    public String carregarBateria() {
        energia = 100;
        return "O robô " + modelo + " está totalmente carregado.";
    }

    // Getters (opcional, se quiser acessar os atributos depois)
    public String getModelo() {
        return modelo;
    }

    public int getEnergia() {
        return energia;
    }

    public String getCor() {
        return cor;
    }
}
