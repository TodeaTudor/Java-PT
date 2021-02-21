package businessLayer;

import com.itextpdf.text.DocumentException;
import model.Product;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Takes each specific line from the input file and calls the specific business logic class to handle the command
 */
public class MainBLL {


    /**
     * Handles the lines of the input
     * @param lines The lines of the input file
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws FileNotFoundException
     * @throws DocumentException
     */
    private void handleLines(ArrayList<String> lines) throws NoSuchFieldException, IllegalAccessException, FileNotFoundException, DocumentException {
        ClientBLL client = new ClientBLL();
        OrderBLL order = new OrderBLL();
        ProductBLL product = new ProductBLL();
        for (String line : lines) {
            if (line.matches("Insert client:.*") || line.matches("Insert Client:.*")) {
                client.insertClient(line);
                order.generateBill(order.getPreviousOrder());
            }else if (line.matches("Delete client: .*") || line.matches("Delete Client:.*")) {
                client.deleteClient(line);
                order.generateBill(order.getPreviousOrder());
            }else if (line.matches("Insert product:.*") || line.matches("Insert Product:.*")) {
                product.insertProduct(line);
                order.generateBill(order.getPreviousOrder());
            }else if (line.matches("Delete product:.*") || line.matches("Delete Product:.*")) {
                product.deleteProduct(line);
                order.generateBill(order.getPreviousOrder());
            }else if (line.matches("Order:.*")) {
                order.handleOrder(line);
            }else if (line.matches("Report client") || line.matches("Report Client")) {
                client.report();
                order.generateBill(order.getPreviousOrder());
            }else if (line.matches("Report order") || line.matches("Report Order")) {
                order.report();
                order.generateBill(order.getPreviousOrder());
            }else if (line.matches("Report product") || line.matches("Report Product")) {
                product.report();
                order.generateBill(order.getPreviousOrder());
            }
        }
        order.generateBill(order.getPreviousOrder());
    }


    public MainBLL(ArrayList<String> lines) throws NoSuchFieldException, IllegalAccessException, FileNotFoundException, DocumentException {
        handleLines(lines);
    }

}
