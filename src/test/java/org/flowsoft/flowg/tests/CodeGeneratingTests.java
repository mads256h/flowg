package org.flowsoft.flowg.tests;

import org.flowsoft.flowg.nodes.*;
import org.flowsoft.flowg.visitors.CodeGeneratingVisitor;
import org.flowsoft.flowg.visitors.SymbolTable;
import org.junit.experimental.theories.*;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.*;

@RunWith(Theories.class)
public class CodeGeneratingTests {

    @DataPoints
    public static BigDecimal[] BigDecimals = new BigDecimal[] {
            new BigDecimal("-1"),
            new BigDecimal("1"),
            new BigDecimal("3"),
            new BigDecimal(Integer.toString(Integer.MAX_VALUE)),
            new BigDecimal(Integer.toString(Integer.MIN_VALUE))};

    @Theory
    public void PlusExpression_ArithmeticOperationsTest(BigDecimal leftVal, BigDecimal rightVal) throws Exception {
        PlusExpressionNode plusExpressionNode = new PlusExpressionNode(
                new NumberLiteralNode(leftVal),
                new NumberLiteralNode(rightVal));

        var expressionValue = new CodeGeneratingVisitor(new SymbolTable()).Visit(plusExpressionNode);
        var actual = expressionValue.GetNumber();
        var expected = leftVal.add(rightVal);

        assertThat(actual).isEqualTo(expected);
    }

    @Theory
    public void MinusExpression_ArithmeticOperationsTest(BigDecimal leftVal, BigDecimal rightVal) throws Exception {
        MinusExpressionNode minusExpressionNode = new MinusExpressionNode(
                new NumberLiteralNode(leftVal),
                new NumberLiteralNode(rightVal));

        var expressionValue = new CodeGeneratingVisitor(new SymbolTable()).Visit(minusExpressionNode);
        var actual = expressionValue.GetNumber();
        var expected = leftVal.subtract(rightVal);

        assertThat(actual).isEqualTo(expected);
    }

    @Theory
    public void TimesExpression_ArithmeticOperationsTest(BigDecimal leftVal, BigDecimal rightVal) throws Exception {
        TimesExpressionNode timesExpressionNode  = new TimesExpressionNode(
                new NumberLiteralNode(leftVal),
                new NumberLiteralNode(rightVal));

        var expressionValue = new CodeGeneratingVisitor(new SymbolTable()).Visit(timesExpressionNode);
        var actual = expressionValue.GetNumber();
        var expected = leftVal.multiply(rightVal);

        assertThat(actual).isEqualTo(expected);
    }

    @Theory
    public void DivideExpression_ArithmeticOperationsTest(BigDecimal leftVal, BigDecimal rightVal) throws Exception {
        DivideExpressionNode divideExpressionNode  = new DivideExpressionNode(
                new NumberLiteralNode(leftVal),
                new NumberLiteralNode(rightVal));

        var expressionValue = new CodeGeneratingVisitor(new SymbolTable()).Visit(divideExpressionNode);
        var actual = expressionValue.GetNumber();

        BigDecimal expected;
        try {
            expected = leftVal.divide(rightVal);
        } catch (ArithmeticException e) {
            expected = leftVal.divide(rightVal, RoundingMode.HALF_UP).setScale(100, RoundingMode.HALF_UP);
        }

        assertThat(actual).isEqualTo(expected);
    }

    @Theory
    public void ZeroDivision_RightVal_DivideExpression_ArithmeticOperationsTest(BigDecimal leftVal) throws Exception {
        DivideExpressionNode divideExpressionNode  = new DivideExpressionNode(
                new NumberLiteralNode(leftVal),
                new NumberLiteralNode(new BigDecimal(0)));

        assertThrows(ArithmeticException.class, () -> new CodeGeneratingVisitor(new SymbolTable()).Visit(divideExpressionNode));
    }
}
