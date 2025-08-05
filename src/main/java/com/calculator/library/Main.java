package com.calculator.library;

import com.calculator.library.basic.Calculator;
import com.calculator.library.basic.CalculatorUI;
import com.calculator.library.converter.BinaryConverter;
import com.calculator.library.converter.BinaryCalculatorUI;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception e) {
            System.err.println("FlatLaf konnte nicht geladen werden.");
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("BLKB Instruments");

            URL iconURL = Main.class.getResource("/com/calculator/library/icon.png");
            if (iconURL != null) {
                Image icon = Toolkit.getDefaultToolkit().getImage(iconURL);
                frame.setIconImage(icon);
            } else {
                System.err.println("Icon konnte nicht geladen werden.");
            }

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 600);
            frame.setMinimumSize(new Dimension(350, 500));

            JTabbedPane tabbedPane = new JTabbedPane();

            // Calculator
            Calculator calculator = new Calculator();
            CalculatorUI calculatorPanel = new CalculatorUI(calculator);
            tabbedPane.addTab("Calculator", calculatorPanel.getMainPanel());

            // Converter
            BinaryConverter binaryConverter = new BinaryConverter();
            BinaryCalculatorUI binaryPanel = new BinaryCalculatorUI(binaryConverter);
            tabbedPane.addTab("Converter", binaryPanel);

            frame.add(tabbedPane);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
