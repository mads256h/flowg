package org.flowsoft.flowg.exceptions;

import java_cup.runtime.ComplexSymbolFactory.Location;

public class TabException extends Exception {
    private final Location _left;
    private final Location _right;

    public TabException(Location left, Location right) {
        _left = left;
        _right = right;
    }

    public Location GetLeft() {
        return _left;
    }

    public Location GetRight() {
        return _right;
    }
}
