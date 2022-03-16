package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.IVisitor;

public class StatementNode extends Node {
    private final DeclarationNode _declaration;
    public StatementNode(DeclarationNode child) {
        _declaration = child;
    }

    public DeclarationNode GetChild() {
        return _declaration;
    }

    @Override
    public <T> T Accept(IVisitor<T> visitor) {
        return visitor.Visit(this);
    }
}
