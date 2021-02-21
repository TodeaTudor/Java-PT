package Presentation;

import BusinessLayer.BaseProduct;
import BusinessLayer.CompositeProduct;
import BusinessLayer.MenuItem;
import BusinessLayer.Order;
import DataLayer.RestarurantSerializator;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import static Presentation.AdminGUI.insertTable;
import static Presentation.AdminGUI.isComposite;
import static Start.Start.restaurant;

public class AddOrder extends JFrame {

    private JPanel panel1;
    private JTextArea orderItems;
    private JTextField tableNo;
    private JButton addOrderItemButton;
    private JTable table1;
    private JButton finalizeOrderButton;
    private RestarurantSerializator serializator = new RestarurantSerializator();

    /**
     * Converts a string of items to a list of MenuItem objects
     * @param orderItemsString
     * @return
     */
    private ArrayList<MenuItem> parseOrderItems(String orderItemsString) {
        ArrayList<MenuItem> items = new ArrayList<>();
        String[] tokens = orderItemsString.split(", ");
        for(String token : tokens) {
            for(MenuItem item : restaurant.getRestaurantMenu()) {
                if (isComposite(token) && item.getClass().getSimpleName().compareTo("CompositeProduct") == 0) {
                    if (((CompositeProduct)item).getName().compareTo(token) == 0) {
                        items.add(item);
                    }
                } else if (item.getClass().getSimpleName().compareTo("BaseProduct") == 0){
                    if (((BaseProduct)item).getName().compareTo(token) == 0) {
                        items.add(item);
                    }
                }
            }
        }
        return items;
    }
    public AddOrder() {
        super("WaiterGUI");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setContentPane(panel1);
        this.setVisible(true);
        this.pack();
        insertTable(table1, restaurant.getRestaurantMenu());

        addOrderItemButton.addActionListener(new ActionListener() {
            @Override
            /**
             * Adds a new item to the order
             */
            public void actionPerformed(ActionEvent e) {
                table1.getSelectedRow();
                StringBuilder orderList = new StringBuilder(orderItems.getText());
                if (!orderItems.getText().isEmpty()) {
                    orderList.append(", ");
                }
                orderList.append((String)table1.getValueAt(table1.getSelectedRow(), 0));
                orderItems.setText(orderList.toString());
                try {
                    serializator.serialize();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        finalizeOrderButton.addActionListener(new ActionListener() {
            @Override
            /**
             * Finalizes the order
             */
            public void actionPerformed(ActionEvent e) {
                int errCount = 0;
                if (tableNo.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a table number", "ERROR", JOptionPane.ERROR_MESSAGE);
                    errCount++;
                }
                if (orderItems.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please order some products", "ERROR", JOptionPane.ERROR_MESSAGE);
                    errCount++;
                }
                if (errCount == 0) {

                    Order order = new Order(Integer.parseInt(tableNo.getText()));
                    ArrayList<MenuItem> items = parseOrderItems(orderItems.getText());
                    restaurant.createNewOrder(order, items);
                    try {
                        serializator.serialize();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    setVisible(false);
                }

            }
        });
    }
}
