package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.visitors.IVisitor;

public class StatementListNode extends BinaryNode<StatementNode, StatementListNode> {

    public StatementListNode(StatementNode child) {
        this(child, null);
    }

    public StatementListNode(StatementNode leftChild, StatementListNode rightChild) {
        super(leftChild, rightChild);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
