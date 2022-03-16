package org.flowsoft.flowg;

import org.flowsoft.flowg.nodes.*;

public interface IVisitor<T> {
    T Visit(ProgramNode programNode);
    T Visit(StatementListNode statementListNode);
    T Visit(StatementNode statementNode);
    T Visit(DeclarationNode declarationNode);
    T Visit(TypeNode typeNode);
    T Visit(IdentifierNode identifierNode);
    T Visit(NumberLiteralNode numberLiteralNode);
    T Visit(BooleanLiteralNode booleanLiteralNode);
}
