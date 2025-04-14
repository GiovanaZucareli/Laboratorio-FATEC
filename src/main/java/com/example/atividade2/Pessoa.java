package com.example.atividade2;

public class Pessoa {

    private String nome;
    private int idade;
    private String profissao;

    public Pessoa (String nome, int idade, String profissao) {
        this.nome = nome;
        this.idade = idade;
        this.profissao = profissao;
    }

    public String digitar() {
        return "A pessoa está digitando";
    }

    public String pensar() {
        return "A pessoa está pensando";
    }

    public String descansar() {
        return "A pessoa está descansando";
    }


}
