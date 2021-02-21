package Presentation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WaiterGUI extends JFrame{
    private JButton addOrderButton;
    private JButton viewOrComputeBillButton;
    private JPanel panel1;
    private AddOrder add;
    private ViewOrComputeBillForOrder view;

    /**
     * The GUI for the Waiter
     */
    public WaiterGUI() {
        super("WaiterGUI");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setContentPane(panel1);
        this.setLocation(500, 0);
        this.setVisible(true);
        this.pack();
        addOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add = new AddOrder();
                add.setVisible(true);
            }
        });
        viewOrComputeBillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view = new ViewOrComputeBillForOrder();
                view.setVisible(true);
            }
        });
    }
}
