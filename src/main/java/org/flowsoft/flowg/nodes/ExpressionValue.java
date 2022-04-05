package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.Type;

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
        return _type;
    }

    public BigDecimal GetNumber() {
        return _number;
    }

    public Boolean GetBoolean() {
        return _boolean;
    }

    @Override
    public String toString() {
        return String.format("Type: %s, Numeric: %s, Boolean: %s", this._type, this._number, this._boolean);
    }
}
