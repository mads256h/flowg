package org.flowsoft.flowg.nodes;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public abstract class Node {
    protected Node[] children;
    protected Object value;

    protected Node(Object value) {
        this(value, new Node[0]);
    }

    protected Node(Node[] children) {
        this(null, children);
    }

    protected Node(Object value, Node[] children){
        this.value = value;
        this.children = children;
    }

    public void Print() {
        Print(0);
    }

    private void Print(int indentation) {
        System.out.println("  ".repeat(indentation) + this);
        for (var child : children) {
            child.Print(indentation + 1);
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + (value != null ? "{value=" + value + '}' : "");
    }
}

