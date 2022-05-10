package org.flowsoft.flowg.tests;

import java_cup.runtime.ComplexSymbolFactory;
import org.flowsoft.flowg.Type;
import org.flowsoft.flowg.Yylex;
import org.flowsoft.flowg.nodes.*;
import org.flowsoft.flowg.nodes.base.INode;
import org.flowsoft.flowg.nodes.functions.*;
import org.flowsoft.flowg.nodes.math.operators.PlusExpressionNode;
import org.flowsoft.flowg.parser;
import org.flowsoft.flowg.visitors.PrettyPrintingVisitor;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

@RunWith(Theories.class)
public class ParserTests {

    @DataPoints
    public static final String[] INVALID_SYNTAX = {
            "number x = y = 2;",
            "number number = 2"
    };
    private static final ComplexSymbolFactory.Location N = new ComplexSymbolFactory.Location("test", 0, 0, 0);
    @DataPoints
    public static final TextAstPair[] TEXT_AST_PAIRS = {
            new TextAstPair(
                    "number hello = 2;",
                    new StatementListNode(
                            new ArrayList<>() {
                                {
                                    add(new DeclarationNode(new TypeNode(Type.Number, N, N), new IdentifierNode("hello", N, N), new NumberLiteralNode(new BigDecimal(2), N, N), N, N));
                                }
                            }, N, N
                    )
            ),
            new TextAstPair(
                    "bool world = true;",
                    new StatementListNode(
                            new ArrayList<>() {
                                {
                                    add(new DeclarationNode(new TypeNode(Type.Boolean, N, N), new IdentifierNode("world", N, N), new BooleanLiteralNode(true, N, N), N, N));
                                }
                            }, N, N
                    )
            ),
            new TextAstPair(
                    "number a = 1 + 2;",
                    new StatementListNode(
                            new ArrayList<>() {
                                {
                                    add(
                                            new DeclarationNode(
                                                    new TypeNode(Type.Number, N, N),
                                                    new IdentifierNode("a", N, N),
                                                    new PlusExpressionNode(new NumberLiteralNode(new BigDecimal(1), N, N), new NumberLiteralNode(new BigDecimal(2), N, N), N, N), N, N)
                                    );
                                }
                            }, N, N
                    )
            ),
            /* COmmented out because move does not exist
            new TextAstPair(
                    "move(1, 2, 3);",
                    new StatementListNode(
                            new ArrayList<>() {
                                {
                                    add(new MoveNode(
                                            new ActualParameterListNode(
                                                    new ArrayList<>() {
                                                        {
                                                            add(new NumberLiteralNode(new BigDecimal(1), N, N));
                                                            add(new NumberLiteralNode(new BigDecimal(2), N, N));
                                                            add(new NumberLiteralNode(new BigDecimal(3), N, N));
                                                        }
                                                    }, N, N
                                            ), N, N
                                    ));
                                }
                            }, N, N
                    )
            ),
            */

            new TextAstPair(
                    "number x = 2;\n" +
                            "number y = x + 2;",
                    new StatementListNode(
                            new ArrayList<>() {
                                {
                                    add(new DeclarationNode(new TypeNode(Type.Number, N, N), new IdentifierNode("x", N, N), new NumberLiteralNode(new BigDecimal(2), N, N), N, N));

                                    add(new DeclarationNode(new TypeNode(Type.Number, N, N), new IdentifierNode("y", N, N),
                                            new PlusExpressionNode(
                                                    new IdentifierExpressionNode(
                                                            new IdentifierNode("x", N, N), N, N
                                                    ),
                                                    new NumberLiteralNode(new BigDecimal(2), N, N), N, N
                                            ), N, N
                                    ));
                                }
                            }, N, N
                    )
            ),
            new TextAstPair(
                    """
                            void test(number x, number y) {
                               number z = x + y;
                            };
                            test(1,2);""",
                    new StatementListNode(
                            new ArrayList<>() {
                                {
                                    add(new FunctionDefinitionNode(
                                            new TypeNode(Type.Void, N, N),
                                            new IdentifierNode("test", N, N),
                                            new FormalParameterListNode(
                                                    new ArrayList<>() {
                                                        {
                                                            add(new FormalParameterNode(new TypeNode(Type.Number, N, N), new IdentifierNode("x", N, N), N, N));
                                                            add(new FormalParameterNode(new TypeNode(Type.Number, N, N), new IdentifierNode("y", N, N), N, N));
                                                        }
                                                    }, N, N
                                            ),
                                            new StatementListNode(new ArrayList<>() {
                                                {
                                                    add(new DeclarationNode(
                                                            new TypeNode(Type.Number, N, N),
                                                            new IdentifierNode("z", N, N),
                                                            new PlusExpressionNode(
                                                                    new IdentifierExpressionNode(new IdentifierNode("x", N, N), N, N),
                                                                    new IdentifierExpressionNode(new IdentifierNode("y", N, N), N, N), N, N
                                                            ), N, N));
                                                }
                                            }, N, N), N, N
                                    ));

                                    add(new FunctionCallNode(
                                            new IdentifierNode("test", N, N),
                                            new ActualParameterListNode(
                                                    new ArrayList<>() {
                                                        {
                                                            add(new NumberLiteralNode(new BigDecimal("1"), N, N));
                                                            add(new NumberLiteralNode(new BigDecimal("2"), N, N));
                                                        }
                                                    }, N, N
                                            ), N, N));
                                }
                            }, N, N
                    )
            )
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
        Yylex lexer = new Yylex(new StringReader(input), "test");
        parser parser = new parser(lexer, new ComplexSymbolFactory());
        return (StatementListNode) parser.parse().value;
    }

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
}
