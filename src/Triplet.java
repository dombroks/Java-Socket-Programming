import java.io.Serializable;

public class Triplet implements Serializable {
    private String first;
    private String second;
    private String third;

    public Triplet(String first, String second, String third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    @Override
    public String toString() {
        return "Triplet{" +
                "first='" + first + '\'' +
                ", second='" + second + '\'' +
                ", third='" + third + '\'' +
                '}';
    }
}
