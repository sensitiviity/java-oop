package uni.com;

/**
 * Represents a wardrobe that aggregates Clothes objects.
 */
public class Wardrobe {
    private Clothes[] clothes;

    /**
     * Creates wardrobe with existing Clothes array.
     *
     * @param clothes array of Clothes objects
     */
    public Wardrobe(Clothes[] clothes) {
        if (clothes == null || clothes.length == 0) throw new IllegalArgumentException("Array is empyt.");
        this.clothes = clothes;
    }

    /**
     * Displays all clothes stored in wardrobe.
     */
    public void display() {
        for (Clothes c : clothes) {
            System.out.println(c);
        }
    }
}
