package com.calculator.library.converter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class BaseConverterUI extends JPanel {
    private JTextField inputField;
    private JTextField resultField;
    private JComboBox<NumberBase> fromComboBox;
    private JComboBox<NumberBase> toComboBox;
    private JButton swapButton;
    private BaseConverter converter;
    private JButton[] allButtons;

    public BaseConverterUI() {
        this.converter = new BaseConverter();
        setupUI();
        updateButtonStates();
        setupKeyBindings();
        updateDisplay(); // Initial conversion
    }

    private void setupUI() {
        setLayout(new BorderLayout());

        // Top Panel: Input Field (groß)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        inputField = new JTextField("0");
        inputField.setFont(new Font("Arial", Font.BOLD, 24));
        inputField.setHorizontalAlignment(JTextField.RIGHT);
        inputField.setEditable(false);
        inputField.setPreferredSize(new Dimension(0, 50));

        topPanel.add(inputField, BorderLayout.CENTER);

        // Selection Panel: From/To Dropdowns + Swap Button
        JPanel selectionPanel = new JPanel(new GridBagLayout());
        selectionPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        selectionPanel.setPreferredSize(new Dimension(0, 70));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // From Label + ComboBox
        gbc.gridx = 0; gbc.gridy = 0;
        selectionPanel.add(new JLabel("From:"), gbc);

        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        fromComboBox = new JComboBox<>(NumberBase.values());
        fromComboBox.addActionListener(e -> onFromBaseChanged());
        selectionPanel.add(fromComboBox, gbc);

        // Swap Button (zwischen den ComboBoxes)
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        swapButton = new JButton("↔");
        swapButton.setFont(new Font("Arial", Font.BOLD, 16));
        swapButton.setPreferredSize(new Dimension(50, 30));
        swapButton.addActionListener(e -> onSwapBases());
        selectionPanel.add(swapButton, gbc);

        // To Label + ComboBox
        gbc.gridx = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        selectionPanel.add(new JLabel("To:"), gbc);

        gbc.gridx = 4; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        toComboBox = new JComboBox<>(NumberBase.values());
        toComboBox.setSelectedItem(NumberBase.BINARY);
        toComboBox.addActionListener(e -> onToBaseChanged());
        selectionPanel.add(toComboBox, gbc);

        // Result Panel
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Result"));
        resultPanel.setPreferredSize(new Dimension(0, 60));

        resultField = new JTextField("0");
        resultField.setFont(new Font("Arial", Font.BOLD, 18));
        resultField.setHorizontalAlignment(JTextField.RIGHT);
        resultField.setEditable(false);
        resultField.setPreferredSize(new Dimension(0, 35));

        resultPanel.add(resultField, BorderLayout.CENTER);

        // Main Center Panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(selectionPanel, BorderLayout.NORTH);
        centerPanel.add(resultPanel, BorderLayout.SOUTH);

        // Button Panel - ALLE Buttons immer sichtbar
        JPanel buttonPanel = createAllButtonsPanel();

        // Final Layout
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createAllButtonsPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 4, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Alle möglichen Zeichen definieren
        String[] allChars = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        allButtons = new JButton[19]; // 16 Zeichen + 3 Control Buttons

        // Character Buttons (0-F)
        for (int i = 0; i < 16; i++) {
            String character = allChars[i];
            allButtons[i] = new JButton(character);
            allButtons[i].setFont(new Font("Arial", Font.BOLD, 18));
            allButtons[i].setPreferredSize(new Dimension(60, 50));
            allButtons[i].addActionListener(e -> handleInput(character));
            panel.add(allButtons[i]);
        }

        // Control Buttons
        allButtons[16] = new JButton("C");
        allButtons[16].setFont(new Font("Arial", Font.BOLD, 18));
        allButtons[16].addActionListener(e -> {
            converter.clear();
            updateDisplay();
        });
        panel.add(allButtons[16]);

        allButtons[17] = new JButton("CE");
        allButtons[17].setFont(new Font("Arial", Font.BOLD, 18));
        allButtons[17].addActionListener(e -> {
            converter.clearAll();
            fromComboBox.setSelectedItem(converter.getFromBase());
            toComboBox.setSelectedItem(converter.getToBase());
            updateDisplay();
            updateButtonStates();
        });
        panel.add(allButtons[17]);

        allButtons[18] = new JButton("←");
        allButtons[18].setFont(new Font("Arial", Font.BOLD, 18));
        allButtons[18].addActionListener(e -> {
            converter.backspace();
            updateDisplay();
        });
        panel.add(allButtons[18]);

        return panel;
    }

    private void handleInput(String input) {
        if (converter.enterCharacter(input)) {
            updateDisplay(); // Real-time conversion
        }
    }

    private void onFromBaseChanged() {
        NumberBase newFromBase = (NumberBase) fromComboBox.getSelectedItem();
        converter.setFromBase(newFromBase);
        updateDisplay();
        updateButtonStates(); // Enable/Disable buttons
    }

    private void onToBaseChanged() {
        NumberBase newToBase = (NumberBase) toComboBox.getSelectedItem();
        converter.setToBase(newToBase);
        updateDisplay(); // Real-time conversion
    }

    private void onSwapBases() {
        // UI Swap
        NumberBase currentFrom = (NumberBase) fromComboBox.getSelectedItem();
        NumberBase currentTo = (NumberBase) toComboBox.getSelectedItem();

        fromComboBox.setSelectedItem(currentTo);
        toComboBox.setSelectedItem(currentFrom);

        // Logic Swap
        converter.swapBases();
        updateDisplay();
        updateButtonStates();
    }

    private void updateDisplay() {
        inputField.setText(converter.getCurrentInput());
        performConversion();
    }

    private void performConversion() {
        try {
            String result = converter.convert();
            resultField.setText(result);
            highlightResult();
        } catch (Exception ex) {
            resultField.setText("Error");
            highlightError();
        }
    }

    private void updateButtonStates() {
        NumberBase currentBase = converter.getFromBase();
        String[] validChars = currentBase.getValidChars();

        // Alle Character-Buttons deaktivieren
        for (int i = 0; i < 16; i++) {
            allButtons[i].setEnabled(false);
        }

        // Nur gültige Buttons für aktuelle Base aktivieren
        for (String validChar : validChars) {
            for (int i = 0; i < 16; i++) {
                if (allButtons[i].getText().equals(validChar)) {
                    allButtons[i].setEnabled(true);
                    break;
                }
            }
        }

        // Control Buttons immer aktiv
        allButtons[16].setEnabled(true); // C
        allButtons[17].setEnabled(true); // CE
        allButtons[18].setEnabled(true); // ←
    }

    private void highlightResult() {
        Color originalColor = resultField.getBackground();
        resultField.setBackground(new Color(0.329f, 1f, 0f, 0.5f));
        Timer timer = new Timer(500, e -> resultField.setBackground(originalColor));
        timer.setRepeats(false);
        timer.start();
    }

    private void highlightError() {
        Color originalColor = resultField.getBackground();
        resultField.setBackground(new Color(1f, 0f, 0f, 0.5f));
        Timer timer = new Timer(1000, e -> resultField.setBackground(originalColor));
        timer.setRepeats(false);
        timer.start();
    }

    private void setupKeyBindings() {
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        // Zahlen 0-9
        for (int i = 0; i <= 9; i++) {
            String num = String.valueOf(i);
            inputMap.put(KeyStroke.getKeyStroke(num), "char_" + num);
            inputMap.put(KeyStroke.getKeyStroke("NUMPAD" + i), "char_" + num);
            actionMap.put("char_" + num, new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    handleInput(num);
                }
            });
        }

        // Hex-Buchstaben A-F
        for (char c = 'A'; c <= 'F'; c++) {
            String letter = String.valueOf(c);
            inputMap.put(KeyStroke.getKeyStroke(letter.toLowerCase()), "char_" + letter);
            inputMap.put(KeyStroke.getKeyStroke(letter), "char_" + letter);
            actionMap.put("char_" + letter, new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    handleInput(letter);
                }
            });
        }

        // Backspace
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "backspace");
        actionMap.put("backspace", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                converter.backspace();
                updateDisplay();
            }
        });

        // Escape für CE
        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "clear_all");
        actionMap.put("clear_all", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                converter.clearAll();
                fromComboBox.setSelectedItem(converter.getFromBase());
                toComboBox.setSelectedItem(converter.getToBase());
                updateDisplay();
                updateButtonStates();
            }
        });

        // Delete für C
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "clear");
        actionMap.put("clear", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                converter.clear();
                updateDisplay();
            }
        });

        // Tab für Swap
        inputMap.put(KeyStroke.getKeyStroke("TAB"), "swap");
        actionMap.put("swap", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                onSwapBases();
            }
        });
    }
}
