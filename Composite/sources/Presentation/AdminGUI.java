package Presentation;

import BusinessLayer.BaseProduct;
import BusinessLayer.CompositeProduct;
import BusinessLayer.MenuItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Creates the main Admin GUI
 */
public class AdminGUI extends JFrame{
    private JButton addItemButton;
    private JButton deleteItemButton;
    private JButton editItemButton;
    private JPanel panel;
    private JButton viewMenuButton;
    private AddProduct add;
    private DeleteProduct delete;
    private EditProduct edit;
    private ViewMenu view;

    /**
     * Inserts the Menu items into a JTable
     * @param table1
     * @param items
     */
    public static void insertTable(JTable table1, ArrayList<MenuItem> items) {
        String[] columns = {"Product Name", "Price"};
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.setColumnIdentifiers(columns);
        if (items != null) {
            for(MenuItem item : items) {
                String[] data = new String[2];
                if (item.getClass().getSimpleName().equals("BaseProduct")) {
                    data[0] = ((BaseProduct) item).getName();
                    data[1] = Double.toString(((BaseProduct) item).getCost());
                }else {
                    data[0] = ((CompositeProduct) item).getName();
                    data[1] = Double.toString(((CompositeProduct) item).getCost());
                }
                model.addRow(data);
            }
        }

        table1.setModel(model);
    }


    /**
     *
     * @param name Checks if the name is one of a composite item
     * @return
     */
    public static boolean isComposite(String name) {
        char[] chars = name.toCharArray();
        for (char c : chars) {
            if (c == '+') {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the items present in a table row
     * @param table
     * @param name
     * @return
     */
    public static ArrayList<MenuItem> getMenuItems(JTable table, String name) {
        ArrayList<MenuItem> items = new ArrayList<>();
        String[] tokens = name.split(" + ");
        for (String token : tokens) {
            for (int i = 0; i < table.getRowCount(); i++) {
                String entry = (String)table.getValueAt(i, 0);
                if (token.compareTo(entry) == 0) {
                    double entryCost = Double.parseDouble((String)table.getValueAt(i, 1));
                    items.add(new BaseProduct(token, entryCost));
                }
            }
        }
        return items;
    }

    public AdminGUI() {
        super("AdminGUI");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel);
        this.setVisible(true);
        this.pack();
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add = new AddProduct();
                add.setVisible(true);
            }
        });
        deleteItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete  = new DeleteProduct();
                delete.setVisible(true);
            }
        });
        editItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                edit = new EditProduct();
                edit.setVisible(true);
            }
        });
        viewMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view = new ViewMenu();
                view.setVisible(true);
            }
        });
    }

    public AddProduct getAdd() {
        return add;
    }

}
