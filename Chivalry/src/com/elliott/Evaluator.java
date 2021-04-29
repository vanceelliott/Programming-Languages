package com.elliott;

import java.util.ArrayList;

import static com.elliott.TokenType.*;

public class Evaluator {
    private static final boolean debug = true;

    public Lexeme eval(Lexeme tree, Environment environment) {
        if (tree == null) return null;
        switch (tree.getType()) {
            case PROGRAM:
                return eval(tree.getLeft(), environment);
            case STATEMENT_LIST:
                return evalStatementList(tree, environment);
            case STATEMENT:
                return evalStatement(tree, environment);
            case HOLD:
                return evalInitialization(tree, environment);
            case INITIALIZATION:
                return evalFuncInitialization(tree, environment);
            case FUNCTION_CALL:
                return evalFunctionCall(tree, environment);
            case DECLARATION:
                return evalDeclaration(tree, environment);
            case DO:
                return evalFunctionDefinition(tree, environment);
            case GIVE:
                return evalAssignment(tree, environment);
            case O_SQUARE:
                return evalBlock(tree, environment);
            case RIVER_TROLLS:
            case FEED:
            case MISCHIEVOUS:
                return evalLoop(tree, environment);
            case DIVINE:
                //self-evaluating types
            case NUM:
            case NUM_WITH_CALC:
            case WORD:
            case GOOD:
            case EVIL:
                return tree;
            case IDENTIFIER:
                return environment.retrieve(tree);
            //Simple Binary Operators
            case COMBINE:
            case SMASH:
            case WITCHCRAFT:
            case CHOP:
            case BETTER:
            case BETTER_EQUAL:
            case EQUAL:
                return evalSimpleOperator(tree, environment);
            case GROUP:
                return evalGroup(tree, environment);
            default:
                return null;
        }
    }

    private Lexeme evalStatementList(Lexeme statementList, Environment environment) {
        if (debug) System.out.println("Evaluating StatementList...");
        Lexeme result = null;
        while (statementList != null) {
            result = eval(statementList.getLeft(), environment);
            statementList = statementList.getRight();
        }
        return result;
    }

    private Lexeme evalStatement(Lexeme statement, Environment environment) {
        if (debug) System.out.println("Evaluating statement...");
        return eval(statement.getLeft(), environment);
    }

    private Lexeme evalFuncInitialization(Lexeme initialization, Environment environment) {
        if (debug) System.out.println("Evaluating funcInitialization...");
        return eval(initialization.getLeft(), environment);
    }

    private Lexeme evalInitialization(Lexeme initialization, Environment environment) {
        if (debug) System.out.println("Evaluating initialization...");
        environment.insert(initialization.getRight().getLeft(), evalExpression(initialization.getRight().getRight(), environment));
        return null;
    }

    private Lexeme evalDeclaration(Lexeme declaration, Environment environment) {
        if (debug) System.out.println("Evaluating declaration...");
        environment.insert(declaration.getRight(), null);
        return null;
    }

    private Lexeme evalAssignment(Lexeme assignment, Environment environment) {
        if (debug) System.out.println("Evaluating assignment...");
        environment.update(assignment.getLeft(), evalPrimary(assignment.getRight(), environment));
        return null;
    }

    private Lexeme evalLoop(Lexeme loop, Environment environment) {
        if (debug) System.out.println("Evaluating loop...");
        switch (loop.getType()) {
            case RIVER_TROLLS:
                return evalRiverTrolls(loop, environment);
            case FEED:
                return evalMountainTrolls(loop, environment);
            case MISCHIEVOUS:
                return evalMischievousTrolls(environment);
            default:
                return null;
        }
    }

    private Lexeme evalRiverTrolls(Lexeme loop, Environment environment) {
        if (debug) System.out.println("Evaluating river trolls...");
        Lexeme returnVal = null;
        Environment newEnvironment = new Environment(environment);
        Lexeme foodLeft = new Lexeme(IDENTIFIER, "foodLeft", loop.getRight().getRight().getLineNumber());
        Lexeme foodLeftVal = new Lexeme(NUM, 0, loop.getRight().getRight().getLineNumber());
        newEnvironment.insert(foodLeft, foodLeftVal);
        if (loop.getLeft().getType() == NUM) {
            for (int foodLeftNum = loop.getRight().getLeft().getIntValue(); foodLeftNum > 0; foodLeftNum -= loop.getLeft().getIntValue()) {
                Lexeme newVal = new Lexeme(NUM, foodLeftNum, loop.getRight().getRight().getLineNumber());
                newEnvironment.update(foodLeft, newVal);
                returnVal = eval(loop.getRight().getRight(), newEnvironment);
            }
        } else if (loop.getLeft().getType() == NUM_WITH_CALC) {
            for (double foodLeftNum = loop.getRight().getLeft().getDoubleValue(); foodLeftNum > 0; foodLeftNum -= loop.getLeft().getDoubleValue()) {
                Lexeme newVal = new Lexeme(NUM_WITH_CALC, foodLeftNum, loop.getRight().getRight().getLineNumber());
                newEnvironment.update(foodLeft, newVal);
                returnVal = eval(loop.getRight().getRight(), newEnvironment);
            }
        } else {
            Chivalry.error(loop.getLeft(), "Incorrect type. " + loop.getLeft().getType() + " is not a num or num_with_calc");
            returnVal = null;
        }
        return returnVal;
    }

    private Lexeme evalMountainTrolls(Lexeme loop, Environment environment) {
        if (debug) System.out.println("Evaluating mountain trolls...");
        Environment newEnvironment = new Environment(environment);
        Lexeme returnVal = null;
        while (evalPrimary(loop.getLeft(), environment).getStringValue() == "evil") {
            returnVal = eval(loop.getRight(), newEnvironment);
        }
        return returnVal;
    }

    private Lexeme evalMischievousTrolls(Environment environment) {
        if (debug) System.out.println("Evaluating initialization...");
        Chivalry.mischievous(environment);
        return null;
    }

    private Lexeme evalBlock(Lexeme block, Environment environment) {
        System.out.println("Evaluating block...");
        return eval(block.getLeft(), environment);
    }

    private Lexeme evalSimpleOperator(Lexeme tree, Environment environment) {
        if (debug) System.out.println("Evaluating simpleOperator");
        switch (tree.getType()) {
            case COMBINE:
                return evalCombine(tree, environment);
            case SMASH:
                return evalSmash(tree, environment);
            case WITCHCRAFT:
                return evalWitchcraft(tree, environment);
            case CHOP:
                return evalChop(tree, environment);
            case BETTER:
                return evalBetter(tree, environment);
            case BETTER_EQUAL:
                return evalBetterEqual(tree, environment);
            case EQUAL:
                return evalEqual(tree, environment);
            default:
                Chivalry.error(tree, "Unrecognized operator: " + tree.toString());
                return null;
        }
    }

    private Lexeme evalCombine(Lexeme tree, Environment environment) {
        if (debug) System.out.println("Evaluating combine...");
        ArrayList<Lexeme> pList = evalPrimaryList(tree.getLeft(), environment);
        Lexeme sum = pList.get(0);
        for (int i = 1; i < pList.size(); i++) {
            Lexeme left = sum;
            Lexeme right = pList.get(i);
            TokenType leftType = left.getType();
            TokenType rightType = right.getType();

            if (leftType == NUM && rightType == NUM)
                sum = new Lexeme(NUM, left.getIntValue() + right.getIntValue(), left.getLineNumber());
            else if (leftType == NUM && rightType == NUM_WITH_CALC)
                sum = new Lexeme(NUM_WITH_CALC, left.getIntValue() + right.getDoubleValue(), left.getLineNumber());
            else if (leftType == NUM && rightType == WORD)
                sum = new Lexeme(WORD, left.getIntValue() + right.getStringValue(), left.getLineNumber());
            else if (leftType == NUM && rightType == MORAL) {
                if (left.getIntValue() % 2 == 0)
                    sum = new Lexeme(MORAL, right.getStringValue(), left.getLineNumber());
                else {
                    if (right.getStringValue().toLowerCase() == "good")
                        sum = new Lexeme(MORAL, "evil", left.getLineNumber());
                    else {
                        sum = new Lexeme(MORAL, "good", left.getLineNumber());
                    }
                }
            } else if (leftType == NUM_WITH_CALC && rightType == NUM)
                sum = new Lexeme(NUM_WITH_CALC, left.getDoubleValue() + right.getIntValue(), left.getLineNumber());
            else if (leftType == NUM_WITH_CALC && rightType == NUM_WITH_CALC)
                sum = new Lexeme(NUM_WITH_CALC, left.getDoubleValue() + right.getDoubleValue(), left.getLineNumber());
            else if (leftType == NUM_WITH_CALC && rightType == WORD)
                sum = new Lexeme(WORD, left.getDoubleValue() + right.getStringValue(), left.getLineNumber());
            else if (leftType == WORD && rightType == WORD)
                sum = new Lexeme(WORD, left.getStringValue() + right.getStringValue(), left.getLineNumber());
            else if (leftType == WORD && rightType == MORAL) {
                if (right.getStringValue() == "good")
                    sum = new Lexeme(WORD, left.getStringValue() + "righteous", left.getLineNumber());
                else
                    sum = new Lexeme(WORD, left.getStringValue() + "despicable", left.getLineNumber());
            } else if (leftType == MORAL && rightType == NUM) {
                if (right.getIntValue() % 2 == 0)
                    sum = new Lexeme(MORAL, left.getStringValue(), left.getLineNumber());
                else {
                    if (left.getStringValue().toLowerCase() == "good")
                        sum = new Lexeme(MORAL, "evil", left.getLineNumber());
                    else {
                        sum = new Lexeme(MORAL, "good", left.getLineNumber());
                    }
                }
            } else if (leftType == MORAL && rightType == WORD) {
                if (left.getStringValue() == "good")
                    sum = new Lexeme(WORD, "righteous" + right.getStringValue(), left.getLineNumber());
                else
                    sum = new Lexeme(WORD, "despicable" + right.getStringValue(), left.getLineNumber());
            } else if (leftType == MORAL && rightType == MORAL) {
                if (left.getStringValue() == "good" || right.getStringValue() == "good")
                    sum = new Lexeme(MORAL, "good", left.getLineNumber());
                else
                    sum = new Lexeme(MORAL, "evil", left.getLineNumber());
            } else {
                Chivalry.error(left, "Incompatible types. Cannot add " + leftType + " to " + rightType);
                sum = null;
            }
        }
        return sum;
    }

    private Lexeme evalSmash(Lexeme tree, Environment environment) {
        if (debug) System.out.println("Evaluating smash...");
        ArrayList<Lexeme> pList = evalPrimaryList(tree.getLeft(), environment);
        Lexeme sum = pList.get(0);
        for (int i = 1; i < pList.size(); i++) {
            Lexeme left = sum;
            Lexeme right = pList.get(i);
            TokenType leftType = left.getType();
            TokenType rightType = right.getType();

            if (leftType == NUM && rightType == NUM)
                sum = new Lexeme(NUM, left.getIntValue() - right.getIntValue(), left.getLineNumber());
            else if (leftType == NUM && rightType == NUM_WITH_CALC)
                sum = new Lexeme(NUM_WITH_CALC, left.getIntValue() - right.getDoubleValue(), left.getLineNumber());
            else if (leftType == NUM_WITH_CALC && rightType == NUM)
                sum = new Lexeme(NUM_WITH_CALC, left.getDoubleValue() - right.getIntValue(), left.getLineNumber());
            else if (leftType == NUM_WITH_CALC && rightType == NUM_WITH_CALC)
                sum = new Lexeme(NUM_WITH_CALC, left.getDoubleValue() - right.getDoubleValue(), left.getLineNumber());
            else {
                Chivalry.error(left, "Incompatible types. Cannot subtract " + leftType + " by " + rightType);
                sum = null;
            }
        }
        return sum;
    }

    private Lexeme evalWitchcraft(Lexeme tree, Environment environment) {
        if (debug) System.out.println("Evaluating witchcraft...");
        ArrayList<Lexeme> pList = evalPrimaryList(tree.getLeft(), environment);
        Lexeme sum = pList.get(0);
        for (int i = 1; i < pList.size(); i++) {
            Lexeme left = sum;
            Lexeme right = pList.get(i);
            TokenType leftType = left.getType();
            TokenType rightType = right.getType();

            if (leftType == NUM && rightType == NUM)
                sum = new Lexeme(NUM, left.getIntValue() * right.getIntValue(), left.getLineNumber());
            else if (leftType == NUM && rightType == NUM_WITH_CALC)
                sum = new Lexeme(NUM_WITH_CALC, left.getIntValue() * right.getDoubleValue(), left.getLineNumber());
            else if (leftType == NUM && rightType == WORD) {
                String a = right.getStringValue();
                String c = "";
                for (int b = 0; b < left.getIntValue(); b++) {
                    c = c + a;
                }
                sum = new Lexeme(WORD, c, left.getLineNumber());
            } else if (leftType == NUM_WITH_CALC && rightType == NUM)
                sum = new Lexeme(NUM_WITH_CALC, left.getDoubleValue() * right.getIntValue(), left.getLineNumber());
            else if (leftType == NUM_WITH_CALC && rightType == NUM_WITH_CALC)
                sum = new Lexeme(NUM_WITH_CALC, left.getDoubleValue() * right.getDoubleValue(), left.getLineNumber());
            else if (leftType == NUM_WITH_CALC && rightType == WORD) {
                String a = right.getStringValue();
                String c = "";
                int intVal = ((int) ((double) left.getDoubleValue()));
                double remainder = left.getDoubleValue() - intVal;
                for (int b = 0; b < intVal; b++) {
                    c = c + a;
                }
                int h = (int) ((remainder * ((double) a.length())) + 0.5);
                for (int x = 0; x < h; x++) {
                    c = c + a.charAt(x);
                }
                sum = new Lexeme(WORD, c, left.getLineNumber());
            } else if (leftType == WORD && rightType == NUM) {
                String a = left.getStringValue();
                String c = "";
                for (int b = 0; b < right.getIntValue(); b++) {
                    c = c + a;
                }
                sum = new Lexeme(WORD, c, left.getLineNumber());
            } else if (leftType == WORD && rightType == NUM_WITH_CALC) {
                String a = left.getStringValue();
                String c = "";
                int intVal = ((int) ((double) right.getDoubleValue()));
                double remainder = right.getDoubleValue() - intVal;
                for (int b = 0; b < intVal; b++) {
                    c = c + a;
                }
                int h = (int) ((remainder * ((double) a.length())) + 0.5);
                for (int x = 0; x < h; x++) {
                    c = c + a.charAt(x);
                }
                sum = new Lexeme(WORD, c, left.getLineNumber());
            } else if (leftType == WORD && rightType == WORD) {
                String a = left.getStringValue();
                String b = right.getStringValue();
                String c = "";
                for (int f = 0; f < a.length(); f++) {
                    for (int g = 0; g < b.length(); g++) {
                        c = c + a.charAt(f) + b.charAt(g);
                    }
                }
                sum = new Lexeme(WORD, c, left.getLineNumber());
            } else if (leftType == WORD && rightType == MORAL) {
                if (right.getStringValue() == "evil") {
                    sum = new Lexeme(WORD, null, left.getLineNumber());
                } else {
                    sum = new Lexeme(WORD, left.getStringValue(), left.getLineNumber());
                }
            } else if (leftType == MORAL && rightType == WORD) {
                if (left.getStringValue() == "evil") {
                    sum = new Lexeme(WORD, null, left.getLineNumber());
                } else {
                    sum = new Lexeme(WORD, right.getStringValue(), left.getLineNumber());
                }
            } else if (leftType == MORAL && rightType == MORAL) {
                if (left.getStringValue() == "good" && right.getStringValue() == "good") {
                    sum = new Lexeme(MORAL, "good", left.getLineNumber());
                } else {
                    sum = new Lexeme(MORAL, "evil", left.getLineNumber());
                }
            } else {
                Chivalry.error(left, "Incompatible types. Cannot multiply " + leftType + " to " + rightType);
                sum = null;
            }
        }
        return sum;
    }

    private Lexeme evalChop(Lexeme tree, Environment environment) {
        if (debug) System.out.println("Evaluating chop...");
        ArrayList<Lexeme> pList = evalPrimaryList(tree.getLeft(), environment);
        Lexeme sum = pList.get(0);
        for (int i = 1; i < pList.size(); i++) {
            Lexeme left = sum;
            Lexeme right = pList.get(i);
            TokenType leftType = left.getType();
            TokenType rightType = right.getType();

            if (leftType == NUM && rightType == NUM)
                sum = new Lexeme(NUM_WITH_CALC, left.getIntValue() / ((double) right.getIntValue()), left.getLineNumber());
            else if (leftType == NUM && rightType == NUM_WITH_CALC)
                sum = new Lexeme(NUM_WITH_CALC, left.getIntValue() / right.getDoubleValue(), left.getLineNumber());
            else if (leftType == NUM_WITH_CALC && rightType == NUM)
                sum = new Lexeme(NUM_WITH_CALC, left.getDoubleValue() / ((double) right.getIntValue()), left.getLineNumber());
            else if (leftType == NUM_WITH_CALC && rightType == NUM_WITH_CALC)
                sum = new Lexeme(NUM_WITH_CALC, left.getDoubleValue() / right.getDoubleValue(), left.getLineNumber());
            else {
                Chivalry.error(left, "Incompatible types. Cannot divide " + leftType + " by " + rightType);
                sum = null;
            }
        }
        return sum;
    }

    private Lexeme evalBetter(Lexeme tree, Environment environment) {
        if (debug) System.out.println("Evaluating better...");
        Lexeme left = eval(tree.getLeft(), environment);
        Lexeme right = eval(tree.getRight(), environment);
        TokenType leftType = left.getType();
        TokenType rightType = right.getType();
        if (leftType == NUM && rightType == NUM)
            if (left.getIntValue() > right.getIntValue())
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else if (leftType == NUM && rightType == NUM_WITH_CALC)
            if (left.getIntValue() > right.getDoubleValue())
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else if (leftType == NUM && rightType == WORD)
            if (left.getIntValue() > Integer.parseInt(right.getStringValue()))
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else if (leftType == NUM_WITH_CALC && rightType == NUM)
            if (left.getDoubleValue() > right.getIntValue())
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else if (leftType == NUM_WITH_CALC && rightType == NUM_WITH_CALC)
            if (left.getDoubleValue() > right.getDoubleValue())
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else if (leftType == NUM_WITH_CALC && rightType == WORD)
            if (left.getDoubleValue() > Double.parseDouble(right.getStringValue()))
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else if (leftType == WORD && rightType == NUM)
            if (Integer.parseInt(left.getStringValue()) > right.getIntValue())
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else if (leftType == WORD && rightType == NUM_WITH_CALC)
            if (Double.parseDouble(left.getStringValue()) > right.getDoubleValue())
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else if (leftType == WORD && rightType == WORD)
            if (Integer.parseInt(left.getStringValue()) > Integer.parseInt(right.getStringValue()))
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else if (leftType == MORAL && rightType == MORAL)
            if (left.getStringValue() == "good" && right.getStringValue() == "evil")
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else {
            Chivalry.error(left, "Incompatible types. Cannot compare " + leftType + " to " + rightType);
            return null;
        }

    }

    private Lexeme evalBetterEqual(Lexeme tree, Environment environment) {
        if (debug) System.out.println("Evaluating betterEqual...");
        Lexeme left = eval(tree.getLeft(), environment);
        Lexeme right = eval(tree.getRight(), environment);
        TokenType leftType = left.getType();
        TokenType rightType = right.getType();
        if (leftType == NUM && rightType == NUM)
            if (left.getIntValue() >= right.getIntValue())
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else if (leftType == NUM && rightType == NUM_WITH_CALC)
            if (left.getIntValue() >= right.getDoubleValue())
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else if (leftType == NUM && rightType == WORD)
            if (left.getIntValue() >= Integer.parseInt(right.getStringValue()))
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else if (leftType == NUM_WITH_CALC && rightType == NUM)
            if (left.getDoubleValue() >= right.getIntValue())
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else if (leftType == NUM_WITH_CALC && rightType == NUM_WITH_CALC)
            if (left.getDoubleValue() >= right.getDoubleValue())
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else if (leftType == NUM_WITH_CALC && rightType == WORD)
            if (left.getDoubleValue() >= Double.parseDouble(right.getStringValue()))
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else if (leftType == WORD && rightType == NUM)
            if (Integer.parseInt(left.getStringValue()) >= right.getIntValue())
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else if (leftType == WORD && rightType == NUM_WITH_CALC)
            if (Double.parseDouble(left.getStringValue()) >= right.getDoubleValue())
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else if (leftType == WORD && rightType == WORD)
            if (Integer.parseInt(left.getStringValue()) >= Integer.parseInt(right.getStringValue()))
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else if (leftType == MORAL && rightType == MORAL)
            if (left.getStringValue() == "good")
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else {
            Chivalry.error(left, "Incompatible types. Cannot compare " + leftType + " to " + rightType);
            return null;
        }

    }

    private Lexeme evalEqual(Lexeme tree, Environment environment) {
        if (debug) System.out.println("Evaluating equal...");
        Lexeme left = eval(tree.getLeft(), environment);
        Lexeme right = eval(tree.getRight(), environment);
        TokenType leftType = left.getType();
        TokenType rightType = right.getType();
        if (leftType == NUM && rightType == NUM)
            if (left.getIntValue() == right.getIntValue())
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else if (leftType == NUM && rightType == NUM_WITH_CALC)
            if (left.getIntValue() == right.getDoubleValue())
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else if (leftType == NUM && rightType == WORD)
            if (left.getIntValue() == Integer.parseInt(right.getStringValue()))
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else if (leftType == NUM_WITH_CALC && rightType == NUM)
            if (left.getDoubleValue() == right.getIntValue())
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else if (leftType == NUM_WITH_CALC && rightType == NUM_WITH_CALC)
            if (left.getDoubleValue() == right.getDoubleValue())
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else if (leftType == NUM_WITH_CALC && rightType == WORD)
            if (left.getDoubleValue() == Double.parseDouble(right.getStringValue()))
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else if (leftType == WORD && rightType == NUM)
            if (Integer.parseInt(left.getStringValue()) == right.getIntValue())
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else if (leftType == WORD && rightType == NUM_WITH_CALC)
            if (Double.parseDouble(left.getStringValue()) == right.getDoubleValue())
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else if (leftType == WORD && rightType == WORD)
            if (Integer.parseInt(left.getStringValue()) == Integer.parseInt(right.getStringValue()))
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else if (leftType == MORAL && rightType == MORAL)
            if (left.getStringValue() == right.getStringValue())
                return new Lexeme(MORAL, "good", left.getLineNumber());
            else
                return new Lexeme(MORAL, "evil", left.getLineNumber());
        else {
            Chivalry.error(left, "Incompatible types. Cannot compare " + leftType + " to " + rightType);
            return null;
        }

    }

    private Lexeme evalGroup(Lexeme tree, Environment environment) {
        if (debug) System.out.println("Evaluating group...");
        return evalExpression(tree.getLeft(), environment);
    }

    private Lexeme evalExpression(Lexeme tree, Environment environment) {
        if (debug) System.out.println("Evaluating expression...");
        switch (tree.getType()) {
            case NUM:
            case NUM_WITH_CALC:
            case WORD:
            case GOOD:
            case EVIL:
                return tree;
            case GROUP:
                return evalGroup(tree, environment);
            case IDENTIFIER:
                return environment.retrieve(tree);
            case SMASH:
            case COMBINE:
            case WITCHCRAFT:
            case VOODOO:
            case BETTER:
            case BETTER_EQUAL:
            case EQUAL:
                return evalSimpleOperator(tree, environment);
            case FUNCTION_CALL:
                return evalFunctionCall(tree, environment);
            default:
                return null;
        }
    }

    private Lexeme evalFunctionDefinition(Lexeme tree, Environment environment) {
        System.out.println("Evaluation functionDefinition");
        environment.insert(tree.getRight().getLeft(), tree);
        return null;
    }

    private Lexeme evalFunctionCall(Lexeme tree, Environment environment) {
        System.out.println("Evaluation functionCall");
        Environment newEnvironment = new Environment(environment);
        Lexeme environmentTree = environment.retrieve(tree.getLeft());
        ArrayList<Lexeme> listOfIdentifiers = evalParameterList(environmentTree.getRight().getRight().getRight());
        ArrayList<Lexeme> listOfPrimaries = evalPrimaryList(tree.getRight(), environment);
        for (int i = 0; i < listOfIdentifiers.size(); i++) {
            newEnvironment.insert(listOfIdentifiers.get(i), listOfPrimaries.get(i));
        }
        return eval(tree.getRight().getRight().getLeft(), newEnvironment);
    }

    private Lexeme evalPrimary(Lexeme tree, Environment environment) {
        if (debug) System.out.println("Evaluating primary...");
        switch (tree.getType()) {
            case NUM:
            case NUM_WITH_CALC:
            case WORD:
            case GOOD:
            case EVIL:
                return tree;
            case GROUP:
                return evalGroup(tree, environment);
            case IDENTIFIER:
                return environment.retrieve(tree);
            default:
                return null;
        }
    }

    private ArrayList<Lexeme> evalPrimaryList(Lexeme tree, Environment environment) {
        System.out.println("Evaluation primaryList");
        ArrayList<Lexeme> types = new ArrayList<>();
        while (tree != null) {
            types.add(evalPrimary(tree.getLeft(), environment));
            tree = tree.getRight();
        }
        return types;
    }

    private ArrayList<Lexeme> evalParameterList(Lexeme tree) {
        System.out.println("Evaluation parameterList");
        ArrayList<Lexeme> identifiers = new ArrayList<>();
        while (tree != null) {
            identifiers.add(evalParameter(tree.getLeft()));
            tree = tree.getRight();
        }
        return identifiers;
    }

    private Lexeme evalParameter(Lexeme tree) {
        System.out.println("Evaluation parameter");
        return tree.getRight();
    }

}
