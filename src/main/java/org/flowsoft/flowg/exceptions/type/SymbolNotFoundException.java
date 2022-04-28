package org.flowsoft.flowg.exceptions.type;

import java_cup.runtime.ComplexSymbolFactory.Location;

public class SymbolNotFoundException extends TypeException {
    private final String _identifier;
    private final Location _left;
    private final Location _right;

    public SymbolNotFoundException(String identifier, Location left, Location right) {
        _identifier = identifier;
        _left = left;
        _right = right;
    }

    public String GetIdentifier() {
        return _identifier;
    }

    public Location GetLeft() {
        return _left;
    }

    public Location GetRight() {
        return _right;
    }
}
