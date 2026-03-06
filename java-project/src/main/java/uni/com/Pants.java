package uni.com;

/**
 * Derived class Pants.
 * Extends the base class Clothes and adds a specific attribute: presence of pockets
 */
public class Pants extends Clothes {
    private boolean hasPockets;

    /**
     * Constructs a Pants object.
     *
     * @param name name of the clothing item
     * @param color color of the item
     * @param size size of the item
     * @param price price of the item
     * @param brand brand name
     * @param material material type
     * @param hasPockets indicates presence of pockets
     */
    public Pants(String name, String color, Size size, double price, String brand, String material, boolean hasPockets) {
        super(name, color, size, price, brand, material);
        this.hasPockets = hasPockets;
    }

    /**
     * Getter that returns information about pockets.
     *
     * @return true if pants have pockets
     */
    public boolean getHasPockets() {
        return hasPockets;
    }

    /**
     * Returns a string representation of Pants.
     * Demonstrates polymorphism
     *
     * @return formatted string with pants details
     */
    @Override
    public String toString() {
        return "Pants: " + super.toString() + ", hasPockets=" + hasPockets;
    }
}