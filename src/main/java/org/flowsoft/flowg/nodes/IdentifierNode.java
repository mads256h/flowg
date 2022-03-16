package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.IVisitor;

public class IdentifierNode extends NullaryNode<String> {
    public IdentifierNode(String value) {
        super(value);
    }

    @Override
    public <T> T Accept(IVisitor<T> visitor) {
        return visitor.Visit(this);
    }
}
