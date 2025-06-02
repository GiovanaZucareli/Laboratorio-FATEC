package Classes;

public class Jogador {

    private int idJogador;
    private String nome;
    private int idade;
    private String esporte;

    public Jogador (int idJogador, String nome, int idade, String esporte) {
        this.idJogador = idJogador;
        this.nome = nome;
        this.idade = idade;
        this.esporte = esporte;
    }

    public int getIdJogador() {
        return idJogador;
    }

    public void setIdJogador (int idJogador) {
        this.idJogador = idJogador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome (String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade (int idade) {
        this.idade = idade;
    }

    public String getEsporte() {
        return esporte;
    }

    public void setEsporte (String esporte) {
        this.esporte = esporte;
    }

}
