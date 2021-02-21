package Model;

import java.lang.reflect.Array;
import java.util.*;

/**
 * The polynomial is characterised by a list of Monomials
 */
public class Polynomial implements PolynomialOperations {
    private ArrayList<Monomial> polynomial;

    public Polynomial(ArrayList<Monomial> polynomial) {
        this.polynomial = polynomial;
    }

    public ArrayList<Monomial> getPolynomial() {
        return polynomial;
    }


    /**
     *  We sum two polynomials.
     * @param p The polynomial that we wish to add to our current one
     * @return The sum of the two polynomials
     */
    public Polynomial add(Polynomial p) {
        ArrayList<Monomial> resultList = new ArrayList<>();
        ArrayList<Monomial> toRemove = new ArrayList<>();

        for (Monomial firstMonomial: this.getPolynomial()) {
            resultList.add(firstMonomial);
        }
        ArrayList<Monomial> resultList2 = new ArrayList<>();
        for (Monomial secondMonomial: p.getPolynomial()) {
            int flag = 0;
            for(Monomial inList: resultList) {
                if (secondMonomial.getPower() == inList.getPower()) {
                    inList.setCoefficient(inList.getDoubleCoefficient() + secondMonomial.getDoubleCoefficient());
                    if(inList.getDoubleCoefficient() == 0) {
                        toRemove.add(inList);
                    }
                    flag++;
                }
            }
            if (flag == 0) {
                resultList2.add(secondMonomial);
            }
        }
        resultList.removeAll(toRemove);
        ArrayList<Monomial> finalList = new ArrayList<>(resultList);
        finalList.addAll(resultList2);
        Polynomial result = new Polynomial(finalList);

        Collections.sort(result.getPolynomial());
        return result;
    }

    /**
     * We subtract a polynomial from our current one
     * @param p The polynomial that is to be subtracted
     * @return The result of the subtraction
     */
    public Polynomial subtract(Polynomial p) {
        ArrayList<Monomial> resultList = new ArrayList<>();
        ArrayList<Monomial> toRemove = new ArrayList<>();
        for (Monomial firstMonomial: this.getPolynomial()) {
            resultList.add(firstMonomial);
        }
        ArrayList<Monomial> resultList2 = new ArrayList<>();

        for (Monomial secondMonomial: p.getPolynomial()) {
            int flag = 0;
            for(Monomial inList: resultList) {
                if (secondMonomial.getPower() == inList.getPower()) {
                    inList.setCoefficient(inList.getDoubleCoefficient() - secondMonomial.getDoubleCoefficient());
                    if(inList.getDoubleCoefficient() == 0) {
                        toRemove.add(inList);
                    }
                    flag++;
                }
            }
            if (flag == 0) {
                secondMonomial.setCoefficient(- secondMonomial.getDoubleCoefficient());
                resultList2.add(secondMonomial);
            }
        }
        resultList.removeAll(toRemove);
        ArrayList<Monomial> finalList = new ArrayList<>(resultList);
        finalList.addAll(resultList2);
        Polynomial result = new Polynomial(finalList);

        Collections.sort(result.getPolynomial());
        return result;
    }

    /**
     * We multiply our polynomial with another one
     * @param p The polynomial we multiply with
     * @return The result of the multiplication
     */
    public Polynomial multiply(Polynomial p) {
        ArrayList<Monomial> resultList = new ArrayList<>();
        Polynomial result = new Polynomial(resultList);

        for (Monomial firstMonomial: this.getPolynomial()) {
            ArrayList<Monomial> intermediateResult = new ArrayList<>();
            for (Monomial secondMonomial: p.getPolynomial()) {
                Monomial aux = new Monomial(firstMonomial.getCoefficient()*secondMonomial.getCoefficient(), firstMonomial.getPower() + secondMonomial.getPower());
                aux.setCoefficient(firstMonomial.getDoubleCoefficient()*secondMonomial.getDoubleCoefficient());
                intermediateResult.add(aux);
            }
            Polynomial intermediatePolynomial = new Polynomial(intermediateResult);
            result = result.add(intermediatePolynomial);

        }

        Collections.sort(result.getPolynomial());
        return result;
    }

    /**
     * We differentiate our polynomial
     */
    public void differentiate() {
        ArrayList<Monomial> toRemove = new ArrayList<>();
        Collections.sort(this.getPolynomial());

        for (Monomial iterator: this.getPolynomial()) {
            if (iterator.getPower() - 1 >= 0) {
                iterator.setCoefficient(iterator.getCoefficient() * iterator.getPower());
                iterator.setPower(iterator.getPower() - 1);
            }else {
                toRemove.add(iterator);
            }
        }
        this.getPolynomial().removeAll(toRemove);

    }

    /**
     * We integrate our polynomial
     */
    public void integrate() {
        Collections.sort(this.getPolynomial());
        for (Monomial iterator: this.getPolynomial()) {
            iterator.setPower(iterator.getPower() + 1);
            iterator.setCoefficient((double)iterator.getCoefficient()/iterator.getPower());

        }
    }

    /**
     * We perform long division between two polynomial
     * @param p The polynomial by which we divide our polynomial
     * @return A pair of polynomials, namely the quotient and the remainder of the division
     */
    public Pair divide(Polynomial p) {
        ArrayList<Monomial> quotientList = new ArrayList<>();
        ArrayList<Monomial> remainderList = new ArrayList<>(this.getPolynomial());
        Polynomial quotient = new Polynomial(quotientList);
        Polynomial remainder = new Polynomial(remainderList);

        Collections.sort(remainder.getPolynomial());
        Collections.sort(p.getPolynomial());

        while (!remainder.getPolynomial().isEmpty() && remainder.getPolynomial().get(0).getPower() >= p.getPolynomial().get(0).getPower()) {
            double auxCoefficient = remainder.getPolynomial().get(0).getDoubleCoefficient()/ p.getPolynomial().get(0).getDoubleCoefficient();
            int auxPower = remainder.getPolynomial().get(0).getPower() - p.getPolynomial().get(0).getPower();
            Monomial aux = new Monomial((int)auxCoefficient, auxPower);
            aux.setCoefficient(auxCoefficient);
            ArrayList<Monomial> auxList = new ArrayList<>();
            auxList.add(aux);
            Polynomial auxPolynomial = new Polynomial(auxList);
            quotient = quotient.add(auxPolynomial);
            remainder = remainder.subtract(auxPolynomial.multiply(p));

        }

        return new Pair(quotient, remainder);
    }
}

