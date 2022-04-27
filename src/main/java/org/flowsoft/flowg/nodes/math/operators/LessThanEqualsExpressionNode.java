package org.flowsoft.flowg.nodes.math.operators;

import java_cup.runtime.ComplexSymbolFactory.Location;
import org.flowsoft.flowg.nodes.base.BinaryNode;
import org.flowsoft.flowg.nodes.base.ExpressionNode;
import org.flowsoft.flowg.visitors.IVisitor;

public class LessThanEqualsExpressionNode extends BinaryNode<ExpressionNode, ExpressionNode> implements ExpressionNode {
    public LessThanEqualsExpressionNode(ExpressionNode leftChild, ExpressionNode rightChild, Location left, Location right) {
        super(leftChild, rightChild, left, right);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
