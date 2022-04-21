package org.flowsoft.flowg.nodes.functions;

import org.flowsoft.flowg.nodes.base.StatementNode;
import org.flowsoft.flowg.nodes.base.UnaryNode;
import org.flowsoft.flowg.visitors.IVisitor;

public class LineNode extends UnaryNode<ActualParameterListNode> implements StatementNode {
    public LineNode(ActualParameterListNode actualParameterListNode) {
        super(actualParameterListNode);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
