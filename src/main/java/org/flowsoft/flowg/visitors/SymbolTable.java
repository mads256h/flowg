package org.flowsoft.flowg.visitors;

import org.flowsoft.flowg.Type;
import org.flowsoft.flowg.nodes.ExpressionValue;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private Map<String, VariableEntry> _variableEntries = new HashMap<>();

    public void Enter(String identifier, Type type, ExpressionValue expressionValue) {
        _variableEntries.put(identifier, new VariableEntry(identifier, type, expressionValue));
    }

    public void Enter(String identifier, Type type) {
        _variableEntries.put(identifier, new VariableEntry(identifier, type));
    }

    public VariableEntry Lookup(String identifier) {
        return _variableEntries.get(identifier);
    }

    public void Print() {
        for (var entry : _variableEntries.entrySet()) {
            var variableEntry = entry.getValue();
            System.out.println(variableEntry.Identifier
                    + ": "
                    + variableEntry.Value.toString()
//                    + variableEntry.Type
//                    + ", Numeric: "
//                    + (variableEntry.Value.GetNumber() == null ? " NULL " : variableEntry.Value.GetNumber())
//                    + ", Boolean: "
//                    + (variableEntry.Value.GetBoolean() == null ? " NULL " : variableEntry.Value.GetBoolean())
                    );
        }
    }
}

