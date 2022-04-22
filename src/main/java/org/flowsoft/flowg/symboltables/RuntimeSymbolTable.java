package org.flowsoft.flowg.symboltables;

import org.flowsoft.flowg.TypeException;
import org.flowsoft.flowg.visitors.ExpressionValue;

import java.util.HashMap;
import java.util.Map;

public class RuntimeSymbolTable {
    private final SymbolTable _base;
    private final RuntimeSymbolTable _parent;
    
    private final Map<String, ExpressionValue> _variableMap = new HashMap<>();

    public RuntimeSymbolTable(SymbolTable base, RuntimeSymbolTable parent) {
        _base = base;
        _parent = parent;

        for (var variable : base.GetVariables()) {
            _variableMap.put(variable.GetIdentifier(), null);
        }
    }

    public RuntimeSymbolTable Create(SymbolTable symbolTable) {
        return new RuntimeSymbolTable(symbolTable, this);
    }

    public void SetValue(String identifier, ExpressionValue expressionValue) {
        if (_variableMap.containsKey(identifier)) {
            _variableMap.put(identifier, expressionValue);
        }
        else
        {
            throw new IllegalStateException();
        }
    }

    public ExpressionValue LookupVariable(String identifier) {
        if (_variableMap.containsKey(identifier)) {
            var value = _variableMap.get(identifier);
            assert(value != null);
            return value;
        }

        if (_parent != null) {
            return _parent.LookupVariable(identifier);
        }

        throw new IllegalStateException();
    }

    public FunctionEntry LookupFunction(String identifier) throws TypeException {
        return _base.LookupFunction(identifier);
    }
}
