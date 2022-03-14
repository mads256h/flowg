package org.flowsoft.flowg.tests;

import java_cup.runtime.Symbol;
import org.flowsoft.flowg.Yylex;
import org.flowsoft.flowg.parser;
import org.junit.Test;

import java.io.StringReader;

public class ParserTests {

    @Test
    public void ParseNumberVariableDeclaration() throws Exception {
        Parse("number hello = 2;");
    }

    @Test
    public void ParseBooleanVariableDeclaration() throws Exception {
        Parse("bool world = true;");
    }

    private Symbol Parse(String input) throws Exception {
        Yylex lexer = new Yylex(new StringReader(input));
        parser parser = new parser(lexer);
        return parser.debug_parse();
    }
}
