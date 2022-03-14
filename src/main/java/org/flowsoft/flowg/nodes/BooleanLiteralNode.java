package org.flowsoft.flowg.nodes;

public class BooleanLiteralNode extends Node {
    public BooleanLiteralNode(Boolean value) {
        super(value);
    }

    public Boolean GetValue() {
        return (Boolean) value;
    }
}
