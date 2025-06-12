package Classes;

public class Ciclista {

    private int idCiclista;
    private String nome;
    private String equipe;
    private String nacionalidade;
    private int idade;

    public Ciclista(String nome, String equipe, String nacionalidade, int idade) {
        this.idCiclista = 0; // ou algum valor padrão para novo registro
        this.nome = nome;
        this.equipe = equipe;
        this.nacionalidade = nacionalidade;
        this.idade = idade;
    }


    public int getIdCiclista() {
        return idCiclista;
    }

    public void setIdCiclista(int idCiclista) {
        this.idCiclista = idCiclista;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEquipe() {
        return equipe;
    }

    public void setEquipe(String equipe) {
        this.equipe = equipe;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
}
