package com.calculator.library.converter;

import java.math.BigDecimal;

public class BinaryConverter {

    public enum InputMode {
        DECIMAL, BINARY
    }

    private StringBuilder inputBuffer = new StringBuilder();
    private InputMode currentMode = InputMode.DECIMAL;
    private boolean isNewInput = true;
    private BigDecimal currentValue = BigDecimal.ZERO;

    public void enterNumber(String input) {
        if (!isValidInput(input)) return;

        if (isNewInput) {
            inputBuffer.setLength(0);
            isNewInput = false;
        }

        // länge begrenzen je nach modus
        if (currentMode == InputMode.BINARY && inputBuffer.length() >= 32) return;
        if (currentMode == InputMode.DECIMAL && inputBuffer.length() >= 15) return;

        inputBuffer.append(input);
        updateCurrentValue();
    }
    // input validation
    private boolean isValidInput(String input) {
        return switch (currentMode) {
            case BINARY -> input.matches("[01]");
            case DECIMAL -> input.matches("[0-9]");
        };
    }

    private void updateCurrentValue() {
        if (inputBuffer.length() == 0) {
            currentValue = BigDecimal.ZERO;
            return;
        }

        try {
            if (currentMode == InputMode.DECIMAL) {
                currentValue = new BigDecimal(inputBuffer.toString());
            } else {
                String binaryStr = getCleanBinaryInput();
                long decimalValue = Long.parseLong(binaryStr, 2);
                currentValue = BigDecimal.valueOf(decimalValue);
            }
        } catch (NumberFormatException e) {
            currentValue = BigDecimal.ZERO;
        }
    }

    public void convert() {
        updateCurrentValue();

        switch (currentMode) {
            case DECIMAL -> {
                currentMode = InputMode.BINARY;
                long longValue = currentValue.longValue();

                if (longValue < 0) {
                    // negative in zweierkomplement (32 bit)
                    String binaryString = Integer.toBinaryString((int) longValue);
                    inputBuffer = new StringBuilder(binaryString);
                } else {
                    // positiv → auf 4er-länge auffüllen
                    String binaryString = Integer.toBinaryString((int) longValue);
                    int targetLength = ((binaryString.length() + 3) / 4) * 4;
                    String paddedBinary = String.format("%" + targetLength + "s", binaryString).replace(" ", "0");
                    inputBuffer = new StringBuilder(paddedBinary);
                }
            }
            case BINARY -> {
                currentMode = InputMode.DECIMAL;
                String binaryStr = getCleanBinaryInput();

                if (binaryStr.length() == 32 && binaryStr.charAt(0) == '1') {
                    // negativ (zweierkomplement)
                    long longValue = Long.parseUnsignedLong(binaryStr, 2);
                    int intValue = (int) longValue;
                    currentValue = BigDecimal.valueOf(intValue);
                } else {
                    long longValue = Long.parseLong(binaryStr, 2);
                    currentValue = BigDecimal.valueOf(longValue);
                }

                inputBuffer = new StringBuilder(currentValue.toString());
            }
        }

        isNewInput = true;
    }
    // +/-
    public void changeSign() {
        if (currentMode != InputMode.DECIMAL) return;

        updateCurrentValue();
        currentValue = currentValue.negate();
        inputBuffer = new StringBuilder(currentValue.toString());
    }
    // CE
    public void clearAll() {
        inputBuffer.setLength(0);
        currentValue = BigDecimal.ZERO;
        currentMode = InputMode.DECIMAL;
        isNewInput = true;
    }
    // C
    public void clearCurrent() {
        inputBuffer.setLength(0);
        currentValue = BigDecimal.ZERO;
        isNewInput = true;
    }
    // <-
    public void backspace() {
        if (inputBuffer.length() > 0) {
            inputBuffer.deleteCharAt(inputBuffer.length() - 1);
            if (inputBuffer.length() == 0) {
                currentValue = BigDecimal.ZERO;
            } else {
                updateCurrentValue();
            }
        }
    }

    public String getCurrentInput() {
        if (inputBuffer.length() == 0) return "0";

        String input = getCleanBinaryInput();

        if (currentMode == InputMode.BINARY) {
            return formatIn4Groups(input); // binär schön formatieren
        }

        return inputBuffer.toString();
    }

    private String formatIn4Groups(String binary) {
        binary = binary.replace(" ", "");

        if (binary.length() <= 4) return binary;

        StringBuilder formatted = new StringBuilder();
        int length = binary.length();

        // gruppiert binär in 4er-blöcke von rechts
        for (int i = length; i > 0; i -= 4) {
            int start = Math.max(0, i - 4);
            if (formatted.length() > 0) formatted.insert(0, " ");
            formatted.insert(0, binary.substring(start, i));
        }

        return formatted.toString();
    }

    // entfernt leerzeichen aus binärstring
    private String getCleanBinaryInput() {
        return inputBuffer.toString().replace(" ", "");
    }

    public InputMode getCurrentMode() {
        return currentMode;
    }

    public void switchMode() {
        currentMode = (currentMode == InputMode.DECIMAL) ? InputMode.BINARY : InputMode.DECIMAL;
        clearCurrent();
    }
}
