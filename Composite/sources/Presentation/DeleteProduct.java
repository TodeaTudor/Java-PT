package Presentation;

import static Presentation.AdminGUI.*;
import static Start.Start.restaurant;

import BusinessLayer.BaseProduct;
import BusinessLayer.CompositeProduct;
import BusinessLayer.MenuItem;
import DataLayer.RestarurantSerializator;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class DeleteProduct extends JFrame{
    private JTable itemsTable;
    private JPanel panel1;
    private JButton deleteButton;
    private RestarurantSerializator serializator = new RestarurantSerializator();

    /**
     * Deletes an item from the menu
     * @param item to be deleted
     * @throws IOException
     */
    private void deleteFromMenu(MenuItem item) throws IOException {
        ArrayList<MenuItem> toRemove = new ArrayList<>();
        for(MenuItem entry : restaurant.getRestaurantMenu()) {
            if (entry.getClass() == item.getClass()) {
                if (item.getClass().getSimpleName().compareTo("BaseProduct") == 0) {
                    BaseProduct auxEntry = (BaseProduct) entry;
                    BaseProduct auxItem = (BaseProduct) item;
                    if (auxEntry.getName().compareTo(auxItem.getName()) == 0) {
                        toRemove.add(entry);
                        break;
                    }
                } else {
                    CompositeProduct auxEntry = (CompositeProduct) entry;
                    CompositeProduct auxItem = (CompositeProduct) item;
                    if (auxEntry.getName().compareTo(auxItem.getName()) == 0) {
                        toRemove.add(entry);
                        break;
                    }
                }
            }
        }
        restaurant.deleteMenuItem(toRemove.get(0));
        serializator.serialize();
    }


    public DeleteProduct() {
        super("Delete Product");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();
        insertTable(itemsTable, restaurant.getRestaurantMenu());
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = (String)itemsTable.getValueAt(itemsTable.getSelectedRow(), 0);
                double price = Double.parseDouble((String)itemsTable.getValueAt(itemsTable.getSelectedRow(), 1));
                if(isComposite(name)) {
                    try {
                        deleteFromMenu(new CompositeProduct(name, getMenuItems(itemsTable, name)));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }else {
                    try {
                        deleteFromMenu(new BaseProduct(name, price));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                setVisible(false);
            }
        });
    }

}
