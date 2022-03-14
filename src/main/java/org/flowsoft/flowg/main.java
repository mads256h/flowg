package org.flowsoft.flowg;

import java_cup.runtime.Symbol;
import org.flowsoft.flowg.nodes.Node;

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
            var symbol = parser.debug_parse();
            PrintSymbol(symbol);
            ((Node)symbol.value).Print();
        }
        catch (Exception e) {
            System.out.println("Could not parse");
            e.printStackTrace();
            return;
        }
    }

    private static void PrintSymbol(Symbol symbol) {
        System.out.print("Symbol: ");
        System.out.print(symbol.sym);
        System.out.print(" value: ");
        System.out.print(symbol.value);
        System.out.print(" left: ");
        System.out.print(symbol.left);
        System.out.print(" right: ");
        System.out.println(symbol.right);
    }
}