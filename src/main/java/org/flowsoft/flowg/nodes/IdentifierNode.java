package org.flowsoft.flowg.nodes;

public class IdentifierNode extends Node {
    public IdentifierNode(String identifier) {
        super(identifier);
    }

    public String GetValue() {
        return (String) value;
    }
}
