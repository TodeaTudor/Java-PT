package View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class View extends JFrame {

    private JFrame frame;
    private JTextField polynomialField;
    private JLabel p1Label;
    private JComboBox operationBox;
    private JLabel comboLabel;
    private JLabel p2Label;
    private JTextField polynomial2Field;
    private JButton compute;
    private JLabel resultLabel;
    private JTextField resultField;


    public View() {
        frame = new JFrame("Polynomial Calculator");
        polynomialField = new JTextField("Example: 2x^3+4x+5");
        p1Label = new JLabel();
        String[] operationArray = {"Addition", "Subtraction", "Division", "Multiplication", "Differentiation", "Integration"};
        operationBox = new JComboBox(operationArray);
        comboLabel = new JLabel();
        p2Label = new JLabel();
        polynomial2Field = new JTextField();
        compute  = new JButton("Compute");
        resultLabel = new JLabel();
        resultField = new JTextField();

        p1Label.setText("Insert the first polynomial:");
        p1Label.setBounds(25, 50, 200, 30);
        polynomialField.setBounds(235, 50, 200, 30);

        comboLabel.setText("Select operation");
        comboLabel.setBounds(25, 100, 130, 30);
        operationBox.setBounds(235, 100, 200, 30);

        p2Label.setText("Insert the second polynomial:");
        p2Label.setBounds(25, 150, 200, 30);
        polynomial2Field.setBounds(235, 150, 200, 30);

        compute.setBounds(163, 200, 100, 30);

        resultLabel.setText("The result is:");
        resultLabel.setVisible(false);
        resultField.setVisible(false);
        resultLabel.setBounds(25, 250, 200, 30);
        resultField.setBounds(235, 250, 200, 30);

        operationBox.addActionListener (new ActionListener() {
            /**
             * Hides the input field for the second polynomial if we select the differentiation or integration operation
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                if (operationBox.getSelectedIndex() == 4 || operationBox.getSelectedIndex() == 5) {
                    p2Label.setVisible(false);
                    polynomial2Field.setVisible(false);
                    polynomial2Field.revalidate();
                    polynomial2Field.repaint();
                    compute.setBounds(163, 150, 100, 30);
                    resultLabel.setBounds(25, 200, 200, 30);
                    resultField.setBounds(235, 200, 200, 30);
                } else {
                    p2Label.setVisible(true);
                    polynomial2Field.setVisible(true);
                    polynomial2Field.revalidate();
                    polynomial2Field.repaint();
                    compute.setBounds(163, 200, 100, 30);
                    resultLabel.setBounds(25, 250, 200, 30);
                    resultField.setBounds(235, 250, 200, 30);

                }
            }
        });

        compute.addActionListener(new ActionListener() {
            /**
             * Sets the result field visible only after we press the "Compute" button
             * @param e
             */
            public void actionPerformed(ActionEvent e) {
                resultLabel.setVisible(true);
                resultField.setVisible(true);
                resultField.revalidate();
                resultField.repaint();
            }
        });


        frame.add(resultField);
        frame.add(resultLabel);
        frame.add(compute);
        frame.add(polynomial2Field);
        frame.add(p2Label);
        frame.add(comboLabel);
        frame.add(operationBox);
        frame.add(polynomialField);
        frame.add(p1Label);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(460, 330);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(new JFrame(), message, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    public JButton getCompute() {
        return compute;
    }

    public JComboBox getOperationBox() {
        return operationBox;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JLabel getComboLabel() {
        return comboLabel;
    }

    public JLabel getP1Label() {
        return p1Label;
    }

    public JLabel getP2Label() {
        return p2Label;
    }

    public JLabel getResultLabel() {
        return resultLabel;
    }

    public JTextField getPolynomial2Field() {
        return polynomial2Field;
    }

    public JTextField getPolynomialField() {
        return polynomialField;
    }

    public JTextField getResultField() {
        return resultField;
    }

    public void setComboLabel(JLabel comboLabel) {
        this.comboLabel = comboLabel;
    }

    public void setCompute(JButton compute) {
        this.compute = compute;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void setOperationBox(JComboBox operationBox) {
        this.operationBox = operationBox;
    }

    public void setP1Label(JLabel p1Label) {
        this.p1Label = p1Label;
    }

    public void setP2Label(JLabel p2Label) {
        this.p2Label = p2Label;
    }

    public void setPolynomial2Field(JTextField polynomial2Field) {
        this.polynomial2Field = polynomial2Field;
    }

    public void setPolynomialField(JTextField polynomialField) {
        this.polynomialField = polynomialField;
    }

    public void setResultField(JTextField resultField) {
        this.resultField = resultField;
    }

    public void setResultLabel(JLabel resultLabel) {
        this.resultLabel = resultLabel;
    }
}
