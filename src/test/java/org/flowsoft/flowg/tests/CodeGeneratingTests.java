package org.flowsoft.flowg.tests;

import org.flowsoft.flowg.BigDecimalUtils;
import org.flowsoft.flowg.Type;
import org.flowsoft.flowg.nodes.*;
import org.flowsoft.flowg.visitors.CodeGeneratingVisitor;
import org.flowsoft.flowg.visitors.Point;
import org.flowsoft.flowg.visitors.SymbolTable;
import org.junit.experimental.theories.*;
import org.junit.runner.RunWith;

import java.math.BigDecimal;

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

    @Theory
    public void TestExpressionNumberSuccess(@FromDataPoints("number") Thing<BigDecimal> thing) throws Exception {
        var node = thing.GetNode();
        var expected = thing.GetExpected();

        var value = node.Accept(new CodeGeneratingVisitor(new SymbolTable()));
        var actual = value.GetNumber();

        assertThat(value.GetType()).isEqualTo(Type.Number);
        assertTrue(actual.compareTo(expected) == 0);
    }

    @Theory
    public void TestExpressionPointSuccess(@FromDataPoints("point") Thing<Point> thing) throws Exception {
        var node = thing.GetNode();
        var expected = thing.GetExpected();

        var value = node.Accept(new CodeGeneratingVisitor(new SymbolTable()));
        var actual = value.GetPoint();

        assertThat(value.GetType()).isEqualTo(Type.Point);
        assertThat(actual).isEqualTo(expected);
    }
}
