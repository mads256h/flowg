package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.IVisitor;

public class ProgramNode extends Node {
    private final StatementListNode _child;
    public ProgramNode(StatementListNode child) {
        _child = child;
    }

    public StatementListNode GetChild() {
        return _child;
    }

    @Override
    public <T> T Accept(IVisitor<T> visitor) {
        return visitor.Visit(this);
    }
}
