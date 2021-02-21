package BusinessLayer;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * A Composite Product is a product that consists of multiple Menu Items, which can be BaseProducts or other CompositeProducts
 */
public class CompositeProduct extends MenuItem implements Serializable {

    private String name;
    private ArrayList<MenuItem> items;
    private double cost;

    public CompositeProduct(String name, ArrayList<MenuItem> items) {
        this.name = name;
        this.items = items;
        this.cost = computePrice();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItems(ArrayList<MenuItem> items) {
        this.items = items;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public ArrayList<MenuItem> getItems() {
        return items;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public double computePrice() {
        double cost = 0;

        for (MenuItem item : items) {
            cost += item.computePrice();
        }
        return cost;
    }
}
