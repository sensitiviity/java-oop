package uni.com;

/**
 * Клас, що описує одяг
 */
public class Clothes {
    private String name;
    private String color;
    private String size;
    private double price;
    private String brand;
    private String material;

    /**
     * Конструктор з параметрами.
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
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    /**
     * Повертає текстове представлення об'єкта.
     */
    @Override
    public String toString() {
        return "Clothes{name='" + name + "', color='" + color + "', size='" + size + "', price=" + price + "}";
    }

    /**
     * Порівняння об'єктів.
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(!(obj instanceof Clothes)) return false;

        Clothes c = (Clothes)obj;
        return name.equals(c.name) && color.equals(c.color) && size.equals(c.size) && price == c.price;
    }
}