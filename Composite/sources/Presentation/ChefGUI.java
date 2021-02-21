package Presentation;


import BusinessLayer.BaseProduct;
import BusinessLayer.CompositeProduct;
import BusinessLayer.MenuItem;
import BusinessLayer.Order;
import jdk.internal.util.xml.impl.Pair;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import static Start.Start.restaurant;

public class ChefGUI extends JFrame implements Observer {
    private JPanel panel1;
    private JTextArea orderTextArea;

    public ChefGUI() {
        super("ChefGUI");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setContentPane(panel1);
        this.setLocation(1000, 0);
        this.setVisible(true);
        this.pack();
    }

    @Override
    /**
     * It's updated whenever we add a new order. We print the order information in the ChefGUI window
     */
    public void update(Observable o, Object arg) {
        ArrayList<MenuItem> items = restaurant.getOrderTracker().get((Order)arg);
        StringBuilder order = new StringBuilder();
        order.append("Order with ID: " + Integer.toString(((Order) arg).getOrderID()) + '\n');
        order.append("Items: \n");
        for(MenuItem item : items) {
            if(item.getClass().getSimpleName().compareTo("BaseProduct") == 0) {
                order.append(((BaseProduct)item).getName() + '\n');
            }else {
                order.append(((CompositeProduct)item).getName() + '\n');
            }
        }
        order.append("For table: " + Integer.toString(((Order) arg).getTable()));
        orderTextArea.setText(order.toString());
    }
}
