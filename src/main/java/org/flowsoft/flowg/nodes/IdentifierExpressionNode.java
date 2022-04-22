package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.nodes.base.ExpressionNode;
import org.flowsoft.flowg.nodes.base.UnaryNode;
import org.flowsoft.flowg.visitors.IVisitor;

public class IdentifierExpressionNode extends UnaryNode<IdentifierNode> implements ExpressionNode {
    public IdentifierExpressionNode(IdentifierNode identifierNode) {
        super(identifierNode);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
