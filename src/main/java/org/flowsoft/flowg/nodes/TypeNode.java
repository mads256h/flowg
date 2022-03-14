package org.flowsoft.flowg.nodes;

public class TypeNode extends Node {
    public TypeNode(String type) {
        super(type);
    }

    public String GetValue() {
        return (String) value;
    }
}
