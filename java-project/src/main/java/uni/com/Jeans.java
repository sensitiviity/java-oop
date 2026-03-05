package uni.com;

/**
 * Represents Jeans as a specific type of Pants.
 * Adds information about ripped style.
 */
public class Jeans extends Pants{
    public boolean ripped;

    /**
     * Constructs a Jeans object.
     *
     * @param name name of the clothing item
     * @param color color of the item
     * @param size size of the item
     * @param price price of the item
     * @param brand brand name
     * @param material material type
     * @param hasPockets indicates presence of pockets
     * @param ripped indicates ripped style
     */
    public Jeans(String name, String color, Size size, double price, String brand, String material, boolean hasPockets, boolean ripped) {
        super(name, color, size, price, brand, material, hasPockets);
        this.ripped = ripped;
    }

    /**
     * Returns information about ripped style.
     *
     * @return true if jeans are ripped
     */
    public boolean isRipped(){
        return this.ripped;
    }

    @Override
    public String toString() {
        return "Jeans: " + super.toString() + ", ripped=" + ripped;
    }
}
