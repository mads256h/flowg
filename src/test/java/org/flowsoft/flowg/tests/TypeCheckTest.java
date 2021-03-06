package org.flowsoft.flowg.tests;

import java_cup.runtime.ComplexSymbolFactory;
import org.flowsoft.flowg.Type;
import org.flowsoft.flowg.exceptions.type.TypeException;
import org.flowsoft.flowg.nodes.BooleanLiteralNode;
import org.flowsoft.flowg.nodes.NumberLiteralNode;
import org.flowsoft.flowg.nodes.PointNode;
import org.flowsoft.flowg.nodes.base.ExpressionNode;
import org.flowsoft.flowg.nodes.functions.ActualParameterListNode;
import org.flowsoft.flowg.nodes.math.operators.DivideExpressionNode;
import org.flowsoft.flowg.nodes.math.operators.MinusExpressionNode;
import org.flowsoft.flowg.nodes.math.operators.PlusExpressionNode;
import org.flowsoft.flowg.nodes.math.operators.TimesExpressionNode;
import org.flowsoft.flowg.visitors.TypeCheckingVisitor;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

@RunWith(Theories.class)
public class TypeCheckTest {
    private static final Path P = Path.of("");
    private static final ComplexSymbolFactory.Location N = new ComplexSymbolFactory.Location("test", 0, 0, 0);

    @DataPoints({"number", "notboolean", "notpoint"})
    public static final ExpressionNode[] NUMBER_NODES = new ExpressionNode[]{
            new PlusExpressionNode(new NumberLiteralNode(new BigDecimal(1), N, N), new NumberLiteralNode(new BigDecimal(2), N, N), N, N),
            new MinusExpressionNode(new NumberLiteralNode(new BigDecimal(1), N, N), new NumberLiteralNode(new BigDecimal(2), N, N), N, N),
            new NumberLiteralNode(new BigDecimal(1), N, N)};


    @DataPoints({"boolean", "notnumber", "notpoint"})
    public static final ExpressionNode[] BOOLEAN_NODES = new BooleanLiteralNode[]{new BooleanLiteralNode(true, N, N), new BooleanLiteralNode(false, N, N)};

    @DataPoints({"point", "notnumber", "notboolean"})
    public static final ExpressionNode[] POINT_NODES = new PointNode[]{
            new PointNode(new NumberLiteralNode(new BigDecimal(1), N, N), new NumberLiteralNode(new BigDecimal(2), N, N), new NumberLiteralNode(new BigDecimal(3), N, N), N, N)
    };


    @Theory
    public void TestNumberSuccess(@FromDataPoints("number") ExpressionNode left, @FromDataPoints("number") ExpressionNode right) throws Exception {
        var n1 = new PlusExpressionNode(left, right, N, N);
        var n2 = new MinusExpressionNode(left, right, N, N);
        var n3 = new TimesExpressionNode(left, right, N, N);
        var n4 = new DivideExpressionNode(left, right, N, N);

        assertThat(n1.Accept(new TypeCheckingVisitor(P))).isEqualTo(Type.Number);
        assertThat(n2.Accept(new TypeCheckingVisitor(P))).isEqualTo(Type.Number);
        assertThat(n3.Accept(new TypeCheckingVisitor(P))).isEqualTo(Type.Number);
        assertThat(n4.Accept(new TypeCheckingVisitor(P))).isEqualTo(Type.Number);
    }

    @Theory
    public void TestPointNumberMultiplySuccess(@FromDataPoints("point") ExpressionNode left, @FromDataPoints("number") ExpressionNode right) throws Exception {
        var n1 = new TimesExpressionNode(left, right, N, N);
        var n2 = new TimesExpressionNode(right, left, N, N);

        var n3 = new DivideExpressionNode(left, right, N, N);
        var n4 = new DivideExpressionNode(right, left, N, N);


        assertThat(n1.Accept(new TypeCheckingVisitor(P))).isEqualTo(Type.Point);
        assertThat(n2.Accept(new TypeCheckingVisitor(P))).isEqualTo(Type.Point);

        assertThat(n3.Accept(new TypeCheckingVisitor(P))).isEqualTo(Type.Point);
        assertThat(n4.Accept(new TypeCheckingVisitor(P))).isEqualTo(Type.Point);
    }

    @Theory
    public void TestBooleanFailure(@FromDataPoints("boolean") ExpressionNode left, ExpressionNode right) {
        var n1 = new PlusExpressionNode(left, right, N, N);
        var n2 = new PlusExpressionNode(right, left, N, N);

        var n3 = new MinusExpressionNode(left, right, N, N);
        var n4 = new MinusExpressionNode(right, left, N, N);

        var n5 = new TimesExpressionNode(left, right, N, N);
        var n6 = new TimesExpressionNode(right, left, N, N);

        var n7 = new DivideExpressionNode(left, right, N, N);
        var n8 = new DivideExpressionNode(right, left, N, N);


        assertThrows(TypeException.class, () -> n1.Accept(new TypeCheckingVisitor(P)));
        assertThrows(TypeException.class, () -> n2.Accept(new TypeCheckingVisitor(P)));

        assertThrows(TypeException.class, () -> n3.Accept(new TypeCheckingVisitor(P)));
        assertThrows(TypeException.class, () -> n4.Accept(new TypeCheckingVisitor(P)));

        assertThrows(TypeException.class, () -> n5.Accept(new TypeCheckingVisitor(P)));
        assertThrows(TypeException.class, () -> n6.Accept(new TypeCheckingVisitor(P)));

        assertThrows(TypeException.class, () -> n7.Accept(new TypeCheckingVisitor(P)));
        assertThrows(TypeException.class, () -> n8.Accept(new TypeCheckingVisitor(P)));
    }

    /* Commented out because move does not exist
    @Theory
    public void TestMoveBuiltinSuccess(@FromDataPoints("point") ExpressionNode node) throws Exception {
        var list = new ArrayList<ExpressionNode>();
        list.add(node);

        var n = new MoveNode(new ActualParameterListNode(list, N, N), N, N);

        // Move has type void
        assertThat(n.Accept(new TypeCheckingVisitor(P))).isEqualTo(Type.Void);
    }

    @Theory
    public void TestMoveBuiltinTypeFailure(@FromDataPoints("notpoint") ExpressionNode node) {
        var list = new ArrayList<ExpressionNode>();
        list.add(node);

        var n = new MoveNode(new ActualParameterListNode(list, N, N), N, N);

        assertThrows(TypeException.class, () -> n.Accept(new TypeCheckingVisitor(P)));
    }

 */
}
