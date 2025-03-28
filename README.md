# Java Calculator (Swing GUI)

A simple, user-friendly calculator built with Java Swing.  
Supports all basic math operations and can be fully controlled with both mouse and keyboard.

> **Note:** The project documentation and code comments are in German because this project was created as part of an assignment at Work in Switzerland.

## Features

- Add, subtract, multiply, divide
- Decimal numbers & sign switching (+/-)
- Clear input (C), clear all (CE), backspace
- Keyboard control (numpad, enter, backspace, shift, escape)
- Visual feedback (green for result, red for errors)

## Special Things

- Controlleable by keyboard or mouse
- Handles errors like division by 0
- Uses BigDecimal for accurate calculations

## How to Use

1. Clone this repo
2. Open in your IDE (e.g. IntelliJ, Eclipse) or compile manually:
   ```bash
   javac Calculator.java CalculatorUI.java
   java com.calculator.library.basic.CalculatorUI
   ```
3. Start calculating and use mouse or just type!

## Future Ideas

- Binary 
- currency mode

> Made with Java + Swing + JUnit 5
