package org.flowsoft.flowg.visitors;

import org.flowsoft.flowg.NoException;
import org.flowsoft.flowg.nodes.*;
import org.flowsoft.flowg.nodes.base.ExpressionNode;
import org.flowsoft.flowg.nodes.base.INode;
import org.flowsoft.flowg.nodes.base.StatementNode;
import org.flowsoft.flowg.nodes.controlflow.ForToNode;
import org.flowsoft.flowg.nodes.controlflow.IfElseNode;
import org.flowsoft.flowg.nodes.controlflow.ReturnNode;
import org.flowsoft.flowg.nodes.functions.*;
import org.flowsoft.flowg.nodes.math.functions.*;
import org.flowsoft.flowg.nodes.math.operators.*;

public class TreePrintingVisitor implements IVisitor<String, NoException> {

    private int _indentation = 0;

    private String PrintNode(INode node) {

        return "  ".repeat(_indentation) + node.toString() + "\n";
    }

    private String PrintNode(INode node, INode... children) throws NoException {
        var str = new StringBuilder(PrintNode(node));
        _indentation++;
        for (var child : children) {
            str.append(child.Accept(this));
        }
        _indentation--;

        return str.toString();
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
    public String Visit(LineNode lineNode) throws NoException {
        return PrintNode(lineNode, lineNode.GetChild());
    }

    @Override
    public String Visit(SqrtNode sqrtNode) throws NoException {
        return PrintNode(sqrtNode, sqrtNode.GetChild());
    }

    @Override
    public String Visit(SinNode sinNode) throws NoException {
        return PrintNode(sinNode, sinNode.GetChild());
    }

    @Override
    public String Visit(CosNode cosNode) throws NoException {
        return PrintNode(cosNode, cosNode.GetChild());
    }

    @Override
    public String Visit(TanNode tanNode) throws NoException {
        return PrintNode(tanNode, tanNode.GetChild());
    }

    @Override
    public String Visit(ArcsinNode arcsinNode) throws NoException {
        return PrintNode(arcsinNode, arcsinNode.GetChild());
    }

    @Override
    public String Visit(ArccosNode arccosNode) throws NoException {
        return PrintNode(arccosNode, arccosNode.GetChild());
    }

    @Override
    public String Visit(ArctanNode arctanNode) throws NoException {
        return PrintNode(arctanNode, arctanNode.GetChild());
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
    public String Visit(GCodeFuncNode gCodeFuncNode) throws NoException {
        return PrintNode(gCodeFuncNode, gCodeFuncNode.GetIdentifierNode(),  gCodeFuncNode.GetFormalParameterListNode(), gCodeFuncNode.GetGCodeCodeNode());
    }

    @Override
    public String Visit(GCodeCodeNode gCodeCodeNode) throws NoException {
        return PrintNode(gCodeCodeNode);
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
    public String Visit(PointEntryNode pointEntryNode) throws NoException {
        return PrintNode(pointEntryNode, pointEntryNode.GetLeftChild(), pointEntryNode.GetRightChild());
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
    public String Visit(PowerExpressionNode powerExpressionNode) throws NoException {
        return PrintNode(powerExpressionNode, powerExpressionNode.GetLeftChild(), powerExpressionNode.GetRightChild());
    }

    @Override
    public String Visit(NotExpressionNode notExpressionNode) throws NoException {
        return PrintNode(notExpressionNode, notExpressionNode.GetChild());
    }

    @Override
    public String Visit(IdentifierExpressionNode identifierExpressionNode) throws NoException {
        return PrintNode(identifierExpressionNode, identifierExpressionNode.GetChild());
    }

    @Override
    public String Visit(FunctionCallNode functionCallNode) throws NoException {
        return PrintNode(functionCallNode, functionCallNode.GetLeftChild(), functionCallNode.GetRightChild());
    }

    @Override
    public String Visit(ReturnNode returnNode) throws NoException {
        var child = returnNode.GetChild();
        if (child != null) {
            return PrintNode(returnNode, returnNode.GetChild());
        }

        return PrintNode(returnNode);
    }

    @Override
    public String Visit(IfElseNode ifElseNode) throws NoException {
        var child = ifElseNode.GetThirdNode();
        if (child != null) {
            return PrintNode(ifElseNode, ifElseNode.GetFirstNode(), ifElseNode.GetSecondNode(), ifElseNode.GetThirdNode());
        }

        return PrintNode(ifElseNode, ifElseNode.GetFirstNode(), ifElseNode.GetSecondNode());
    }

    @Override
    public String Visit(AssignmentNode assignmentNode) throws NoException {
        return PrintNode(assignmentNode, assignmentNode.GetLeftChild(), assignmentNode.GetRightChild());
    }
    
    @Override
    public String Visit(ForToNode forToNode) throws NoException {
        return PrintNode(forToNode, forToNode.GetFirstNode(), forToNode.GetSecondNode(), forToNode.GetThirdNode());
    }

    @Override
    public String Visit(GreaterThanExpressionNode greaterThanExpressionNode) throws NoException {
        return PrintNode(greaterThanExpressionNode, greaterThanExpressionNode.GetLeftChild(), greaterThanExpressionNode.GetRightChild());
    }

    @Override
    public String Visit(LessThanExpressionNode lessThanExpressionNode) throws NoException {
        return PrintNode(lessThanExpressionNode, lessThanExpressionNode.GetLeftChild(), lessThanExpressionNode.GetRightChild());
    }

    @Override
    public String Visit(EqualsExpressionNode equalsExpressionNode) throws NoException {
        return PrintNode(equalsExpressionNode, equalsExpressionNode.GetLeftChild(), equalsExpressionNode.GetRightChild());
    }

    @Override
    public String Visit(GreaterThanEqualsExpressionNode greaterThanEqualsExpressionNode) throws NoException {
        return PrintNode(greaterThanEqualsExpressionNode, greaterThanEqualsExpressionNode.GetLeftChild(), greaterThanEqualsExpressionNode.GetRightChild());
    }

    @Override
    public String Visit(LessThanEqualsExpressionNode lessThanEqualsExpressionNode) throws NoException {
        return PrintNode(lessThanEqualsExpressionNode, lessThanEqualsExpressionNode.GetLeftChild(), lessThanEqualsExpressionNode.GetRightChild());
    }

    @Override
    public String Visit(AndExpressionNode andExpressionNode) throws NoException {
        return PrintNode(andExpressionNode, andExpressionNode.GetLeftChild(), andExpressionNode.GetRightChild());
    }

    @Override
    public String Visit(OrExpressionNode orExpressionNode) throws NoException {
        return PrintNode(orExpressionNode, orExpressionNode.GetLeftChild(), orExpressionNode.GetRightChild());
    }
}
