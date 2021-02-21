package DataLayer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class FileWriter {

    /**
     * Writes the Bill information to a file
     * @param orderID
     * @param orderedItems
     * @param table
     * @param cost
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public void writeToFile(String orderID,
                             String orderedItems,
                             String table,
                             String cost) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("OrderBill" + orderID + 'T' + table + ".txt");
        writer.println("OrderID: " + orderID);
        writer.println("Table: " + table);
        writer.println("Ordered Items: ");
        for (String token : orderedItems.split(", ")) {
            writer.println(token);
        }
        writer.println("Cost: " + cost);
        writer.close();
    }
}
