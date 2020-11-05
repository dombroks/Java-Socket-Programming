package model;

import java.io.Serializable;

public class Commande implements Serializable {
    private String Operation;
    private int CCP;
    private Float Somme;

    public Commande(String operation, int CCP, Float Somme) {
        this.Operation = operation;
        this.CCP = CCP;
        this.Somme = Somme;
    }


    public Commande(String operation, int CCP) {
        this.Operation = operation;
        this.CCP = CCP;
    }

    public String getOperation() {
        return Operation;
    }

    public void setOperation(String operation) {
        this.Operation = operation;
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

