package org.flowsoft.flowg.nodes;

public class ExpressionNode extends Node {
    public ExpressionNode(NumberLiteralNode child) {
        super(new Node[] {child});
    }

    public ExpressionNode(BooleanLiteralNode child) {
        super(new Node[] {child});
    }

    public NumberLiteralNode GetNumberLiteralChild() {
        return (NumberLiteralNode) children[0];
    }

    public BooleanLiteralNode GetBooleanLiteralChild() {
        return (BooleanLiteralNode) children[0];
    }
}
