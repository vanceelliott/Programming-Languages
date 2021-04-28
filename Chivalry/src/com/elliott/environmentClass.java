package com.elliott;

import static com.elliott.TokenType.*;

public class environmentClass {
    public static void main(String[] args) {
        Environment highest = new Environment(null);
        Environment middle = new Environment(highest);
        Environment bottom = new Environment(middle);

        Lexeme one = new Lexeme(IDENTIFIER, "zac", 1);
        Lexeme two = new Lexeme(IDENTIFIER, "ryan", 2);
        Lexeme three = new Lexeme(IDENTIFIER, "table", 3);
        Lexeme four = new Lexeme(IDENTIFIER, "pirate", 4);

        Lexeme five = new Lexeme(NUM, 4, 1);
        Lexeme six = new Lexeme(WORD, "HELLO", 2);
        Lexeme seven = new Lexeme(NUM_WITH_CALC, 2.1, 3);
        Lexeme eight = new Lexeme(WORD, "hop", 4);
        Lexeme newFive = new Lexeme(NUM, 3, 1);

        highest.insert(one, five);
        highest.insert(two, six);
        middle.insert(three, seven);
        bottom.insert(four, eight);

        System.out.println(highest);
        System.out.println(middle);
        System.out.println(bottom);

        highest.update(one, newFive);

        System.out.println(highest);

        System.out.println(middle.retrieve(three));


    }
}
