package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.visitors.IVisitor;

public class BooleanLiteralNode extends NullaryNode<Boolean> implements ExpressionNode {

    public BooleanLiteralNode(Boolean value) {
        super(value);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
