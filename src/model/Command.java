package model;

import java.io.Serializable;

public class Command implements Serializable {
    private String Operation;
    private int CCP;
    private Float Sum;

    public Command(String operation, int CCP, Float Sum) {
        this.Operation = operation;
        this.CCP = CCP;
        this.Sum = Sum;
    }


    public Command(String operation, int CCP) {
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

    public Float getSum() {
        return Sum;
    }

    public void setSum(Float sum) {
        Sum = sum;
    }


}

