package uni.com;

/**
 * Creates Clothes object with validation.
 * @throws IllegalArgumentException if parameters are invalid
 */
public class Clothes {
    private String name;
    private String color;
    private String size;
    private double price;
    private String brand;
    private String material;

    /**
     * Creates Clothes object with validation.
     *
     * @throws IllegalArgumentException if parameters are invalid
     */
    public Clothes(String name, String color, String size, double price, String brand, String material) {
        setName(name);
        setColor(color);
        setSize(size);
        setPrice(price);
        setBrand(brand);
        setMaterial(material);
    }

    //гетери
    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
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

    //сетери
    public void setName(String name) {
        if(name == null || name.isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
        this.name = name;
    }

    public void setColor(String color) {
        if(color == null || color.isEmpty()) throw new IllegalArgumentException("Color cannot be empty");
        this.color = color;
    }

    public void setSize(String size) {
        if(size == null || size.isEmpty()) throw new IllegalArgumentException("Size cannot be empty");
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
     * Returns formatted string representation.
     */
    @Override
    public String toString() {
        return "name=" + name + ", color=" + color + ", size=" + size + ", price=" + price + ", brand=" + brand + ", material=" + material;
    }

    /**
     * Compares two Clothes objects.
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(!(obj instanceof Clothes)) return false;

        Clothes c = (Clothes)obj;
        return name.equals(c.name) && color.equals(c.color) && size.equals(c.size) && price == c.price && brand.equals(c.brand) && material.equals(c.material);
    }
}