package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.IVisitor;

import java.math.BigDecimal;

public class NumberLiteralNode extends NullaryNode<BigDecimal> implements ExpressionNode {

    public NumberLiteralNode(BigDecimal value) {
        super(value);
    }

    @Override
    public <T> T Accept(IVisitor<T> visitor) {
        return visitor.Visit(this);
    }
}
