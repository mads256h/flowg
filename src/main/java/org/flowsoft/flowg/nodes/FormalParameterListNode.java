package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.visitors.IVisitor;

import java.util.List;

public class FormalParameterListNode extends ArrayNode<FormalParameterNode>{
    public FormalParameterListNode(List<FormalParameterNode> children) {
        super(children);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
