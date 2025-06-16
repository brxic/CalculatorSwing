package com.calculator.library.converter;

import java.math.BigInteger;

/**
 * Logik für From/To Zahlensystem-Konvertierungen
 */
public class BaseConverter {
    private String currentInput = "";
    private NumberBase fromBase = NumberBase.DECIMAL;
    private NumberBase toBase = NumberBase.BINARY;

    /**
     * Fügt ein Zeichen zur Eingabe hinzu (nur wenn gültig für fromBase)
     */
    public boolean enterCharacter(String character) {
        if (character == null || character.isEmpty()) return false;

        // Prüfe ob das Zeichen für das FROM-Zahlensystem gültig ist
        if (!fromBase.isValidChar(character)) {
            return false;
        }

        currentInput += character.toUpperCase();
        return true;
    }

    /**
     * Setzt das FROM Zahlensystem
     */
    public void setFromBase(NumberBase base) {
        this.fromBase = base;
        // Eingabe validieren für neues FROM-System
        if (!fromBase.isValidInput(currentInput)) {
            currentInput = "";
        }
    }

    /**
     * Setzt das TO Zahlensystem
     */
    public void setToBase(NumberBase base) {
        this.toBase = base;
    }

    /**
     * Konvertiert die aktuelle Eingabe von fromBase zu toBase
     */
    public String convert() {
        if (currentInput.isEmpty()) return "0";

        try {
            // Schritt 1: FROM-Base in Dezimal konvertieren
            BigInteger decimal = new BigInteger(currentInput, fromBase.getBase());

            // Schritt 2: Von Dezimal ins TO-System konvertieren
            String result = decimal.toString(toBase.getBase()).toUpperCase();

            // Schritt 3: Leading Zeros für Binär hinzufügen
            return formatResult(result, decimal);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ungültige Eingabe für " + fromBase.getDisplayName());
        }
    }

    /**
     * Formatiert das Ergebnis mit Leading Zeros für bessere Lesbarkeit
     */
    private String formatResult(String result, BigInteger decimal) {
        if (toBase == NumberBase.BINARY) {
            // Binär: auf 4, 8, 16, oder 32 Bit auffüllen
            int length = result.length();
            int targetLength;
            if (length <= 4) targetLength = 4;
            else if (length <= 8) targetLength = 8;
            else if (length <= 16) targetLength = 16;
            else targetLength = 32;

            return String.format("%" + targetLength + "s", result).replace(' ', '0');
        }

        return result;
    }

    /**
     * Tauscht FROM und TO Zahlensysteme
     */
    public void swapBases() {
        NumberBase temp = fromBase;
        fromBase = toBase;
        toBase = temp;

        // Input für neue FROM-Base validieren
        if (!fromBase.isValidInput(currentInput)) {
            currentInput = "";
        }
    }

    /**
     * Löscht das letzte Zeichen
     */
    public void backspace() {
        if (!currentInput.isEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
        }
    }

    /**
     * Löscht die aktuelle Eingabe
     */
    public void clear() {
        currentInput = "";
    }

    /**
     * Löscht alles (wie CE)
     */
    public void clearAll() {
        currentInput = "";
        fromBase = NumberBase.DECIMAL;
        toBase = NumberBase.BINARY;
    }

    /**
     * Setzt eine neue Eingabe direkt
     */
    public boolean setInput(String input) {
        if (input == null) {
            currentInput = "";
            return true;
        }

        if (fromBase.isValidInput(input)) {
            currentInput = input.toUpperCase();
            return true;
        }
        return false;
    }

    public String getCurrentInput() {
        return currentInput.isEmpty() ? "0" : currentInput;
    }

    public NumberBase getFromBase() {
        return fromBase;
    }

    public NumberBase getToBase() {
        return toBase;
    }
}
