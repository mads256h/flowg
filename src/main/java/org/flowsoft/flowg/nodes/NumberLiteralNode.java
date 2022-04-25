package org.flowsoft.flowg.nodes;

import java_cup.runtime.ComplexSymbolFactory.Location;
import org.flowsoft.flowg.nodes.base.ExpressionNode;
import org.flowsoft.flowg.nodes.base.NullaryNode;
import org.flowsoft.flowg.visitors.IVisitor;

import java.math.BigDecimal;

public class NumberLiteralNode extends NullaryNode<BigDecimal> implements ExpressionNode {

    public NumberLiteralNode(BigDecimal value, Location left, Location right) {
        super(value, left, right);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
