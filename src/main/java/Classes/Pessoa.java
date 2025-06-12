package Classes;

public class Pessoa {

    private int idPessoa;
    private String nome;
    private int idade;
    private String cpf;

    public Pessoa(int idPessoa, String nome, int idade, String cpf) {
        this.idPessoa = idPessoa;
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
    }

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
