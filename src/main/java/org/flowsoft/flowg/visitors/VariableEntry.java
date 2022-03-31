package org.flowsoft.flowg.visitors;

import org.flowsoft.flowg.Type;
import org.flowsoft.flowg.nodes.ExpressionValue;

public class VariableEntry {

    public VariableEntry(String identifier, org.flowsoft.flowg.Type type, ExpressionValue expressionValue) {
        Identifier = identifier;
        Type = type;
        Value = expressionValue;
    }

    public VariableEntry(String identifier, org.flowsoft.flowg.Type type) {
        Identifier = identifier;
        Type = type;
        Value = null;
    }

    public String Identifier;
    public Type Type;
    public ExpressionValue Value;
}
