package org.flowsoft.flowg;

import org.flowsoft.flowg.nodes.*;

public class PrettyPrintingVisitor implements IVisitor<String> {
    @Override
    public String Visit(ProgramNode programNode) {
        return programNode.GetChild().Accept(this);
    }

    @Override
    public String Visit(StatementListNode statementListNode) {
        var statement = statementListNode.GetLeftChild();
        var statementList = statementListNode.GetRightChild();

        return statement.Accept(this) + ";\n"
                + (statementList == null ? "" : statementList.Accept(this));
    }

    @Override
    public String Visit(StatementNode statementNode) {
        return statementNode.GetChild().Accept(this);
    }

    @Override
    public String Visit(DeclarationNode declarationNode) {
        return declarationNode.GetTypeChild().Accept(this)
                + " " + declarationNode.GetIdentifierChild().Accept(this)
                + " ="
                + " " + declarationNode.GetExpressionChild().Accept(this);
    }

    @Override
    public String Visit(TypeNode typeNode) {
        return typeNode.GetValue();
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
}
