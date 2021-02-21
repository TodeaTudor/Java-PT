package BusinessLayer;

import java.io.Serializable;

/**
 * Abstract class that represents a MenuItems
 */
public abstract class MenuItem implements Serializable {

    public abstract double computePrice();

}
