package Presentation;

import BusinessLayer.BaseProduct;
import BusinessLayer.CompositeProduct;
import BusinessLayer.MenuItem;
import BusinessLayer.Order;
import DataLayer.FileWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static Start.Start.restaurant;

public class ViewOrComputeBillForOrder extends JFrame{
    private JButton generateBill;
    private JTable table1;
    private JPanel panel1;

    public ViewOrComputeBillForOrder() {
        super("View and Generate Bill");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setContentPane(panel1);
        this.setVisible(true);
        this.pack();
        String[] columns = {"Order ID", "Table", "Ordered Items", "Cost"};
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.setColumnIdentifiers(columns);

        /**
         * Creates a table containing the orders
         */
        if (restaurant.getOrderTracker() != null) {
            for(Order order : restaurant.getOrderTracker().keySet()) {
                String[] data = new String[4];
                data[0] = Integer.toString(order.getOrderID());
                data[1] = Integer.toString(order.getTable());
                ArrayList<MenuItem> items = restaurant.getOrderTracker().get(order);
                StringBuilder orderedItems = new StringBuilder();
                for (MenuItem item : items) {
                    if (item.getClass().getSimpleName().compareTo("BaseProduct") == 0) {
                        orderedItems.append(((BaseProduct) item).getName());
                    }else {
                        orderedItems.append(((CompositeProduct) item).getName());
                    }
                    orderedItems.append(", ");
                }
                data[2] = orderedItems.toString().substring(0, orderedItems.toString().length() - 2);
                data[3] = Double.toString(restaurant.computePrice(order));
                model.addRow(data);
            }
        }
        table1.setModel(model);

        generateBill.addActionListener(new ActionListener() {
            @Override
            /**
             * Takes the order information from the JTable and sends it forward to be printed in a .txt file
             */
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                String orderID = (String)table1.getValueAt(row, 0);
                String table = (String)table1.getValueAt(row, 1);
                String orderedItems = (String)table1.getValueAt(row, 2);
                String orderCost = (String)table1.getValueAt(row, 3);
                try {
                    restaurant.generateBill(orderID, orderedItems, table, orderCost);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                }

                setVisible(false);
            }
        });
    }
}
