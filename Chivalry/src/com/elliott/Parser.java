package com.elliott;

import java.util.ArrayList;

import static com.elliott.TokenType.*;

public class Parser {
    private static final boolean debug = true;

    // ----------- Instance Variables --------
    ArrayList<Lexeme> lexemes;
    private int nextLexemeIndex = 0;
    private Lexeme currentLexeme;

    // ----------- Constructor ---------------
    public Parser(ArrayList<Lexeme> lexemes) {
        this.lexemes = lexemes;
        advance();
    }

    // ----------- Utility Methods -----------
    private boolean check(TokenType expected) {
        return currentLexeme.getType() == expected;
    }

    private boolean checkNext(TokenType type) {
        if (nextLexemeIndex >= lexemes.size()) return false;
        return lexemes.get(nextLexemeIndex).getType() == type;
    }

    private Lexeme consume(TokenType expected) {
        if (check(expected)) {
            Lexeme current = currentLexeme;
            advance();
            return current;
        } else Chivalry.error(currentLexeme, "Expected" + expected + " but found " + currentLexeme);
        return null;
    }

    private void advance() {
        currentLexeme = lexemes.get(nextLexemeIndex);
        nextLexemeIndex++;
    }
    // ----------- Consumption Methods -------

    public Lexeme program() {
        if (debug) System.out.println("-- program --");
        Lexeme program = new Lexeme(PROGRAM, currentLexeme.getLineNumber());
        if (statementListPending()) {
            Lexeme statementList = statementList();
            program.setLeft(statementList);
        }
        return program;
    }

    private Lexeme statementList() {
        if (debug) System.out.println("-- statementList --");
        Lexeme statementList = new Lexeme(STATEMENT_LIST, currentLexeme.getLineNumber());
        Lexeme statement = statement();
        statementList.setLeft(statement);
        if (statementListPending()) {
            Lexeme statementListTwo = statementList();
            statementList.setRight(statementListTwo);
        }
        return statementList;
    }

    private Lexeme statement() {
        if (debug) System.out.println("-- statement --");
        Lexeme statement = new Lexeme(STATEMENT, currentLexeme.getLineNumber());
        if (initializationPending()) {
            statement.setLeft(initialization());
        } else if (declarationPending()) {
            statement.setLeft(declaration());
        } else if (assignmentPending()) {
            statement.setLeft(assignment());
        } else if (functionCallPending()) {
            Lexeme functionCall = functionCall();
            consume(BANG);
            statement.setLeft(functionCall);
        } else if (loopPending()) {
            statement.setLeft(loop());
        } else if (conditionalPending()) {
            statement.setLeft(conditional());
        } else {
            Lexeme expression = expression();
            consume(BANG);
            statement.setLeft(expression);
        }
        return statement;
    }

    private Lexeme loop() {
        if (debug) System.out.println("-- loop --");
        if (riverTrollsPending()) {
            return riverTrolls();
        } else if (mountainTrollsPending()) {
            return mountainTrolls();
        } else {
            return mischievousTrolls();
        }
    }

    private Lexeme riverTrolls() {
        if (debug) System.out.println("-- riverTrolls --");
        Lexeme riverTrolls = new Lexeme(RIVER_TROLLS, currentLexeme.getLineNumber());
        Lexeme primary = primary();
        riverTrolls.setLeft(primary);
        consume(RIVER);
        consume(TROLLS);
        consume(WORK);
        consume(FOR);
        Lexeme primaryTwo = primary();
        Lexeme glue = new Lexeme(GLUE, currentLexeme.getLineNumber());
        riverTrolls.setRight(glue);
        glue.setLeft(primaryTwo);
        consume(FOOD);
        Lexeme block = block();
        glue.setRight(block);
        return riverTrolls;
    }

    private Lexeme mountainTrolls() {
        if (debug) System.out.println("-- mountainTrolls --");
        Lexeme feed = consume(FEED);
        consume(MOUNTAIN);
        consume(TROLL);
        Lexeme primary = primary();
        feed.setLeft(primary);
        Lexeme block = block();
        feed.setRight(block);
        return feed;
    }

    private Lexeme mischievousTrolls() {
        if (debug) System.out.println("-- mischievousTrolls --");
        Lexeme mischievous = consume(MISCHIEVOUS);
        consume(TROLLS);
        consume(PLAY);
        return mischievous;
    }

    private Lexeme conditional() {
        if (debug) System.out.println("-- conditional --");
        Lexeme divine = consume(DIVINE);
        Lexeme expression = expression();
        divine.setLeft(expression);
        Lexeme glue = new Lexeme(GLUE, currentLexeme.getLineNumber());
        divine.setRight(glue);
        Lexeme block = block();
        glue.setLeft(block);
        if (check(VILLAIN)) {
            Lexeme villain = consume(VILLAIN);
            glue.setRight(villain);
            Lexeme blockTwo = block();
            villain.setLeft(blockTwo);
        }
        return divine;
    }

    private Lexeme expression() {
        if (debug) System.out.println("-- expression --");
        if (functionCallPending()) {
            return functionCall();
        } else if (comparatorPending()) {
            return comparator();
        } else if (primaryPending()) {
            return primary();
        } else if (unaryPending()) {
            return unary();
        } else {
            return variadic();
        }
    }

    private Lexeme primary() {
        if (debug) System.out.println("-- primary --");
        if (typePending()) {
            return type();
        } else if (tavernCallPending()) {
            return tavernCall();
        } else if (groupPending()) {
            return group();
        } else return consume(IDENTIFIER);
    }

    private Lexeme type() {
        if (debug) System.out.println("-- type --");
        if (check(NUM)) {
            return consume(NUM);
        } else if (check(WORD)) {
            return consume(WORD);
        } else if (check(NUM_WITH_CALC)) {
            return consume(NUM_WITH_CALC);
        } else {
            return moralLiteral();
        }
    }

    private Lexeme moralLiteral() {
        if (debug) System.out.println("-- moralLiteral --");
        if (check(GOOD)) {
            return consume(GOOD);
        } else {
            return consume(EVIL);
        }

    }

    private Lexeme tavernCall() {
        if (debug) System.out.println("-- tavernCall --");
        Lexeme tavernCall = new Lexeme(TAVERN_CALL, currentLexeme.getLineNumber());
        Lexeme identifier = consume(IDENTIFIER);
        tavernCall.setLeft(identifier);
        consume(ROOM_NUMBER);
        Lexeme expression = expression();
        tavernCall.setRight(expression);
        return tavernCall;
    }

    private Lexeme group() {
        if (debug) System.out.println("-- group --");
        Lexeme group = new Lexeme(GROUP, currentLexeme.getLineNumber());
        consume(O_PAREN);
        Lexeme expression = expression();
        group.setLeft(expression);
        consume(C_PAREN);
        return group;
    }

    private Lexeme unary() {
        if (debug) System.out.println("-- unary --");
        Lexeme unaryOperator = unaryOperator();
        consume(O_PAREN);
        Lexeme primary = primary();
        unaryOperator.setLeft(primary);
        consume(C_PAREN);
        return unaryOperator;
    }

    private Lexeme unaryOperator() {
        if (debug) System.out.println("-- unaryOperator --");
        if (check(SWITCH)) {
            return consume(SWITCH);
        } else {
            return consume(DESPAIR);
        }
    }

    private Lexeme variadic() {
        if (debug) System.out.println("-- variadic --");
        Lexeme variadic;
        if (binaryOperatorPending()) {
            variadic = binaryOperator();
        } else if (ternaryOperatorPending()) {
            variadic = ternaryOperator();
        } else {
            variadic = variadicOperator();
        }
        consume(O_PAREN);
        Lexeme primaryList = primaryList();
        variadic.setLeft(primaryList);
        consume(C_PAREN);
        return variadic;
    }

    private Lexeme binaryOperator() {
        if (debug) System.out.println("-- binaryOperator --");
        if (check(SEER)) {
            return consume(SEER);
        } else if (check(VOODOO)) {
            return consume(VOODOO);
        } else if (check(HOLY)) {
            return consume(HOLY);
        } else {
            return consume(REALISTIC);
        }
    }

    private Lexeme ternaryOperator() {
        if (debug) System.out.println("-- ternaryOperator --");
        return consume(PRETTY_GOOD);

    }

    private Lexeme variadicOperator() {
        if (debug) System.out.println("-- variadicOperator --");
        if (check(COMBINE)) {
            return consume(COMBINE);
        } else if (check(SMASH)) {
            return consume(SMASH);
        } else if (check(WITCHCRAFT)) {
            return consume(WITCHCRAFT);
        } else {
            return consume(CHOP);
        }
    }

    private Lexeme primaryList() {
        if (debug) System.out.println("-- primaryList --");
        Lexeme primaryList = new Lexeme(PRIMARY_LIST, currentLexeme.getLineNumber());
        Lexeme primary = primary();
        primaryList.setLeft(primary);
        if (check(COMMA)) {
            consume(COMMA);
            Lexeme primaryListTwo = primaryList();
            primaryList.setRight(primaryListTwo);
        }
        return primaryList;
    }

    private Lexeme comparator() {
        if (debug) System.out.println("-- comparator --");
        Lexeme primaryOne = primary();
        Lexeme comparator = comparatorOperator();
        Lexeme primaryTwo = primary();
        comparator.setLeft(primaryOne);
        comparator.setRight(primaryTwo);
        return comparator;
    }

    private Lexeme comparatorOperator() {
        if (debug) System.out.println("-- comparator --");
        if (check(BETTER)) {
            return consume(BETTER);
        } else if (check(EQUAL)) {
            return consume(EQUAL);
        } else {
            return consume(BETTER_EQUAL);
        }
    }

    private Lexeme functionCall() {
        if (debug) System.out.println("-- functionCall --");
        Lexeme functionCall = new Lexeme(FUNCTION_CALL, currentLexeme.getLineNumber());
        Lexeme identifier = consume(IDENTIFIER);
        functionCall.setLeft(identifier);
        consume(WITH);
        consume(O_PAREN);
        Lexeme primaryList = primaryList();
        functionCall.setRight(primaryList);
        consume(C_PAREN);
        return functionCall;
    }

    private Lexeme initialization() {
        if (debug) System.out.println("-- initialization --");
        Lexeme initialization;
        if (check(HOLD)) {
            initialization = consume(HOLD);
            Lexeme type = type();
            initialization.setLeft(type);
            consume(SQUIRE);
            consume(IDENTIFIER);
            Lexeme expression = expression();
            initialization.setRight(expression);
            consume(BANG);

        } else if (functionDefinitionPending()) {
            initialization = new Lexeme(INITIALIZATION, currentLexeme.getLineNumber());
            Lexeme functionDefinition = functionDefinition();
            initialization.setLeft(functionDefinition);
        } else {
            initialization = new Lexeme(INITIALIZATION, currentLexeme.getLineNumber());
            Lexeme tavernDeclaration = tavernDeclaration();
            initialization.setLeft(tavernDeclaration);
        }
        return initialization;
    }

    private Lexeme functionDefinition() {
        if (debug) System.out.println("-- functionDefinition --");
        Lexeme functionDefinition = consume(DO);
        Lexeme type = type();
        functionDefinition.setLeft(type);
        consume(SQUIRE);
        Lexeme glue = new Lexeme(GLUE, currentLexeme.getLineNumber());
        Lexeme identifier = consume(IDENTIFIER);
        functionDefinition.setRight(glue);
        glue.setLeft(identifier);
        Lexeme glueTwo = new Lexeme(GLUE, currentLexeme.getLineNumber());
        glue.setRight(glueTwo);
        Lexeme block = block();
        glueTwo.setLeft(block);
        consume(WITH);
        consume(O_PAREN);
        Lexeme parameterList = parameterList();
        glueTwo.setRight(parameterList);
        consume(C_PAREN);
        consume(BANG);
        return functionDefinition;
    }

    private Lexeme block() {
        if (debug) System.out.println("-- block --");
        Lexeme block = consume(O_SQUARE);
        Lexeme statementList = statementList();
        block.setLeft(statementList);
        consume(C_SQUARE);
        return block;
    }

    private Lexeme parameterList() {
        if (debug) System.out.println("-- parameterList --");
        Lexeme parameterList = new Lexeme(PARAMETER_LIST, currentLexeme.getLineNumber());
        Lexeme parameter = parameter();
        parameterList.setLeft(parameter);
        if (check(COMMA)) {
            consume(COMMA);
            Lexeme parameterListTwo = parameterList();
            parameterList.setRight(parameterListTwo);
        }
        return parameterList;
    }

    private Lexeme parameter() {
        if (debug) System.out.println("-- parameter --");
        Lexeme parameter = new Lexeme(PARAMETER, currentLexeme.getLineNumber());
        Lexeme type = type();
        parameter.setLeft(type);
        Lexeme identifier = consume(IDENTIFIER);
        parameter.setRight(identifier);
        return parameter;
    }

    private Lexeme tavernDeclaration() {
        if (debug) System.out.println("-- tavernDeclaration --");
        Lexeme tavernDeclaration = consume(BUILD);
        consume(TAVERN);
        consume(IDENTIFIER);
        Lexeme primary = primary();
        tavernDeclaration.setLeft(primary);
        consume(ROOMS);
        return tavernDeclaration;
    }

    private Lexeme declaration() {
        if (debug) System.out.println("-- declaration --");
        Lexeme declaration = new Lexeme(DECLARATION, currentLexeme.getLineNumber());
        Lexeme type = type();
        declaration.setLeft(type);
        consume(SQUIRE);
        Lexeme identifier = consume(IDENTIFIER);
        declaration.setRight(identifier);
        consume(BANG);
        return declaration;
    }

    private Lexeme assignment() {
        if (debug) System.out.println("-- assignment --");
        Lexeme assignment;
        if (check(GIVE)) {
            assignment = consume(GIVE);
            Lexeme identifier = consume(IDENTIFIER);
            assignment.setLeft(identifier);
            Lexeme primary = primary();
            assignment.setRight(primary);
            consume(BANG);
        } else {
            assignment = new Lexeme(ASSIGNMENT, currentLexeme.getLineNumber());
            Lexeme tavernAssignment = tavernAssignment();
            assignment.setLeft(tavernAssignment);
        }
        return assignment;
    }

    private Lexeme tavernAssignment() {
        if (debug) System.out.println("-- tavernAssignment --");
        Lexeme tavernAssignment = new Lexeme(TAVERN_ASSIGNMENT, currentLexeme.getLineNumber());
        Lexeme identifier = consume(IDENTIFIER);
        tavernAssignment.setLeft(identifier);
        consume(ROOM);
        Lexeme glue = new Lexeme(GLUE, currentLexeme.getLineNumber());
        Lexeme primary = primary();
        tavernAssignment.setRight(glue);
        glue.setLeft(primary);
        consume(GUEST);
        Lexeme expression = expression();
        glue.setRight(expression);
        return tavernAssignment;
    }
    // ----------- Pending Methods -----------

    private boolean statementListPending() {
        return statementPending();
    }

    private boolean statementPending() {
        return initializationPending() || declarationPending() || assignmentPending() ||
                functionCallPending() || loopPending() || conditionalPending() || expressionPending();
    }

    private boolean expressionPending() {
        return comparatorPending() || primaryPending() || unaryPending() || variadicPending() ||
                functionCallPending();
    }

    private boolean primaryPending() {
        return typePending() || check(IDENTIFIER) || tavernCallPending() || groupPending();
    }

    private boolean typePending() {
        return check(NUM) || check(NUM_WITH_CALC) || check(WORD) || moralLiteralPending();
    }

    private boolean moralLiteralPending() {
        return check(GOOD) || check(EVIL);
    }

    private boolean tavernCallPending() {
        return check(IDENTIFIER) && checkNext(ROOM_NUMBER);
    }

    private boolean groupPending() {
        return check(O_PAREN);
    }

    private boolean unaryPending() {
        return unaryOperatorPending();
    }

    private boolean unaryOperatorPending() {
        return check(SWITCH) || check(DESPAIR);
    }

    private boolean variadicPending() {
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

    private boolean comparatorPending() {
        return primaryPending() && (checkNext(BETTER) || checkNext(EQUAL) || checkNext(BETTER_EQUAL));
    }

    private boolean functionCallPending() {
        return check(IDENTIFIER) && checkNext(WITH);
    }

    private boolean initializationPending() {
        return check(HOLD) || functionDefinitionPending() || tavernDeclarationPending();
    }

    private boolean functionDefinitionPending() {
        return check(DO);
    }

    private boolean tavernDeclarationPending() {
        return check(BUILD);
    }

    private boolean declarationPending() {
        return typePending() && checkNext(SQUIRE);
    }

    private boolean assignmentPending() {
        return check(GIVE) || tavernAssignmentPending();
    }

    private boolean tavernAssignmentPending() {
        return check(IDENTIFIER) && checkNext(ROOM);
    }

    private boolean loopPending() {
        return riverTrollsPending() || mountainTrollsPending() || mischievousTrollsPending();
    }

    private boolean riverTrollsPending() {
        return primaryPending() && checkNext(RIVER);
    }

    private boolean mountainTrollsPending() {
        return check(FEED);
    }

    private boolean mischievousTrollsPending() {
        return check(MISCHIEVOUS);
    }

    private boolean conditionalPending() {
        return check(DIVINE);
    }
}
