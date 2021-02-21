package model;

/**
 * An order is characterized by the client to which it belongs, a unique ID and the total cost of the order
 */
public class Order {
    private int id;
    private String name;
    private double cost;
    private int deleted;

    public Order (String name) {
        this.id = 0;
        this.name = name;
        this.cost = 0;
        this.deleted = 0;
    }

    public Order () {}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                '}';
    }
}
