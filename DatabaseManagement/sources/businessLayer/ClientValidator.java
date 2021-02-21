package businessLayer;

/**
 * We check if the client to be inserted is valid
 */
public class ClientValidator {

    /**
     * We check if the client name is a string starting with an upper case letter
     * The '-' character is allowed for names
     * @param name Client's first name, last name and city
     * @return
     */
    private boolean isName(String name) {
        if (Character.getType(name.charAt(0)) != Character.UPPERCASE_LETTER) {
            return false;
        }

        char[] chars = name.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c) && c != '-') {
                return false;
            }
        }

        return true;
    }

    /**
     *
     * @param name The full name of our client
     * @param city The city
     * @return
     */
    public boolean validateClient (String name, String city) {
        String[] tokens = name.split(" ");

        for (String aux : tokens) {
            if (!isName(aux)) {
                return false;
            }
        }

        if (!isName(city)) {
            return false;
        }

        return true;
    }
}
