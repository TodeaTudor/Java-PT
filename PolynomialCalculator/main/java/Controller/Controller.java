package Controller;
import Model.*;
import View.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Controller {

    /**
     * Gets the coefficient from a monomial in string form
     * @param input A monomial that has been parsed from the user input
     * @return The coefficient of that monomial
     */
    private int parseCoefficient(String input) {
        int coefficient;
        String pattern = "(\\A[+-]?\\d++)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);


        if(!m.find()) {
            if (input.charAt(0) == '-') {
                return -1;
            }else {
                return 1;
            }
        }

        coefficient = Integer.parseInt(m.group(0));


        return coefficient;
    }


    /**
     * Gets the power from a monomial in string form
     * @param input A monomial that has been parsed from the user input
     * @return The degree of that monomial
     */
    public int parsePower(String input) {
        int power;
        String pattern = "(\\^\\d*)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);


        if (!m.find()) {
            if(input.charAt(input.length() - 1) == 'x' || input.charAt(input.length() - 1) == 'X') {
                return 1;
            }else {
                return 0;
            }
        }

        String aux = m.group(0).substring(1);

        power = Integer.parseInt(aux);


        return power;
    }


    /**
     * It takes the parsed monomial as a string and converts it into a monomial object
     * @param input Monomial as a string
     * @return The converted monomial object
     */
    private Monomial stringToMonomial(String input) {
        int coefficient;
        int power ;

        coefficient = parseCoefficient(input);
        power = parsePower(input);

        return new Monomial(coefficient, power);
    }


    /**
     * Takes a polynomial as a string and parses it in string monomials
     * @param input The user input polynomial
     * @return The polynomial object created
     */
    public  Polynomial createPolynomial(String input) {
        String pattern = "(\\A\\d*[xX]\\^?\\d*)|([+-]\\d*[xX]\\^?\\d*)|([+-]\\d*[xX])|([+-]\\d*)|(\\A\\d*)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);

        ArrayList<Monomial> monomials = new ArrayList<>();

        while(m.find()) {
            Monomial aux = stringToMonomial(m.group(0));
            monomials.add(aux);
        }

        Polynomial result = new Polynomial(monomials);
        return result;

    }


    /**
     * It takes a polynomial object and converts it into a string that is displayed on the interface
     * @param polynomial The polynomial object that is to be displayed
     * @return The polynomial as a string
     */
    private String polynomialToString(Polynomial polynomial) {
        StringBuilder resultString = new StringBuilder();
        String zeroString = "0";
        DecimalFormat dc = new DecimalFormat("0.00");


        for(Monomial it: polynomial.getPolynomial()) {
            if(it.getDoubleCoefficient() > 0) {
                resultString.append("+");
            }
            if (it.getPower() > 1) {
                resultString.append(dc.format(it.getDoubleCoefficient()) + "X^" + it.getPower());
            }else if (it. getPower() == 0){
                resultString.append(dc.format(it.getDoubleCoefficient()));
            }else {
                resultString.append(dc.format(it.getDoubleCoefficient()) + "X");

            }
        }

        if (resultString.toString().length() == 0) {
            return zeroString;
        }else {
            if (resultString.charAt(0) == '+') {
                resultString.deleteCharAt(0);
            }
            return resultString.toString();
        }
    }

    /**
     * Check if the polynomial that is provided by the user is correct
     * @param input The polynomial given as a string
     * @return Whether the input is valid or not
     */
    private boolean checkInput(String input) {
        String pattern = "(\\A\\d*[xX]\\^?\\d*)|([+-]\\d*[xX]\\^?\\d*)|([+-]\\d*[xX])|([+-]\\d*)|(\\A\\d*)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);

        StringBuilder checker = new StringBuilder();
        while (m.find()) {
            checker.append(m.group(0));
        }

        if(checker.toString().equals(input)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * In the controller we check the input, execute the operation selected by the user, and provide the result
     */
    public Controller() {
        final View view = new View();


        view.getCompute().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<Monomial> mockArray = new ArrayList<>();
                Polynomial p1 = new Polynomial(mockArray);
                Polynomial p2 = new Polynomial(mockArray);
                int errorCount = 0;

                if(checkInput(view.getPolynomialField().getText()) && !view.getPolynomialField().getText().isEmpty()) {
                    p1 = createPolynomial(view.getPolynomialField().getText());
                }else {
                    view.displayErrorMessage("Invalid Polynomial 1 Inputs");
                    view.getResultField().setText("Invalid inputs");
                    errorCount++;
                }

                if(view.getOperationBox().getSelectedIndex() < 4) {
                    if(checkInput(view.getPolynomial2Field().getText()) && !view.getPolynomial2Field().getText().isEmpty()) {
                        p2 = createPolynomial(view.getPolynomial2Field().getText());
                    }else {
                        view.displayErrorMessage("Invalid Polynomial 2 Inputs");
                        view.getResultField().setText("Invalid inputs");
                        errorCount++;
                    }

                }


                if(view.getOperationBox().getSelectedItem() == "Division" && polynomialToString(p2).equals("0.00")) {
                    view.displayErrorMessage("Unable to divide by 0");
                    errorCount++;
                }
                    if (errorCount == 0) {
                    switch (view.getOperationBox().getSelectedIndex()) {

                        case 0:
                            Polynomial result = p1.add(p2);
                            view.getResultField().setText(polynomialToString(result));
                            break;
                        case 1:
                            result = p1.subtract(p2);
                            view.getResultField().setText(polynomialToString(result));
                            break;
                        case 3:
                            result = p1.multiply(p2);
                            view.getResultField().setText(polynomialToString(result));
                            break;
                        case 2:
                            StringBuilder divisionString = new StringBuilder();
                            divisionString.append("Quotient: ");
                            divisionString.append(polynomialToString(p1.divide(p2).getQuotient()));
                            divisionString.append(" ");
                            divisionString.append("Remainder: ");
                            divisionString.append(polynomialToString(p1.divide(p2).getRemainder()));
                            view.getResultField().setText(divisionString.toString());
                            break;
                        case 4:
                            p1.differentiate();
                            view.getResultField().setText(polynomialToString(p1));
                            break;
                        case 5:
                            p1.integrate();
                            view.getResultField().setText(polynomialToString(p1));
                            break;


                    }
                }
            }
        });


    }

    public static void main(String[] args) {
        Controller polynomialApp = new Controller();
    }


}
