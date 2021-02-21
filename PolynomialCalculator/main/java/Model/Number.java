package Model;

/**
 * Parent class for the Monomial, used when doing computations that result in a floating point coefficient
 */
public class Number {
    private double doubleCoefficient;

    Number(double coefficient) {
        this.doubleCoefficient = coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.doubleCoefficient = coefficient;
    }

    public double getDoubleCoefficient() {
        return this.doubleCoefficient;
    }
}
