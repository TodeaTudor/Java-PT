package model;

/**
 * A product is described by its name (Primary Key), and has quantity and price as its attributes. We delete items
 * from our database by triggering the deleted flag rather than forcefully deleting them
 */
public class Product {
    private String name;
    private int quantity;
    private double price;
    private int deleted;

    public Product(String name, int quantity, double price) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.deleted = 0;
    }

    public Product() {}

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Product{" +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
