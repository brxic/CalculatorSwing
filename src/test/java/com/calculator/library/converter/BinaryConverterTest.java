package com.calculator.library.converter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BinaryConverterTest {

    @Test
    void testDecToBinPos() {
        BinaryConverter c = new BinaryConverter();
        c.enterNumber("5");
        c.convert();
        assertEquals("0101", c.getCurrentInput().replace(" ", ""));
    }

    @Test
    void testDecToBinNeg() {
        BinaryConverter c = new BinaryConverter();
        c.enterNumber("5");
        c.changeSign();
        c.convert();
        String bin = c.getCurrentInput().replace(" ", "");
        assertEquals(32, bin.length());
        assertTrue(bin.startsWith("111"));
    }

    @Test
    void testBinToDecPos() {
        BinaryConverter c = new BinaryConverter();
        c.switchMode();
        c.enterNumber("1");
        c.enterNumber("0");
        c.enterNumber("1");
        c.convert();
        assertEquals("5", c.getCurrentInput());
    }

    @Test
    void testBinToDecNeg() {
        BinaryConverter c = new BinaryConverter();
        c.switchMode();
        String neg5 = Integer.toBinaryString(-5);
        for (char b : neg5.toCharArray()) c.enterNumber(Character.toString(b));
        c.convert();
        assertEquals("-5", c.getCurrentInput());
    }

    @Test
    void testNegativeDecToBinToDec() {
        BinaryConverter c = new BinaryConverter();
        c.enterNumber("1");
        c.changeSign(); // -1
        c.convert();     // to binary
        c.convert();     // back to decimal
        assertEquals("-1", c.getCurrentInput());
    }

    @Test
    void testZeroDecimalToBinary() {
        BinaryConverter c = new BinaryConverter();
        c.enterNumber("0");
        c.convert();
        assertEquals("0000", c.getCurrentInput().replace(" ", ""));
    }

    @Test
    void testManualNegativeBinaryToDecimal() {
        BinaryConverter c = new BinaryConverter();
        c.switchMode();
        String neg8 = Integer.toBinaryString(-8); // 11111111111111111111111111111000
        for (char b : neg8.toCharArray()) {
            c.enterNumber(Character.toString(b));
        }
        c.convert();
        assertEquals("-8", c.getCurrentInput());
    }

    @Test
    void testFormatting() {
        BinaryConverter c = new BinaryConverter();
        c.enterNumber("5");
        c.enterNumber("8"); // 58 => 0011 1010
        c.convert();
        assertEquals("0011 1010", c.getCurrentInput());
    }

    @Test
    void testChangeSignDec() {
        BinaryConverter c = new BinaryConverter();
        c.enterNumber("7");
        c.changeSign();
        assertEquals("-7", c.getCurrentInput());
    }

    @Test
    void testBackspace() {
        BinaryConverter c = new BinaryConverter();
        c.enterNumber("1");
        c.enterNumber("2");
        c.backspace();
        assertEquals("1", c.getCurrentInput());
        c.backspace();
        assertEquals("0", c.getCurrentInput());
    }

    @Test
    void testClearAll() {
        BinaryConverter c = new BinaryConverter();
        c.enterNumber("9");
        c.clearAll();
        assertEquals("0", c.getCurrentInput());
        assertEquals(BinaryConverter.InputMode.DECIMAL, c.getCurrentMode());
    }

    @Test
    void testSwitchModeClearsCurrent() {
        BinaryConverter c = new BinaryConverter();
        c.enterNumber("7");
        c.switchMode();
        assertEquals("0", c.getCurrentInput());
    }


}
