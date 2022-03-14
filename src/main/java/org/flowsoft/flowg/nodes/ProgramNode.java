package org.flowsoft.flowg.nodes;

public class ProgramNode extends Node {
    public ProgramNode(StatementListNode child) {
        super(new Node[] {child});
    }

    public StatementListNode GetChild() {
        return (StatementListNode) children[0];
    }
}
