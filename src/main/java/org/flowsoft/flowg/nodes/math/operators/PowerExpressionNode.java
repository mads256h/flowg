package org.flowsoft.flowg.nodes.math.operators;

import org.flowsoft.flowg.nodes.base.BinaryNode;
import org.flowsoft.flowg.nodes.base.ExpressionNode;
import org.flowsoft.flowg.visitors.IVisitor;

public class PowerExpressionNode extends BinaryNode<ExpressionNode, ExpressionNode> implements ExpressionNode {
    public PowerExpressionNode(ExpressionNode expressionNode, ExpressionNode expressionNode2) {
        super(expressionNode, expressionNode2);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
