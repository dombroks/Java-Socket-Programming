import java.io.Serializable;

public class Triplet implements Serializable {
    private final String firstParam;
    private final String secondParam;
    private final String thirdParam;

    public Triplet(String first, String second, String third) {
        this.firstParam = first;
        this.secondParam = second;
        this.thirdParam = third;
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
        return "Triplet{" +
                "first='" + firstParam + '\'' +
                ", second='" + secondParam + '\'' +
                ", third='" + thirdParam + '\'' +
                '}';
    }
}
