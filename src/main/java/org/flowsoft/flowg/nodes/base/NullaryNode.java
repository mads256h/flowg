package org.flowsoft.flowg.nodes.base;

import java_cup.runtime.ComplexSymbolFactory.Location;

public abstract class NullaryNode<TValue> extends Node {
    private final TValue _value;
    protected NullaryNode(TValue value, Location left, Location right) {
        super(left, right);
        _value = value;
    }

    public TValue GetValue() {
        return _value;
    }
}
