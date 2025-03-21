package com.calculator.library.basic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorUI {
    private JFrame frame;
    private JTextField display;
    private Calculator calculator;

    public CalculatorUI(Calculator calculator) {
        this.calculator = calculator; // konstruktor

        // das fenster wird erstellt
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setMinimumSize(new Dimension(260, 200));
        frame.setLayout(new BorderLayout());

        // die anzeige wird zusammengestellt
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        frame.add(display, BorderLayout.NORTH);

        // button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4));

        // button bezeichnungnen
        String[] buttons = {
                "CE", "C", "←", "÷",
                "7", "8", "9", "×",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "+/-", "0", ".", "="
        };

        // styles und clicklistener
        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // click -und actionlistener funktionen
    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.matches("[0-9]")) { // wenn der input von einem button mit 0-9 kommt
                calculator.enterNumber(Integer.parseInt(command)); // nummer auslesen und weiterverwenden
                display.setText(String.valueOf(calculator.getCurrentInput())); // nummer an resultatfeld geben

            } else if (command.matches("[+\\-×÷]")) { // wenn der input von einem button mit den operatoren kommt
                calculator.setOperator(command); // verwendet den ausgelesenen operator
                display.setText(""); // leert das eingabe feld fürs feeling (nicht zwingend nötig)

            } else if (command.equals("=")) { // wenn das gleichzeichen gedrückt wird
                try {
                    double result = calculator.calculate(); // führt die berechnung durch
                    display.setText(String.valueOf(result));
                } catch (ArithmeticException ex) {
                    display.setText("Error");
                }
            } else if (command.equals("CE")) { // wenn CE gedrückt wird
                calculator.clearAll(); // führe eine löschung aller bisherigen rechnungnen durch
                display.setText("");
            } else if (command.equals("C")) { // wenn C gedrückt wird
                calculator.clearCurrent(); // führe eine löschung der derzeitigen rechnung durch
                display.setText("");
            } else if (command.equals("←")) { // wenn ← gedrückt wird
                calculator.backspace(); // führe eine löschung der letzten eingabe durch
                display.setText(String.valueOf(calculator.getCurrentInput()));
            }
        }
    }
}

