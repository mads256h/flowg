package org.flowsoft.flowg.tests;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

import org.flowsoft.flowg.InvalidTokenException;
import org.flowsoft.flowg.Yylex;
import org.flowsoft.flowg.sym;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.StringReader;

@RunWith(Theories.class)
public class LexerTests {

    private static class TextSymbolPair {
        private final String _input;
        private final int[] _symbols;

        public TextSymbolPair(String input, int... symbols) {
            _input = input;
            _symbols = symbols;
        }

        public String GetText() {
            return  _input;
        }

        public int[] GetSymbols() {
            return _symbols;
        }

    }

    @DataPoints
    public static final TextSymbolPair[] _pairs = {
            new TextSymbolPair("number x = 2;", sym.TYPE, sym.IDENTIFIER, sym.ASSIGNMENT, sym.NUMBER_LITERAL, sym.SEMICOLON),
            new TextSymbolPair("bool y = true;", sym.TYPE, sym.IDENTIFIER, sym.ASSIGNMENT, sym.BOOLEAN_LITERAL, sym.SEMICOLON),
            //new TextSymbolPair("point z = [1,2,3];", sym.TYPE, sym.IDENTIFIER, sym.ASSIGNMENT, sym.BOOLEAN_LITERAL, sym.SEMICOLON),
    };

    @DataPoints
    public static final String[] _invalidInput = {
            "\"",
            "#",
            "$",
            "%",
            ":",
            "\\"
    };

    @Theory
    public void TestLexerSuccess(TextSymbolPair pair) throws IOException, InvalidTokenException {
        var lexer = new Yylex(new StringReader(pair.GetText()), "test");
        for (int symbol : pair.GetSymbols()) {
            assertThat(lexer.next_token().sym).isEqualTo(symbol);
        }
        assertThat(lexer.next_token().sym).isEqualTo(sym.EOF);
    }

    @Theory
    public void TestLexerFailure(String input) throws IOException, InvalidTokenException {
        var lexer = new Yylex(new StringReader(input), "test");
        assertThrows(InvalidTokenException.class, lexer::next_token);
        assertThat(lexer.next_token().sym).isEqualTo(sym.EOF);
    }

}
