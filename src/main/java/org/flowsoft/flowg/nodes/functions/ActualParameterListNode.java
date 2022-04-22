package org.flowsoft.flowg.nodes.functions;

import org.flowsoft.flowg.nodes.base.ArrayNode;
import org.flowsoft.flowg.nodes.base.ExpressionNode;
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
