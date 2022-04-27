package org.flowsoft.flowg.nodes.math.operators;

import java_cup.runtime.ComplexSymbolFactory.Location;
import org.flowsoft.flowg.nodes.base.BinaryNode;
import org.flowsoft.flowg.nodes.base.ExpressionNode;
import org.flowsoft.flowg.nodes.base.UnaryNode;
import org.flowsoft.flowg.visitors.IVisitor;

public class ArithmeticNegationExpressionNode extends UnaryNode<ExpressionNode> implements ExpressionNode {

    public ArithmeticNegationExpressionNode(ExpressionNode child, Location left, Location right) {
        super(child, left, right);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
