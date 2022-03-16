package org.flowsoft.flowg.tests;

import static com.google.common.truth.Truth.assertThat;

import org.flowsoft.flowg.Yylex;
import org.flowsoft.flowg.nodes.*;
import org.flowsoft.flowg.parser;
import org.junit.Test;

import java.io.StringReader;
import java.math.BigDecimal;

public class ParserTests {

    @Test
    public void ParseNumberVariableDeclaration() throws Exception {
        var programNode = Parse("number hello = 2;");
        var statementList = programNode.GetChild();
        assertThat(statementList.GetRightChild()).isNull();

        var statement = statementList.GetLeftChild();
        var declaration = statement.GetChild();
        var type = declaration.GetTypeChild();
        var identifier = declaration.GetIdentifierChild();
        assertThat(type.GetValue()).isEqualTo("number");
        assertThat(identifier.GetValue()).isEqualTo("hello");

        var numberLiteral = (NumberLiteralNode)declaration.GetExpressionChild();
        assertThat(numberLiteral.GetValue()).isEqualTo(new BigDecimal("2"));
    }

    @Test
    public void ParseBooleanVariableDeclaration() throws Exception {
        var programNode = Parse("bool world = true;");
        var statementList = programNode.GetChild();
        assertThat(statementList.GetRightChild()).isNull();

        var statement = statementList.GetLeftChild();
        var declaration = statement.GetChild();
        var type = declaration.GetTypeChild();
        var identifier = declaration.GetIdentifierChild();
        assertThat(type.GetValue()).isEqualTo("bool");
        assertThat(identifier.GetValue()).isEqualTo("world");

        var booleanLiteral = (BooleanLiteralNode)declaration.GetExpressionChild();
        assertThat(booleanLiteral.GetValue()).isEqualTo(true);
    }

    private ProgramNode Parse(String input) throws Exception {
        Yylex lexer = new Yylex(new StringReader(input));
        parser parser = new parser(lexer);
        return (ProgramNode) parser.debug_parse().value;
    }
}
