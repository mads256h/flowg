package org.flowsoft.flowg.nodes;

public class DeclarationNode extends Node {
    public DeclarationNode(TypeNode type, IdentifierNode identifier, ExpressionNode expression) {
        super(new Node[] {type, identifier, expression});
    }

    public TypeNode GetTypeChild() {
        return (TypeNode) children[0];
    }

    public IdentifierNode GetIdentifierChild() {
        return (IdentifierNode) children[1];
    }

    public ExpressionNode GetExpressionChild() {
        return (ExpressionNode) children[2];
    }
}
