package Classes;

public class Jogador {

    private int idJogador;
    private String nome;
    private int idade;
    private String posicao;
    private String time;

    public Jogador(int idJogador, String nome, int idade, String posicao, String time) {
        this.idJogador = idJogador;
        this.nome = nome;
        this.idade = idade;
        this.posicao = posicao;
        this.time = time;
    }

    public Jogador(String nome, int idade, String posicao, String time) {
        this.nome = nome;
        this.idade = idade;
        this.posicao = posicao;
        this.time = time;
    }

    public int getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(int idJogador) {
        this.idJogador = idJogador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
