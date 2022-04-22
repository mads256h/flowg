package org.flowsoft.flowg;

import java_cup.runtime.ComplexSymbolFactory.Location;

public class TypeMismatchException extends TypeException {
    private final Type _leftType;
    private final Type _rightType;
    private final Location _left;
    private final Location _right;

    public TypeMismatchException(Type leftType, Type rightType, Location left, Location right) {
        _leftType = leftType;
        _rightType = rightType;
        _left = left;
        _right = right;
    }

    public Type GetLeftType() {
        return _leftType;
    }

    public Type GetRightType() {
        return _rightType;
    }

    public Location GetLeft() {
        return _left;
    }

    public Location GetRight() {
        return _right;
    }
}
