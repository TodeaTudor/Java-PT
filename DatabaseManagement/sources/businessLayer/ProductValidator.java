package businessLayer;

/**
 * We check if the command to insert a product is valid
 */
public class ProductValidator {

    /**
     * We check if the name is a string
     * @param name The product's name
     * @return
     */
    private boolean isAlpha(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * We check if the quantity is an integer
     * @param num The quantity
     * @return
     */
    public boolean isInt(String num) {
        if (!num.matches("\\d+")) {
            return false;
        }
        return true;
    }

    /**
     * We check if the price is a double
     * @param num The price
     * @return
     */
    private boolean isDouble(String num) {
        try {
            Double.parseDouble(num);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param name The name of the product
     * @param quantity The quantity of the product
     * @param price The product's price
     * @return
     */
    public boolean validateProduct(String name, String quantity, String price) {
        if (!isAlpha(name) || !isDouble(price) || !isInt(quantity)) {
            return false;
        }
        return true;
    }
}
