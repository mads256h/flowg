package org.flowsoft.flowg.symboltables;

import org.flowsoft.flowg.Cloneable;
import org.flowsoft.flowg.Type;

public class VariableEntry implements Cloneable<VariableEntry> {
    private final String _identifier;
    private final Type _type;

    public VariableEntry(String identifier, Type type) {
        _identifier = identifier;
        _type = type;
    }

    public String GetIdentifier() {
        return _identifier;
    }

    public Type GetType() {
        return _type;
    }


    @Override
    public VariableEntry Clone() {
        return new VariableEntry(GetIdentifier(), GetType());
    }
}
