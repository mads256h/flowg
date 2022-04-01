package org.flowsoft.flowg.nodes;

import java.util.List;

public abstract class ArrayNode<TNode> extends Node {

    private final List<TNode> _children;

    public ArrayNode(List<TNode> children) {
        _children = children;
    }

    public List<TNode> GetChildren() {
        return _children;
    }
}