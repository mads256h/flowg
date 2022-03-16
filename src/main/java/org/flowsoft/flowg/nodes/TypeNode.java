package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.IVisitor;

public class TypeNode extends Node {
    private final String _type;
    public TypeNode(String type) {
        _type = type;
    }

    public String GetValue() {
        return _type;
    }

    @Override
    public <T> T Accept(IVisitor<T> visitor) {
        return visitor.Visit(this);
    }
}
