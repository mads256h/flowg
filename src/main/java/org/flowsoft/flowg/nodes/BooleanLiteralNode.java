package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.IVisitor;

public class BooleanLiteralNode extends Node implements ExpressionNode {
    private final Boolean _value;

    public BooleanLiteralNode(Boolean value) {
        _value = value;
    }

    public Boolean GetValue() {
        return _value;
    }

    @Override
    public <T> T Accept(IVisitor<T> visitor) {
        return visitor.Visit(this);
    }
}
