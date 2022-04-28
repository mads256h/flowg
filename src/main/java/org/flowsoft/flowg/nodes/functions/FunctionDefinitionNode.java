package org.flowsoft.flowg.nodes.functions;

import java_cup.runtime.ComplexSymbolFactory.Location;
import org.flowsoft.flowg.nodes.IdentifierNode;
import org.flowsoft.flowg.nodes.StatementListNode;
import org.flowsoft.flowg.nodes.TypeNode;
import org.flowsoft.flowg.nodes.base.Node;
import org.flowsoft.flowg.nodes.base.StatementNode;
import org.flowsoft.flowg.visitors.IVisitor;

public class FunctionDefinitionNode extends Node implements StatementNode {
    private final TypeNode _typeNode;
    private final IdentifierNode _identifierNode;
    private final FormalParameterListNode _formalParameterListNode;
    private final StatementListNode _statementListNode;

    public FunctionDefinitionNode(TypeNode typeNode, IdentifierNode identifierNode, FormalParameterListNode formalParameterListNode, StatementListNode statementListNode, Location left, Location right) {
        super(left, right);
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
