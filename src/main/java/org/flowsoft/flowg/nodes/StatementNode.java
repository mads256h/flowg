package org.flowsoft.flowg.nodes;

public class StatementNode extends Node {
    public StatementNode(DeclarationNode child) {
        super(new Node[] {child});
    }

    public DeclarationNode GetChild() {
        return (DeclarationNode) children[0];
    }
}
