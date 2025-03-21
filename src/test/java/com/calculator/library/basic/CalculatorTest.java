package com.calculator.library.basic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void testAddition() { // testet die addition
        Calculator calc = new Calculator();
        calc.enterNumber(5);
        calc.setOperator("+");
        calc.enterNumber(3);
        assertEquals(8, calc.calculate());
    }

    @Test
    void testSubtraction() { // tested die subtraktion
        Calculator calc = new Calculator();
        calc.enterNumber(10);
        calc.setOperator("-");
        calc.enterNumber(4);
        assertEquals(6, calc.calculate());
    }

    @Test
    void testMultiplication() { // testet die multiplikation
        Calculator calc = new Calculator();
        calc.enterNumber(6);
        calc.setOperator("×");
        calc.enterNumber(7);
        assertEquals(42, calc.calculate());
    }

    @Test
    void testDivision() { // testet die division
        Calculator calc = new Calculator();
        calc.enterNumber(20);
        calc.setOperator("÷");
        calc.enterNumber(5);
        assertEquals(4, calc.calculate());
    }
}
