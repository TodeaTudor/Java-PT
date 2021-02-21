package Presentation;

import javax.swing.*;

import static Presentation.AdminGUI.insertTable;
import static Start.Start.restaurant;

/**
 * Prints the Menu of the Restaurant in a JTable
 */
public class ViewMenu extends JFrame{
    private JPanel panel1;
    private JTable table1;

    public ViewMenu() {
        super("ViewMenu");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setContentPane(panel1);
        this.setVisible(true);
        this.pack();
        insertTable(table1, restaurant.getRestaurantMenu());
    }
}
