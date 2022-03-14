package org.flowsoft.flowg.tests;

import static com.google.common.truth.Truth.assertThat;

import org.flowsoft.flowg.Yylex;
import org.flowsoft.flowg.sym;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

public class LexerTests {

    private Yylex _lexer;

    @Test
    public void ScanNumberVariableDeclaration() throws IOException {
        Scan("number x = 2;");
        assertThat(NextToken()).isEqualTo(sym.TYPE);
        assertThat(NextToken()).isEqualTo(sym.IDENTIFIER);
        assertThat(NextToken()).isEqualTo(sym.ASSIGNMENT);
        assertThat(NextToken()).isEqualTo(sym.NUMBER_LITERAL);
        assertThat(NextToken()).isEqualTo(sym.SEMICOLON);
    }

    @Test
    public void ScanBooleanVariableDeclaration() throws IOException {
        Scan("bool y = true;");
        assertThat(NextToken()).isEqualTo(sym.TYPE);
        assertThat(NextToken()).isEqualTo(sym.IDENTIFIER);
        assertThat(NextToken()).isEqualTo(sym.ASSIGNMENT);
        assertThat(NextToken()).isEqualTo(sym.BOOLEAN_LITERAL);
        assertThat(NextToken()).isEqualTo(sym.SEMICOLON);
    }

    private void Scan(String input) {
        _lexer = new Yylex(new StringReader(input));
    }

    private int NextToken() throws IOException {
        return _lexer.next_token().sym;
    }
}
