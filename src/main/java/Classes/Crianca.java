package Classes;

public class Crianca {

    private int idCrianca;
    private String nome;
    private int idade;

    public Crianca (int idCrianca, String nome, int idade) {
        this.idCrianca = idCrianca;
        this.nome = nome;
        this.idade = idade;
    }

    public int getIdCrianca() {
        return idCrianca;
    }

    public void setIdCrianca (int idCrianca) {
        this.idCrianca = idCrianca;
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

}
