package com.calculator.library.basic;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void testAddition() {
        Calculator calc = new Calculator();
        calc.enterNumber("1");
        calc.enterNumber("0");
        calc.setOperator("+");
        calc.enterNumber("5");
        assertEquals(new BigDecimal("15"), calc.calculate());
    }

    @Test
    void testSubtraction() {
        Calculator calc = new Calculator();
        calc.enterNumber("1");
        calc.enterNumber("0");
        calc.setOperator("-");
        calc.enterNumber("5");
        assertEquals(new BigDecimal("5"), calc.calculate());
    }

    @Test
    void testMultiplication() {
        Calculator calc = new Calculator();
        calc.enterNumber("1");
        calc.enterNumber("0");
        calc.setOperator("*");
        calc.enterNumber("5");
        assertEquals(new BigDecimal("50"), calc.calculate());
    }

    @Test
    void testDivision() {
        Calculator calc = new Calculator();
        calc.enterNumber("1");
        calc.enterNumber("0");
        calc.setOperator("/");
        calc.enterNumber("2");
        assertEquals(new BigDecimal("5.0000000000"), calc.calculate());
    }

    @Test
    void testDecimalAddition() {
        Calculator calc = new Calculator();
        calc.enterNumber("3");
        calc.enterNumber(".");
        calc.enterNumber("5");
        calc.setOperator("+");
        calc.enterNumber("1");
        calc.enterNumber(".");
        calc.enterNumber("25");
        assertEquals(new BigDecimal("4.75"), calc.calculate());
    }

    @Test
    void testDecimalSubtraction() {
        Calculator calc = new Calculator();
        calc.enterNumber("3");
        calc.enterNumber(".");
        calc.enterNumber("5");
        calc.setOperator("-");
        calc.enterNumber("1");
        calc.enterNumber(".");
        calc.enterNumber("25");
        assertEquals(new BigDecimal("2.25"), calc.calculate());
    }

    @Test
    void testDecimalMultiplication() {
        Calculator calc = new Calculator();
        calc.enterNumber("3");
        calc.enterNumber(".");
        calc.enterNumber("5");
        calc.setOperator("*");
        calc.enterNumber("1");
        calc.enterNumber(".");
        calc.enterNumber("25");
        assertEquals(new BigDecimal("4.375"), calc.calculate());
    }

    @Test
    void testDecimalDivision() {
        Calculator calc = new Calculator();
        calc.enterNumber("4");
        calc.enterNumber(".");
        calc.enterNumber("5");
        calc.setOperator("/");
        calc.enterNumber("1");
        calc.enterNumber(".");
        calc.enterNumber("25");
        assertEquals(new BigDecimal("3.6000000000"), calc.calculate());
    }

    @Test
    void testDoubleOperatorIgnored() {
        Calculator calc = new Calculator();
        calc.enterNumber("4");
        assertTrue(calc.setOperator("+"));
        assertFalse(calc.setOperator("*")); // Zweiter Operator wird blockiert
    }

    @Test
    void testDivisionByZero() {
        Calculator calc = new Calculator();
        calc.enterNumber("8");
        calc.setOperator("/");
        calc.enterNumber("0");
        assertThrows(ArithmeticException.class, calc::calculate);
    }

    @Test
    void testChangeSign() {
        Calculator calc = new Calculator();
        calc.enterNumber("5");
        calc.changeSign();
        assertEquals(new BigDecimal("-5"), new BigDecimal(calc.getCurrentInput()));
    }

    @Test
    void testBackspace() {
        Calculator calc = new Calculator();
        calc.enterNumber("1");
        calc.enterNumber("2");
        calc.enterNumber("3");
        calc.backspace();
        assertEquals("12", calc.getCurrentInput());
    }
}
