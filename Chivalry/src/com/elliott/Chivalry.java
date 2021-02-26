package com.elliott;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Chivalry {
    public static void main(String[] args) throws IOException {
        /*try {
            if(singlePathProvided(args)) runFile(args[0]);
            else {
                System.out.println("Put exactly one file path, you absolute idiot");
                System.exit(64);
            }
        } catch (IOException exception) {
            throw new IOException(exception.toString());
        }*/
        Lexeme keyword = new Lexeme(TokenType.CHOP, 21);
        Lexeme identifier = new Lexeme(TokenType.IDENTIFIER, "hello", 22);
        Lexeme integernum = new Lexeme(TokenType.NUM, 2, 23);
        Lexeme doublenum = new Lexeme(TokenType.NUMWITHCALC, 3.5, 24);
        System.out.println(keyword);
        System.out.println(identifier);
        System.out.println(integernum);
        System.out.println(doublenum);
    }
    public static void runFile(String path) throws IOException {
        String sourceCode = getSourceCodeFromFile(path);
        run(sourceCode);
    }

    private static void run(String sourceCode) {
        Lexer lexer = new Lexer(sourceCode);
        ArrayList<Lexeme> lexemes = lexer.lex();
        printLexemes(lexemes);
    }

    private static void printLexemes(ArrayList<Lexeme> lexemes) {
        for(Lexeme lexeme : lexemes) System.out.println(lexeme);
    }

    private static boolean singlePathProvided(String[] args){ return args.length == 1; }

    private static String getSourceCodeFromFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        return new String(bytes, Charset.defaultCharset());
    }
}
