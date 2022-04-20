package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.visitors.IVisitor;

public class GCodeFuncNode extends Node implements StatementNode {
    private final IdentifierNode _identifierNode;
    private final FormalParameterListNode _formalParameterListNode;
    private final GCodeCodeNode _gCodeCodeNode;
    public GCodeFuncNode(IdentifierNode identifierNode, FormalParameterListNode formalParameterListNode, GCodeCodeNode gCodeCodeNode){
        _identifierNode = identifierNode;
        _formalParameterListNode = formalParameterListNode;
        _gCodeCodeNode = gCodeCodeNode;
    }

    public IdentifierNode GetIdentifierNode(){ return _identifierNode; }
    public FormalParameterListNode GetFormalParameterListNode() { return _formalParameterListNode; }
    public GCodeCodeNode GetGCodeCodeNode() { return _gCodeCodeNode; }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
