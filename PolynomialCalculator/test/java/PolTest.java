import Controller.Controller;
import Model.*;
import Model.Polynomial;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PolTest {

    /**
     * We check if the expected output matches the actual output
     * @param expected The expected output
     * @param actual The actual output of the operation performed
     */
    public static void testEval(Polynomial expected, Polynomial actual) {
        Iterator<Monomial> it1 = actual.getPolynomial().iterator();
        Iterator<Monomial> it2 = expected.getPolynomial().iterator();

        while(it1.hasNext() && it2.hasNext()) {
            Monomial aux1 = it1.next();
            Monomial aux2 = it2.next();
            assertEquals(aux1.getPower(), aux2.getPower());
            assertEquals(aux1.getDoubleCoefficient(), aux2.getDoubleCoefficient(), 0.01f);
        }
    }

    /**
     * We provide two polynomials as strings and the expected result of their addition
     * @param pol1 The first polynomial
     * @param pol2 The second polynomial
     * @param expected The expected result of their addition
     */
    public static void computeAddTest(String pol1, String pol2, String expected) {
        Controller test = new Controller();

        Polynomial polynomial1 = test.createPolynomial(pol1);
        Polynomial polynomial2 = test.createPolynomial(pol2);
        Polynomial result = test.createPolynomial(expected);
        Polynomial expectedResult = polynomial1.add(polynomial2);

        testEval(expectedResult, result);
    }

    /**
     * We provide two polynomials as strings and the expected result of the subtraction of the second one from the first one
     * @param pol1 The first polynomial
     * @param pol2 The second polynomial
     * @param expected The expected result of the subtraction
     */
    public static void computeSubtractTest(String pol1, String pol2, String expected) {
        Controller test = new Controller();

        Polynomial polynomial1 = test.createPolynomial(pol1);
        Polynomial polynomial2 = test.createPolynomial(pol2);
        Polynomial result = test.createPolynomial(expected);
        Polynomial expectedResult = polynomial1.subtract(polynomial2);

        testEval(expectedResult, result);
    }

    /**
     * We provide two polynomials as strings and the expected result of their multiplication
     * @param pol1 The first polynomial
     * @param pol2 The second polynomial
     * @param expected The expected result of their multiplication
     */
    public static void computeMultiplyTest(String pol1, String pol2, String expected) {
        Controller test = new Controller();

        Polynomial polynomial1 = test.createPolynomial(pol1);
        Polynomial polynomial2 = test.createPolynomial(pol2);
        Polynomial result = test.createPolynomial(expected);
        Polynomial expectedResult = polynomial1.multiply(polynomial2);

        testEval(expectedResult, result);
    }

    /**
     * We provide a polynomial to be differentiated
     * @param pol1 The polynomial to be differentiated
     * @param expected The expected result of the differentiation
     */
    public static void computeDifferentiateTest(String pol1, String expected) {
        Controller test = new Controller();

        Polynomial polynomial1 = test.createPolynomial(pol1);
        Polynomial result = test.createPolynomial(expected);
        polynomial1.differentiate();

        testEval(polynomial1, result);
    }

    /**
     * We parse a monomial and select its coefficients. We need this for the parsing of the expected result for integration and division.
     * We can not use the one in our Controller class since that works only on int coefficients.
     * @param input Monomial with double coefficients as a string
     * @return The double coefficient
     */
    public static double createDoubleCoefficient(String input) {
        double coefficient;
        String pattern = "([-+]?[0-9]*\\.?[0-9]+)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);


        if(!m.find()) {
            if (input.charAt(0) == '-') {
                return -1;
            }else {
                return 1;
            }
        }

        coefficient = Double.parseDouble(m.group(0));


        return coefficient;
    }

    /**
     * We take a monomial with double coefficient and convert it into a monomial object
     * @param input A monomial as a string
     * @return A monomial with double coefficients as a monomial object
     */
    public static Monomial createDoubleMonomial(String input) {
        Controller test = new Controller();

        double coefficient;
        int power ;

        coefficient = createDoubleCoefficient(input);
        power = test.parsePower(input);

        Monomial aux = new Monomial((int) coefficient, power);
        aux.setCoefficient(coefficient);
        return aux;
    }

    /**
     * Converts a polynomial with double coefficients from a string into a polynomial object
     * @param input The polynomial as a string
     * @return The polynomial as a Polynomial object
     */
    public static Polynomial createDoublePolynomial(String input) {
        String pattern = "(\\A\\d*[xX]\\^?\\d*)|([+-]\\d*[xX]\\^?\\d*)|([-+]?[0-9]*\\.?[0-9]+[xX]?\\^?\\d*)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);

        ArrayList<Monomial> monomials = new ArrayList<>();

        while(m.find()) {
            Monomial aux = createDoubleMonomial(m.group(0));
            monomials.add(aux);
        }

        Polynomial result = new Polynomial(monomials);
        return result;
    }

    /**
     * We compute the integration of a polynomial
     * @param pol1 The polynomial to be integrated
     * @param expected The expected result of the integration
     */
    public static void computeIntegrateTest(String pol1, String expected) {
        Controller test = new Controller();

        Polynomial polynomial1 = test.createPolynomial(pol1);
        Polynomial result = createDoublePolynomial(expected);
        polynomial1.integrate();

        testEval(polynomial1, result);
    }

    /**
     * We divide two polynomials and check the quotient
     * @param pol1 The first polynomial, the numerator
     * @param pol2 The second polynomial, the denominator
     * @param expected The expected quotient of their division
     */
    public static void computeDivideTest(String pol1, String pol2, String expected) {
        Controller test = new Controller();

        Polynomial polynomial1 = test.createPolynomial(pol1);
        Polynomial polynomial2 = test.createPolynomial(pol2);
        Polynomial result = createDoublePolynomial(expected);
        Polynomial expectedResult = polynomial1.divide(polynomial2).getQuotient();

        testEval(expectedResult, result);
    }

    @Test
    public void addTest () {

        computeAddTest("x", "x", "2x");

        computeAddTest("2", "2", "4");

        computeAddTest("-x", "-x", "-2x");

        computeAddTest("-2", "-2", "-4");

        computeAddTest("-2x^3+6x+7", "3x^6+4x^5+3", "3x^6+4x^5-2x^3+6x+10");


    }

    @Test
    public void subtractTest() {

        computeSubtractTest("x", "x", "0");

        computeSubtractTest("-x", "-x", "-2x");

        computeSubtractTest("2", "2", "0");

        computeSubtractTest("2", "-2", "4");

        computeSubtractTest("-2", "-2", "4");

        computeSubtractTest("2x^7-6x^3+2x^2+4+2x", "-6x^10+5x^8-3x^7+6x^2-13-x", "6x^10-5x^8+5x^7-6x^3-4x^2+3x+17");

    }

    @Test
    public void multiplyTest() {

        computeMultiplyTest("x", "x", "x^2");

        computeMultiplyTest("-x", "-x", "x^2");

        computeMultiplyTest("-2x", "-2x", "4x^2");

        computeMultiplyTest("2x", "-2x", "-4x^2");

        computeMultiplyTest("-2", "-3", "6");

        computeMultiplyTest("-2", "3", "-6");

        computeMultiplyTest("-2x^4+6x^3+3x^2+2", "-3x^2+6x", "6x^6-30x^5+27x^4+18x^3-6x^2+12x");

    }

    @Test
    public void divideTest() {
        computeDivideTest("2", "2", "1");

        computeDivideTest("2x", "x", "2");

        computeDivideTest("-x^2", "2x", "-0.50x");

        computeDivideTest("-4x^6+5x^3+4x+2", "x^3+7x+2", "-4x^3+28x+13");

    }

    @Test
    public void differentiateTest() {

        computeDifferentiateTest("x", "1");

        computeDifferentiateTest("-x", "-1");

        computeDifferentiateTest("6", "0");

        computeDifferentiateTest("-6", "0");

        computeDifferentiateTest("x^3", "3x^2");

        computeDifferentiateTest("-x^2", "-2x");

        computeDifferentiateTest("-x^4+6", "-4x^3");

        computeDifferentiateTest("7x^4+3x^3-2x^2-4x-10", "28x^3+9x^2-4x-4");


    }

    @Test
    public void integrateTest() {

        computeIntegrateTest("x", "0.5x^2");

        computeIntegrateTest("-x", "-0.5x^2");

        computeIntegrateTest("2", "2x");

        computeIntegrateTest("-4x^6+5x^3+4x+2", "-0.57x^7+1.25x^4+2x^2+2x");


    }
}
