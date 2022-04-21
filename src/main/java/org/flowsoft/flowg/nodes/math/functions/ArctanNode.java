package org.flowsoft.flowg.nodes.math.functions;

import org.flowsoft.flowg.nodes.functions.ActualParameterListNode;
import org.flowsoft.flowg.nodes.base.ExpressionNode;
import org.flowsoft.flowg.nodes.base.UnaryNode;
import org.flowsoft.flowg.visitors.IVisitor;

public class ArctanNode extends UnaryNode<ActualParameterListNode> implements ExpressionNode {
    public ArctanNode(ActualParameterListNode actualParameterListNode) {
        super(actualParameterListNode);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
