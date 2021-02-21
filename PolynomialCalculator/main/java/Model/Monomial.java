package Model;

/**
 * A polynomial is made up of multiple monomials characterised by their coefficient and power
 */
public class Monomial extends Number implements Comparable<Monomial> {

    private int coefficient;
    private int power;

    public Monomial(int coefficient, int power) {

        super(coefficient);
        this.coefficient = coefficient;
        this.power = power;
    }

    /**
     * It is used to sort the polynomial based on the monomial degree in descending order
     * @param monomial
     * @return
     */
    public int compareTo(Monomial monomial) {
        return monomial.getPower() - this.getPower();
    }

    public int getCoefficient() {
        return this.coefficient;
    }

    public int getPower() {
        return this.power;
    }

    void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
        setCoefficient((double)coefficient);
    }

    void setPower(int power) {
        this.power = power;
    }

}
