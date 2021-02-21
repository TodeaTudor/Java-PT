package presentation;

import businessLayer.MainBLL;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dataAccessLayer.OrderItemDAO;
import model.Client;
import model.Order;
import model.OrderItem;
import model.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Handles the input file and the PDF reports
 */
public class FileManipulator {

    private int clientReportCount = 0;
    private int productReportCount = 0;
    private int orderReportCount = 0;

    /**
     * Parses the input file into separate lines
     * @param file
     * @return the lines of the file
     * @throws FileNotFoundException
     */
    private ArrayList<String> getFileLines (String file) throws FileNotFoundException {
        File in = new File(file);
        Scanner inScanner = new Scanner(in);
        ArrayList<String> lines = new ArrayList<>();

        while (inScanner.hasNextLine()) {
            lines.add(inScanner.nextLine());
        }

        return lines;
    }

    /**
     * We create a MainBLL object to which we pass the lines of the file
     * @param file
     * @throws FileNotFoundException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws DocumentException
     */
    public FileManipulator(String file) throws FileNotFoundException, NoSuchFieldException, IllegalAccessException, DocumentException {
        ArrayList<String> lines = getFileLines(file);
        MainBLL mainBusinessLogic = new MainBLL(lines);

    }

    /**
     * Empty constructor. Used to call the report methods from the BLL classes
     */
    public FileManipulator() {

    }

    /**
     * Sets the header of our Client table
     * @param table
     */
    private void addClientTableHeader(PdfPTable table) {
        Stream.of("Client Name", "City")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    /**
     * Adds a row with the attributes of a client
     * @param table
     * @param client
     */
    private void addClientRows(PdfPTable table, Client client) {
        table.addCell(client.getName());
        table.addCell(client.getCity());
    }

    /**
     * Creates a PDF file to which we print the clients present in our database
     * @param clients
     * @throws FileNotFoundException
     * @throws DocumentException
     */
    public void reportClients(List<Client> clients) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        StringBuilder name = new StringBuilder();
        name.append("ClientReport");
        if (clientReportCount != 0) {
            name.append(clientReportCount);
        }
        name.append(".pdf");
        clientReportCount++;
        PdfWriter.getInstance(document, new FileOutputStream(name.toString()));

        document.open();

        PdfPTable table = new PdfPTable(2);
        addClientTableHeader(table);
        for (Client client: clients) {
            addClientRows(table, client);
        }
        document.add(table);
        document.close();
    }

    /**
     * Sets the header of the Product table
     * @param table
     */
    private void addProductTableHeader(PdfPTable table) {
        Stream.of("Product Name", "Quantity", "Price")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    /**
     * Adds a table row representing a product
     * @param table
     * @param product
     */
    private void addProductRows(PdfPTable table, Product product) {
        table.addCell(product.getName());
        table.addCell(Integer.toString(product.getQuantity()));
        table.addCell(Double.toString(product.getPrice()));

    }

    /**
     * Report the products from our database
     * @param products
     * @throws FileNotFoundException
     * @throws DocumentException
     */
    public void reportProducts(List<Product> products) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        StringBuilder name = new StringBuilder();
        name.append("ProductReport");
        if (productReportCount != 0) {
            name.append(productReportCount);
        }
        name.append(".pdf");
        productReportCount++;
        PdfWriter.getInstance(document, new FileOutputStream(name.toString()));

        document.open();

        PdfPTable table = new PdfPTable(3);
        addProductTableHeader(table);
        for (Product product: products) {
            addProductRows(table, product);
        }
        document.add(table);
        document.close();
    }

    /**
     * We create a personalized warning PDF for the clients who want to order more items than we have in stock.
     * It's a pandemic, so we are kind of low on items
     * @param product
     * @param clientName
     * @param quantity
     * @throws FileNotFoundException
     * @throws DocumentException
     */
    public void reportInsufficientStock(Product product, String clientName, String quantity) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        StringBuilder name = new StringBuilder();
        String noBlanksClientName = clientName.replaceAll(" ", "");
        name.append("InsufficientStock_" + noBlanksClientName + "_" + product.getName() + ".pdf");
        PdfWriter.getInstance(document, new FileOutputStream(name.toString()));

        document.open();
        Font font = FontFactory.getFont(FontFactory.TIMES, 14, BaseColor.BLACK);
        Paragraph paragraph = new Paragraph("Insufficient stock to complete the order for: " + clientName + ", " + product.getName() + ", " + quantity + "\n" +
                                "Actual stock: " + product.getQuantity(), font);

        document.add(paragraph);
        document.close();
    }

    /**
     * Creates the header of the Order report
     * @param table
     */
    private void addOrderTableHeader(PdfPTable table) {
        Stream.of("ID", "Name", "Product Name", "Quantity")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    /**
     * Adds an order and its attributes as a row in the PDF file
     * @param table
     * @param orderItem
     * @param order
     */
    private void addOrderRows(PdfPTable table, OrderItem orderItem, Order order) {
        table.addCell(Integer.toString(order.getId()));
        table.addCell(order.getName());
        table.addCell(orderItem.getName());
        table.addCell(Integer.toString(orderItem.getQuantity()));

    }

    /**
     * We create a bill for every order we have to process
     * @param orderItems
     * @param order
     * @throws FileNotFoundException
     * @throws DocumentException
     */
    public void reportBill(List <OrderItem> orderItems, Order order) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        StringBuilder name = new StringBuilder();
        name.append("BillForOrder" + order.getId() + ".pdf");
        PdfWriter.getInstance(document, new FileOutputStream(name.toString()));

        document.open();
        PdfPTable table = new PdfPTable(4);
        addOrderTableHeader(table);
        for (OrderItem orderItem: orderItems) {
            addOrderRows(table, orderItem, order);
        }
        document.add(table);

        table = new PdfPTable(1);
        PdfPCell costCell = new PdfPCell(new Paragraph("Total Cost: " + order.getCost()));
        table.addCell(costCell);
        document.add(table);

        document.close();
    }

    /**
     * We print all the orders and their contents to a PDF file
     * @param orders
     * @throws FileNotFoundException
     * @throws DocumentException
     */
    public void reportOrder(List <Order> orders) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        StringBuilder name = new StringBuilder();
        name.append("OrderReport");
        if (orderReportCount != 0) {
            name.append(orderReportCount);
        }
        name.append(".pdf");
        orderReportCount++;
        PdfWriter.getInstance(document, new FileOutputStream(name.toString()));

        document.open();

        PdfPTable table = new PdfPTable(4);
        addOrderTableHeader(table);
        OrderItemDAO orderItemDAO = new OrderItemDAO();
        for (Order order: orders) {
            List<OrderItem> orderItems = orderItemDAO.findOrder(order.getId());
            for (OrderItem orderItem: orderItems) {
                addOrderRows(table, orderItem, order);
            }
            if (orderItems != null) {
                PdfPCell costCell = new PdfPCell(new Paragraph("Total Cost: " + order.getCost()));
                costCell.setColspan(4);
                table.addCell(costCell);
            }
        }
        document.add(table);
        document.close();
    }
}
