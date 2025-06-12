package Classes;

public class Crianca {

    private int idCrianca;
    private String nome;
    private int idade;
    private String turma;

    // Construtor com id (para ler do banco)
    public Crianca(int idCrianca, String nome, int idade, String turma) {
        this.idCrianca = idCrianca;
        this.nome = nome;
        this.idade = idade;
        this.turma = turma;
    }

    // Construtor sem id (para criar novo registro)
    public Crianca(String nome, int idade, String turma) {
        this.idCrianca = 0; // ou valor padrão
        this.nome = nome;
        this.idade = idade;
        this.turma = turma;
    }

    // Getters e setters
    public int getIdCrianca() {
        return idCrianca;
    }

    public void setIdCrianca(int idCrianca) {
        this.idCrianca = idCrianca;
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

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }
}
