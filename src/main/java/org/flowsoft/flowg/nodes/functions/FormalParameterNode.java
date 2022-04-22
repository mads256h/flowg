package org.flowsoft.flowg.nodes.functions;

import org.flowsoft.flowg.nodes.IdentifierNode;
import org.flowsoft.flowg.nodes.TypeNode;
import org.flowsoft.flowg.nodes.base.BinaryNode;
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
