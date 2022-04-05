package org.flowsoft.flowg.tests;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

import org.flowsoft.flowg.Type;
import org.flowsoft.flowg.Yylex;
import org.flowsoft.flowg.nodes.*;
import org.flowsoft.flowg.parser;
import org.flowsoft.flowg.visitors.PrettyPrintingVisitor;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;

@RunWith(Theories.class)
public class ParserTests {

    private static class TextAstPair {
        private final String _text;
        private final INode _astTree;

        public TextAstPair(String text, INode astTree) {
            _text = text;
            _astTree = astTree;
        }

        public String GetText() {
            return _text;
        }

        public INode GetAstTree() {
            return _astTree;
        }

        @Override
        public String toString() {
            return "TextAstPair{" +
                    "_text='" + _text + '\'' +
                    '}';
        }
    }

    @DataPoints
    public static final TextAstPair[] TEXT_AST_PAIRS = {
            new TextAstPair(
                    "number hello = 2;",
                    new StatementListNode(
                            new ArrayList<StatementNode>() {
                                {
                                    add(new DeclarationNode(new TypeNode(Type.Number), new IdentifierNode("hello"), new NumberLiteralNode(new BigDecimal(2))));
                                }
                            }
                    )
            ),
            new TextAstPair(
                    "bool world = true;",
                    new StatementListNode(
                            new ArrayList<>() {
                                {
                                    add(new DeclarationNode(new TypeNode(Type.Boolean), new IdentifierNode("world"), new BooleanLiteralNode(true)));
                                }
                            }
                    )
            ),
            new TextAstPair(
                    "number a = 1 + 2;",
                    new StatementListNode(
                            new ArrayList<>() {
                                {
                                    add(
                                            new DeclarationNode(
                                                    new TypeNode(Type.Number),
                                                    new IdentifierNode("a"),
                                                    new PlusExpressionNode(new NumberLiteralNode(new BigDecimal(1)), new NumberLiteralNode(new BigDecimal(2))))
                                    );
                                }
                            }
                    )
            ),
            new TextAstPair(
                    "move(1, 2, 3);",
                    new StatementListNode(
                            new ArrayList<>() {
                                {
                                    add(new MoveNode(
                                            new ActualParameterListNode(
                                                    new ArrayList<>() {
                                                        {
                                                            add(new NumberLiteralNode(new BigDecimal(1)));
                                                            add(new NumberLiteralNode(new BigDecimal(2)));
                                                            add(new NumberLiteralNode(new BigDecimal(3)));
                                                        }
                                                    }
                                            )
                                    ));
                                }
                            }
                    )
            )
    };

    @DataPoints
    public static final String[] INVALID_SYNTAX = {
            "number x = y = 2;",
            "number number = 2"
    };

    @Theory
    public void TestParseSuccess(TextAstPair pair) throws Exception {
            var ast = Parse(pair.GetText());
            // We are using pretty printing to assert that two ast trees are equal.
            // This probably not a good idea but I don't have a better one.
            var actualString = ast.Accept(new PrettyPrintingVisitor());
            var expectedString = pair.GetAstTree().Accept(new PrettyPrintingVisitor());
            assertThat(actualString).isEqualTo(expectedString);
    }

    @Theory
    public void TestParseFailure(String input) {
        assertThrows(Exception.class, () -> Parse(input));
    }


    private StatementListNode Parse(String input) throws Exception {
        Yylex lexer = new Yylex(new StringReader(input));
        parser parser = new parser(lexer);
        return (StatementListNode) parser.parse().value;
    }
}
