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

//        System.out.println(
//                declarationNode.GetIdentifierChild().GetValue()
//                + " has value: "
//                + expressionNode.Accept(this).getNumber()
//        );

        var nodeId = identifierNode.GetValue();
        var typeNodeType = declarationNode.GetTypeChild().GetValue();
        var expressionValue = expressionNode.Accept(this);
        _symbolTable.Enter(nodeId, typeNodeType, expressionValue);
        return null;
    }

    @Override
    public ExpressionValue Visit(TypeNode typeNode) throws Exception {
        var expressionValue = new ExpressionValue();
        expressionValue.setType(typeNode.GetValue());
        return expressionValue;
    }

    @Override
    public ExpressionValue Visit(IdentifierNode identifierNode) throws Exception {
        var expressionValue = new ExpressionValue();

        var variableEntry = _symbolTable.Lookup(identifierNode.GetValue());
        expressionValue.setType(variableEntry.Type);

        return expressionValue;
    }

    @Override
    public ExpressionValue Visit(NumberLiteralNode numberLiteralNode) throws Exception {
        var expressionValue = new ExpressionValue();

        var number = numberLiteralNode.GetValue();

        expressionValue.setNumber(number);
        expressionValue.setType(Type.Number);
        return expressionValue;
    }

    @Override
    public ExpressionValue Visit(BooleanLiteralNode booleanLiteralNode) throws Exception {
        var expressionValue = new ExpressionValue();
        expressionValue.setType(Type.Boolean);
        return expressionValue;
    }

    @Override
    public ExpressionValue Visit(PlusExpressionNode plusExpressionNode) throws Exception {
        ExpressionValue expressionValue = new ExpressionValue();

        var leftChildValue = plusExpressionNode.GetLeftChild().Accept(this).getNumber();
        var rightChildValue = plusExpressionNode.GetRightChild().Accept(this).getNumber();

        var sum = leftChildValue.add(rightChildValue);

        expressionValue.setNumber(sum);
        expressionValue.setType(Type.Number);
        return expressionValue;
    }

    @Override
    public ExpressionValue Visit(MinusExpressionNode minusExpressionNode) throws Exception {
        ExpressionValue expressionValue = new ExpressionValue();

        var leftChildValue = minusExpressionNode.GetLeftChild().Accept(this).getNumber();
        var rightChildValue = minusExpressionNode.GetRightChild().Accept(this).getNumber();

        var sum = leftChildValue.subtract(rightChildValue);

        expressionValue.setNumber(sum);
        expressionValue.setType(Type.Number);
        return expressionValue;
    }

    @Override
    public ExpressionValue Visit(TimesExpressionNode multiplyExpressionNode) throws Exception {
        ExpressionValue expressionValue = new ExpressionValue();

        var leftChildValue = multiplyExpressionNode.GetLeftChild().Accept(this).getNumber();
        var rightChildValue = multiplyExpressionNode.GetRightChild().Accept(this).getNumber();

        var sum = leftChildValue.multiply(rightChildValue);

        expressionValue.setNumber(sum);
        expressionValue.setType(Type.Number);
        return expressionValue;
    }

    @Override
    public ExpressionValue Visit(DivideExpressionNode divisionExpressionNode) throws Exception {
        ExpressionValue expressionValue = new ExpressionValue();

        var leftChildValue = divisionExpressionNode.GetLeftChild().Accept(this).getNumber();
        var rightChildValue = divisionExpressionNode.GetRightChild().Accept(this).getNumber();

        BigDecimal sum;
        try {
            sum = leftChildValue.divide(rightChildValue);
        } catch (ArithmeticException e) {
            sum = leftChildValue.divide(rightChildValue, RoundingMode.HALF_UP).setScale(100, RoundingMode.HALF_UP);
        }

        expressionValue.setNumber(sum);
        expressionValue.setType(Type.Number);
        return expressionValue;
    }
}
