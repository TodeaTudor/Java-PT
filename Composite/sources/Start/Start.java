package Start;

import DataLayer.RestarurantSerializator;
import BusinessLayer.Restaurant;
import Presentation.AdminGUI;
import Presentation.ChefGUI;
import Presentation.WaiterGUI;

import java.io.IOException;

public class Start {

    public static Restaurant restaurant = new Restaurant();

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        RestarurantSerializator ser = new RestarurantSerializator();
        if (args.length != 0) {
            ser.deserialize(args[0]);
        }
        AdminGUI admin = new AdminGUI();
        WaiterGUI waiter = new WaiterGUI();
        ChefGUI chef = new ChefGUI();
        restaurant.addObserver(chef);
    }

}
