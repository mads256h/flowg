package org.flowsoft.flowg.tests;

import org.flowsoft.flowg.Type;
import org.flowsoft.flowg.TypeException;
import org.flowsoft.flowg.nodes.*;
import org.flowsoft.flowg.visitors.TypeCheckingVisitor;
import org.junit.experimental.theories.*;
import org.junit.runner.RunWith;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

import java.math.BigDecimal;
import java.util.ArrayList;

@RunWith(Theories.class)
public class TypeCheckTest {

    @DataPoints("number")
    public static ExpressionNode[] numberLiteralNode = new ExpressionNode[] {
            new PlusExpressionNode(new NumberLiteralNode(new BigDecimal(1)), new NumberLiteralNode(new BigDecimal(2))),
            new MinusExpressionNode(new NumberLiteralNode(new BigDecimal(1)), new NumberLiteralNode(new BigDecimal(2))),
            new NumberLiteralNode(new BigDecimal(1))};


    @DataPoints({"boolean", "notnumber"})
    public static ExpressionNode[] booleanLiteralNode = new BooleanLiteralNode[] {new BooleanLiteralNode(true), new BooleanLiteralNode(false)};


    @Theory
    public void TestNumberSuccess(@FromDataPoints("number") ExpressionNode left, @FromDataPoints("number") ExpressionNode right) throws Exception {
        var n1 = new PlusExpressionNode(left, right);
        var n2 = new MinusExpressionNode(left, right);
        var n3 = new TimesExpressionNode(left, right);
        var n4 = new DivideExpressionNode(left, right);

        assertThat(n1.Accept(new TypeCheckingVisitor())).isEqualTo(Type.Number);
        assertThat(n2.Accept(new TypeCheckingVisitor())).isEqualTo(Type.Number);
        assertThat(n3.Accept(new TypeCheckingVisitor())).isEqualTo(Type.Number);
        assertThat(n4.Accept(new TypeCheckingVisitor())).isEqualTo(Type.Number);
    }

    @Theory
    public void TestBooleanFailure(@FromDataPoints("boolean") ExpressionNode left, ExpressionNode right) {
        var n1 = new PlusExpressionNode(left, right);
        var n2 = new PlusExpressionNode(right, left);

        var n3 = new MinusExpressionNode(left, right);
        var n4 = new MinusExpressionNode(right, left);

        var n5 = new TimesExpressionNode(left, right);
        var n6 = new TimesExpressionNode(right, left);

        var n7 = new DivideExpressionNode(left, right);
        var n8 = new DivideExpressionNode(right, left);


        assertThrows(TypeException.class, () -> n1.Accept(new TypeCheckingVisitor()));
        assertThrows(TypeException.class, () -> n2.Accept(new TypeCheckingVisitor()));

        assertThrows(TypeException.class, () -> n3.Accept(new TypeCheckingVisitor()));
        assertThrows(TypeException.class, () -> n4.Accept(new TypeCheckingVisitor()));

        assertThrows(TypeException.class, () -> n5.Accept(new TypeCheckingVisitor()));
        assertThrows(TypeException.class, () -> n6.Accept(new TypeCheckingVisitor()));

        assertThrows(TypeException.class, () -> n7.Accept(new TypeCheckingVisitor()));
        assertThrows(TypeException.class, () -> n8.Accept(new TypeCheckingVisitor()));
    }


    @Theory
    public void TestMoveBuiltinSuccess(@FromDataPoints("number") ExpressionNode first, @FromDataPoints("number") ExpressionNode second, @FromDataPoints("number") ExpressionNode third) throws Exception {
        var list = new ArrayList<ExpressionNode>();
        list.add(first);
        list.add(second);
        list.add(third);

        var n = new MoveNode(new ActualParameterListNode(list));

        // Move has type void
        assertThat(n.Accept(new TypeCheckingVisitor())).isEqualTo(Type.Void);
    }

    @Theory
    public void TestMoveBuiltinTypeFailure(ExpressionNode first, ExpressionNode second, @FromDataPoints("notnumber") ExpressionNode third) {
        var list = new ArrayList<ExpressionNode>();
        list.add(first);
        list.add(second);
        list.add(third);

        var n = new MoveNode(new ActualParameterListNode(list));

        assertThrows(TypeException.class, () -> n.Accept(new TypeCheckingVisitor()));
    }
}
