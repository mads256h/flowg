package org.flowsoft.flowg.nodes;

import java.math.BigDecimal;

public class NumberLiteralNode extends Node {
    public NumberLiteralNode(BigDecimal value) {
        super(value);
    }

    public BigDecimal GetValue() {
        return (BigDecimal) value;
    }
}
