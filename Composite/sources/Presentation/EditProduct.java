package Presentation;

import BusinessLayer.BaseProduct;
import BusinessLayer.CompositeProduct;
import BusinessLayer.MenuItem;
import DataLayer.RestarurantSerializator;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import static Presentation.AdminGUI.*;
import static Start.Start.restaurant;

public class EditProduct extends JFrame{
    private JButton editProductButton;
    private JTextField priceField;
    private JTable itemsTable;
    private JPanel panel1;
    private RestarurantSerializator serializator = new RestarurantSerializator();

    public EditProduct() {
        super("Edit Product");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();
        insertTable(itemsTable, restaurant.getRestaurantMenu());

        editProductButton.addActionListener(new ActionListener() {
            @Override
            /**
             * Changes the price of an already existing item
             */
            public void actionPerformed(ActionEvent e) {
                try {
                    double newPrice = Double.parseDouble(priceField.getText());
                    String name = (String)itemsTable.getValueAt(itemsTable.getSelectedRow(), 0);
                    if(isComposite(name)) {
                        CompositeProduct toEdit = new CompositeProduct(name, getMenuItems(itemsTable, name));
                        toEdit.setCost(newPrice);
                        restaurant.editMenuItem(toEdit);
                    }else {
                        restaurant.editMenuItem(new BaseProduct(name, newPrice));
                    }
                    try {
                        serializator.serialize();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    setVisible(false);
                } catch(NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "Please enter a number as the new price", "ERROR", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }

}
