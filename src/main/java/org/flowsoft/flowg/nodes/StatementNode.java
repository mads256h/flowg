package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.IVisitor;

public class StatementNode extends UnaryNode<DeclarationNode> {
    public StatementNode(DeclarationNode child) {
        super(child);
    }

    @Override
    public <T> T Accept(IVisitor<T> visitor) {
        return visitor.Visit(this);
    }
}
