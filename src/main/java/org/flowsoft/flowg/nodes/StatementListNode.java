package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.visitors.IVisitor;

import java.util.ArrayList;

public class StatementListNode extends Node {
    private final ArrayList<StatementNode> _children;

    public StatementListNode(ArrayList<StatementNode> children) {
        _children = children;
    }

    public ArrayList<StatementNode> GetChildren() {
        return _children;
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
