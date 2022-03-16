package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.IVisitor;

public class StatementListNode extends BinaryNode<StatementNode, StatementListNode> {

    public StatementListNode(StatementNode child) {
        this(child, null);
    }

    public StatementListNode(StatementNode leftChild, StatementListNode rightChild) {
        super(leftChild, rightChild);
    }

    @Override
    public <T> T Accept(IVisitor<T> visitor) {
        return visitor.Visit(this);
    }
}
