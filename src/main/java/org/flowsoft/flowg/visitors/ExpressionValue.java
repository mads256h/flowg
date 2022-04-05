package org.flowsoft.flowg.visitors;

import org.flowsoft.flowg.Type;
import org.flowsoft.flowg.TypeException;

import java.math.BigDecimal;

public class ExpressionValue {

    private Type _type = null;
    private BigDecimal _number = null;
    private Boolean _boolean = null;

    public ExpressionValue(BigDecimal Number) {
        _number = Number;
        _type = Type.Number;
    }

    public ExpressionValue(Boolean Boolean) {
        _boolean = Boolean;
        _type = Type.Boolean;
    }

    public ExpressionValue(Type Type) {
        _type = Type;
    }

    public Type GetType() {
        assert (_type != null);

        return _type;
    }

    public BigDecimal GetNumber() throws TypeException {
        if (_type != Type.Number) {
            throw new TypeException();
        }
        assert (_number != null);
        return _number;
    }

    public Boolean GetBoolean() throws TypeException {
        if (_type != Type.Boolean) {
            throw new TypeException();
        }
        assert (_boolean != null);
        return _boolean;
    }

    @Override
    public String toString() {
        var str = "Type: " + _type + " Value: ";
        switch (_type) {

            case Void -> {
            }
            case Number -> {
                str += _number;
            }
            case Boolean -> {
                str += _boolean;
            }
            case Point -> {
                // TODO
            }
        }

        return str;
    }
}
