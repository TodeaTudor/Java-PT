package Model;

/**
 * Describes the polynomial operations that are to be implemented
 */
public interface PolynomialOperations {
    public Polynomial add(Polynomial p);
    public Polynomial subtract(Polynomial p);
    public Polynomial multiply(Polynomial p);
    public Pair divide(Polynomial p);
    public void differentiate();
    public void integrate();

}
