package uni.com;

/**
 * Клас, що описує одяг
 */
public class Clothes {
    private String name;
    private String color;
    private String size;
    private double price;

    /**
     * Конструктор з параметрами.
     */
    public Clothes(String name, String color, String size, double price) {
        this.name = name;
        this.color = color;
        this.size = size;
        this.price = price;
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

    //сетери
    public void setName(String name) {
        this.name = name;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setPrice(double price) {
        this.price = price;
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