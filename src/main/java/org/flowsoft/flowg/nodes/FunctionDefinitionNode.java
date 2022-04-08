package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.visitors.IVisitor;

public class FunctionDefinitionNode extends Node implements StatementNode{
    private final TypeNode _typeNode;
    private final IdentifierNode _identifierNode;
    private final FormalParameterListNode _formalParameterListNode;
    private final StatementListNode _statementListNode;

    public FunctionDefinitionNode(TypeNode typeNode, IdentifierNode identifierNode, FormalParameterListNode formalParameterListNode, StatementListNode statementListNode) {
        _typeNode = typeNode;
        _identifierNode = identifierNode;
        _formalParameterListNode = formalParameterListNode;
        _statementListNode = statementListNode;
    }


    public TypeNode GetTypeNode() {
        return _typeNode;
    }

    public IdentifierNode GetIdentifierNode() {
        return _identifierNode;
    }

    public FormalParameterListNode GetFormalParameterListNode() {
        return _formalParameterListNode;
    }

    public StatementListNode GetStatementListNode() {
        return _statementListNode;
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
