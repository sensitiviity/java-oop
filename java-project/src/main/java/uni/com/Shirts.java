package uni.com;

/**
 * Derived class Shirts
 */
public class Shirts extends Clothes {
    private boolean longSleeve;

    public Shirts(String name, String color, Size size, double price, String brand, String material, boolean longSleeve) {
        super(name, color, size, price, brand, material);
        this.longSleeve = longSleeve;
    }

    @Override
    public String toString() {
        return "Shirts: " + super.toString() + ", longSleeve=" + longSleeve;
    }
}