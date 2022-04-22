package org.flowsoft.flowg;

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
}
