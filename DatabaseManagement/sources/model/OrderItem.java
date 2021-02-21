package model;

/**
 * An OrderItem is characterized by the order_id which is the id of the order to which it belongs, a name and the
 * ordered quantity
 */
public class OrderItem {
    private int order_id;
    private String name;
    private int quantity;

    public OrderItem(int order_id, String name, int quantity) {
        this.order_id = order_id;
        this.name = name;
        this.quantity = quantity;
    }

    public OrderItem(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public OrderItem() {}

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
