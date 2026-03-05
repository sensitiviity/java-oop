package uni.com;

/**
 * Represents a TShirt as a specific type of Shirts.
 * Adds information about print presence.
 */
public class TShirts extends Shirts{
    private boolean hasPrints;

    public TShirts(String name, String color, Size size, double price, String brand, String material, boolean longSleeve, boolean hasPrints) {
        super(name, color, size, price, brand, material, longSleeve);
        this.hasPrints = hasPrints;
    }

    public boolean getHasPrints() {
        return hasPrints;
    }

    @Override
    public String toString() {
        return "T-Shirt: " + super.toString() + ", hasPrints=" + hasPrints;
    }
}
