package org.flowsoft.flowg.visitors;

import org.flowsoft.flowg.Type;

public class VariableEntry {
    public VariableEntry(String identifier, org.flowsoft.flowg.Type type) {
        Identifier = identifier;
        Type = type;
    }

    public String Identifier;
    public Type Type;
}
