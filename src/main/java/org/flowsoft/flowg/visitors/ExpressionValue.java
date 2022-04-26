package org.flowsoft.flowg.visitors;

import org.flowsoft.flowg.Point;
import org.flowsoft.flowg.Type;
import org.flowsoft.flowg.TypeException;

import java.math.BigDecimal;

public class ExpressionValue {

    private final Type _type;
    private final BigDecimal _number;
    private final Boolean _boolean;
    private final Point _point;

    private ExpressionValue(Type type, BigDecimal number, Boolean bool, Point point) {
        _type = type;
        _number = number;
        _boolean = bool;
        _point = point;
    }

    public ExpressionValue(BigDecimal number) {
        this(Type.Number, number, null, null);
    }

    public ExpressionValue(Boolean bool) {
        this(Type.Boolean, null, bool, null);
    }

    public ExpressionValue(Point point) {
        this(Type.Point, null, null, point);
    }

    public ExpressionValue(Type type) {
        this(type, null, null, null);

        assert(type == Type.Void);
    }

    public Type GetType() {
        assert (_type != null);

        return _type;
    }

    public BigDecimal GetNumber() {
        if (_type != Type.Number) {
            throw new IllegalStateException();
        }
        assert (_number != null);
        return _number;
    }

    public Boolean GetBoolean() {
        if (_type != Type.Boolean) {
            throw new IllegalStateException();
        }
        assert (_boolean != null);
        return _boolean;
    }

    public Point GetPoint() {
        if (_type != Type.Point) {
            throw new IllegalStateException();
        }

        assert (_point != null);
        return _point;
    }

    @Override
    public String toString() {
        var str = "Type: " + _type + " Value: ";
        switch (_type) {

            case Void -> {}
            case Number -> str += _number;
            case Boolean -> str += _boolean;
            case Point -> str += _point.toString();
        }

        return str;
    }
}
