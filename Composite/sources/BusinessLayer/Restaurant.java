package BusinessLayer;

import DataLayer.FileWriter;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;


public class Restaurant extends Observable implements IRestaurantProcessing, Serializable {

    private Map<Order, ArrayList<MenuItem>> orderTracker;
    private ArrayList<MenuItem> restaurantMenu;

    public Restaurant() {
        this.orderTracker = new HashMap<>();
        this.restaurantMenu = new ArrayList<>();
    }

    /**
     *
     * @param o non-null object
     */
    private void invariant (Object o) {
        assert o != null;
    }

    /**
     *
     * @param item must be a non-null item to be added to the menu
     */
    @Override
    public void createMenuItem(MenuItem item) {
        invariant(item);
        int initSize = restaurantMenu.size();
        restaurantMenu.add(item);
        assert initSize == restaurantMenu.size() - 1;
    }

    /**
     *
     * @param item must be a non-null item to be deleted from the menu
     */
    @Override
    public void deleteMenuItem(MenuItem item) {
        invariant(item);
        int initSize = restaurantMenu.size();
        restaurantMenu.remove(item);
        assert initSize == restaurantMenu.size() + 1;

    }

    /**
     *
     * @param item non-null and existing item to have its price edited
     */
    @Override
    public void editMenuItem(MenuItem item) {
        invariant(item);
        for(MenuItem entry : getRestaurantMenu()) {
            if (entry.getClass() == item.getClass()) {
                if (item.getClass().getSimpleName().compareTo("BaseProduct") == 0) {
                    if (((BaseProduct)entry).getName().compareTo(((BaseProduct)item).getName()) == 0) {
                        ((BaseProduct)entry).setCost(((BaseProduct)item).getCost());
                    }
                } else {
                    if (((CompositeProduct)entry).getName().compareTo(((CompositeProduct)item).getName()) == 0) {
                        ((CompositeProduct)entry).setCost(((CompositeProduct)item).getCost());
                    }
                }
            }
        }
    }

    /**
     *
     * @param item finds an item in the menu
     * @return null if the item is not fond. Otherwise returns the entry
     */
    public MenuItem findInMenu(MenuItem item) {
        invariant(item);
        for(MenuItem entry : getRestaurantMenu()) {
            if (entry.getClass() == item.getClass()) {
                if (item.getClass().getSimpleName().compareTo("BaseProduct") == 0) {
                    if (((BaseProduct)entry).getName().compareTo(((BaseProduct)item).getName()) == 0) {
                        return entry;
                    }
                } else {
                    if (((CompositeProduct)entry).getName().compareTo(((CompositeProduct)item).getName()) == 0) {
                        return entry;
                    }
                }
            }
        }
        return null;
    }

    /**
     *
     * @param order non-null order to be created
     * @param items non-null List of items specific to our order
     */
    @Override
    public void createNewOrder(Order order, ArrayList<MenuItem> items) {
        invariant(order);
        int initSize = orderTracker.size();
        orderTracker.put(order, items);
        setChanged();
        notifyObservers(order);
        assert initSize == orderTracker.size() - 1;
    }

    /**
     *
     * @param order the order to have its cost computed
     * @return the cost of the order
     */
    @Override
    public double computePrice(Order order) {
        double cost = 0;
        ArrayList<MenuItem> items = orderTracker.get(order);
        for (MenuItem item : items) {
            if (item.getClass().getSimpleName().compareTo("BaseProduct") == 0) {
                cost += ((BaseProduct)item).getCost();
            }else {
                cost += ((CompositeProduct)item).getCost();
            }
        }
        assert cost >= 0;
        return cost;
    }

    /**
     * Generate the bill for the order with the below properties
     * @param orderID
     * @param orderedItems
     * @param table
     * @param cost
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    @Override
    public void generateBill(String orderID,
                             String orderedItems,
                             String table,
                             String cost) throws FileNotFoundException, UnsupportedEncodingException {
        FileWriter fw = new FileWriter();
        fw.writeToFile(orderID, orderedItems, table, cost);


    }

    public ArrayList<MenuItem> getRestaurantMenu() {
        return restaurantMenu;
    }

    public void setOrderTracker(Map<Order, ArrayList<MenuItem>> orderTracker) {
        this.orderTracker = orderTracker;
    }

    public void setRestaurantMenu(ArrayList<MenuItem> restaurantMenu) {
        this.restaurantMenu = restaurantMenu;
    }

    public Map<Order, ArrayList<MenuItem>> getOrderTracker() {
        return orderTracker;
    }
}
