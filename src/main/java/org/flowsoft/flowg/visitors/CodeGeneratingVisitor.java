package org.flowsoft.flowg.visitors;

import org.flowsoft.flowg.Type;
import org.flowsoft.flowg.nodes.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CodeGeneratingVisitor implements IVisitor<ExpressionValue, Exception> {

    private SymbolTable _symbolTable;

    private final StringBuilder _stringBuilder = new StringBuilder();

    public CodeGeneratingVisitor(SymbolTable symbolTable) {
        _symbolTable = symbolTable;
    }

    public String GetCode() {
        return _stringBuilder.toString();
    }

    @Override
    public ExpressionValue Visit(StatementListNode statementListNode) throws Exception {
        for (var child : statementListNode.GetChildren()) {
            child.Accept(this);
        }
        return null;
    }

    @Override
    public ExpressionValue Visit(MoveNode moveNode) throws Exception {
        var parameters = moveNode.GetChild().GetChildren();

        var first = parameters.get(0).Accept(this);
        var second = parameters.get(1).Accept(this);
        var third = parameters.get(2).Accept(this);

        _stringBuilder
                .append("G1")
                // Don't extrude anything we are moving :)
                .append(" E0")
                .append(" X").append(first.GetNumber())
                .append(" Y").append(second.GetNumber())
                .append(" Z").append(third.GetNumber())
                .append('\n');

        return new ExpressionValue(Type.Void);
    }

    @Override
    public ExpressionValue Visit(ActualParameterListNode actualParameterListNode) throws Exception {
        for (var child : actualParameterListNode.GetChildren()) {
            child.Accept(this);
        }
        return null;
    }

    @Override
    public ExpressionValue Visit(DeclarationNode declarationNode) throws Exception {
        var identifierNode = declarationNode.GetIdentifierChild();
        var expressionNode = declarationNode.GetExpressionChild();

        var nodeId = identifierNode.GetValue();
        var typeNodeType = declarationNode.GetTypeChild().GetValue();
        var expressionValue = expressionNode.Accept(this);
        _symbolTable.Enter(nodeId, typeNodeType, expressionValue);
        return null;
    }

    @Override
    public ExpressionValue Visit(TypeNode typeNode) throws Exception {
        return null;
    }

    @Override
    public ExpressionValue Visit(IdentifierNode identifierNode) throws Exception {
        var variableEntry = _symbolTable.Lookup(identifierNode.GetValue());
        return new ExpressionValue(variableEntry.Type);
    }

    @Override
    public ExpressionValue Visit(NumberLiteralNode numberLiteralNode) throws Exception {
        return new ExpressionValue(numberLiteralNode.GetValue());
    }

    @Override
    public ExpressionValue Visit(BooleanLiteralNode booleanLiteralNode) throws Exception {
        return new ExpressionValue(booleanLiteralNode.GetValue());
    }

    @Override
    public ExpressionValue Visit(PlusExpressionNode plusExpressionNode) throws Exception {
        var leftChildValue = plusExpressionNode.GetLeftChild().Accept(this).GetNumber();
        var rightChildValue = plusExpressionNode.GetRightChild().Accept(this).GetNumber();
        var sum = leftChildValue.add(rightChildValue);
        return new ExpressionValue(sum);
    }

    @Override
    public ExpressionValue Visit(MinusExpressionNode minusExpressionNode) throws Exception {
        var leftChildValue = minusExpressionNode.GetLeftChild().Accept(this).GetNumber();
        var rightChildValue = minusExpressionNode.GetRightChild().Accept(this).GetNumber();
        var sum = leftChildValue.subtract(rightChildValue);
        return new ExpressionValue(sum);
    }

    @Override
    public ExpressionValue Visit(TimesExpressionNode multiplyExpressionNode) throws Exception {
        var leftChildValue = multiplyExpressionNode.GetLeftChild().Accept(this).GetNumber();
        var rightChildValue = multiplyExpressionNode.GetRightChild().Accept(this).GetNumber();
        var sum = leftChildValue.multiply(rightChildValue);
        return new ExpressionValue(sum);
    }

    @Override
    public ExpressionValue Visit(DivideExpressionNode divisionExpressionNode) throws Exception {
        var leftChildValue = divisionExpressionNode.GetLeftChild().Accept(this).GetNumber();
        var rightChildValue = divisionExpressionNode.GetRightChild().Accept(this).GetNumber();

        BigDecimal sum;
        try {
            sum = leftChildValue.divide(rightChildValue);
        } catch (ArithmeticException e) {
            sum = leftChildValue.divide(rightChildValue, RoundingMode.HALF_UP).setScale(100, RoundingMode.HALF_UP);
        }

        return new ExpressionValue(sum);
    }
}
