package org.flowsoft.flowg.symboltables;

import org.flowsoft.flowg.Type;
import java_cup.runtime.ComplexSymbolFactory.Location;
import org.flowsoft.flowg.TypeException;
import org.flowsoft.flowg.visitors.ExpressionValue;

import java.util.HashMap;
import java.util.Map;

public class RuntimeSymbolTable {
    private static final Location N = new Location("null", 0, 0, 0);

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
        }else if(_parent != null){
            _parent.SetValue(identifier, expressionValue);
        }
        else if (_parent != null) {
            _parent.SetValue(identifier, expressionValue);
        }
        else {
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
    /* Function that only finds variables from current symboltable
    public ExpressionValue LookupLocalVariable(String identifier) throws TypeException {
        if (_variableMap.containsKey(identifier)) {
            var value = _variableMap.get(identifier);
            assert (value != null);
            return value;
        }
        return new ExpressionValue(Type.Void);
    }
*/
    public FunctionEntry LookupFunction(String identifier) throws TypeException {
        return _base.LookupFunction(identifier, N, N);
    }
}
