package com.elliott;

public class Lexeme {
    private final TokenType type;
    private final int lineNumber;

    private final String stringValue;
    private final Integer intValue;
    private final Double doubleValue;

    //Constructor for special characters, keywords, operators, etc.
    public Lexeme(TokenType type, int lineNumber) {
        this.type = type;
        this.lineNumber = lineNumber;
        this.stringValue = null;
        this.intValue = null;
        this.doubleValue = null;
    }

    //Constructor for identifiers
    public Lexeme(TokenType type, String stringValue, int lineNumber) {
        this.type = type;
        this.lineNumber = lineNumber;
        this.stringValue = stringValue;
        this.intValue = null;
        this.doubleValue = null;
    }

    //Constructor for integers
    public Lexeme(TokenType type, int intValue, int lineNumber) {
        this.type = type;
        this.lineNumber = lineNumber;
        this.stringValue = null;
        this.intValue = intValue;
        this.doubleValue = null;
    }

    //Constructor for real numbers
    public Lexeme(TokenType type, double doubleValue, int lineNumber) {
        this.type = type;
        this.lineNumber = lineNumber;
        this.stringValue = null;
        this.intValue = null;
        this.doubleValue = doubleValue;
    }

    public TokenType getType() { return type;}

    public int getLineNumber() { return lineNumber;}

    public String toString() {
        if(stringValue != null) {
            return type + ": '" + stringValue + "' [" + lineNumber + "]";
        }
        else if(intValue != null) {
            return type + ": " + intValue + " [" + lineNumber + "]";
        }
        else if(doubleValue != null) {
            return type + ": " + doubleValue + " [" + lineNumber + "]";
        }
        else {
            return type + " [" + lineNumber + "]";
        }
    }



}
