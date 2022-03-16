package org.flowsoft.flowg.nodes;

public abstract class UnaryNode<TChildNode extends INode> extends Node{
    private final TChildNode _childNode;
    protected UnaryNode(TChildNode childNode) {
        _childNode = childNode;
    }

    public TChildNode GetChild() {
        return _childNode;
    }
}
