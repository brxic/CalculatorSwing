package com.calculator.library;

import com.calculator.library.basic.Calculator;
import com.calculator.library.basic.CalculatorUI;
import com.calculator.library.converter.BaseConverterUI;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception e) {
            System.err.println("FlatLaf konnte nicht geladen werden.");
        }

        SwingUtilities.invokeLater(() -> {
            createCalculatorSuite();
        });
    }

    private static void createCalculatorSuite() {
        JFrame frame = new JFrame("Calculator Suite");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setMinimumSize(new Dimension(350, 500));

        // Tab Panel erstellen
        JTabbedPane tabbedPane = new JTabbedPane();

        // Basic Calculator Tab
        Calculator calculator = new Calculator();
        JPanel calculatorPanel = createCalculatorPanel(calculator);
        tabbedPane.addTab("Calculator", calculatorPanel);

        // Base Converter Tab
        BaseConverterUI converterUI = new BaseConverterUI();
        tabbedPane.addTab("Base Converter", converterUI);

        frame.add(tabbedPane);
        frame.setLocationRelativeTo(null); // Zentrieren
        frame.setVisible(true);
    }

    /**
     * Erstellt Calculator Panel ohne eigenes JFrame
     */
    private static JPanel createCalculatorPanel(Calculator calculator) {
        JPanel panel = new JPanel(new BorderLayout());

        // Display
        JTextField display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        panel.add(display, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(5, 4));
        String[] buttons = {
                "CE", "C", "←", "÷",
                "7", "8", "9", "×",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "+/-", "0", ".", "="
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 22));
            button.addActionListener(e -> handleCalculatorInput(e.getActionCommand(), calculator, display));
            buttonPanel.add(button);
        }

        panel.add(buttonPanel, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Calculator Input Handler (aus CalculatorUI extrahiert)
     */
    private static void handleCalculatorInput(String command, Calculator calculator, JTextField display) {
        if (command.matches("[0-9\\.]")) {
            calculator.enterNumber(command);
            display.setText(calculator.getCurrentInput());
        } else if (command.matches("[+\\-*/×÷]")) {
            String op = switch (command) {
                case "×" -> "*";
                case "÷" -> "/";
                default -> command;
            };
            if (calculator.setOperator(op)) {
                display.setText("");
            }
        } else if (command.equals("=")) {
            try {
                var result = calculator.calculate();
                display.setText(result.stripTrailingZeros().toPlainString());
                highlightField(display, new Color(0.329f, 1f, 0f, 0.5f));
            } catch (ArithmeticException ex) {
                highlightField(display, new Color(1f, 0f, 0f, 0.5f));
                display.setText("Error");
            }
        } else if (command.equals("CE")) {
            calculator.clearAll();
            display.setText("");
        } else if (command.equals("C")) {
            calculator.clearCurrent();
            display.setText("");
        } else if (command.equals("←")) {
            calculator.backspace();
            display.setText(calculator.getCurrentInput());
        } else if (command.equals("+/-")) {
            calculator.changeSign();
            display.setText(calculator.getCurrentInput());
        }
    }

    private static void highlightField(JTextField field, Color color) {
        Color originalColor = field.getBackground();
        field.setBackground(color);
        Timer timer = new Timer(1000, e -> field.setBackground(originalColor));
        timer.setRepeats(false);
        timer.start();
    }
}