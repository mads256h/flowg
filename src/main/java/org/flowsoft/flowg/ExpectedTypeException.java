package org.flowsoft.flowg;

import java_cup.runtime.ComplexSymbolFactory.Location;

public class ExpectedTypeException extends TypeException {
    private final Type _expected;
    private final Type _actual;
    private final Location _left;
    private final Location _right;

    public ExpectedTypeException(Type expected, Type actual, Location left, Location right) {
        _expected = expected;
        _actual = actual;
        _left = left;
        _right = right;
    }

    public Type GetExpected() {
        return _expected;
    }

    public Type GetActual() {
        return _actual;
    }

    public Location GetLeft() {
        return _left;
    }

    public Location GetRight() {
        return _right;
    }
}
