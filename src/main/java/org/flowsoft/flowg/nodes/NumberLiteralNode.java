package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.IVisitor;

import java.math.BigDecimal;

public class NumberLiteralNode extends Node implements ExpressionNode {
    private final BigDecimal _value;

    public NumberLiteralNode(BigDecimal value) {
        _value = value;
    }

    public BigDecimal GetValue() {
        return _value;
    }

    @Override
    public <T> T Accept(IVisitor<T> visitor) {
        return visitor.Visit(this);
    }
}
