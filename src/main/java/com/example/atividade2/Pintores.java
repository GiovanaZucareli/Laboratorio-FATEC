package com.example.atividade2;

import java.util.ArrayList;
import java.util.List;

public class Pintores {

    private List<String> nomes;
    private String corDaParede;
    private String tipoDeTinta;

    public Pintores(String corDaParede, String tipoDeTinta) {
        this.nomes = new ArrayList<>();
        this.corDaParede = corDaParede;
        this.tipoDeTinta = tipoDeTinta;
    }

    public void adicionarPintor(String nome) {
        nomes.add(nome);
    }

    public String misturarCores() {
        return String.join(", ", nomes) + " misturaram as tintas.";
    }

    public String pintar() {
        return String.join(", ", nomes) + " estão pintando a parede de " + corDaParede + " com tinta " + tipoDeTinta + "!";
    }

    public String limparPincel() {
        return String.join(", ", nomes) + " limparam os pincéis.";
    }

    // Getters (opcionais)
    public List<String> getNomes() {
        return nomes;
    }

    public String getCorDaParede() {
        return corDaParede;
    }

    public String getTipoDeTinta() {
        return tipoDeTinta;
    }
}
