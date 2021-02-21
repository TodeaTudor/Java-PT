package businessLayer;

import com.itextpdf.text.DocumentException;
import dataAccessLayer.ClientDAO;
import dataAccessLayer.OrderDAO;
import dataAccessLayer.OrderItemDAO;
import dataAccessLayer.ProductDAO;
import model.Order;
import model.OrderItem;
import model.Product;
import presentation.FileManipulator;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Handles the business logic of an order
 */
public class OrderBLL {
    private Order previousOrder = null;
    private ProductValidator pv = new ProductValidator();
    private ProductDAO prodDAO = new ProductDAO();
    private ClientDAO clientDAO = new ClientDAO();
    private OrderDAO orderDAO = new OrderDAO();
    private OrderItemDAO orderItemDAO = new OrderItemDAO();
    private Product auxProd;
    private FileManipulator fm = new FileManipulator();


    /**
     * Creates a new order
     * @param auxOrder The new order to be inserted in the table
     * @param auxOrderItem The order item to be associated with the order
     */
    public void handleOrderFromNewClient(Order auxOrder, OrderItem auxOrderItem) {
        auxOrder.setCost(auxProd.getPrice()*auxOrderItem.getQuantity());
        Order currentOrder = orderDAO.insert(auxOrder);
        currentOrder.setId(orderDAO.selectLastID());
        auxOrderItem.setOrder_id(orderDAO.selectLastID());
        orderItemDAO.insert(auxOrderItem);
        previousOrder = currentOrder;
    }

    /**
     * Handles a consecutive order from the same client
     * @param auxOrderItem The product to be added to the order
     */
    public void handleOrderFromTheSameClient(OrderItem auxOrderItem) {
        auxOrderItem.setOrder_id(previousOrder.getId());
        orderItemDAO.insert(auxOrderItem);
        orderDAO.updateCost(previousOrder.getId(), previousOrder.getCost() + auxProd.getPrice()*auxOrderItem.getQuantity());
        previousOrder.setCost(previousOrder.getCost() + auxProd.getPrice()*auxOrderItem.getQuantity());
    }

    /**
     * Handles an order by checking the validity of the name and the requested product
     * If those are valid, if we have enough products on stock, we create a new order or append to an existing one
     * depending on the previous input command
     * @param line The order input command
     * @throws FileNotFoundException
     * @throws DocumentException
     */
    public void handleOrder(String line) throws FileNotFoundException, DocumentException {
        String replaced = line.replaceAll("Order: ", "");
        String[] tokens = replaced.split(", ");
        if (!pv.isInt(tokens[2]) || (auxProd = prodDAO.findByName(tokens[1])) == null || clientDAO.findByName(tokens[0]) == null) {
            System.out.println("Invalid order");
        }else {
            if (Integer.parseInt(tokens[2]) > auxProd.getQuantity()) {
                if (previousOrder != null) {
                    generateBill(previousOrder);
                }
                fm.reportInsufficientStock(auxProd, tokens[0], tokens[2]);
                return;
            }
            Order auxOrder = new Order(tokens[0]);
            OrderItem auxOrderItem = new OrderItem(auxOrder.getId(), tokens[1], Integer.parseInt(tokens[2]));
            if (previousOrder == null || !previousOrder.getName().equals(auxOrder.getName())) {
                if (previousOrder != null) {
                    generateBill(previousOrder);
                }
                handleOrderFromNewClient(auxOrder, auxOrderItem);
            } else if (previousOrder.getName().equals(auxOrder.getName())) {
                handleOrderFromTheSameClient(auxOrderItem);
            }
            prodDAO.updateProduct(new Product(auxProd.getName(), -Integer.parseInt(tokens[2]), auxProd.getPrice()));
        }
    }

    public Order getPreviousOrder() {
        return previousOrder;
    }

    /**
     * Generates a bill for an order
     * @param order
     * @throws FileNotFoundException
     * @throws DocumentException
     */
    public void generateBill(Order order) throws FileNotFoundException, DocumentException {
        if (previousOrder != null)  {
            List<OrderItem> orderItems = orderItemDAO.findOrder(order.getId());
            fm.reportBill(orderItems, order);
            previousOrder = null;
        }
        return;
    }

    /**
     * Prints all the orders to a PDF file
     * @throws FileNotFoundException
     * @throws DocumentException
     */
    public void report() throws FileNotFoundException, DocumentException {
        List<Order> orders = orderDAO.findAll();
        fm.reportOrder(orders);
    }
}
