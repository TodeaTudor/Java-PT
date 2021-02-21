package DataLayer;
import BusinessLayer.Restaurant;

import java.io.*;

import static Start.Start.restaurant;

public class RestarurantSerializator {

    /**
     * Serialize the Restaurant object
     * @throws IOException
     */
    public void serialize() throws IOException {
        Restaurant toSerialize = new Restaurant();
        toSerialize.setRestaurantMenu(restaurant.getRestaurantMenu());
        toSerialize.setOrderTracker(restaurant.getOrderTracker());

        FileOutputStream file = new FileOutputStream("restaurant.ser");
        ObjectOutputStream out = new ObjectOutputStream(file);
        out.writeObject(toSerialize);

        out.close();
        file.close();
    }

    /**
     * Deserialize the Restaurant object
     * @param fileName
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void deserialize(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(fileName);
        ObjectInputStream in = new ObjectInputStream(file);
        restaurant = (Restaurant) in.readObject();

        in.close();
        file.close();
    }
}
