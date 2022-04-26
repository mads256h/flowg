package org.flowsoft.flowg.nodes.base;

import java.util.List;

import java_cup.runtime.ComplexSymbolFactory.Location;

public abstract class ArrayNode<TNode extends INode> extends Node {

    private final List<TNode> _children;

    protected ArrayNode(List<TNode> children, Location left, Location right) {
        super(left, right);
        _children = children;
    }

    public List<TNode> GetChildren() {
        return _children;
    }
}
