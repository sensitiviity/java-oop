package uni.com;

/**
 * Derived class Pants
 */
public class Pants extends Clothes {
    private boolean hasPockets;

    public Pants(String name, String color, Size size, double price, String brand, String material, boolean hasPockets) {
        super(name, color, size, price, brand, material);
        this.hasPockets = hasPockets;
    }

    @Override
    public String toString() {
        return "Pants: " + super.toString() + ", hasPockets=" + hasPockets;
    }
}