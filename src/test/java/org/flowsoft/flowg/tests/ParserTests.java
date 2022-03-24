package org.flowsoft.flowg.tests;

import static com.google.common.truth.Truth.assertThat;

import org.flowsoft.flowg.Type;
import org.flowsoft.flowg.Yylex;
import org.flowsoft.flowg.nodes.*;
import org.flowsoft.flowg.parser;
import org.junit.Test;

import java.io.StringReader;
import java.math.BigDecimal;

public class ParserTests {

    @Test
    public void ParseNumberVariableDeclaration() throws Exception {
        var statementList = Parse("number hello = 2;");
        assertThat(statementList.GetRightChild()).isNull();

        var declaration = (DeclarationNode)statementList.GetLeftChild();
        var type = declaration.GetTypeChild();
        var identifier = declaration.GetIdentifierChild();
        assertThat(type.GetValue()).isEqualTo(Type.Number);
        assertThat(identifier.GetValue()).isEqualTo("hello");

        var numberLiteral = (NumberLiteralNode)declaration.GetExpressionChild();
        assertThat(numberLiteral.GetValue()).isEqualTo(new BigDecimal("2"));
    }

    @Test
    public void ParseBooleanVariableDeclaration() throws Exception {
        var statementList = Parse("bool world = true;");
        assertThat(statementList.GetRightChild()).isNull();

        var declaration = (DeclarationNode)statementList.GetLeftChild();
        var type = declaration.GetTypeChild();
        var identifier = declaration.GetIdentifierChild();
        assertThat(type.GetValue()).isEqualTo(Type.Boolean);
        assertThat(identifier.GetValue()).isEqualTo("world");

        var booleanLiteral = (BooleanLiteralNode)declaration.GetExpressionChild();
        assertThat(booleanLiteral.GetValue()).isEqualTo(true);
    }

    private StatementListNode Parse(String input) throws Exception {
        Yylex lexer = new Yylex(new StringReader(input));
        parser parser = new parser(lexer);
        return (StatementListNode) parser.debug_parse().value;
    }
}
