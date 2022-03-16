package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.IVisitor;

public class StatementListNode extends Node {
    private final StatementNode _statement;
    private final StatementListNode _statementList;

    public StatementListNode(StatementNode child) {
        this(child, null);
    }

    public StatementListNode(StatementNode leftChild, StatementListNode rightChild) {
        _statement = leftChild;
        _statementList = rightChild;
    }

    public StatementNode GetStatementChild() {
        return _statement;
    }

    public StatementListNode GetStatementListChild() {
        return _statementList;
    }

    @Override
    public <T> T Accept(IVisitor<T> visitor) {
        return visitor.Visit(this);
    }
}
