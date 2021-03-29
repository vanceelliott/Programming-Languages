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
        if(expressionPending()) expression();
        if(initializationPending()) initialization();
        if(declarationPending()) declaration();
        if(assignmentPending()) assignment();
    }

    private void expression(){
        if(debug) System.out.println("-- expression --");
        if(primaryPending()) primary();
        if(unaryPending()) unary();
        if(variadicPending()) variadic();
        if(comparatorPending()) comparator();
        if(functionCallPending()) functionCall();
    }

    private void primary(){
        if(debug) System.out.println("-- primary --");
        if(typePending()) type();
        if(tavernCallPending()) tavernCall();
        if(groupPending()) group();
        if(check(IDENTIFIER)) consume(IDENTIFIER);
    }

    private void type(){
        if(debug) System.out.println("-- type --");
        if(check(NUM)) consume(NUM);
        if(check(WORD)) consume(WORD);
        if(moralLiteralPending()) moralLiteral();
    }

    private void moralLiteral(){
        if(debug) System.out.println("-- moralLiteral --");
        if(check(GOOD)) consume(GOOD);
        if(check(EVIL)) consume(EVIL);
    }

    private void tavernCall(){
        if(debug) System.out.println("-- tavernCall --");
        consume(IDENTIFIER);
        consume(ROOM_NUMBER);
        expression();
    }

    private void group(){
        if(debug) System.out.println("-- group --");
        consume(O_PAREN);
        expression();
        consume(C_PAREN);
    }

    private void unary(){
        if(debug) System.out.println("-- unary --");
        unaryOperator();
        consume(O_PAREN);
        primary();
        consume(C_PAREN);
    }

    private void unaryOperator() {
        if(debug) System.out.println("-- unaryOperator --");
        if(check(SWITCH)) consume(SWITCH);
        if(check(DESPAIR)) consume(DESPAIR);
    }

    private void variadic(){
        if(binaryOperatorPending()) binaryOperator();
        if(ternaryOperatorPending()) ternaryOperator();
        if(variadicOperatorPending()) variadicOperator();
        consume(O_PAREN);
        primaryList();
        consume(C_PAREN);
    }

    private void binaryOperator(){
        if(check(SEER)) consume(SEER);
        if(check(VOODOO)) consume(VOODOO);
        if(check(HOLY)) consume(HOLY);
        if(check(REALISTIC)) consume(REALISTIC);
    }

    private void ternaryOperator(){
        consume(PRETTY_GOOD);
    }

    private void variadicOperator(){
        if(check(COMBINE)) consume(COMBINE);
        if(check(SMASH)) consume(SMASH);
        if(check(WITCHCRAFT)) consume(WITCHCRAFT);
        if(check(CHOP)) consume(CHOP);
    }

    private void primaryList(){
        primary();
        if(check(COMMA)){
            consume(COMMA);
            primaryList();
        }
    }

    private void comparator(){
        if(check(BETTER)) consume(BETTER);
        if(check(EQUAL)) consume(EQUAL);
        if(check(BETTER_EQUAL)) consume(BETTER_EQUAL);
    }

    private void functionCall(){
        consume(IDENTIFIER);
        consume(WITH);
        consume(O_PAREN);
        primaryList();
        consume(C_PAREN);
    }

    private void initialization(){
        if(check(HOLD)) {
            consume(HOLD);
            type();
            consume(SQUIRE);
            consume(IDENTIFIER);
            expression();
            consume(BANG);

        }
        if(functionDefinitionPending()) functionDefinition();
        if(tavernDeclarationPending()) tavernDeclaration();
    }

    private void functionDefinition(){
        consume(DO);
        type();
        consume(SQUIRE);
        consume(IDENTIFIER);
        block();
        consume(WITH);
        consume(O_PAREN);
        parameterList();
        consume(C_PAREN);
        consume(BANG);
    }

    private void block(){
        consume(O_SQUARE);
        statementList();
        consume(C_SQUARE);
    }

    private void parameterList(){
        parameter();
        if(check(COMMA)){
            consume(COMMA);
            parameterList();
        }
    }

    private void parameter(){
        type();
        consume(IDENTIFIER);
    }

    private void tavernDeclaration(){
        consume(BUILD);
        consume(TAVERN);
        consume(IDENTIFIER);
        primary();
        consume(ROOMS);
    }

    private void declaration(){
        type();
        consume(SQUIRE);
        consume(IDENTIFIER);
        consume(BANG);
    }

    private void assignment
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
        return check(IDENTIFIER) && checkNext(WITH);
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
        return check(IDENTIFIER) && checkNext(ROOM);
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
