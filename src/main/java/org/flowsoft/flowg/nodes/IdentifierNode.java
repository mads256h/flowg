package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.IVisitor;

public class IdentifierNode extends Node {
    private final String _value;

    public IdentifierNode(String value) {
        _value = value;
    }

    public String GetValue() {
        return _value;
    }

    @Override
    public <T> T Accept(IVisitor<T> visitor) {
        return visitor.Visit(this);
    }
}
