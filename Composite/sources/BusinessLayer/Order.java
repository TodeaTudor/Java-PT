package BusinessLayer;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * The model of an order
 */
public class Order implements Serializable {

    private int orderID;
    private Date orderDate;
    private int table;

    /**
     *
     * @return generates and OrderID based on the hour the Order was made
     */
    private int generateOrderID() {
        return orderDate.getHours()*100 + orderDate.getMinutes();
    }

    /**
     *
     * @param table the table of the order
     */
    public Order(int table) {
        this.orderDate = new Date();
        this.table = table;
        this.orderID = generateOrderID();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderID == order.orderID &&
                table == order.table &&
                orderDate.equals(order.orderDate);
    }

    /**
     *
     * @return the hashCode of a specific Order. Used to map orders in the Restaurant
     */
    @Override
    public int hashCode() {
        return 3 * orderID + 2 * table + orderDate.getMonth();
    }

    public int getOrderID() {
        return orderID;
    }

    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
    }
}
