package uni.com;

/**
 * Represents Jeans as a specific type of Pants.
 * Adds information about ripped style.
 */
public class Jeans extends Pants{
    public boolean ripped;

    public Jeans(String name, String color, Size size, double price, String brand, String material, boolean hasPockets, boolean ripped) {
        super(name, color, size, price, brand, material, hasPockets);
        this.ripped = ripped;
    }

    public boolean isRipped(){
        return this.ripped;
    }

    @Override
    public String toString() {
        return "Jeans: " + super.toString() + ", ripped=" + ripped;
    }
}
