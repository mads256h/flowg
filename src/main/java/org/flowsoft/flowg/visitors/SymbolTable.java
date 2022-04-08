package org.flowsoft.flowg.visitors;

import org.flowsoft.flowg.Type;
import org.flowsoft.flowg.TypeException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private final Map<String, VariableEntry> _variableEntries = new HashMap<>();
    private final Map<String, FunctionEntry> _functionEntries = new HashMap<>();

    public void Enter(String identifier, Type type, ExpressionValue expressionValue) {
        _variableEntries.put(identifier, new VariableEntry(identifier, type, expressionValue));
    }

    public void Enter(String identifier, Type type) {
        _variableEntries.put(identifier, new VariableEntry(identifier, type));
    }

    public void Enter(Type returnType, String identifier, ArrayList<Type> parameterTypes) {
        _functionEntries.put(identifier, new FunctionEntry(returnType, identifier, parameterTypes));
    }

    public VariableEntry LookupVariable(String identifier) throws TypeException {
        if (_variableEntries.containsKey(identifier)) {
            return _variableEntries.get(identifier);
        }
        throw new TypeException();
    }

    public FunctionEntry LookupFunction(String identifier) throws TypeException {
        if (_functionEntries.containsKey(identifier)) {
            return _functionEntries.get(identifier);
        }

        throw new TypeException();
    }

    public void Print() {
        System.out.println("Variables:");
        for (var entry : _variableEntries.entrySet()) {
            var variableEntry = entry.getValue();
            System.out.println(variableEntry.Identifier
                    + ": "
                    + variableEntry.Value.toString()
                    );
        }

        System.out.println("Functions:");
        for (var entry : _functionEntries.entrySet()) {
            var functionEntry = entry.getValue();
            System.out.print(functionEntry.GetReturnType()
                    + " "
                    + functionEntry.GetIdentifier()
                    + "(");
            var first = true;
            for (var type : functionEntry.GetParameterTypes()) {
                if (!first) {
                    System.out.print(", ");
                }
                first = false;

                System.out.print(type);
            }
            System.out.println(")");
        }
    }
}

