package com.calculator.library.converter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BinaryCalculatorUI extends JPanel {

    private final BinaryConverter converter;
    private final JTextField display;

    public BinaryCalculatorUI(BinaryConverter converter) {
        this.converter = converter;
        setLayout(new BorderLayout());

        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);

        add(display, BorderLayout.NORTH);
        add(createButtonPanel(), BorderLayout.CENTER);

        updateDisplay();
    }


    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4));

        buttonPanel.add(createButton("CE"));
        buttonPanel.add(createButton("C"));
        buttonPanel.add(createButton("←"));
        buttonPanel.add(createButton("<>"));

        buttonPanel.add(createButton("7"));
        buttonPanel.add(createButton("8"));
        buttonPanel.add(createButton("9"));
        buttonPanel.add(createEmptyPanel());

        buttonPanel.add(createButton("4"));
        buttonPanel.add(createButton("5"));
        buttonPanel.add(createButton("6"));
        buttonPanel.add(createEmptyPanel());

        buttonPanel.add(createButton("1"));
        buttonPanel.add(createButton("2"));
        buttonPanel.add(createButton("3"));
        buttonPanel.add(createEmptyPanel());

        buttonPanel.add(createButton("+/-"));
        buttonPanel.add(createButton("0"));
        buttonPanel.add(createButton("="));
        buttonPanel.add(createEmptyPanel());

        return buttonPanel;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 22));
        button.addActionListener(new ButtonClickListener(text));
        return button;
    }

    private JPanel createEmptyPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        return panel;
    }

    private void updateDisplay() {
        display.setText(converter.getCurrentInput());
        updateButtonStates();
    }

    private void updateButtonStates() {
        Component[] components = ((Container) getComponent(1)).getComponents();
        for (Component component : components) {
            if (component instanceof JButton button) {
                String text = button.getText();

                if (text.matches("[2-9]|\\+/-")) {
                    button.setEnabled(converter.getCurrentMode() == BinaryConverter.InputMode.DECIMAL);
                }

            }
        }
    }

    private void highlightConversion() {
        Color originalColor = display.getBackground();
        display.setBackground(new Color(0.329f, 1f, 0f, 0.5f));
        Timer timer = new Timer(800, e -> display.setBackground(originalColor));
        timer.setRepeats(false);
        timer.start();
    }

    private class ButtonClickListener implements ActionListener {
        private final String command;

        public ButtonClickListener(String command) {
            this.command = command;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            handleInput(command);
        }
    }

    private void handleInput(String command) {
        if (command.matches("[0-9]") && !(converter.getCurrentMode() == BinaryConverter.InputMode.BINARY && command.matches("[2-9]"))) {
            converter.enterNumber(command);
        }
        else if (command.equals("<>")) {
            converter.switchMode();
        } else if (command.equals("=")) {
            converter.convert();
            highlightConversion();
        } else if (command.equals("CE")) {
            converter.clearAll();
        } else if (command.equals("C")) {
            converter.clearCurrent();
        } else if (command.equals("←")) {
            converter.backspace();
        } else if (command.equals("+/-")) {
            converter.changeSign();
        }

        updateDisplay();
    }
}