package uni.com;

/**
 * Represents a clothing item with characteristics: name, color, size, price, brand and material.
 * The class provides validation of input data in constructors and setters. Invalid values cause IllegalArgumentException.
 */
public class Clothes {
    private static int objectCount = 0;

    private String name;
    private String color;
    private Size size;
    private double price;
    private String brand;
    private String material;

    /**
     * Creates a new {@code Clothes} object.
     *
     * @param name name of clothes (cannot be empty)
     * @param color color (cannot be empty)
     * @param size size (cannot be empty)
     * @param price price (must be non-negative)
     * @param brand brand name (cannot be empty)
     * @param material material type (cannot be empty)
     *
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public Clothes(String name, String color, Size size, double price, String brand, String material) {
        setName(name);
        setColor(color);
        setSize(size);
        setPrice(price);
        setBrand(brand);
        setMaterial(material);

        objectCount++;
    }

    //гетери
    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Size getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public String getBrand() {
        return brand;
    }

    public String getMaterial() {
        return material;
    }

    public static int getObjectCount() {
        return objectCount;
    }

    //сетери
    public void setName(String name) {
        if(name == null || name.isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
        this.name = name;
    }

    public void setColor(String color) {
        if(color == null || color.isEmpty()) throw new IllegalArgumentException("Color cannot be empty");
        this.color = color;
    }

    public void setSize(Size size) {
        if(size == null) throw new IllegalArgumentException("Size cannot be empty");
        this.size = size;
    }

    public void setPrice(double price) {
        if(price < 0) throw new IllegalArgumentException("Price cannot be negative");
        this.price = price;
    }

    public void setBrand(String brand) {
        if(brand == null || brand.isEmpty()) throw new IllegalArgumentException("Brand cannot be empty");
        this.brand = brand;
    }

    public void setMaterial(String material) {
        if(material == null || material.isEmpty()) throw new IllegalArgumentException("Material cannot be empty");
        this.material = material;
    }

    /**
     * Returns string representation of the object.
     *
     * @return formatted string describing clothes
     */
    @Override
    public String toString() {
        return "Clothes{name='" + name + "', color='" + color + "', size='" + size + "', price='" + price + "', brand='" + brand + "', material='" + material + "'}";
    }

    /**
     * Compares this object with another for equality.
     *
     * @param obj object to compare
     * @return true if objects contain identical data
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(!(obj instanceof Clothes)) return false;

        Clothes c = (Clothes)obj;
        return name.equals(c.name) && color.equals(c.color) && size.equals(c.size) && price == c.price && brand.equals(c.brand) && material.equals(c.material);
    }

    /**
     * Copy constructor.
     *
     * @param other object to copy
     */
    public Clothes(Clothes other) {
        if (other == null) throw new IllegalArgumentException("Object to copy cannot be null");
        this.name = other.name;
        this.color = other.color;
        this.size = other.size;
        this.price = other.price;
        this.brand = other.brand;
        this.material = other.material;

        objectCount++;
    }
}