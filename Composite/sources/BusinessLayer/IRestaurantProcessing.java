package BusinessLayer;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public interface IRestaurantProcessing {
    /**
     * @param item must be a non-null item to be added to the menu
     */
    void createMenuItem(MenuItem item);

    /**
     * @param item must be a non-null item to be deleted from the menu
     */
    void deleteMenuItem(MenuItem item);

    /**
     *
     * @param item non-null and existing item to have its price edited
     */
    void editMenuItem(MenuItem item);

    /**
     *
     * @param order non-null order to be created
     * @param items non-null List of items specific to our order
     */
    void createNewOrder(Order order, ArrayList<MenuItem> items);

    /**
     *
     * @param order the order to have its cost computed
     * @return the cost of the order
     */
    double computePrice(Order order);

    /**
     * Generate the bill for the order with the below properties
     * @param orderID
     * @param orderedItems
     * @param table
     * @param cost
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    void generateBill(String orderID,
                      String orderedItems,
                      String table,
                      String cost) throws FileNotFoundException, UnsupportedEncodingException;

}
