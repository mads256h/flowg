package org.flowsoft.flowg.exceptions.type;

import org.flowsoft.flowg.visitors.ExpressionValue;

public class ReturnException extends Exception {
    private final ExpressionValue _value;

    public ReturnException(ExpressionValue value) {
        _value = value;
    }


    public ExpressionValue GetExpressionValue() {
        return _value;
    }
}
