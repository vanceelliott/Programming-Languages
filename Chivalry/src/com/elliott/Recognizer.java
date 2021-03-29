package com.elliott;

import java.util.ArrayList;

import static com.elliott.TokenType.*;

public class Recognizer {
    private static final boolean debug = true;

    // ----------- Instance Variables --------
    ArrayList<Lexeme> lexemes;
    private int nextLexemeIndex = 0;
    private Lexeme currentLexeme;

    // ----------- Constructor ---------------
    public Recognizer(ArrayList<Lexeme> lexemes){
        this.lexemes = lexemes;
        advance();
    }

    // ----------- Utility Methods -----------
    private boolean check(TokenType expected){
        return currentLexeme.getType() == expected;
    }

    private boolean checkNext(TokenType type){
        if(nextLexemeIndex >= lexemes.size()) return false;
        return lexemes.get(nextLexemeIndex).getType() == type;
    }

    private void consume(TokenType expected){
        if(check(expected)) advance();
        else Chivalry.error(currentLexeme, "Expected" + expected + " but found " + currentLexeme);
    }

    private void advance(){
        currentLexeme = lexemes.get(nextLexemeIndex);
        nextLexemeIndex++;
    }
    // ----------- Consumption Methods -------

    public void program(){
        if(debug) System.out.println("-- program --");
        if(statementListPending()) statementList();
    }

    private void statementList(){
        if(debug) System.out.println("-- statementList --");
        statement();
        if(statementListPending()) statementList();
    }

    private void statement(){
        if(debug) System.out.println("-- statement --");
        if(expressionPending()) expressionStatement();
        if(initializationPending()) initialization();
        if(declarationPending()) declaration();
        if(assignmentPending()) assignment();
    }

    // ----------- Pending Methods -----------

    private boolean statementListPending() {
        return statementPending();
    }

    private boolean statementPending(){
        return expressionPending() || initializationPending() || declarationPending() || assignmentPending() ||
                functionCallPending() ||  loopPending() || conditionalPending();
    }

    private boolean expressionPending(){
        return primaryPending() || unaryPending() || variadicPending() || comparatorPending() ||
                functionCallPending();
    }

    private boolean primaryPending(){
        return typePending() || check(IDENTIFIER) || tavernCallPending() || groupPending();
    }

    private boolean typePending(){
        return check(NUM) || check(WORD) || moralLiteralPending();
    }

    private boolean moralLiteralPending(){
        return check(GOOD) || check(EVIL);
    }

    private boolean tavernCallPending(){
        return check(IDENTIFIER) && checkNext(ROOM_NUMBER);
    }

    private boolean groupPending(){
        return check(O_PAREN);
    }

    private boolean unaryPending(){
        return unaryOperatorPending();
    }

    private boolean unaryOperatorPending(){
        return check(SWITCH) || check(DESPAIR);
    }

    private boolean variadicPending(){
        return binaryOperatorPending() || ternaryOperatorPending() || variadicOperatorPending();
    }

    private boolean variadicOperatorPending() {
        return check(COMBINE) || check(SMASH) || check(WITCHCRAFT) || check(CHOP);
    }

    private boolean ternaryOperatorPending() {
        return check(PRETTY_GOOD);
    }

    private boolean binaryOperatorPending() {
        return check(SEER) || check(VOODOO) || check(HOLY) || check(REALISTIC);
    }

    private boolean comparatorPending(){
        return check(BETTER) || check(EQUAL) || check(BETTER_EQUAL);
    }

    private boolean functionCallPending(){
        return check(IDENTIFIER) && check(WITH);
    }

    private boolean initializationPending(){
        return check(HOLD) || functionDefinitionPending() || tavernDeclarationPending();
    }

    private boolean functionDefinitionPending(){
        return check(DO);
    }

    private boolean tavernDeclarationPending(){
        return check(BUILD);
    }

    private boolean declarationPending(){
        return typePending() && checkNext(SQUIRE);
    }

    private boolean assignmentPending(){
        return check(GIVE) || tavernAssignmentPending();
    }

    private boolean tavernAssignmentPending(){
        return check(IDENTIFIER) && check(ROOM);
    }

    private boolean loopPending(){
        return riverTrollsPending() || mountainTrollsPending() || mischievousTrollsPending();
    }

    private boolean riverTrollsPending(){
        return primaryPending() && checkNext(RIVER);
    }

    private boolean mountainTrollsPending(){
        return check(FEED);
    }
    
    private boolean mischievousTrollsPending(){
        return check(MISCHIEVOUS);
    }

    private boolean conditionalPending(){
        return check(DIVINE);
    }
}
