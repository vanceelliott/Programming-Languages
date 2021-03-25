package com.elliott;

import java.util.ArrayList;
import java.util.HashMap;
import static com.elliott.TokenType.*;

public class Lexer {
    private final String source;
    private final ArrayList<Lexeme> lexemes = new ArrayList<>();
    private int currentPosition = 0;
    private int startOfCurrentLexeme = 0;
    private int lineNumber = 1;
    private static final HashMap<String, TokenType> keywords;
    static {
        keywords = new HashMap<>();
        keywords.put("holler", HOLLER);
        keywords.put("hither", HITHER);
        keywords.put("squire", SQUIRE);
        keywords.put("give", GIVE);
        keywords.put("combine", COMBINE);
        keywords.put("smash", SMASH);
        keywords.put("witchcraft", WITCHCRAFT);
        keywords.put("chop", CHOP);
        keywords.put("seer", SEER);
        keywords.put("voodoo", VOODOO);
        keywords.put("hold", HOLD);
        keywords.put("do", DO);
        keywords.put("with", WITH);
        keywords.put("and", AND);
        keywords.put("retrieve", RETRIEVE);
        keywords.put("divine", DIVINE);
        keywords.put("villain", VILLAIN);
        keywords.put("river", RIVER);
        keywords.put("trolls", TROLLS);
        keywords.put("work", WORK);
        keywords.put("for", FOR);
        keywords.put("food", FOOD);
        keywords.put("food_left", FOOD_LEFT);
        keywords.put("feed", FEED);
        keywords.put("mountain", MOUNTAIN);
        keywords.put("troll", TROLL);
        keywords.put("mischievous", MISCHIEVOUS);
        keywords.put("play", PLAY);
        keywords.put("switch", SWITCH);
        keywords.put("despair", DESPAIR);
        keywords.put("holy", HOLY);
        keywords.put("realistic", REALISTIC);
        keywords.put("pretty_good", PRETTY_GOOD);
        keywords.put("better", BETTER);
        keywords.put("equal", EQUAL);
        keywords.put("better_equal", BETTER_EQUAL);
        keywords.put("build", BUILD);
        keywords.put("tavern", TAVERN);
        keywords.put("rooms", ROOMS);
        keywords.put("guest", GUEST);
        keywords.put("room", ROOM);
        keywords.put("good", GOOD);
        keywords.put("evil", EVIL);
        keywords.put("ghoul", GHOUL);
        keywords.put("room number", ROOM_NUMBER);
    }
    public Lexer(String source){
        this.source = source;
    }
    private char peek() {
        if(isAtEnd()) return '\0';
        return source.charAt(currentPosition);
    }
    private char peekNext() {
        if(currentPosition + 1 >= source.length()) return '\0';
        return source.charAt(currentPosition+1);
    }
    private boolean match(char expected) {
        if(isAtEnd() || source.charAt(currentPosition) != expected) return false;
        currentPosition++;
        return true;
    }
    private char advance(){
        char currentChar = source.charAt(currentPosition);
        if(currentChar == '\n' || currentChar == '\r') lineNumber++;
        currentPosition++;
        return currentChar;
    }
    private boolean isAtEnd() {return currentPosition >= source.length();}
    private boolean isDigit(char c) { return c >= '0' && c <= '9';}
    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }
    private boolean isAlphaNumeric(char c) {return isAlpha(c)||isDigit(c);}
    public ArrayList<Lexeme> lex() {
        while(!isAtEnd()) {
            startOfCurrentLexeme = currentPosition;
            Lexeme nextLexeme = getNextLexeme();
            if(nextLexeme != null) lexemes.add(nextLexeme);
        }
        lexemes.add(new Lexeme(EOF, lineNumber));
        return lexemes;
    }
    private Lexeme getNextLexeme() {
        char c = advance();
        switch(c) {
            //Whitespace
            case ' ':
            case '\t':
            case '\n':
            case '\r':
                return null;

            //Single-Character Tokens
            case '(':
                return new Lexeme(O_PAREN, lineNumber);
            case ')':
                return new Lexeme(C_PAREN, lineNumber);
            case '[':
                return new Lexeme(O_SQUARE, lineNumber);
            case ']':
                return new Lexeme(C_SQUARE, lineNumber);
            case '!':
                return new Lexeme(BANG, lineNumber);
            case '"':
                return lexString();
            default:
                if(isDigit(c)) return lexNumber();
                else if (isAlpha(c)) return lexIdentifierOrKeyword();
                else Chivalry.error(lineNumber, "Unexpected character: " + c);
                return null;
        }
    }
    private Lexeme lexNumber() {
        boolean isInteger = true;
        while (isDigit(peek())) advance();
        if(peek() == '.') {
            if(!isDigit(peekNext())) Chivalry.error(lineNumber, "Number ends in decimal point");
            isInteger = false;
            advance();
            while (isDigit(peek())) advance();
        }
        String numberString = source.substring(startOfCurrentLexeme, currentPosition);
        if(isInteger){
            int number = Integer.parseInt(numberString);
            return new Lexeme(NUM, number, lineNumber);
        } else {
            double number = Double.parseDouble(numberString);
            return new Lexeme(NUMWITHCALC, number, lineNumber);
        }
    }
    private Lexeme lexString() {
        boolean check = true;
        int currentLine = lineNumber;
        while (check) {
            if(peek()=='"'){
                check = false;
            }
            if(currentPosition==source.length()){
                Chivalry.error(currentLine, "Word does not have closed quotation mark");
            }
            advance();
        }
        String stringString = source.substring(startOfCurrentLexeme+1, currentPosition-1);
        return new Lexeme(WORD, stringString, lineNumber);
    }
    private Lexeme lexIdentifierOrKeyword() {
        while(isAlphaNumeric(peek())) advance();
        String text = source.substring(startOfCurrentLexeme, currentPosition);
        TokenType type = keywords.get(text);
        if(type==null)
            return new Lexeme(IDENTIFIER, text, lineNumber);
        else
            return new Lexeme(type, lineNumber);
    }
}
