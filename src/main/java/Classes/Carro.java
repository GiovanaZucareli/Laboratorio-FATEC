package Classes;

public class Carro {

    private int idCarro;
    private String marca;
    private String cor;

    public Carro(String marca, String cor, int idCarro) {
        this.marca = marca;
        this.cor = cor;
        this.idCarro = idCarro;
    }

    public int getIdCarro() {
        return idCarro;
    }

    public void setIdCarro(int idCarro) {
        this.idCarro = idCarro;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String Marca) {
        this.marca = marca;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }


}
