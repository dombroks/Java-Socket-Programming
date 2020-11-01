import java.io.Serializable;

public class Commande implements Serializable {
    private String nature;
    private int CCP;
    private Float Somme;

    public Commande(String nature, int CCP, Float Somme) {
        this.nature = nature;
        this.CCP = CCP;
        this.Somme = Somme;
    }

    public Commande(String nature, int CCP) {
        this.nature = nature;
        this.CCP = CCP;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public int getCCP() {
        return CCP;
    }

    public void setCCP(int CCP) {
        this.CCP = CCP;
    }

    public Float getSomme() {
        return Somme;
    }

    public void setSomme(Float somme) {
        Somme = somme;
    }


}

