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
    public String Visit(LineNode lineNode) throws NoException {
        return "line(" + lineNode.GetChild().Accept(this) + ")";
    }

    @Override
    public String Visit(SqrtNode sqrtNode) throws NoException {
        return "sqrt(" + sqrtNode.GetChild().Accept(this) + ")";
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
    public String Visit(FormalParameterListNode formalParameterListNode) throws NoException {
        var sb = new StringBuilder();
        boolean first = true;
        for (var child : formalParameterListNode.GetChildren()) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(child.Accept(this));
            first = false;
        }

        return sb.toString();
    }

    @Override
    public String Visit(FormalParameterNode formalParameterNode) throws NoException {
        return formalParameterNode.GetLeftChild().Accept(this) + " " + formalParameterNode.GetRightChild().Accept(this);
    }

    @Override
    public String Visit(DeclarationNode declarationNode) throws NoException {
        return declarationNode.GetFirstNode().Accept(this)
                + " " + declarationNode.GetSecondNode().Accept(this)
                + " ="
                + " " + declarationNode.GetThirdNode().Accept(this);
    }

    @Override
    public String Visit(FunctionDefinitionNode functionDefinitionNode) throws NoException {
        return
                functionDefinitionNode.GetTypeNode().Accept(this) + " " +
                functionDefinitionNode.GetIdentifierNode().Accept(this) + "(" +
                functionDefinitionNode.GetFormalParameterListNode().Accept(this) + ") {\n" +
                functionDefinitionNode.GetStatementListNode().Accept(this) + "}";
    }

    @Override
    public String Visit(GCodeFuncNode gCodeFuncNode) throws NoException {
        return null;
    }

    @Override
    public String Visit(GCodeCodeNode gCodeCodeNode) throws NoException {
        return null;
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
    public String Visit(PowerExpressionNode powerExpressionNode) throws NoException {
        return powerExpressionNode.GetLeftChild().Accept(this) + "^" + powerExpressionNode.GetRightChild().Accept(this);
    }
    
    @Override
    public String Visit(NotExpressionNode notExpressionNode) throws NoException {
        return "!" + notExpressionNode.GetChild().Accept(this);
    }

    @Override
    public String Visit(IdentifierExpressionNode identifierExpressionNode) throws NoException {
        return identifierExpressionNode.GetChild().Accept(this);
    }

    @Override
    public String Visit(FunctionCallNode functionCallNode) throws NoException {
        return
                functionCallNode.GetLeftChild().Accept(this) + "(" +
                functionCallNode.GetRightChild().Accept(this) + ")";
    }

    @Override
    public String Visit(ReturnNode returnNode) throws NoException {
        var child = returnNode.GetChild();
        return "return" + (child == null ? "" : " " + child.Accept(this));
    }

    @Override
    public String Visit(AssignmentNode assignmentNode) throws NoException {
        return assignmentNode.GetLeftChild().Accept(this) + " = " + assignmentNode.GetRightChild().Accept(this);
    }

    @Override
    public String Visit(ForToNode forToNode) throws NoException {
        return "for (" + forToNode.GetFirstNode().Accept(this) + " to " + forToNode.GetSecondNode().Accept(this) + ") {\n" + forToNode.GetThirdNode().Accept(this) + "}";
    }

    @Override
    public String Visit(GreaterThanExpressionNode greaterThanExpressionNode) throws NoException {
        return greaterThanExpressionNode.GetLeftChild().Accept(this) + " > " + greaterThanExpressionNode.GetRightChild().Accept(this);
    }

    @Override
    public String Visit(LessThanExpressionNode lessThanExpressionNode) throws NoException {
        return lessThanExpressionNode.GetLeftChild().Accept(this) + " < " + lessThanExpressionNode.GetRightChild().Accept(this);
    }

    @Override
    public String Visit(EqualsExpressionNode equalsExpressionNode) throws NoException {
        return equalsExpressionNode.GetLeftChild().Accept(this) + " == " + equalsExpressionNode.GetRightChild().Accept(this);
    }

    @Override
    public String Visit(GreaterThanEqualsExpressionNode greaterThanEqualsExpressionNode) throws NoException {
        return greaterThanEqualsExpressionNode.GetLeftChild().Accept(this) + " >= " + greaterThanEqualsExpressionNode.GetRightChild().Accept(this);
    }

    @Override
    public String Visit(LessThanEqualsExpressionNode lessThanEqualsExpressionNode) throws NoException {
        return lessThanEqualsExpressionNode.GetLeftChild().Accept(this) + " <= " + lessThanEqualsExpressionNode.GetRightChild().Accept(this);
    }

    @Override
    public String Visit(AndExpressionNode andExpressionNode) throws NoException {
        return andExpressionNode.GetLeftChild().Accept(this) + " && " + andExpressionNode.GetRightChild().Accept(this);
    }

    @Override
    public String Visit(OrExpressionNode orExpressionNode) throws NoException {
        return orExpressionNode.GetLeftChild().Accept(this) + " || " + orExpressionNode.GetRightChild().Accept(this);
    }
}
