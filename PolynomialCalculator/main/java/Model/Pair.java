package Model;

/**
 * Used for the polynomial division. The division of two polynomials results in a quotient and a remainder
 */
public class Pair {
    private Polynomial remainder;
    private Polynomial quotient;

    public Pair (Polynomial quotient, Polynomial remainder) {
        this.remainder = remainder;
        this.quotient = quotient;
    }

    public Polynomial getRemainder () {
        return this.remainder;
    }

    public Polynomial getQuotient() {
        return quotient;
    }

}
