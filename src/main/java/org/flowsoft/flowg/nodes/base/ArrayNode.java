package org.flowsoft.flowg.nodes.base;

import java.util.List;

public abstract class ArrayNode<TNode extends INode> extends Node {

    private final List<TNode> _children;

    protected ArrayNode(List<TNode> children) {
        _children = children;
    }

    public List<TNode> GetChildren() {
        return _children;
    }
}
