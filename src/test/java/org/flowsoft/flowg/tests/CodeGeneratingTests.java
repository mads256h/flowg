package org.flowsoft.flowg.tests;

import org.flowsoft.flowg.BigDecimalUtils;
import org.flowsoft.flowg.Type;
import org.flowsoft.flowg.nodes.*;
import org.flowsoft.flowg.visitors.*;
import org.junit.Test;
import org.junit.experimental.theories.*;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.util.ArrayList;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.*;

@RunWith(Theories.class)
public class CodeGeneratingTests {
    private static class Thing<T> {
        private final T _expected;
        private final INode _node;

        public Thing(T expected, INode node) {
            _expected = expected;
            _node = node;
        }

        public T GetExpected() {
            return _expected;
        }

        public INode GetNode() {
            return _node;
        }
    }

    @DataPoints("number")
    public static final Thing<BigDecimal>[] NUMBER_PLUS_THING = new Thing[]{
            new Thing<>(
                    new BigDecimal("2"),
                    new PlusExpressionNode(
                            new NumberLiteralNode(new BigDecimal("1")),
                            new NumberLiteralNode(new BigDecimal("1"))
                    )),
            new Thing<>(
                    new BigDecimal("-2"),
                    new PlusExpressionNode(
                            new NumberLiteralNode(new BigDecimal("-1")),
                            new NumberLiteralNode(new BigDecimal("-1"))
                    )
            ),
            new Thing<>(
                    new BigDecimal("1"),
                    new PlusExpressionNode(
                            new NumberLiteralNode(new BigDecimal("0.5")),
                            new NumberLiteralNode(new BigDecimal("0.5"))
                    )
            )
    };

    @DataPoints("point")
    public static final Thing<Point>[] POINT_PLUS_THING = new Thing[]{
            new Thing<>(
                    new Point(new BigDecimal("1"), new BigDecimal("2"), new BigDecimal("3")),
                    new PlusExpressionNode(
                            new PointNode(
                                    new NumberLiteralNode(new BigDecimal("1")),
                                    new NumberLiteralNode(new BigDecimal("1")),
                                    new NumberLiteralNode(new BigDecimal("1"))
                            ),
                            new PointNode(
                                    new NumberLiteralNode(new BigDecimal("0")),
                                    new NumberLiteralNode(new BigDecimal("1")),
                                    new NumberLiteralNode(new BigDecimal("2"))
                            )
                    )
            )
    };

    @DataPoints("number")
    public static final Thing<BigDecimal>[] NUMBER_MINUS_THING = new Thing[] {
            new Thing<>(
                    new BigDecimal("0"),
                    new MinusExpressionNode(
                            new NumberLiteralNode(new BigDecimal("1")),
                            new NumberLiteralNode(new BigDecimal("1"))
                    )
            ),
            new Thing<>(
                    new BigDecimal("2"),
                    new MinusExpressionNode(
                            new NumberLiteralNode(new BigDecimal("1")),
                            new NumberLiteralNode(new BigDecimal("-1"))
                    )
            )
    };

    @DataPoints("point")
    public static final Thing<Point>[] POINT_MINUS_THING = new Thing[] {
            new Thing<>(
                    new Point(new BigDecimal("1"), new BigDecimal("1"), new BigDecimal("1")),
                    new MinusExpressionNode(
                            new PointNode(
                                    new NumberLiteralNode(new BigDecimal("2")),
                                    new NumberLiteralNode(new BigDecimal("3")),
                                    new NumberLiteralNode(new BigDecimal("4"))
                            ),
                            new PointNode(
                                    new NumberLiteralNode(new BigDecimal("1")),
                                    new NumberLiteralNode(new BigDecimal("2")),
                                    new NumberLiteralNode(new BigDecimal("3"))
                            )
                    )
            )
    };

    @DataPoints("number")
    public static final Thing<BigDecimal>[] NUMBER_TIMES_THING = new Thing[] {
            new Thing<>(
                    new BigDecimal("6"),
                    new TimesExpressionNode(
                            new NumberLiteralNode(new BigDecimal("2")),
                            new NumberLiteralNode(new BigDecimal("3"))
                    )
            ),
            new Thing<>(
                    new BigDecimal("2.5"),
                    new TimesExpressionNode(
                            new NumberLiteralNode(new BigDecimal("5")),
                            new NumberLiteralNode(new BigDecimal("0.5"))
                    )
            )
    };

    @DataPoints("point")
    public static final Thing<Point>[] POINT_TIMES_THING = new Thing[] {
            new Thing<>(
                    new Point(new BigDecimal("2"), new BigDecimal("4"), new BigDecimal("6")),
                    new TimesExpressionNode(
                            new PointNode(
                                    new NumberLiteralNode(new BigDecimal("1")),
                                    new NumberLiteralNode(new BigDecimal("2")),
                                    new NumberLiteralNode(new BigDecimal("3"))
                            ),
                            new NumberLiteralNode(new BigDecimal("2"))
                    )
            ),
            new Thing<>(
                    new Point(new BigDecimal("1"), new BigDecimal("2"), new BigDecimal("3")),
                    new TimesExpressionNode(
                            new PointNode(
                                    new NumberLiteralNode(new BigDecimal("2")),
                                    new NumberLiteralNode(new BigDecimal("4")),
                                    new NumberLiteralNode(new BigDecimal("6"))
                            ),
                            new NumberLiteralNode(new BigDecimal("0.5"))
                    )
            )
    };

    @DataPoints("number")
    public static final Thing<BigDecimal>[] NUMBER_DIVIDE_THING = new Thing[] {
            new Thing<>(
                    new BigDecimal("1"),
                    new DivideExpressionNode(
                            new NumberLiteralNode(new BigDecimal("1")),
                            new NumberLiteralNode(new BigDecimal("1"))
                    )
            ),
            new Thing<>(
                    new BigDecimal("0.5"),
                    new DivideExpressionNode(
                            new NumberLiteralNode(new BigDecimal("1")),
                            new NumberLiteralNode(new BigDecimal("2"))
                    )
            ),
            new Thing<>(
                    BigDecimalUtils.Divide(new BigDecimal("1"), new BigDecimal("3")),
                    new DivideExpressionNode(
                            new NumberLiteralNode(new BigDecimal("1")),
                            new NumberLiteralNode(new BigDecimal("3"))
                    )
            )
    };

    @DataPoints("point")
    public static final Thing<Point>[] POINT_DIVIDE_THING = new Thing[] {
            new Thing<>(
                    new Point(new BigDecimal("1"), new BigDecimal("2"), new BigDecimal("3")),
                    new DivideExpressionNode(
                            new PointNode(
                                    new NumberLiteralNode(new BigDecimal("2")),
                                    new NumberLiteralNode(new BigDecimal("4")),
                                    new NumberLiteralNode(new BigDecimal("6"))
                            ),
                            new NumberLiteralNode(new BigDecimal("2"))
                    )
            )
    };

    @DataPoints("bool")
    public static final Thing<Boolean>[] BOOL_EXPR_THING = new Thing[] {
            new Thing<>(
                    true,
                    new GreaterThanExpressionNode(
                            new NumberLiteralNode(new BigDecimal("1")),
                            new NumberLiteralNode(new BigDecimal("0"))
                    )
            ),
            new Thing<>(
                    false,
                    new GreaterThanExpressionNode(
                            new NumberLiteralNode(new BigDecimal("0")),
                            new NumberLiteralNode(new BigDecimal("1"))
                    )
            ),
            new Thing<>(
                    true,
                    new LessThanExpressionNode(
                            new NumberLiteralNode(new BigDecimal("0")),
                            new NumberLiteralNode(new BigDecimal("1"))
                    )
            ),
            new Thing<>(
                    false,
                    new LessThanExpressionNode(
                            new NumberLiteralNode(new BigDecimal("1")),
                            new NumberLiteralNode(new BigDecimal("0"))
                    )
            ),
            new Thing<>(
                    true,
                    new GreaterThanEqualsExpressionNode(
                            new NumberLiteralNode(new BigDecimal("0")),
                            new NumberLiteralNode(new BigDecimal("0"))
                    )
            ),
            new Thing<>(
                    true,
                    new GreaterThanEqualsExpressionNode(
                            new NumberLiteralNode(new BigDecimal('1')),
                            new NumberLiteralNode(new BigDecimal('0'))
                    )
            ),
            new Thing<>(
                    false,
                    new GreaterThanEqualsExpressionNode(
                            new NumberLiteralNode(new BigDecimal('0')),
                            new NumberLiteralNode(new BigDecimal('1'))
                    )
            ),
            new Thing<>(
                    true,
                    new LessThanEqualsExpressionNode(
                            new NumberLiteralNode(new BigDecimal('0')),
                            new NumberLiteralNode(new BigDecimal('0'))
                    )
            ),
            new Thing<>(
                    true,
                    new LessThanEqualsExpressionNode(
                            new NumberLiteralNode(new BigDecimal('0')),
                            new NumberLiteralNode(new BigDecimal('1'))
                    )
            ),
            new Thing<>(
                    false,
                    new LessThanEqualsExpressionNode(
                            new NumberLiteralNode(new BigDecimal('1')),
                            new NumberLiteralNode(new BigDecimal('0'))
                    )
            ),
            new Thing<>(
                    true,
                    new AndExpressionNode(
                            new BooleanLiteralNode(true),
                            new BooleanLiteralNode(true)
                    )
            ),
            new Thing<>(
                    false,
                    new AndExpressionNode(
                            new BooleanLiteralNode(false),
                            new BooleanLiteralNode(true)
                    )
            ),
            new Thing<>(
                    false,
                    new AndExpressionNode(
                            new BooleanLiteralNode(true),
                            new BooleanLiteralNode(false)
                    )
            ),
            new Thing<>(
                    true,
                    new OrExpressionNode(
                            new BooleanLiteralNode(true),
                            new BooleanLiteralNode(true)
                    )
            ),
            new Thing<>(
                    false,
                    new OrExpressionNode(
                            new BooleanLiteralNode(false),
                            new BooleanLiteralNode(false)
                    )
            ),
            new Thing<>(
                    true,
                    new OrExpressionNode(
                            new BooleanLiteralNode(false),
                            new BooleanLiteralNode(true)
                    )
            ),
            new Thing<>(
                    true,
                    new OrExpressionNode(
                            new BooleanLiteralNode(true),
                            new BooleanLiteralNode(false)
                    )
            ),
            new Thing<>(
                    true,
                    new EqualsExpressionNode(
                            new NumberLiteralNode(new BigDecimal("1")),
                            new NumberLiteralNode(new BigDecimal("1"))
                    )
            ),
            new Thing<>(
                    true,
                    new EqualsExpressionNode(
                            new NumberLiteralNode(new BigDecimal("0")),
                            new NumberLiteralNode(new BigDecimal("0"))
                    )
            ),
            new Thing<>(
                    true,
                    new EqualsExpressionNode(
                            new NumberLiteralNode(new BigDecimal("-1")),
                            new NumberLiteralNode(new BigDecimal("-1"))
                    )
            ),
            new Thing<>(
                    false,
                    new EqualsExpressionNode(
                            new NumberLiteralNode(new BigDecimal("1")),
                            new NumberLiteralNode(new BigDecimal("0"))
                    )
            ),
            new Thing<>(
                    false,
                    new EqualsExpressionNode(
                            new NumberLiteralNode(new BigDecimal("0")),
                            new NumberLiteralNode(new BigDecimal("1"))
                    )
            ),
            new Thing<>(
                    false,
                    new EqualsExpressionNode(
                            new NumberLiteralNode(new BigDecimal("-1")),
                            new NumberLiteralNode(new BigDecimal("1"))
                    )
            ),
            new Thing<>(
                    false,
                    new EqualsExpressionNode(
                            new NumberLiteralNode(new BigDecimal("1")),
                            new NumberLiteralNode(new BigDecimal("-1"))
                    )
            ),
            new Thing<>(
                    true,
                    new EqualsExpressionNode(
                            new PointNode(
                                    new NumberLiteralNode(new BigDecimal("1")),
                                    new NumberLiteralNode(new BigDecimal("2")),
                                    new NumberLiteralNode(new BigDecimal("3"))),
                            new PointNode(
                                    new NumberLiteralNode(new BigDecimal("1")),
                                    new NumberLiteralNode(new BigDecimal("2")),
                                    new NumberLiteralNode(new BigDecimal("3")))
                    )
            ),
            new Thing<>(
                    false,
                    new EqualsExpressionNode(
                            new PointNode(
                                    new NumberLiteralNode(new BigDecimal("1")),
                                    new NumberLiteralNode(new BigDecimal("2")),
                                    new NumberLiteralNode(new BigDecimal("3"))),
                            new PointNode(
                                    new NumberLiteralNode(new BigDecimal("3")),
                                    new NumberLiteralNode(new BigDecimal("2")),
                                    new NumberLiteralNode(new BigDecimal("1")))
                    )
            ),
            new Thing<>(
                    true,
                    new EqualsExpressionNode(
                            new BooleanLiteralNode(true),
                            new BooleanLiteralNode(true)
                    )
            ),
            new Thing<>(
                    true,
                    new EqualsExpressionNode(
                            new BooleanLiteralNode(false),
                            new BooleanLiteralNode(false)
                    )
            ),
            new Thing<>(
                    false,
                    new EqualsExpressionNode(
                            new BooleanLiteralNode(false),
                            new BooleanLiteralNode(true)
                    )
            ),
            new Thing<>(
                    false,
                    new EqualsExpressionNode(
                            new BooleanLiteralNode(true),
                            new BooleanLiteralNode(false)
                    )
            ),
    };

    @Theory
    public void TestExpressionNumberSuccess(@FromDataPoints("number") Thing<BigDecimal> thing) throws Exception {
        var node = thing.GetNode();
        var expected = thing.GetExpected();

        var value = node.Accept(new CodeGeneratingVisitor(new SymbolTable(null)));
        var actual = value.GetNumber();

        assertThat(value.GetType()).isEqualTo(Type.Number);
        assertTrue(actual.compareTo(expected) == 0);
    }

    @Theory
    public void TestExpressionPointSuccess(@FromDataPoints("point") Thing<Point> thing) throws Exception {
        var node = thing.GetNode();
        var expected = thing.GetExpected();

        var value = node.Accept(new CodeGeneratingVisitor(new SymbolTable(null)));
        var actual = value.GetPoint();

        assertThat(value.GetType()).isEqualTo(Type.Point);
        assertThat(actual).isEqualTo(expected);
    }

    @Theory
    public void TestExpressionBooleanSuccess(@FromDataPoints("bool") Thing<Boolean> thing) throws Exception {
        var node = thing.GetNode();
        var expected = thing.GetExpected();

        var value = node.Accept(new CodeGeneratingVisitor(new SymbolTable(null)));
        var actual = value.GetBoolean();

        assertThat(value.GetType()).isEqualTo(Type.Boolean);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void TestNumberAssignmentSuccess() throws Exception {
        var node = new StatementListNode(new ArrayList<>() {
            {
                add(new DeclarationNode(new TypeNode(Type.Number), new IdentifierNode("x"), new NumberLiteralNode(new BigDecimal("2"))));
                add(new AssignmentNode(new IdentifierNode("x"), new NumberLiteralNode(new BigDecimal("4"))));
            }
        });

        var typeChecker = new TypeCheckingVisitor();
        node.Accept(typeChecker);

        var codeGen = new CodeGeneratingVisitor(typeChecker.GetSymbolTable());
        node.Accept(codeGen);


        var symbolTable = codeGen.GetSymbolTable();

        assertTrue(symbolTable.LookupVariable("x").GetNumber().compareTo(new BigDecimal("4")) == 0);
    }

    @Test
    public void TestFunctionCall() throws Exception {
        var node = new StatementListNode(new ArrayList<>() {
            {
                add(new FunctionDefinitionNode(
                        new TypeNode(Type.Void),
                        new IdentifierNode("test"),
                        new FormalParameterListNode(
                                new ArrayList<>() {
                                    {
                                        add(new FormalParameterNode(new TypeNode(Type.Number), new IdentifierNode("x")));
                                        add(new FormalParameterNode(new TypeNode(Type.Number), new IdentifierNode("y")));
                                        add(new FormalParameterNode(new TypeNode(Type.Number), new IdentifierNode("z")));
                                    }
                                }
                        ),
                        new StatementListNode(
                                new ArrayList<>() {
                                    {
                                        add(new MoveNode(
                                                new ActualParameterListNode(
                                                        new ArrayList<>() {
                                                            {
                                                                add(new PointNode(
                                                                        new PlusExpressionNode(new IdentifierExpressionNode(new IdentifierNode("x")), new NumberLiteralNode(new BigDecimal("1"))),
                                                                        new PlusExpressionNode(new IdentifierExpressionNode(new IdentifierNode("y")), new NumberLiteralNode(new BigDecimal("2"))),
                                                                        new PlusExpressionNode(new IdentifierExpressionNode(new IdentifierNode("z")), new NumberLiteralNode(new BigDecimal("3")))
                                                                ));
                                                            }
                                                        }
                                                )
                                        ));
                                    }
                                }
                        )
                ));

                add(new FunctionCallNode(
                        new IdentifierNode("test"),
                        new ActualParameterListNode(
                                new ArrayList<>() {
                                    {
                                        add(new NumberLiteralNode(new BigDecimal("1")));
                                        add(new NumberLiteralNode(new BigDecimal("2")));
                                        add(new NumberLiteralNode(new BigDecimal("3")));
                                    }
                                }
                        )));
            }
        });

        var typeChecker = new TypeCheckingVisitor();
        node.Accept(typeChecker);

        var codeGen = new CodeGeneratingVisitor(typeChecker.GetSymbolTable());
        node.Accept(codeGen);


        var symbolTable = typeChecker.GetSymbolTable();

        assertThat(codeGen.GetCode()).isEqualTo("G0 X2 Y4 Z6\n");
    }
}
