package org.flowsoft.flowg.exceptions.type;

import java_cup.runtime.ComplexSymbolFactory.Location;

public class IncludeNotFoundException extends TypeException {
    private final String _filename;
    private final Location _left;
    private final Location _right;

    public IncludeNotFoundException(String filename, Location left, Location right) {
        _filename = filename;
        _left = left;
        _right = right;
    }

    public String GetFilename() {
        return _filename;
    }

    public Location GetLeft() {
        return _left;
    }

    public Location GetRight() {
        return _right;
    }
}
