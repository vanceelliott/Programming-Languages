package com.elliott;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Chivalry {
    public static void main(String[] args) throws IOException {
        try {
            if (singlePathProvided(args)) runFile(args[0]);
            else {
                System.out.println("Put exactly one file path, you absolute idiot");
                System.exit(64);
            }
        } catch (IOException exception) {
            throw new IOException(exception.toString());
        }
    }

    public static void runFile(String path) throws IOException {
        String sourceCode = getSourceCodeFromFile(path);
        run(sourceCode);
    }

    private static void run(String sourceCode) {
        Lexer lexer = new Lexer(sourceCode);
        ArrayList<Lexeme> lexemes = lexer.lex();
        Parser recognizer = new Parser(lexemes);
        Environment globalEnvironment = new Environment(null);
        Lexeme parseTree = recognizer.program();
        Evaluator evaluator = new Evaluator();
        Lexeme programResult = evaluator.eval(parseTree, globalEnvironment);

        System.out.println("Program result: " + programResult);
        System.out.println(globalEnvironment);
    }

    private static void printLexemes(ArrayList<Lexeme> lexemes) {
        for (Lexeme lexeme : lexemes) System.out.println(lexeme);
    }

    private static boolean singlePathProvided(String[] args) {
        return args.length == 1;
    }

    private static String getSourceCodeFromFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        return new String(bytes, Charset.defaultCharset());
    }

    public static void error(int lineNumber, String errorMessage) {
        System.out.println("Error on " + lineNumber + ". " + errorMessage);
    }

    public static void error(Lexeme lexeme, String errorMessage) {
        if (lexeme.getType() == TokenType.EOF)
            report(lexeme.getLineNumber(), "at end of file", errorMessage);
        else report(lexeme.getLineNumber(), "at '" + lexeme + "'", errorMessage);
    }

    public static void report(int lineNumber, String where, String message) {
        System.err.println("[line " + lineNumber + "] Error " + where + ": " + message);
    }

    public static void mischievous(Environment environment) {
        System.out.println();
        System.out.println();
        printSlow("YOU FOOL!!! The one thing you were told not to do, you go and do anyway.");
        System.out.println();

        printSlow("How could someone be this cruel? Do you know what you've released upon this world?");
        System.out.println();

        redPrintSlow("Who summoned me?");
        System.out.println();

        printSlow("It was the person behind the computer screen. The one staring at us.");
        System.out.println();

        redPrintSlow("So you see that I talk in red text right? And you still summoned me?");
        System.out.println();

        printSlow("I don't think they knew about the red text, but I warned them in the README.");
        System.out.println();

        redPrintSlow("So they know I just mess with the program?");
        System.out.println();

        printSlow("Yeah.");
        System.out.println();

        redPrintSlow("And they still typed my summoning spell on their magic metal box?");
        System.out.println();

        printSlow("Yes.");
        System.out.println();

        redPrintSlow("Idiot.");
        System.out.println();

        printSlow("I completely agree. The program deserves to be ruined.");
        System.out.println();

        redPrintSlow("Well then I guess I'll get on with it.");
        System.out.println();

        redPrintSlow("Herbert, herbert, lemon sherbert, what d-");
        System.out.println();

        printSlow("Are you having a stroke or something?");
        System.out.println();

        redPrintSlow("No I'm casting a spell.");
        System.out.println();

        printSlow("For what?");
        System.out.println();

        redPrintSlow("To see what I'm going to do to the program.");
        System.out.println();

        printSlow("Why do you need a spell to do that. Just decide on your own.");
        System.out.println();

        redPrintSlow("That's not how it works. And please stop interrupting-");
        System.out.println();

        printSlow("What do you mean that's not how 'it' works?");
        System.out.println();

        redPrintSlow("That's just the way 'it' is. I don't know. Casting spells is just what I do, so please SHUT UP and let me do my thing.");
        System.out.println();

        redPrintSlow("Skittle, skittle, Chicken Little, tell me wha-");
        System.out.println();

        printSlow("That's not the same spell.");
        System.out.println();

        redPrintSlow("STOP INTERRUPTING ME!!!");
        System.out.println();

        printSlow("But you said herbert, herbert, lem-");
        System.out.println();

        redPrintSlow("I KNOW WHAT I SAID!");
        System.out.println();

        printSlow("Please don't interrupt me. I'm trying to be civil here.");
        System.out.println();

        redPrintSlow("I SWEAR I'M GONNA *********** THE ******** OFF THE **** *** **** AND THEN **** **** GRANDMA'S ***** ***** ORCA ***** BUTT.");
        System.out.println();

        printSlow("That was a lot.");
        System.out.println();

        redPrintSlow("I'm a troll, a creature of chaos. I can do whatever I want.");
        System.out.println();

        printSlow("Ok fine just get on with the spell.");
        System.out.println();

        redPrintSlow("Ok ... Don't interrupt me. Here we go.");
        System.out.println();

        redPrintSlow("Rattlesnake, rattlesnake, go jump in a lake, tell me what to do to this program Jake!");
        System.out.println();

        printSlow("Who's Jake?");
        System.out.println();

        printReallySlow("   I     A M     J A K E     .");

        printSlow("Oh, that's Jake. Hello.");
        System.out.println();

        printReallySlow("        H E L L O      .");

        redPrintSlow("What shall I do to the program oh great Jake?");
        System.out.println();

        printReallySlow("        H m m . . .      ");

        double rand = Math.random() * 3;
        if (environment.checkIdents()) {
            if (rand < 2) {
                printReallySlow("  E N D    T H E    P R O G R A M   .");

                redPrintSlow("Welp now I know what I got to do.");
                System.out.println();

                printSlow("Can I at least say goodbye to my wife?");
                System.out.println();

                redPrintSlow("Nope");
                System.out.println();

                System.exit(0);
            } else {
                printReallySlow("  M A K E   A N   I N F I N I T E   L O O P   .");

                redPrintSlow("Hahaha the most evilest of schemes.");
                System.out.println();

                printSlow("You wouldn't dare.");
                System.out.println();

                redPrintSlow("Oh? watch me.");
                System.out.println();

                while (true) {
                    System.out.println("HAHAHAHA");
                }
            }
        } else if (rand < 1) {
            printReallySlow("  K I L L    A    R A N D O M     S Q U I R E   .");

            redPrintSlow("Well you heard the boss.");
            System.out.println();

            printSlow("This is YOUR fault user. An innocent squire is going to die at your hand.");
            System.out.println();

            redPrintSlow("Now who should I kill?");
            System.out.println();

            String name = environment.killSquire().getStringValue();
            redPrintSlow("How about " + name + "?");
            System.out.println();

            printSlow("NOOOO!!! Not " + name + "!");
            System.out.println();

            redPrintSlow("HAHAHAHA! Carry on with your stupid program, stupid knight. Stupid. I literally talk in red text. Stupid to summon me. ");
            System.out.println();

            printSlow("I will never forgive you for this coder. I will avenge " + name + " if it's the last thing I do.");
            System.out.println();


        } else if (rand < 2) {
            printReallySlow("  E N D    T H E    P R O G R A M   .");

            redPrintSlow("Welp now I know what I got to do.");
            System.out.println();

            printSlow("Can I at least say goodbye to my wife?");
            System.out.println();

            redPrintSlow("Nope");
            System.out.println();

            System.exit(0);

        } else {
            printReallySlow("  M A K E   A N   I N F I N I T E   L O O P   .");

            redPrintSlow("Hahaha the most evilest of schemes.");
            System.out.println();

            printSlow("You wouldn't dare.");
            System.out.println();

            redPrintSlow("Oh? watch me.");
            System.out.println();

            while (true) {
                System.out.println("HAHAHAHA");
            }
        }

    }

    public static void printSlow(String input) {
        try {
            for (int i = 0; i < input.length(); i++) {
                Thread.sleep(40);
                System.out.print(input.charAt(i));
            }
            Thread.sleep(500);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }

    public static void redPrintSlow(String input) {
        try {
            for (int i = 0; i < input.length(); i++) {
                Thread.sleep(50);
                System.err.print(input.charAt(i));
            }
            Thread.sleep(500);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }

    public static void printReallySlow(String input) {
        try {
            Thread.sleep(100);
            System.out.println();
            Thread.sleep(100);
            System.out.println();
            Thread.sleep(100);
            for (int i = 0; i < input.length(); i++) {
                Thread.sleep(150);
                System.out.print(input.charAt(i));
            }
            System.out.println();
            Thread.sleep(100);
            System.out.println();
            Thread.sleep(100);
            System.out.println();
            Thread.sleep(100);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}
