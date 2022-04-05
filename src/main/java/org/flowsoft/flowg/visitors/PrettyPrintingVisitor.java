package org.flowsoft.flowg.visitors;

import org.flowsoft.flowg.NoException;
import org.flowsoft.flowg.TypeHelper;
import org.flowsoft.flowg.nodes.*;

public class PrettyPrintingVisitor implements IVisitor<String, NoException> {
    @Override
    public String Visit(StatementListNode statementListNode) throws NoException {
        var children = statementListNode.GetChildren();
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < children.size(); i++) {
            str.append(children.get(i).Accept(this));
            str.append(";\n");
        }


        return str.toString();
    }

    @Override
    public String Visit(MoveNode moveNode) throws NoException {
        return "move(" + moveNode.GetChild().Accept(this) + ")";
    }

    @Override
    public String Visit(ActualParameterListNode actualParameterListNode) throws NoException {
        var sb = new StringBuilder();
        boolean first = true;
        for (var child : actualParameterListNode.GetChildren()) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(child.Accept(this));
            first = false;
        }

        return sb.toString();
    }

    @Override
    public String Visit(DeclarationNode declarationNode) throws NoException {
        return declarationNode.GetFirstNode().Accept(this)
                + " " + declarationNode.GetSecondNode().Accept(this)
                + " ="
                + " " + declarationNode.GetThirdNode().Accept(this);
    }

    @Override
    public String Visit(TypeNode typeNode) {
        return TypeHelper.TypeToString(typeNode.GetValue());
    }

    @Override
    public String Visit(IdentifierNode identifierNode) {
        return identifierNode.GetValue();
    }

    @Override
    public String Visit(NumberLiteralNode numberLiteralNode) {
        return numberLiteralNode.GetValue().toString();
    }

    @Override
    public String Visit(BooleanLiteralNode booleanLiteralNode) {
        return booleanLiteralNode.GetValue().toString();
    }

    @Override
    public String Visit(PointNode pointNode) throws NoException {
        return "["
                + pointNode.GetFirstNode().Accept(this) + ", "
                + pointNode.GetSecondNode().Accept(this) + ", "
                + pointNode.GetThirdNode().Accept(this) +
                "]";
    }

    @Override
    public String Visit(PlusExpressionNode plusExpressionNode) throws NoException {
        return plusExpressionNode.GetLeftChild().Accept(this) + " + " + plusExpressionNode.GetRightChild().Accept(this);
    }

    @Override
    public String Visit(MinusExpressionNode minusExpressionNode) throws NoException {
        return minusExpressionNode.GetLeftChild().Accept(this) + " - " + minusExpressionNode.GetRightChild().Accept(this);
    }

    @Override
    public String Visit(TimesExpressionNode multiplyExpressionNode) throws NoException {
        return multiplyExpressionNode.GetLeftChild().Accept(this) + " * " + multiplyExpressionNode.GetRightChild().Accept(this);
    }

    @Override
    public String Visit(DivideExpressionNode divisionExpressionNode) throws NoException {
        return divisionExpressionNode.GetLeftChild().Accept(this) + " / " + divisionExpressionNode.GetRightChild().Accept(this);
    }

    @Override
    public String Visit(IdentifierExpressionNode identifierExpressionNode) throws NoException {
        return identifierExpressionNode.GetChild().Accept(this);
    }
}
