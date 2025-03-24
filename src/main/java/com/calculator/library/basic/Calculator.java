package com.calculator.library.basic;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculator {
    private BigDecimal currentInput = BigDecimal.ZERO;
    private BigDecimal previousInput = BigDecimal.ZERO;
    private String operator = "";
    private boolean isNewInput = true;
    private boolean operatorSet = false;
    private StringBuilder inputBuffer = new StringBuilder();


    public void enterNumber(String input) {
        if (isNewInput) {
            inputBuffer.setLength(0); // reset
            isNewInput = false;
        }

        // Nur ein Punkt erlaubt
        if (input.equals(".") && inputBuffer.toString().contains(".")) return;

        inputBuffer.append(input); // erweitert den nachkommastellen array um die letze eingegebene zahl resp deren string
        currentInput = new BigDecimal(inputBuffer.toString());
    }

    // Holt den Operator und gibt diesen an die Calculate Funktion weiter
    public boolean setOperator(String op) {
        if (operatorSet) {
            return false;
        }
        if (!operator.isEmpty()) {
            calculate();
        }
        previousInput = currentInput;
        operator = op;
        isNewInput = true;
        operatorSet = true;
        return true;
    }

    // rechenregeln und funktion
    public BigDecimal calculate() {
        switch (operator) {
            case "+":
                currentInput = previousInput.add(currentInput);
                break;
            case "-":
                currentInput = previousInput.subtract(currentInput);
                break;
            case "*":
                currentInput = previousInput.multiply(currentInput);
                break;
            case "/":
                if (currentInput.compareTo(BigDecimal.ZERO) == 0) {
                    throw new ArithmeticException("Division durch 0");
                }
                currentInput = previousInput.divide(currentInput, 10, RoundingMode.HALF_UP); // runden
                break;
        }
        operator = "";
        isNewInput = true;
        operatorSet = false;
        inputBuffer = new StringBuilder(currentInput.stripTrailingZeros().toPlainString()); // entfernt unnötige nachkommastellen, wie viele 0
        return currentInput;
    }

    // CE
    public void clearAll() {
        currentInput = BigDecimal.ZERO;
        previousInput = BigDecimal.ZERO;
        operator = "";
        isNewInput = true;
        operatorSet = false;
        inputBuffer.setLength(0);
    }

    // C
    public void clearCurrent() {
        currentInput = BigDecimal.ZERO;
        isNewInput = true;
        inputBuffer.setLength(0);
    }

    // <-
    public void backspace() {
        if (inputBuffer.length() > 0) {
            inputBuffer.deleteCharAt(inputBuffer.length() - 1);
            if (inputBuffer.length() == 0 || inputBuffer.toString().equals("-")) {
                currentInput = BigDecimal.ZERO;
            } else {
                currentInput = new BigDecimal(inputBuffer.toString());
            }
        }
    }

    // +/-
    public void changeSign() {
        currentInput = currentInput.negate();
        inputBuffer = new StringBuilder(currentInput.stripTrailingZeros().toPlainString());
    }

    // Letzte eingegebene Zahl / die zahl die angezeigt wird
    public String getCurrentInput() {
        return inputBuffer.length() > 0 ? inputBuffer.toString() : "0";
    }
}
