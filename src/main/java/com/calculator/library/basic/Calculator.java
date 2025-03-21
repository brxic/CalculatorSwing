package com.calculator.library.basic;

public class Calculator {
    private double currentInput = 0;
    private double previousInput = 0;
    private String operator = "";
    private boolean isNewInput = true;

    public void enterNumber(double number) {
        if (isNewInput) {
            currentInput = number;
            isNewInput = false;
        } else {
            currentInput = currentInput * 10 + number;
        }
    }

    public void setOperator(String op) {
        if (!operator.isEmpty()) {
            calculate();
        }
        previousInput = currentInput;
        operator = op;
        isNewInput = true;
    }

    public double calculate() {
        switch (operator) {
            case "+": // addition
                currentInput = previousInput + currentInput;
                break;
            case "-": // subtraktion
                currentInput = previousInput - currentInput;
                break;
            case "×": // multiplikation
                currentInput = previousInput * currentInput;
                break;
            case "÷": // division
                if (currentInput == 0) { // regelung von nicht durch 0 rechnung
                    throw new ArithmeticException("Division durch 0 ist nicht erlaubt.");
                }
                currentInput = previousInput / currentInput;
                break;
        }
        operator = "";
        return currentInput;
    }

    public void clearAll() { // CE
        currentInput = 0;
        previousInput = 0;
        operator = "";
        isNewInput = true;
    }

    public void clearCurrent() { // C
        currentInput = 0;
        isNewInput = true;
    }

    public void backspace() { // ←
        currentInput = (int) (currentInput / 10);
    }

    public double getCurrentInput() { // letzte eingabe
        return currentInput;
    }
}
