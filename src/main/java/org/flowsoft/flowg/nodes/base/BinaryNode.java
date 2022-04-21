package org.flowsoft.flowg.nodes.base;

public abstract class BinaryNode<TLeftNode extends INode, TRightNode extends INode> extends Node {
    private final TLeftNode _leftNode;
    private final TRightNode _rightNode;

    protected BinaryNode(TLeftNode leftNode, TRightNode rightNode) {
        _leftNode = leftNode;
        _rightNode = rightNode;
    }

    public TLeftNode GetLeftChild() {
        return _leftNode;
    }

    public TRightNode GetRightChild() {
        return _rightNode;
    }

}
