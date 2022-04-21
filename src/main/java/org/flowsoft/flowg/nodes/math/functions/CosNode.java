package org.flowsoft.flowg.nodes.math.functions;

import org.flowsoft.flowg.nodes.ActualParameterListNode;
import org.flowsoft.flowg.nodes.ExpressionNode;
import org.flowsoft.flowg.nodes.UnaryNode;
import org.flowsoft.flowg.visitors.IVisitor;

public class CosNode extends UnaryNode<ActualParameterListNode> implements ExpressionNode {
    public CosNode(ActualParameterListNode actualParameterListNode) {
        super(actualParameterListNode);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
