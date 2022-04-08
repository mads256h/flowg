package org.flowsoft.flowg.visitors;

import org.flowsoft.flowg.Type;

import java.util.ArrayList;

public class FunctionEntry {
    private final Type _returnType;
    private final String _identifier;
    private final ArrayList<Type> _parameterTypes;

    public FunctionEntry(Type returnType, String identifier, ArrayList<Type> parameterTypes) {
        _returnType = returnType;
        _identifier = identifier;
        _parameterTypes = parameterTypes;
    }

    public Type GetReturnType() {
        return _returnType;
    }

    public String GetIdentifier() {
        return _identifier;
    }

    public ArrayList<Type> GetParameterTypes() {
        return _parameterTypes;
    }
}
