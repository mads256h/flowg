package org.flowsoft.flowg.nodes;

public abstract class NullaryNode<TValue> extends Node {
    private final TValue _value;
    protected NullaryNode(TValue value) {
        _value = value;
    }

    public TValue GetValue() {
        return _value;
    }
}
