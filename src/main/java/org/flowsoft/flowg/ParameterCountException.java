package org.flowsoft.flowg;

import java_cup.runtime.ComplexSymbolFactory.Location;

public class ParameterCountException extends TypeException {

    private final int _expectedCount;
    private final int _actualCount;
    private final Location _left;
    private final Location _right;

    public ParameterCountException(int expectedCount, int actualCount, Location left, Location right) {
        _expectedCount = expectedCount;
        _actualCount = actualCount;
        _left = left;
        _right = right;
    }

    public int GetExpectedCount() {
        return _expectedCount;
    }

    public int GetActualCount() {
        return _actualCount;
    }

    public Location GetLeft() {
        return _left;
    }

    public Location GetRight() {
        return _right;
    }
}
