package org.flowsoft.flowg.visitors;

import org.flowsoft.flowg.nodes.*;

public interface IVisitor<T, TException extends Exception> {
    T Visit(StatementListNode statementListNode) throws TException;
    T Visit(MoveNode moveNode) throws TException;
    T Visit(SqrtNode sqrtNode) throws TException;
    T Visit(ActualParameterListNode actualParameterListNode) throws TException;
    T Visit(FormalParameterListNode formalParameterListNode) throws TException;
    T Visit(FormalParameterNode formalParameterNode) throws TException;
    T Visit(DeclarationNode declarationNode) throws TException;
    T Visit(FunctionDefinitionNode functionDefinitionNode) throws TException;
    T Visit(TypeNode typeNode) throws TException;
    T Visit(IdentifierNode identifierNode) throws TException;
    T Visit(NumberLiteralNode numberLiteralNode) throws TException;
    T Visit(BooleanLiteralNode booleanLiteralNode) throws TException;
    T Visit(PointNode pointNode) throws TException;
    T Visit(PlusExpressionNode plusExpressionNode) throws TException;
    T Visit(MinusExpressionNode minusExpressionNode) throws TException;
    T Visit(TimesExpressionNode multiplyExpressionNode) throws TException;
    T Visit(DivideExpressionNode divisionExpressionNode) throws TException;
    T Visit(PowerExpressionNode powerExpressionNode) throws TException;
    T Visit(NotExpressionNode notExpressionNode) throws TException;
    T Visit(IdentifierExpressionNode identifierExpressionNode) throws TException;
    T Visit(FunctionCallNode functionCallNode) throws TException;
    T Visit(ReturnNode returnNode) throws TException;
    T Visit(AssignmentNode assignmentNode) throws TException;
    T Visit(ForToNode forToNode) throws TException;
    T Visit(GreaterThanExpressionNode greaterThanExpressionNode) throws TException;
    T Visit(LessThanExpressionNode lessThanExpressionNode) throws TException;
    T Visit(EqualsExpressionNode equalsExpressionNode) throws TException;
    T Visit(GreaterThanEqualsExpressionNode greaterThanEqualsExpressionNode) throws TException;
    T Visit(LessThanEqualsExpressionNode lessThanEqualsExpressionNode) throws TException;
    T Visit(AndExpressionNode andExpressionNode) throws TException;
    T Visit(OrExpressionNode orExpressionNode) throws TException;
}
