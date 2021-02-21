package Presentation;

import BusinessLayer.BaseProduct;
import BusinessLayer.CompositeProduct;
import BusinessLayer.MenuItem;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import static Presentation.AdminGUI.*;
import static Start.Start.restaurant;
import DataLayer.RestarurantSerializator;

public class AddProduct extends JFrame{
    private JPanel panel1;
    private JButton submitBaseProductButton;
    private JButton submitCompositeProductButton;
    private JTextField baseName;
    private JLabel h1;
    private JTextField compositeField;
    private JButton addCompositeItemButton;
    private JTable table;
    private JTextField basePrice;
    private JTextField finalCompositePrice;
    private RestarurantSerializator serializator = new RestarurantSerializator();
    private ArrayList<MenuItem> compositeComponents = new ArrayList<>();

    /**
     * Checks if the name is valid
     * @param name
     * @return
     */
    public static boolean checkName(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if (!Character.isLetter(c) && c != ' ') {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the price is a double
     * @param price
     * @return
     */
    public static boolean checkPrice(String price) {
        try {
            Double.parseDouble(price);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the Base Product is already present in the Menu, and if not, inserts it
     * @param name
     * @param price
     * @throws IOException
     */
    public void handleBaseProduct(String name, String price) throws IOException {
        if (checkName(name) && checkPrice(price)) {
            double cost = Double.parseDouble(price);
            MenuItem bp = new BaseProduct(name, cost);
            if (!isInMenu(bp)) {
                restaurant.createMenuItem(bp);
                serializator.serialize();
            }else {
                JOptionPane.showMessageDialog(null, "Item already in the menu", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }else {
            JOptionPane.showMessageDialog(null, "Invalid menu item format", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }


    /**
     * Checks if the item is in the Menu
     * @param item
     * @return
     */
    private boolean isInMenu(MenuItem item) {
        if(restaurant.findInMenu(item) != null) {
            return true;
        }
        return false;
    }


    public AddProduct() {
        super("Add Product");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();
        insertTable(table, restaurant.getRestaurantMenu());

        submitBaseProductButton.addActionListener(new ActionListener() {
            @Override
            /**
             * Adds a new Base Product
             */
            public void actionPerformed(ActionEvent e) {
                try {
                    handleBaseProduct(baseName.getText(), basePrice.getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                setVisible(false);
            }
        });


        addCompositeItemButton.addActionListener(new ActionListener() {
            @Override
            /**
             * Inserts a new component for a composite item
             */
            public void actionPerformed(ActionEvent e) {
                String name = (String)table.getValueAt(table.getSelectedRow(), 0);
                double price = Double.parseDouble((String)table.getValueAt(table.getSelectedRow(), 1));
                if(isComposite(name)) {
                    compositeComponents.add(new CompositeProduct(name, getMenuItems(table, name)));
                }else {
                    compositeComponents.add(new BaseProduct(name, price));
                }
                StringBuilder composite = new StringBuilder();
                composite.append(compositeField.getText());
                if (!compositeField.getText().isEmpty()) {
                    composite.append(" + ");
                }
                composite.append(name);
                compositeField.setText(composite.toString());
                if (!finalCompositePrice.getText().isEmpty()) {
                    double currentPrice = Double.parseDouble(finalCompositePrice.getText());
                    currentPrice += price;
                    finalCompositePrice.setText(Double.toString(currentPrice));
                }else {
                    finalCompositePrice.setText(Double.toString(price));
                }


            }
        });


        submitCompositeProductButton.addActionListener(new ActionListener() {
            @Override
            /**
             * Adds a new Composite Product to the Menu
             */
            public void actionPerformed(ActionEvent e) {
                String productName = compositeField.getText();
                MenuItem item = new CompositeProduct(productName, compositeComponents);
                if (!isInMenu(item)) {
                    restaurant.createMenuItem(item);
                    try {
                        serializator.serialize();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Item already in the menu", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                setVisible(false);

            }
        });
    }

}
