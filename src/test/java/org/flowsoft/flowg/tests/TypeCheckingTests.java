package org.flowsoft.flowg.tests;
import org.flowsoft.flowg.Type;
import org.flowsoft.flowg.TypeException;
import org.flowsoft.flowg.nodes.*;
import org.flowsoft.flowg.visitors.TypeCheckingVisitor;
import org.junit.Test;

import java.math.BigDecimal;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

public class TypeCheckingTests {

    @Test
    public void Number_TypeCheck_PlusExpressionNodeTest() throws Exception {

        PlusExpressionNode plusExpressionNode = new PlusExpressionNode(
                new NumberLiteralNode(new BigDecimal(1)),
                new NumberLiteralNode(new BigDecimal(2)));

        var type = new TypeCheckingVisitor().Visit(plusExpressionNode);
        assertThat(type).isEqualTo(Type.Number);
    }

    @Test
    public void Throws_TypeException_PlusExpressionNodeTest() throws Exception {

        PlusExpressionNode plusExpressionNode = new PlusExpressionNode(
                new NumberLiteralNode(new BigDecimal(1)),
                new BooleanLiteralNode(true));

        assertThrows(TypeException.class, () -> new TypeCheckingVisitor().Visit(plusExpressionNode));
    }

    @Test
    public void Number_TypeCheck_MinusExpressionNodeTest() throws Exception {

        MinusExpressionNode minusExpressionNode = new MinusExpressionNode(
                new NumberLiteralNode(new BigDecimal(1)),
                new NumberLiteralNode(new BigDecimal(2)));

        var type = new TypeCheckingVisitor().Visit(minusExpressionNode);
        assertThat(type).isEqualTo(Type.Number);
    }

    @Test
    public void Boolean_Throws_TypeException_MinusExpressionNodeTest() throws Exception {

        MinusExpressionNode minusExpressionNode = new MinusExpressionNode(
                new NumberLiteralNode(new BigDecimal(1)),
                new BooleanLiteralNode(true));

        assertThrows(TypeException.class, () -> new TypeCheckingVisitor().Visit(minusExpressionNode));
    }

    @Test
    public void Number_TypeCheck_DivideExpressionNodeTest() throws Exception {

        DivideExpressionNode divideExpressionNode = new DivideExpressionNode(
                new NumberLiteralNode(new BigDecimal(1)),
                new NumberLiteralNode(new BigDecimal(2)));

        var type = new TypeCheckingVisitor().Visit(divideExpressionNode);
        assertThat(type).isEqualTo(Type.Number);
    }

    @Test
    public void Boolean_Throws_TypeException_DivideExpressionNodeTest() throws Exception {

        DivideExpressionNode divideExpressionNode = new DivideExpressionNode(
                new NumberLiteralNode(new BigDecimal(1)),
                new BooleanLiteralNode(true));

        assertThrows(TypeException.class, () -> new TypeCheckingVisitor().Visit(divideExpressionNode));
    }

    @Test
    public void Number_TypeCheck_TimesExpressionNodeTest() throws Exception {

        DivideExpressionNode divideExpressionNode = new DivideExpressionNode(
                new NumberLiteralNode(new BigDecimal(1)),
                new NumberLiteralNode(new BigDecimal(2)));

        var type = new TypeCheckingVisitor().Visit(divideExpressionNode);
        assertThat(type).isEqualTo(Type.Number);
    }

    @Test
    public void Boolean_Throws_TypeException_TimesExpressionNodeTest() throws Exception {

        DivideExpressionNode divideExpressionNode = new DivideExpressionNode(
                new NumberLiteralNode(new BigDecimal(1)),
                new BooleanLiteralNode(true));

        assertThrows(TypeException.class, () -> new TypeCheckingVisitor().Visit(divideExpressionNode));
    }
}
