import java.io.Serializable;

public class Triplet implements Serializable {
    private String firstParam;
    private String secondParam;
    private String thirdParam;

    public Triplet(String first, String second, String third) {
        this.firstParam = first;
        this.secondParam = second;
        this.thirdParam = third;
    }

    public void setFirstParam(String firstParam) {
        this.firstParam = firstParam;
    }

    public void setSecondParam(String secondParam) {
        this.secondParam = secondParam;
    }

    public void setThirdParam(String thirdParam) {
        this.thirdParam = thirdParam;
    }

    public String getFirstParam() {
        return firstParam;
    }

    public String getSecondParam() {
        return secondParam;
    }

    public String getThirdParam() {
        return thirdParam;
    }

    @Override
    public String toString() {
        return "(" +
                firstParam +
                ", " + secondParam +
                ", " + thirdParam +
                ')';
    }
}
