package org.flowsoft.flowg.nodes.base;

import java_cup.runtime.ComplexSymbolFactory.Location;

public abstract class Node implements INode {
    private final Location _left;
    private final Location _right;

    public Node(Location left, Location right) {
        _left = left;
        _right = right;
    }

    @Override
    public Location GetLeft() {
        return _left;
    }

    @Override
    public Location GetRight() {
        return _right;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}

