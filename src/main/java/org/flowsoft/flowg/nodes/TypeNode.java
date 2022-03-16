package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.IVisitor;

public class TypeNode extends NullaryNode<String> {
    public TypeNode(String type) {
        super(type);
    }

    @Override
    public <T> T Accept(IVisitor<T> visitor) {
        return visitor.Visit(this);
    }
}
