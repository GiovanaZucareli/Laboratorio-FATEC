package Classes;

public class Ciclista {

    private int idCiclista;
    private String nome;
    private int idade;
    private String bicicleta;

    public Ciclista (int idCiclista, String nome, String bicicleta, int idade) {
        this.idCiclista = idCiclista;
        this.nome = nome;
        this.bicicleta = bicicleta;
        this.idade = idade;
    }

    public int getIdCiclista() {
        return idCiclista;
    }

    public void setIdCiclista (int idCiclista) {
        this.idCiclista = idCiclista;
    }

    public String getNome() {
        return nome;
    }

    public void setNome (String nome) {
        this.nome = nome;
    }

    public String getBicicleta() {
        return bicicleta;
    }

    public void setBicicleta (String bicicleta) {
        this.bicicleta = bicicleta;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade (int idade) {
        this.idade = idade;
    }

}
