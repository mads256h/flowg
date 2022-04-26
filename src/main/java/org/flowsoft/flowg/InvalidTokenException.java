package org.flowsoft.flowg;

import java_cup.runtime.ComplexSymbolFactory.Location;

public class InvalidTokenException extends Exception {
    private final Location _left;
    private final Location _right;

    public InvalidTokenException(Location left, Location right) {
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
