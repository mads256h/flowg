package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.IVisitor;

public class DeclarationNode extends Node implements StatementNode {
    private final TypeNode _type;
    private final IdentifierNode _identifier;
    private final ExpressionNode _expression;

    public DeclarationNode(TypeNode type, IdentifierNode identifier, ExpressionNode expression) {
        _type = type;
        _identifier = identifier;
        _expression = expression;
    }

    public TypeNode GetTypeChild() {
        return _type;
    }

    public IdentifierNode GetIdentifierChild() {
        return _identifier;
    }

    public ExpressionNode GetExpressionChild() {
        return _expression;
    }

    @Override
    public <T> T Accept(IVisitor<T> visitor) {
        return visitor.Visit(this);
    }
}
