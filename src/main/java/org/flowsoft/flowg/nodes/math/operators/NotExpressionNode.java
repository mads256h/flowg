package org.flowsoft.flowg.nodes.math.operators;

import org.flowsoft.flowg.nodes.base.ExpressionNode;
import org.flowsoft.flowg.nodes.base.UnaryNode;
import org.flowsoft.flowg.visitors.IVisitor;

public class NotExpressionNode extends UnaryNode<ExpressionNode> implements ExpressionNode {
    public NotExpressionNode(ExpressionNode expressionNode) {
        super(expressionNode);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}