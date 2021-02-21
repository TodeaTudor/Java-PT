package BusinessLayer;


import java.io.Serializable;

/**
 * The representation of a Base Product. A product that consists of only one item (itself) and its cots
 */
public class BaseProduct extends MenuItem implements Serializable {

    private double cost;
    private String name;

    public BaseProduct(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double computePrice() {
        return this.cost;
    }
}
