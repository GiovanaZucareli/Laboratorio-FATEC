package com.example.atividade2;

public class Passaro {

    private String especie;
    private String tamanho;
    private String cor;

    public Passaro(String especie, String tamanho, String cor) {
        this.especie = especie;
        this.tamanho = tamanho;
        this.cor = cor;
    }

    public String voar() {
        return "O pássaro " + especie + " voou.";
    }

    public String cantar() {
        return "O pássaro " + especie + " cantou.";
    }

    public String pousar() {
        return "O pássaro " + especie + " pousou.";
    }

    // Getters (opcional)
    public String getEspecie() {
        return especie;
    }

    public String getTamanho() {
        return tamanho;
    }

    public String getCor() {
        return cor;
    }
}
