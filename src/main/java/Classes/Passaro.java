package Classes;

public class Passaro {

    private int idPassaro;
    private String cor;
    private String especie;

    public Passaro (int idPassaro, String cor, String especie) {
        this.idPassaro = idPassaro;
        this.cor = cor;
        this.especie = especie;
    }

    public int getIdPassaro() {
        return idPassaro;
    }

    public void setIdPassaro (int idPassaro) {
        this.idPassaro = idPassaro;
    }

    public String getcor() {
        return cor;
    }

    public void setcor (String cor) {
        this.cor = cor;
    }

    public String getespecie() {
        return especie;
    }

    public void setespecie (String especie) {
        this.especie = especie;
    }
    
}
