package com.calculator.library.converter;

/**
 * Enum für verschiedene Zahlensysteme im BaseConverter
 */
public enum NumberBase {
    BINARY(2, "Binary", new String[]{"0", "1"}),
    OCTAL(8, "Octal", new String[]{"0", "1", "2", "3", "4", "5", "6", "7"}),
    DECIMAL(10, "Decimal", new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}),
    HEXADECIMAL(16, "Hexadecimal", new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"});

    private final int base;
    private final String displayName;
    private final String[] validChars;

    NumberBase(int base, String displayName, String[] validChars) {
        this.base = base;
        this.displayName = displayName;
        this.validChars = validChars;
    }

    public int getBase() {
        return base;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String[] getValidChars() {
        return validChars;
    }

    /**
     * Prüft ob ein Zeichen für dieses Zahlensystem gültig ist
     */
    public boolean isValidChar(String character) {
        if (character == null || character.isEmpty()) return false;

        String upperChar = character.toUpperCase();
        for (String validChar : validChars) {
            if (validChar.equals(upperChar)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Prüft ob ein String für dieses Zahlensystem gültig ist
     */
    public boolean isValidInput(String input) {
        if (input == null || input.isEmpty()) return true; // Leere Eingabe ist ok

        for (char c : input.toCharArray()) {
            if (!isValidChar(String.valueOf(c))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return displayName;
    }
}