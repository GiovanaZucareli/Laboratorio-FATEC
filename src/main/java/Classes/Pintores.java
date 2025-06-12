package Classes;

public class Pintores {

    private int idPintor;
    private String nome;
    private String nacionalidade;
    private int idade;

    public Pintores(int idPintor, String nome, String nacionalidade, int idade) {
        this.idPintor = idPintor;
        this.nome = nome;
        this.nacionalidade = nacionalidade;
        this.idade = idade;
    }

    public int getIdPintor() {
        return idPintor;
    }

    public void setIdPintor(int idPintor) {
        this.idPintor = idPintor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
