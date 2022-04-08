package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.visitors.IVisitor;

public class FormalParameterNode extends BinaryNode<TypeNode, IdentifierNode> {
    public FormalParameterNode(TypeNode typeNode, IdentifierNode identifierNode) {
        super(typeNode, identifierNode);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
