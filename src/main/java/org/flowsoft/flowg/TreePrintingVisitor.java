package org.flowsoft.flowg;

import org.flowsoft.flowg.nodes.*;

public class TreePrintingVisitor implements IVisitor<String> {

    private int _indentation = 0;

    private String PrintNode(INode node) {

        return "  ".repeat(_indentation) + node.toString() + "\n";
    }

    private String PrintNode(INode node, INode... children) {
        var str = PrintNode(node);
        _indentation++;
        for (var child : children) {
            str += child.Accept(this);
        }
        _indentation--;

        return str;
    }

    @Override
    public String Visit(ProgramNode programNode) {
        return PrintNode(programNode, programNode.GetChild());
    }

    @Override
    public String Visit(StatementListNode statementListNode) {
        if (statementListNode.GetStatementListChild() == null){
            return PrintNode(statementListNode, statementListNode.GetStatementChild());
        }
        else {
            return PrintNode(statementListNode, statementListNode.GetStatementChild(), statementListNode.GetStatementListChild());
        }
    }

    @Override
    public String Visit(StatementNode statementNode) {
        return PrintNode(statementNode, statementNode.GetChild());
    }

    @Override
    public String Visit(DeclarationNode declarationNode) {
        return PrintNode(declarationNode, declarationNode.GetTypeChild(), declarationNode.GetIdentifierChild(), declarationNode.GetExpressionChild());
    }

    @Override
    public String Visit(TypeNode typeNode) {
        return PrintNode(typeNode);
    }

    @Override
    public String Visit(IdentifierNode identifierNode) {
        return PrintNode(identifierNode);
    }

    @Override
    public String Visit(NumberLiteralNode numberLiteralNode) {
        return PrintNode(numberLiteralNode);
    }

    @Override
    public String Visit(BooleanLiteralNode booleanLiteralNode) {
        return PrintNode(booleanLiteralNode);
    }
}
