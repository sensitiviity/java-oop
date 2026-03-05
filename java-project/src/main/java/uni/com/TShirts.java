package uni.com;

/**
 * Represents a TShirt as a specific type of Shirts.
 * Adds information about print presence.
 */
public class TShirts extends Shirts{
    private boolean hasPrints;

    /**
     * Constructs a TShirts object.
     *
     * @param name name of the clothing item
     * @param color color of the item
     * @param size size of the item
     * @param price price of the item
     * @param brand brand name
     * @param material material type
     * @param longSleeve indicates if sleeves are long
     * @param hasPrints indicates presence of prints
     */
    public TShirts(String name, String color, Size size, double price, String brand, String material, boolean longSleeve, boolean hasPrints) {
        super(name, color, size, price, brand, material, longSleeve);
        this.hasPrints = hasPrints;
    }

    /**
     * Returns information about prints.
     *
     * @return true if the T-shirt has prints
     */
    public boolean getHasPrints() {
        return hasPrints;
    }

    @Override
    public String toString() {
        return "T-Shirt: " + super.toString() + ", hasPrints=" + hasPrints;
    }
}
