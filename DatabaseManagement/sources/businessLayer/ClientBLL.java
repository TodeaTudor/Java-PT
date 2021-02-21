package businessLayer;
import com.itextpdf.text.DocumentException;
import dataAccessLayer.ClientDAO;
import dataAccessLayer.OrderDAO;
import model.Client;
import model.Order;
import model.Product;
import presentation.FileManipulator;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the business logic for the client
 */
public class ClientBLL {

    private ClientValidator cv = new ClientValidator();
    private ClientDAO clientDAO = new ClientDAO();
    private OrderDAO orderDAO = new OrderDAO();
    private FileManipulator fm = new FileManipulator();

    /**
     * Checks if the client is valid and inserts it
     * @param line Client insertion input line
     */
    public void insertClient(String line) {
        String replaced = line.replaceAll("Insert client: |Insert Client: ", "");
        String[] tokens = replaced.split(", ");
        if (!cv.validateClient(tokens[0], tokens[1])) {
             System.out.println("Invalid inputs for client");
        }else {
            Client aux = new Client(tokens[0], tokens[1]);
            clientDAO.insert(aux);
        }

    }

    /**
     * Checks if the client is a valid one
     * If it's present in the database, we set its deleted flag to 1
     * @param line Client deletion input line
     */
    public void deleteClient(String line) {
        String replaced = line.replaceAll("Delete client: |Delete Client: ", "");
        String[] tokens = replaced.split(", ");
        if (!cv.validateClient(tokens[0], tokens[1])) {
            System.out.println("Invalid client to delete");
        }else {
            Client aux = new Client(tokens[0], tokens[1]);
            if(clientDAO.findByName(tokens[0]) != null) {
                try {
                    orderDAO.delete(new Order(aux.getName()));
                    clientDAO.delete(aux);
                }catch (NoSuchFieldException | IllegalAccessException e) {
                    System.out.println("Client " + tokens[0] + " " + tokens[1] + " not found in the DB");
                }
            }
        }

    }

    /**
     * Creates a list of all the clients and calls the method used to print them to a PDF file
     * @throws FileNotFoundException
     * @throws DocumentException
     */
    public void report() throws FileNotFoundException, DocumentException {
        List<Client> reportedClients = clientDAO.findAll();
        fm.reportClients(reportedClients);
    }
}
