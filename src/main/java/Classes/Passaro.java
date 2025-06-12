package Classes;

public class Passaro {

    private int idPassaro;
    private String nome;
    private int idade;
    private String especie;
    private String cor;

    public Passaro(int idPassaro, String nome, int idade, String especie, String cor) {
        this.idPassaro = idPassaro;
        this.nome = nome;
        this.idade = idade;
        this.especie = especie;
        this.cor = cor;
    }

    public int getIdPassaro() {
        return idPassaro;
    }

    public void setIdPassaro(int idPassaro) {
        this.idPassaro = idPassaro;
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

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }
}
