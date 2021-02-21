package businessLayer;

import com.itextpdf.text.DocumentException;
import dataAccessLayer.ProductDAO;
import model.Client;
import model.Product;
import presentation.FileManipulator;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

/**
 * Handles the business logic for the Product table
 */
public class ProductBLL {

    private ProductDAO productDAO = new ProductDAO();
    private ProductValidator pv = new ProductValidator();
    private FileManipulator fm = new FileManipulator();

    /**
     * Checks if the product is valid and inserts it if it doesn't exist yet
     * If it does, it updates the stock and the price
     * @param line Insert product input line
     */
    public void insertProduct(String line) {
        String replaced = line.replaceAll("Insert product: |Insert Product: ", "");
        String[] tokens = replaced.split(", ");
        if(!pv.validateProduct(tokens[0], tokens[1], tokens[2])) {
            System.out.println("Invalid product");
        }else {
            Product aux = new Product(tokens[0], Integer.parseInt(tokens[1]), Double.parseDouble(tokens[2]));
            if (productDAO.findByName(tokens[0]) == null) {
                productDAO.insert(aux);
            }else {
                productDAO.updateProduct(aux);
            }
        }
    }

    /**
     * Checks if the product is valid and inside the table then sets its deleted flag to 1
     * @param line The delete product input
     */
    public void deleteProduct(String line) {
        String replaced = line.replaceAll("Delete product: |Delete Product: " , "");
        if(productDAO.findByName(replaced) == null) {
            System.out.println("Invalid product to delete");
        }else {
            try {
                productDAO.delete(productDAO.findByName(replaced));
            }catch (NoSuchFieldException | IllegalAccessException e) {
                System.out.println("Could not delete product " + replaced + " from table");
            }
        }
    }

    /**
     * Gets all the products in the table and prints them to a PDF
     * @throws FileNotFoundException
     * @throws DocumentException
     */
    public void report() throws FileNotFoundException, DocumentException {
        List<Product> reportedProducts = productDAO.findAll();
        fm.reportProducts(reportedProducts);
    }
}
