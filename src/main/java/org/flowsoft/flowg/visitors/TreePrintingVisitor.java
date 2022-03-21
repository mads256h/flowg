package org.flowsoft.flowg.visitors;

import org.flowsoft.flowg.NoException;
import org.flowsoft.flowg.nodes.*;

public class TreePrintingVisitor implements IVisitor<String, NoException> {

    private int _indentation = 0;

    private String PrintNode(INode node) {

        return "  ".repeat(_indentation) + node.toString() + "\n";
    }

    private String PrintNode(INode node, INode... children) throws NoException {
        var str = PrintNode(node);
        _indentation++;
        for (var child : children) {
            str += child.Accept(this);
        }
        _indentation--;

        return str;
    }

    @Override
    public String Visit(StatementListNode statementListNode) throws NoException {
        StatementNode[] array = statementListNode.GetChildren().toArray(new StatementNode[0]);

        return PrintNode(statementListNode, array);
    }

    @Override
    public String Visit(DeclarationNode declarationNode) throws NoException {
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

    @Override
    public String Visit(PlusExpressionNode plusExpressionNode) throws NoException { return PrintNode(plusExpressionNode, plusExpressionNode.GetLeftChild(), plusExpressionNode.GetRightChild()); }

    @Override
    public String Visit(MinusExpressionNode minusExpressionNode) throws NoException { return PrintNode(minusExpressionNode, minusExpressionNode.GetLeftChild(), minusExpressionNode.GetRightChild()); }

    @Override
    public String Visit(TimesExpressionNode multiplyExpressionNode) throws NoException { return PrintNode(multiplyExpressionNode, multiplyExpressionNode.GetLeftChild(), multiplyExpressionNode.GetRightChild()); }

    @Override
    public String Visit(DivideExpressionNode divisionExpressionNode) throws NoException { return PrintNode(divisionExpressionNode, divisionExpressionNode.GetLeftChild(), divisionExpressionNode.GetRightChild()); }
}