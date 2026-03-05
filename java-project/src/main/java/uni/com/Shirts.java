package uni.com;

/**
 * Derived class Shirts
 * Extends the base class Clothes and adds a specific attribute: presence of sleeves
 */
public class Shirts extends Clothes {
    private boolean longSleeve;

    /**
     * Constructs a Shirts object.
     *
     * @param name name of the clothing item
     * @param color color of the item
     * @param size size of the item
     * @param price price of the item
     * @param brand brand name
     * @param material material type
     * @param longSleeve indicates if sleeves are long
     */
    public Shirts(String name, String color, Size size, double price, String brand, String material, boolean longSleeve) {
        super(name, color, size, price, brand, material);
        this.longSleeve = longSleeve;
    }

    public boolean getLongSleeve() {
        return longSleeve;
    }

    /**
     * Returns a string representation of Shirts.
     * Demonstrates polymorphism
     *
     * @return formatted string with shirt details
     */
    @Override
    public String toString() {
        return "Shirts: " + super.toString() + ", longSleeve=" + longSleeve;
    }
}