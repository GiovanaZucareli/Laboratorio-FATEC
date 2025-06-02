package Classes;



public class Cachorro {

    private int idCachorro;
    private String raca;
    private String cor;
    private String nome;
    private int idade;

    public Cachorro(String raca, String cor, String nome, int idCachorro, int idade) {
        this.raca = raca;
        this.cor = cor;
        this.nome = nome;
        this.idCachorro = idCachorro;
        this.idade = idade;
    }

    public int getIdCachorro() {
        return idCachorro;
    }

    public void setIdCachorro(int idCachorro) {
        this.idCachorro = idCachorro;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
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


}
