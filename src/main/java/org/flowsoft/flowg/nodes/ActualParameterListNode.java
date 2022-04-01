package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.visitors.IVisitor;

import java.util.List;

public class ActualParameterListNode extends ArrayNode<ExpressionNode> {

    public ActualParameterListNode(List<ExpressionNode> children) {
        super(children);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
