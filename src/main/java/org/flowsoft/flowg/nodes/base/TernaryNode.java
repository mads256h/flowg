package org.flowsoft.flowg.nodes.base;

public abstract class TernaryNode<TFirstNode extends INode, TSecondNode extends INode, TThirdNode extends INode> extends Node {
    private final TFirstNode _firstNode;
    private final TSecondNode _secondNode;
    private final TThirdNode _thirdNode;

    protected TernaryNode(TFirstNode firstNode, TSecondNode secondNode, TThirdNode thirdNode) {
        _firstNode = firstNode;
        _secondNode = secondNode;
        _thirdNode = thirdNode;
    }

    public TFirstNode GetFirstNode() {
        return _firstNode;
    }

    public TSecondNode GetSecondNode() {
        return _secondNode;
    }

    public TThirdNode GetThirdNode() {
        return _thirdNode;
    }
}
