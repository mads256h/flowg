package org.flowsoft.flowg.nodes.base;

import java_cup.runtime.ComplexSymbolFactory.Location;

public abstract class TernaryNode<TFirstNode extends INode, TSecondNode extends INode, TThirdNode extends INode> extends Node {
    private final TFirstNode _firstNode;
    private final TSecondNode _secondNode;
    private final TThirdNode _thirdNode;

    protected TernaryNode(TFirstNode firstNode, TSecondNode secondNode, TThirdNode thirdNode, Location left, Location right) {
        super(left, right);
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
