package org.flowsoft.flowg.visitors;

import org.flowsoft.flowg.Type;
import org.flowsoft.flowg.TypeException;
import org.flowsoft.flowg.nodes.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CodeGeneratingVisitor implements IVisitor<ExpressionValue, Exception> {

    private SymbolTable _symbolTable;

    public CodeGeneratingVisitor(SymbolTable symbolTable) {
        _symbolTable = symbolTable;
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
        return null;
    }

    @Override
    public ExpressionValue Visit(ActualParameterListNode actualParameterListNode) throws Exception {
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
        return new ExpressionValue(typeNode.GetValue());
    }

    @Override
    public ExpressionValue Visit(IdentifierNode identifierNode) throws Exception {
        var variableEntry = _symbolTable.Lookup(identifierNode.GetValue());
        return new ExpressionValue(variableEntry.Type);
    }

    @Override
    public ExpressionValue Visit(NumberLiteralNode numberLiteralNode) throws Exception {
        return new ExpressionValue(Type.Number, numberLiteralNode.GetValue());
    }

    @Override
    public ExpressionValue Visit(BooleanLiteralNode booleanLiteralNode) throws Exception {
        return new ExpressionValue(Type.Boolean, booleanLiteralNode.GetValue());
    }

    @Override
    public ExpressionValue Visit(PlusExpressionNode plusExpressionNode) throws Exception {
        var leftChildValue = plusExpressionNode.GetLeftChild().Accept(this).getNumber();
        var rightChildValue = plusExpressionNode.GetRightChild().Accept(this).getNumber();
        var sum = leftChildValue.add(rightChildValue);
        return new ExpressionValue(Type.Number, sum);
    }

    @Override
    public ExpressionValue Visit(MinusExpressionNode minusExpressionNode) throws Exception {
        var leftChildValue = minusExpressionNode.GetLeftChild().Accept(this).getNumber();
        var rightChildValue = minusExpressionNode.GetRightChild().Accept(this).getNumber();
        var sum = leftChildValue.subtract(rightChildValue);
        return new ExpressionValue(Type.Number, sum);
    }

    @Override
    public ExpressionValue Visit(TimesExpressionNode multiplyExpressionNode) throws Exception {
        var leftChildValue = multiplyExpressionNode.GetLeftChild().Accept(this).getNumber();
        var rightChildValue = multiplyExpressionNode.GetRightChild().Accept(this).getNumber();
        var sum = leftChildValue.multiply(rightChildValue);
        return new ExpressionValue(Type.Number, sum);
    }

    @Override
    public ExpressionValue Visit(DivideExpressionNode divisionExpressionNode) throws Exception {
        var leftChildValue = divisionExpressionNode.GetLeftChild().Accept(this).getNumber();
        var rightChildValue = divisionExpressionNode.GetRightChild().Accept(this).getNumber();

        BigDecimal sum;
        try {
            sum = leftChildValue.divide(rightChildValue);
        } catch (ArithmeticException e) {
            sum = leftChildValue.divide(rightChildValue, RoundingMode.HALF_UP).setScale(100, RoundingMode.HALF_UP);
        }

        return new ExpressionValue(Type.Number, sum);
    }
}
