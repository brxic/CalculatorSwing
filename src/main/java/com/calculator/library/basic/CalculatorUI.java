package com.calculator.library.basic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class CalculatorUI {
    private JFrame frame;
    private JTextField display;
    private Calculator calculator;

    public CalculatorUI(Calculator calculator) {
        this.calculator = calculator; // konstruktor

        // das fenster wird erstellt
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 495);
        frame.setMinimumSize(new Dimension(260, 210));
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
            button.setFont(new Font("Arial", Font.BOLD, 22));
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void highlightResult() {
        // Aktuelle Hintergrundfarbe holen
        Color originalColor = display.getBackground();

        // Anzeige färben
        display.setBackground(new Color(0.329f, 1f, 0f, 0.5f)); // grün

        // Nach 1 Sekunde wieder zurück zur Originalfarbe
        Timer timer = new Timer(1000, e -> display.setBackground(originalColor));
        timer.setRepeats(false); // nur einmal ausführen
        timer.start();
    }

    private void highlightError() {
        Color originalColor = display.getBackground();

        display.setBackground(new Color(1f,0f,0f,.5f ));

        Timer timer = new Timer(1000, e -> display.setBackground(originalColor));
        timer.setRepeats(false);
        timer.start();
    }


    // click -und actionlistener funktionen
    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.matches("[0-9\\.]")) { // wenn der input von einem button mit 0-9 kommt
                calculator.enterNumber(command); // nummer auslesen und weiterverwenden
                display.setText(calculator.getCurrentInput()); // nummer an resultatfeld geben
            } else if (command.matches("[+\\-*/×÷]")) { // wenn der input von einem button mit den operatoren kommt
                String op;
                if (command.equals("×")) {
                    op = "*";
                } else if (command.equals("÷")) {
                    op = "/";
                } else {
                    op = command;
                }

                if (calculator.setOperator(op)) { // wenn ein operator eingegeben wurde
                    display.setText(""); // leert das eingabe feld fürs feeling (nicht zwingend nötig)
                }

            } else if (command.equals("=")) { // wenn das gleichzeichen gedrückt wird
                try {
                    BigDecimal result = calculator.calculate(); // berechnung des resultats
                    display.setText(result.stripTrailingZeros().toPlainString()); // resultat anzeigen und runden
                    highlightResult();
                } catch (ArithmeticException ex) {
                    highlightError();
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
            } else if (command.equals("+/-")) { // wenn +/- gedrückt wird
                calculator.changeSign(); // vorzeichen ändern
                display.setText(calculator.getCurrentInput()); // vorzeichen anwenden
            }
        }
    }
}

