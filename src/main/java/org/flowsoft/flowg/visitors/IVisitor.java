package org.flowsoft.flowg.visitors;

import org.flowsoft.flowg.nodes.*;

public interface IVisitor<T, TException extends Exception> {
    T Visit(StatementListNode statementListNode) throws TException;
    T Visit(DeclarationNode declarationNode) throws TException;
    T Visit(TypeNode typeNode) throws TException;
    T Visit(IdentifierNode identifierNode) throws TException;
    T Visit(NumberLiteralNode numberLiteralNode) throws TException;
    T Visit(BooleanLiteralNode booleanLiteralNode) throws TException;
    T Visit(PlusExpressionNode plusExpressionNode) throws TException;
    T Visit(MinusExpressionNode minusExpressionNode) throws TException;
    T Visit(TimesExpressionNode multiplyExpressionNode) throws TException;
    T Visit(DivideExpressionNode divisionExpressionNode)  throws TException;
}