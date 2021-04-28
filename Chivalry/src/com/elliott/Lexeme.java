package com.elliott;

public class Lexeme {
    private final TokenType type;
    private final int lineNumber;

    private final String stringValue;
    private final Integer intValue;
    private final Double doubleValue;

    private Lexeme left;
    private Lexeme right;

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

    public boolean equals(Lexeme other) {
        return this.type == TokenType.IDENTIFIER &&
                this.type == other.type &&
                this.stringValue != null &&
                this.stringValue.equals(other.stringValue);
    }

    public TokenType getType() {
        return type;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getStringValue() {
        return stringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setLeft(Lexeme input) {
        this.left = input;
    }

    public void setRight(Lexeme input) {
        this.right = input;
    }

    public Lexeme getLeft() {
        return this.left;
    }

    public Lexeme getRight() {
        return this.right;
    }

    public String toString() {
        if (stringValue != null) {
            return type + ": '" + stringValue + "' [" + lineNumber + "]";
        } else if (intValue != null) {
            return type + ": " + intValue + " [" + lineNumber + "]";
        } else if (doubleValue != null) {
            return type + ": " + doubleValue + " [" + lineNumber + "]";
        } else {
            return type + " [" + lineNumber + "]";
        }
    }


    public static void printTree(Lexeme root) {
        String printableTree = getPrintableTree(root, 1);
        System.out.println(printableTree);
    }

    private static String getPrintableTree(Lexeme root, int level) {
        String treeString = root.toString();

        StringBuilder spacer = new StringBuilder("\n");
        spacer.append("\t".repeat(level));

        if (root.left != null)
            treeString += spacer + "with left child: " + getPrintableTree(root.left, level + 1);
        if (root.right != null)
            treeString += spacer + "and right child: " + getPrintableTree(root.right, level + 1);

        return treeString;
    }


}
