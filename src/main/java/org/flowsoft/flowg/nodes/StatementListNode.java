package org.flowsoft.flowg.nodes;

public class StatementListNode extends Node {
    public StatementListNode(StatementNode leftChild, StatementListNode rightChild) {
        super(new Node[] {leftChild, rightChild});
    }

    public StatementListNode(StatementNode child) {
        super(new Node[]{child});
    }

    public StatementNode GetLeftChild() {
        return (StatementNode) children[0];
    }

    public StatementListNode GetRightChild() {
        if (children.length == 2)
            return (StatementListNode) children[1];
        else
            return null;
    }

}
