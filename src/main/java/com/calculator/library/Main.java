package com.calculator.library;

import com.calculator.library.basic.Calculator;

import com.calculator.library.basic.CalculatorUI;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.net.StandardSocketOptions;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception e) {
            System.err.println("FlatLaf konnte nicht geladen werden.");
        }

        SwingUtilities.invokeLater(() -> {
            Calculator calculator = new Calculator();
            new CalculatorUI(calculator);
        });
    }
}
