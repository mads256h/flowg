package org.flowsoft.flowg.visitors;

import org.flowsoft.flowg.nodes.*;
import org.flowsoft.flowg.nodes.controlflow.ForToNode;
import org.flowsoft.flowg.nodes.controlflow.IfElseNode;
import org.flowsoft.flowg.nodes.controlflow.ReturnNode;
import org.flowsoft.flowg.nodes.functions.*;
import org.flowsoft.flowg.nodes.math.functions.*;
import org.flowsoft.flowg.nodes.math.operators.*;

public interface IVisitor<T, TException extends Exception> {
    T Visit(StatementListNode statementListNode) throws TException;

    // Variable declaration and assignment
    T Visit(DeclarationNode declarationNode) throws TException;
    T Visit(AssignmentNode assignmentNode) throws TException;

    // Builtin functions
    T Visit(MoveNode moveNode) throws TException;
    T Visit(LineNode lineNode) throws TException;
    T Visit(CWArcNode cwArcNode) throws TException;
    T Visit(CCWArcNode ccwArcNode) throws TException;

    // Math builtins
    T Visit(SqrtNode sqrtNode) throws TException;
    T Visit(SinNode sinNode) throws TException;
    T Visit(CosNode cosNode) throws TException;
    T Visit(TanNode tanNode) throws TException;
    T Visit(ArcsinNode arcsinNode) throws TException;
    T Visit(ArccosNode arccosNode) throws TException;
    T Visit(ArctanNode arctanNode) throws TException;

    // User supplied functions
    T Visit(FunctionDefinitionNode functionDefinitionNode) throws TException;
    T Visit(FunctionCallNode functionCallNode) throws TException;
    T Visit(ActualParameterListNode actualParameterListNode) throws TException;
    T Visit(FormalParameterListNode formalParameterListNode) throws TException;
    T Visit(FormalParameterNode formalParameterNode) throws TException;

    // Arithmetic operators
    T Visit(PlusExpressionNode plusExpressionNode) throws TException;
    T Visit(MinusExpressionNode minusExpressionNode) throws TException;
    T Visit(TimesExpressionNode multiplyExpressionNode) throws TException;
    T Visit(DivideExpressionNode divisionExpressionNode) throws TException;
    T Visit(PowerExpressionNode powerExpressionNode) throws TException;

    // Unary operators
    T Visit(ArithmeticNegationExpressionNode arithmeticNegationExpressionNode) throws TException;
    T Visit(IncrementExpressionNode incrementExpressionNode) throws TException;
    T Visit(DecrementExpressionNode decrementExpressionNode) throws TException;

    // Boolean operators
    T Visit(GreaterThanExpressionNode greaterThanExpressionNode) throws TException;
    T Visit(LessThanExpressionNode lessThanExpressionNode) throws TException;
    T Visit(EqualsExpressionNode equalsExpressionNode) throws TException;
    T Visit(GreaterThanEqualsExpressionNode greaterThanEqualsExpressionNode) throws TException;
    T Visit(LessThanEqualsExpressionNode lessThanEqualsExpressionNode) throws TException;
    T Visit(AndExpressionNode andExpressionNode) throws TException;
    T Visit(OrExpressionNode orExpressionNode) throws TException;
    T Visit(NotExpressionNode notExpressionNode) throws TException;

    // Control flow
    T Visit(ForToNode forToNode) throws TException;
    T Visit(ReturnNode returnNode) throws TException;
    T Visit(IfElseNode ifElseNode) throws TException;

    T Visit(TypeNode typeNode) throws TException;
    T Visit(IdentifierNode identifierNode) throws TException;
    T Visit(IdentifierExpressionNode identifierExpressionNode) throws TException;
    T Visit(NumberLiteralNode numberLiteralNode) throws TException;
    T Visit(BooleanLiteralNode booleanLiteralNode) throws TException;
    T Visit(PointNode pointNode) throws TException;
    T Visit(PointEntryNode pointEntryNode) throws TException;
}
