package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.Type;

import java.awt.*;
import java.math.BigDecimal;

public class ExpressionValue {

    private Type _type = null;
    private BigDecimal _number = null;
    private Boolean _boolean = null;
    private Type _point = null;

    public ExpressionValue(Type Type, BigDecimal Number) {
        _type = Type;
        _number = Number;
    }

    public ExpressionValue(Type Type, Boolean Boolean) {
        _type = Type;
        _boolean = Boolean;
    }

    public ExpressionValue(Type Type) {
        _type = Type;
    }

    public Type getType() {
        return _type;
    }

    public BigDecimal getNumber() {
        return _number;
    }

    public Boolean getBoolean() {
        return _boolean;
    }

    public Type getPoint() {
        return _point;
    }

    public void setType(Type type) { this._type = _type; }

    public void setNumber(BigDecimal _number) {
        this._number = _number;
    }

    public void setBoolean(Boolean _boolean) {
        this._boolean = _boolean;
    }

    public void setPoint(Type _point) {
        this._point = _point;
    }
}
