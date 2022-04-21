package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.nodes.base.ExpressionNode;
import org.flowsoft.flowg.nodes.base.TernaryNode;
import org.flowsoft.flowg.visitors.IVisitor;

public class PointNode extends TernaryNode<ExpressionNode, ExpressionNode, ExpressionNode> implements ExpressionNode {

    public PointNode(ExpressionNode x, ExpressionNode y, ExpressionNode z) {
        super(x, y, z);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
