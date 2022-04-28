package org.flowsoft.flowg.tests;

import java_cup.runtime.ComplexSymbolFactory.Location;
import org.flowsoft.flowg.BigDecimalUtils;
import org.flowsoft.flowg.Point;
import org.flowsoft.flowg.Type;
import org.flowsoft.flowg.nodes.*;
import org.flowsoft.flowg.nodes.base.INode;
import org.flowsoft.flowg.nodes.controlflow.ReturnNode;
import org.flowsoft.flowg.nodes.functions.*;
import org.flowsoft.flowg.nodes.math.operators.*;
import org.flowsoft.flowg.symboltables.SymbolTable;
import org.flowsoft.flowg.visitors.CodeGeneratingVisitor;
import org.flowsoft.flowg.visitors.TypeCheckingVisitor;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class CodeGeneratingTests {
    private static final Path P = Path.of("");
    private static final Location N = new Location("test", 0, 0, 0);
    @DataPoints("number")
    public static final Thing<BigDecimal>[] NUMBER_PLUS_THING = new Thing[]{
            new Thing<>(
                    new BigDecimal("2"),
                    new PlusExpressionNode(
                            new NumberLiteralNode(new BigDecimal("1"), N, N),
                            new NumberLiteralNode(new BigDecimal("1"), N, N),
                            N, N
                    )),
            new Thing<>(
                    new BigDecimal("-2"),
                    new PlusExpressionNode(
                            new NumberLiteralNode(new BigDecimal("-1"), N, N),
                            new NumberLiteralNode(new BigDecimal("-1"), N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    new BigDecimal("1"),
                    new PlusExpressionNode(
                            new NumberLiteralNode(new BigDecimal("0.5"), N, N),
                            new NumberLiteralNode(new BigDecimal("0.5"), N, N),
                            N, N
                    )
            )
    };
    @DataPoints("point")
    public static final Thing<Point>[] POINT_PLUS_THING = new Thing[]{
            new Thing<>(
                    new Point(new BigDecimal("1"), new BigDecimal("2"), new BigDecimal("3")),
                    new PlusExpressionNode(
                            new PointNode(
                                    new NumberLiteralNode(new BigDecimal("1"), N, N),
                                    new NumberLiteralNode(new BigDecimal("1"), N, N),
                                    new NumberLiteralNode(new BigDecimal("1"), N, N),
                                    N, N
                            ),
                            new PointNode(
                                    new NumberLiteralNode(new BigDecimal("0"), N, N),
                                    new NumberLiteralNode(new BigDecimal("1"), N, N),
                                    new NumberLiteralNode(new BigDecimal("2"), N, N),
                                    N, N
                            ),
                            N, N
                    )
            )
    };
    @DataPoints("number")
    public static final Thing<BigDecimal>[] NUMBER_MINUS_THING = new Thing[]{
            new Thing<>(
                    new BigDecimal("0"),
                    new MinusExpressionNode(
                            new NumberLiteralNode(new BigDecimal("1"), N, N),
                            new NumberLiteralNode(new BigDecimal("1"), N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    new BigDecimal("2"),
                    new MinusExpressionNode(
                            new NumberLiteralNode(new BigDecimal("1"), N, N),
                            new NumberLiteralNode(new BigDecimal("-1"), N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    new BigDecimal("0"),
                    new ArithmeticNegationExpressionNode(
                            new NumberLiteralNode(new BigDecimal("0"), N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    new BigDecimal("-1"),
                    new ArithmeticNegationExpressionNode(
                            new NumberLiteralNode(new BigDecimal("1"), N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    new BigDecimal("1"),
                    new ArithmeticNegationExpressionNode(
                            new NumberLiteralNode(new BigDecimal("-1"), N, N),
                            N, N
                    )
            )
    };
    @DataPoints("point")
    public static final Thing<Point>[] POINT_MINUS_THING = new Thing[]{
            new Thing<>(
                    new Point(new BigDecimal("1"), new BigDecimal("1"), new BigDecimal("1")),
                    new MinusExpressionNode(
                            new PointNode(
                                    new NumberLiteralNode(new BigDecimal("2"), N, N),
                                    new NumberLiteralNode(new BigDecimal("3"), N, N),
                                    new NumberLiteralNode(new BigDecimal("4"), N, N),
                                    N, N
                            ),
                            new PointNode(
                                    new NumberLiteralNode(new BigDecimal("1"), N, N),
                                    new NumberLiteralNode(new BigDecimal("2"), N, N),
                                    new NumberLiteralNode(new BigDecimal("3"), N, N),
                                    N, N
                            ),
                            N, N
                    )
            )
    };
    @DataPoints("number")
    public static final Thing<BigDecimal>[] NUMBER_TIMES_THING = new Thing[]{
            new Thing<>(
                    new BigDecimal("6"),
                    new TimesExpressionNode(
                            new NumberLiteralNode(new BigDecimal("2"), N, N),
                            new NumberLiteralNode(new BigDecimal("3"), N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    new BigDecimal("2.5"),
                    new TimesExpressionNode(
                            new NumberLiteralNode(new BigDecimal("5"), N, N),
                            new NumberLiteralNode(new BigDecimal("0.5"), N, N),
                            N, N
                    )
            )
    };
    @DataPoints("point")
    public static final Thing<Point>[] POINT_TIMES_THING = new Thing[]{
            new Thing<>(
                    new Point(new BigDecimal("2"), new BigDecimal("4"), new BigDecimal("6")),
                    new TimesExpressionNode(
                            new PointNode(
                                    new NumberLiteralNode(new BigDecimal("1"), N, N),
                                    new NumberLiteralNode(new BigDecimal("2"), N, N),
                                    new NumberLiteralNode(new BigDecimal("3"), N, N),
                                    N, N
                            ),
                            new NumberLiteralNode(new BigDecimal("2"), N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    new Point(new BigDecimal("1"), new BigDecimal("2"), new BigDecimal("3")),
                    new TimesExpressionNode(
                            new PointNode(
                                    new NumberLiteralNode(new BigDecimal("2"), N, N),
                                    new NumberLiteralNode(new BigDecimal("4"), N, N),
                                    new NumberLiteralNode(new BigDecimal("6"), N, N),
                                    N, N
                            ),
                            new NumberLiteralNode(new BigDecimal("0.5"), N, N),
                            N, N
                    )
            )
    };
    @DataPoints("number")
    public static final Thing<BigDecimal>[] NUMBER_DIVIDE_THING = new Thing[]{
            new Thing<>(
                    new BigDecimal("1"),
                    new DivideExpressionNode(
                            new NumberLiteralNode(new BigDecimal("1"), N, N),
                            new NumberLiteralNode(new BigDecimal("1"), N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    new BigDecimal("0.5"),
                    new DivideExpressionNode(
                            new NumberLiteralNode(new BigDecimal("1"), N, N),
                            new NumberLiteralNode(new BigDecimal("2"), N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    BigDecimalUtils.Divide(new BigDecimal("1"), new BigDecimal("3")),
                    new DivideExpressionNode(
                            new NumberLiteralNode(new BigDecimal("1"), N, N),
                            new NumberLiteralNode(new BigDecimal("3"), N, N),
                            N, N
                    )
            )
    };
    @DataPoints("point")
    public static final Thing<Point>[] POINT_DIVIDE_THING = new Thing[]{
            new Thing<>(
                    new Point(new BigDecimal("1"), new BigDecimal("2"), new BigDecimal("3")),
                    new DivideExpressionNode(
                            new PointNode(
                                    new NumberLiteralNode(new BigDecimal("2"), N, N),
                                    new NumberLiteralNode(new BigDecimal("4"), N, N),
                                    new NumberLiteralNode(new BigDecimal("6"), N, N),
                                    N, N
                            ),
                            new NumberLiteralNode(new BigDecimal("2"), N, N),
                            N, N
                    )
            )
    };
    @DataPoints("bool")
    public static final Thing<Boolean>[] BOOL_EXPR_THING = new Thing[]{
            new Thing<>(
                    true,
                    new GreaterThanExpressionNode(
                            new NumberLiteralNode(new BigDecimal("1"), N, N),
                            new NumberLiteralNode(new BigDecimal("0"), N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    false,
                    new GreaterThanExpressionNode(
                            new NumberLiteralNode(new BigDecimal("0"), N, N),
                            new NumberLiteralNode(new BigDecimal("1"), N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    true,
                    new LessThanExpressionNode(
                            new NumberLiteralNode(new BigDecimal("0"), N, N),
                            new NumberLiteralNode(new BigDecimal("1"), N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    false,
                    new LessThanExpressionNode(
                            new NumberLiteralNode(new BigDecimal("1"), N, N),
                            new NumberLiteralNode(new BigDecimal("0"), N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    true,
                    new GreaterThanEqualsExpressionNode(
                            new NumberLiteralNode(new BigDecimal("0"), N, N),
                            new NumberLiteralNode(new BigDecimal("0"), N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    true,
                    new GreaterThanEqualsExpressionNode(
                            new NumberLiteralNode(new BigDecimal('1'), N, N),
                            new NumberLiteralNode(new BigDecimal('0'), N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    false,
                    new GreaterThanEqualsExpressionNode(
                            new NumberLiteralNode(new BigDecimal('0'), N, N),
                            new NumberLiteralNode(new BigDecimal('1'), N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    true,
                    new LessThanEqualsExpressionNode(
                            new NumberLiteralNode(new BigDecimal('0'), N, N),
                            new NumberLiteralNode(new BigDecimal('0'), N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    true,
                    new LessThanEqualsExpressionNode(
                            new NumberLiteralNode(new BigDecimal('0'), N, N),
                            new NumberLiteralNode(new BigDecimal('1'), N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    false,
                    new LessThanEqualsExpressionNode(
                            new NumberLiteralNode(new BigDecimal('1'), N, N),
                            new NumberLiteralNode(new BigDecimal('0'), N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    true,
                    new AndExpressionNode(
                            new BooleanLiteralNode(true, N, N),
                            new BooleanLiteralNode(true, N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    false,
                    new AndExpressionNode(
                            new BooleanLiteralNode(false, N, N),
                            new BooleanLiteralNode(true, N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    false,
                    new AndExpressionNode(
                            new BooleanLiteralNode(true, N, N),
                            new BooleanLiteralNode(false, N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    true,
                    new OrExpressionNode(
                            new BooleanLiteralNode(true, N, N),
                            new BooleanLiteralNode(true, N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    false,
                    new OrExpressionNode(
                            new BooleanLiteralNode(false, N, N),
                            new BooleanLiteralNode(false, N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    true,
                    new OrExpressionNode(
                            new BooleanLiteralNode(false, N, N),
                            new BooleanLiteralNode(true, N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    true,
                    new OrExpressionNode(
                            new BooleanLiteralNode(true, N, N),
                            new BooleanLiteralNode(false, N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    true,
                    new EqualsExpressionNode(
                            new NumberLiteralNode(new BigDecimal("1"), N, N),
                            new NumberLiteralNode(new BigDecimal("1"), N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    true,
                    new EqualsExpressionNode(
                            new NumberLiteralNode(new BigDecimal("0"), N, N),
                            new NumberLiteralNode(new BigDecimal("0"), N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    true,
                    new EqualsExpressionNode(
                            new NumberLiteralNode(new BigDecimal("-1"), N, N),
                            new NumberLiteralNode(new BigDecimal("-1"), N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    false,
                    new EqualsExpressionNode(
                            new NumberLiteralNode(new BigDecimal("1"), N, N),
                            new NumberLiteralNode(new BigDecimal("0"), N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    false,
                    new EqualsExpressionNode(
                            new NumberLiteralNode(new BigDecimal("0"), N, N),
                            new NumberLiteralNode(new BigDecimal("1"), N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    false,
                    new EqualsExpressionNode(
                            new NumberLiteralNode(new BigDecimal("-1"), N, N),
                            new NumberLiteralNode(new BigDecimal("1"), N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    false,
                    new EqualsExpressionNode(
                            new NumberLiteralNode(new BigDecimal("1"), N, N),
                            new NumberLiteralNode(new BigDecimal("-1"), N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    true,
                    new EqualsExpressionNode(
                            new PointNode(
                                    new NumberLiteralNode(new BigDecimal("1"), N, N),
                                    new NumberLiteralNode(new BigDecimal("2"), N, N),
                                    new NumberLiteralNode(new BigDecimal("3"), N, N),
                                    N, N
                            ),
                            new PointNode(
                                    new NumberLiteralNode(new BigDecimal("1"), N, N),
                                    new NumberLiteralNode(new BigDecimal("2"), N, N),
                                    new NumberLiteralNode(new BigDecimal("3"), N, N),
                                    N, N
                            ),
                            N, N
                    )
            ),
            new Thing<>(
                    false,
                    new EqualsExpressionNode(
                            new PointNode(
                                    new NumberLiteralNode(new BigDecimal("1"), N, N),
                                    new NumberLiteralNode(new BigDecimal("2"), N, N),
                                    new NumberLiteralNode(new BigDecimal("3"), N, N),
                                    N, N),
                            new PointNode(
                                    new NumberLiteralNode(new BigDecimal("3"), N, N),
                                    new NumberLiteralNode(new BigDecimal("2"), N, N),
                                    new NumberLiteralNode(new BigDecimal("1"), N, N),
                                    N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    true,
                    new EqualsExpressionNode(
                            new BooleanLiteralNode(true, N, N),
                            new BooleanLiteralNode(true, N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    true,
                    new EqualsExpressionNode(
                            new BooleanLiteralNode(false, N, N),
                            new BooleanLiteralNode(false, N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    false,
                    new EqualsExpressionNode(
                            new BooleanLiteralNode(false, N, N),
                            new BooleanLiteralNode(true, N, N),
                            N, N
                    )
            ),
            new Thing<>(
                    false,
                    new EqualsExpressionNode(
                            new BooleanLiteralNode(true, N, N),
                            new BooleanLiteralNode(false, N, N),
                            N, N
                    )
            ),
    };

    @Theory
    public void TestExpressionNumberSuccess(@FromDataPoints("number") Thing<BigDecimal> thing) throws Exception {
        var node = thing.GetNode();
        var expected = thing.GetExpected();

        var value = node.Accept(new CodeGeneratingVisitor(new SymbolTable(null), P));
        var actual = value.GetNumber();

        assertThat(value.GetType()).isEqualTo(Type.Number);
        assertTrue(BigDecimalUtils.Equals(actual, expected));
    }

    @Theory
    public void TestExpressionPointSuccess(@FromDataPoints("point") Thing<Point> thing) throws Exception {
        var node = thing.GetNode();
        var expected = thing.GetExpected();

        var value = node.Accept(new CodeGeneratingVisitor(new SymbolTable(null), P));
        var actual = value.GetPoint();

        assertThat(value.GetType()).isEqualTo(Type.Point);
        assertThat(actual).isEqualTo(expected);
    }

    @Theory
    public void TestExpressionBooleanSuccess(@FromDataPoints("bool") Thing<Boolean> thing) throws Exception {
        var node = thing.GetNode();
        var expected = thing.GetExpected();

        var value = node.Accept(new CodeGeneratingVisitor(new SymbolTable(null), P));
        var actual = value.GetBoolean();

        assertThat(value.GetType()).isEqualTo(Type.Boolean);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void TestNumberAssignmentSuccess() throws Exception {
        var node = new StatementListNode(new ArrayList<>() {
            {
                add(new DeclarationNode(new TypeNode(Type.Number, N, N), new IdentifierNode("x", N, N), new NumberLiteralNode(new BigDecimal("2"), N, N), N, N));
                add(new AssignmentNode(new IdentifierNode("x", N, N), new NumberLiteralNode(new BigDecimal("4"), N, N), N, N));
            }
        }, N, N);

        var typeChecker = new TypeCheckingVisitor(Path.of(""));
        node.Accept(typeChecker);

        var codeGen = new CodeGeneratingVisitor(typeChecker.GetSymbolTable(), P);
        node.Accept(codeGen);


        var symbolTable = codeGen.GetSymbolTable();

        assertTrue(BigDecimalUtils.Equals(symbolTable.LookupVariable("x").GetNumber(), new BigDecimal("4")));
    }

    @Test
    public void TestFunctionCall() throws Exception {
        var node = new StatementListNode(new ArrayList<>() {
            {
                add(new FunctionDefinitionNode(
                        new TypeNode(Type.Void, N, N),
                        new IdentifierNode("test", N, N),
                        new FormalParameterListNode(
                                new ArrayList<>() {
                                    {
                                        add(new FormalParameterNode(new TypeNode(Type.Number, N, N), new IdentifierNode("x", N, N), N, N));
                                        add(new FormalParameterNode(new TypeNode(Type.Number, N, N), new IdentifierNode("y", N, N), N, N));
                                        add(new FormalParameterNode(new TypeNode(Type.Number, N, N), new IdentifierNode("z", N, N), N, N));
                                    }
                                }, N, N
                        ),
                        new StatementListNode(
                                new ArrayList<>() {
                                    {
                                        add(new MoveNode(
                                                new ActualParameterListNode(
                                                        new ArrayList<>() {
                                                            {
                                                                add(new PointNode(
                                                                        new PlusExpressionNode(new IdentifierExpressionNode(new IdentifierNode("x", N, N), N, N), new NumberLiteralNode(new BigDecimal("1"), N, N), N, N),
                                                                        new PlusExpressionNode(new IdentifierExpressionNode(new IdentifierNode("y", N, N), N, N), new NumberLiteralNode(new BigDecimal("2"), N, N), N, N),
                                                                        new PlusExpressionNode(new IdentifierExpressionNode(new IdentifierNode("z", N, N), N, N), new NumberLiteralNode(new BigDecimal("3"), N, N), N, N), N, N
                                                                ));
                                                            }
                                                        }, N, N
                                                ), N, N
                                        ));
                                    }
                                }, N, N
                        ), N, N
                ));

                add(new FunctionCallNode(
                        new IdentifierNode("test", N, N),
                        new ActualParameterListNode(
                                new ArrayList<>() {
                                    {
                                        add(new NumberLiteralNode(new BigDecimal("1"), N, N));
                                        add(new NumberLiteralNode(new BigDecimal("2"), N, N));
                                        add(new NumberLiteralNode(new BigDecimal("3"), N, N));
                                    }
                                }, N, N
                        ), N, N));
            }
        }, N, N);

        var typeChecker = new TypeCheckingVisitor(Path.of(""));
        node.Accept(typeChecker);

        var codeGen = new CodeGeneratingVisitor(typeChecker.GetSymbolTable(), P);
        node.Accept(codeGen);


        var symbolTable = typeChecker.GetSymbolTable();

        // Lookups throw if the symbol does not exist.
        var funcSymbolTable = symbolTable.LookupFunction("test", N, N).GetSymbolTable();
        funcSymbolTable.LookupVariable("x", N, N);
        funcSymbolTable.LookupVariable("y", N, N);
        funcSymbolTable.LookupVariable("z", N, N);

        assertThat(codeGen.GetCode()).isEqualTo("G0 X2 Y4 Z6\n");
    }

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
}
