package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.visitors.IVisitor;

public class GCodeCodeNode extends NullaryNode<String> implements StatementNode {
    public GCodeCodeNode(String value) {
        super(value);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
