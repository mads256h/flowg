package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.IVisitor;

public class ProgramNode extends UnaryNode<StatementListNode> {
    public ProgramNode(StatementListNode child) {
        super(child);
    }

    @Override
    public <T> T Accept(IVisitor<T> visitor) {
        return visitor.Visit(this);
    }
}
