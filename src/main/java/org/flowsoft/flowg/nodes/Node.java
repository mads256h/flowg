package org.flowsoft.flowg.nodes;

public abstract class Node implements INode {
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}

