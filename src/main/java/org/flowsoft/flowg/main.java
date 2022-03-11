package org.flowsoft.flowg;

import java_cup.runtime.Symbol;

import java.io.FileReader;

public class main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Yylex.main(args);

        Yylex scanner;
        try {
            scanner = new Yylex(new FileReader(args[0]));
        }
        catch (Exception e) {
            System.out.println("Usage: <file>");
            e.printStackTrace();
            return;
        }

        parser parser = new parser(scanner);
        try {
            parser.debug_parse();
        }
        catch (Exception e) {
            System.out.println("Could not parse");
            e.printStackTrace();
            return;
        }
    }
}