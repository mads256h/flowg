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
    public String Visit(DeclarationNode declarationNode) throws NoException {
        return declarationNode.GetTypeChild().Accept(this)
                + " " + declarationNode.GetIdentifierChild().Accept(this)
                + " ="
                + " " + declarationNode.GetExpressionChild().Accept(this);
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
}