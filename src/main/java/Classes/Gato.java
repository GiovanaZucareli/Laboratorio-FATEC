package Classes;

public class Gato {

    private int idGato;
    private String nome;
    private int idade;
    private String raca;
    private String cor;

    public Gato (int idGato, String nome, int idade, String raca, String cor) {
        this.idGato = idGato;
        this.nome = nome;
        this.idade = idade;
        this.raca = raca;
        this.cor = cor;
    }

    public int getId() {
        return idGato;
    }

    public void setIdGato (int idGato) {
        this.idGato = idGato;
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

    public String getRaca() {
        return raca;
    }

    public void setRaca (String raca) {
        this.raca = raca;
    }

    public String getCor() {
        return cor;
    }

    public void setCor (String cor) {
        this.cor = cor;
    }

}
