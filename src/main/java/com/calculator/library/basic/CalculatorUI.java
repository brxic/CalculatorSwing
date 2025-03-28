package com.calculator.library.basic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
        setupKeyBindings();
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
            handleInput(e.getActionCommand());
        }
    }

    private void handleInput(String command) {
        System.out.println("Input: '" + command + "'");
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
                BigDecimal result = calculator.calculate();
                display.setText(result.stripTrailingZeros().toPlainString());
                highlightResult();
            } catch (ArithmeticException ex) {
                highlightError();
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

    private void setupKeyBindings() {
        JComponent rootPane = frame.getRootPane();
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = rootPane.getActionMap();

        // Zahlen (0–9) und Dezimalpunkt
        for (int i = 0; i <= 9; i++) {
            String num = String.valueOf(i);
            inputMap.put(KeyStroke.getKeyStroke(num), num);
            inputMap.put(KeyStroke.getKeyStroke("NUMPAD" + i), num);
            actionMap.put(num, new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    handleInput(num);
                }
            });
        }

        inputMap.put(KeyStroke.getKeyStroke('.'), ".");
        inputMap.put(KeyStroke.getKeyStroke("DECIMAL"), ".");
        actionMap.put(".", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                handleInput(".");
            }
        });

        // Operatoren (+, -, *, /) – auch vom Numpad
        String[] ops = {"+", "-", "*", "/"};
        for (String op : ops) {
            inputMap.put(KeyStroke.getKeyStroke(op), op); // normale Taste
            String numpadKey = switch (op) {
                case "+" -> "ADD";
                case "-" -> "SUBTRACT";
                case "*" -> "MULTIPLY";
                case "/" -> "DIVIDE";
                default -> null;
            };
            if (numpadKey != null) {
                inputMap.put(KeyStroke.getKeyStroke(numpadKey), op); // Numpad-Taste
            }
            actionMap.put(op, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleInput(op);
                }
            });
        }


        // Enter
        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "=");
        actionMap.put("=", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                handleInput("=");
            }
        });

        // Backspace
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "←");
        actionMap.put("←", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                handleInput("←");
            }
        });

        // Shift für Vorzeichenwechsel
        inputMap.put(KeyStroke.getKeyStroke("shift SHIFT"), "+/-");
        actionMap.put("+/-", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                handleInput("+/-");
            }
        });

        // Escape für CE
        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "CE");
        actionMap.put("CE", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                handleInput("CE");
            }
        });
    }

}

