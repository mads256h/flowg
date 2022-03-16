package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.IVisitor;

public class BooleanLiteralNode extends NullaryNode<Boolean> implements ExpressionNode {

    public BooleanLiteralNode(Boolean value) {
        super(value);
    }

    @Override
    public <T> T Accept(IVisitor<T> visitor) {
        return visitor.Visit(this);
    }
}
