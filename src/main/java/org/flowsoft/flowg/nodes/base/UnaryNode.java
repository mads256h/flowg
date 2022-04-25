package org.flowsoft.flowg.nodes.base;

import java_cup.runtime.ComplexSymbolFactory.Location;

public abstract class UnaryNode<TChildNode extends INode> extends Node{
    private final TChildNode _childNode;
    protected UnaryNode(TChildNode childNode, Location left, Location right) {
        super(left, right);
        _childNode = childNode;
    }

    public TChildNode GetChild() {
        return _childNode;
    }
}
