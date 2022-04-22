package org.flowsoft.flowg;

import java_cup.runtime.ComplexSymbolFactory.Location;

public class RedeclarationException extends TypeException {
    private final Location _left;
    private final Location _right;

    //TODO: Add location of defined symbol
    public RedeclarationException(Location left, Location right) {
        _left = left;
        _right = right;
    }
}
