package Classes;

public class Robo {

    private int idRobo;
    private String nome;
    private String modelo;
    private int anoFabricacao;

    public Robo(int idRobo, String nome, String modelo, int anoFabricacao) {
        this.idRobo = idRobo;
        this.nome = nome;
        this.modelo = modelo;
        this.anoFabricacao = anoFabricacao;
    }

    public int getIdRobo() {
        return idRobo;
    }

    public void setIdRobo(int idRobo) {
        this.idRobo = idRobo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(int anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }
}
