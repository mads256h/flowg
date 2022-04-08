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
    public String Visit(MoveNode moveNode) throws NoException {
        return PrintNode(moveNode, moveNode.GetChild());
    }

    @Override
    public String Visit(ActualParameterListNode actualParameterListNode) throws NoException {
        var array = actualParameterListNode.GetChildren().toArray(new ExpressionNode[0]);

        return PrintNode(actualParameterListNode, array);
    }

    @Override
    public String Visit(FormalParameterListNode formalParameterListNode) throws NoException {
        var array = formalParameterListNode.GetChildren().toArray(new FormalParameterNode[0]);

        return PrintNode(formalParameterListNode, array);
    }

    @Override
    public String Visit(FormalParameterNode formalParameterNode) throws NoException {
        return PrintNode(formalParameterNode, formalParameterNode.GetLeftChild(), formalParameterNode.GetRightChild());
    }

    @Override
    public String Visit(DeclarationNode declarationNode) throws NoException {
        return PrintNode(declarationNode, declarationNode.GetFirstNode(), declarationNode.GetSecondNode(), declarationNode.GetThirdNode());
    }

    @Override
    public String Visit(FunctionDefinitionNode functionDefinitionNode) throws NoException {
        return PrintNode(functionDefinitionNode, functionDefinitionNode.GetTypeNode(), functionDefinitionNode.GetIdentifierNode(), functionDefinitionNode.GetFormalParameterListNode(), functionDefinitionNode.GetStatementListNode());

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
    public String Visit(PointNode pointNode) throws NoException {
        return PrintNode(pointNode, pointNode.GetFirstNode(), pointNode.GetSecondNode(), pointNode.GetThirdNode());
    }

    @Override
    public String Visit(PlusExpressionNode plusExpressionNode) throws NoException { return PrintNode(plusExpressionNode, plusExpressionNode.GetLeftChild(), plusExpressionNode.GetRightChild()); }

    @Override
    public String Visit(MinusExpressionNode minusExpressionNode) throws NoException { return PrintNode(minusExpressionNode, minusExpressionNode.GetLeftChild(), minusExpressionNode.GetRightChild()); }

    @Override
    public String Visit(TimesExpressionNode multiplyExpressionNode) throws NoException { return PrintNode(multiplyExpressionNode, multiplyExpressionNode.GetLeftChild(), multiplyExpressionNode.GetRightChild()); }

    @Override
    public String Visit(DivideExpressionNode divisionExpressionNode) throws NoException { return PrintNode(divisionExpressionNode, divisionExpressionNode.GetLeftChild(), divisionExpressionNode.GetRightChild()); }

    @Override
    public String Visit(IdentifierExpressionNode identifierExpressionNode) throws NoException {
        return PrintNode(identifierExpressionNode, identifierExpressionNode.GetChild());
    }
}
