package org.flowsoft.flowg.symboltables;

import org.flowsoft.flowg.Type;
import org.flowsoft.flowg.TypeException;
import org.flowsoft.flowg.nodes.functions.FormalParameterNode;
import org.flowsoft.flowg.nodes.StatementListNode;
import org.flowsoft.flowg.Cloneable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable implements Cloneable<SymbolTable> {
    private final SymbolTable _parent;

    private final Map<String, VariableEntry> _variableEntries;
    private final Map<String, FunctionEntry> _functionEntries;


    public SymbolTable(SymbolTable parent) {
        _parent = parent;
        _variableEntries = new HashMap<>();
        _functionEntries = new HashMap<>();
    }

    private SymbolTable(SymbolTable parent, Map<String, VariableEntry> variableEntries, Map<String, FunctionEntry> functionEntries) {
        _parent = parent;
        _variableEntries = variableEntries;
        _functionEntries = functionEntries;
    }

    public void Enter(String identifier, Type type) throws TypeException {
        if (!_variableEntries.containsKey(identifier)) {
            _variableEntries.put(identifier, new VariableEntry(identifier, type));
        }
        else {
            throw new TypeException();
        }
    }

    public void Enter(Type returnType, String identifier, ArrayList<FormalParameterNode> formalParameters, StatementListNode functionBody, SymbolTable parent) throws TypeException {
        if (!_functionEntries.containsKey(identifier)) {
            _functionEntries.put(identifier, new FunctionEntry(returnType, identifier, formalParameters, functionBody, parent));
        }
        else {
            throw new TypeException();
        }
    }

    List<VariableEntry> GetVariables() {
        return _variableEntries.values().stream().toList();
    }

    public VariableEntry LookupVariable(String identifier) throws TypeException {
        if (_variableEntries.containsKey(identifier)) {
            return _variableEntries.get(identifier);
        }

        if (_parent != null) {
            return _parent.LookupVariable(identifier);
        }

        throw new TypeException();
    }

    public FunctionEntry LookupFunction(String identifier) throws TypeException {
        if (_functionEntries.containsKey(identifier)) {
            return _functionEntries.get(identifier);
        }

        if (_parent != null) {
            return _parent.LookupFunction(identifier);
        }

        throw new TypeException();
    }

    public void Print() {
        System.out.println("Variables:");
        for (var entry : _variableEntries.entrySet()) {
            var variableEntry = entry.getValue();
            System.out.println(variableEntry.GetType() + " " + variableEntry.GetIdentifier());
        }

        System.out.println("Functions:");
        for (var entry : _functionEntries.entrySet()) {
            var functionEntry = entry.getValue();
            System.out.print(functionEntry.GetReturnType()
                    + " "
                    + functionEntry.GetIdentifier()
                    + "(");
            var first = true;
            for (var type : functionEntry.GetFormalParameters()) {
                if (!first) {
                    System.out.print(", ");
                }
                first = false;

                System.out.print(type.GetLeftChild().GetValue());
            }
            System.out.println(")");
        }
    }

    @Override
    public SymbolTable Clone() {
        SymbolTable parentClone = null;
        if (_parent != null) {
            parentClone = _parent.Clone();
        }

        var newVariablesMap = new HashMap<String, VariableEntry>();
        for (var entry : _variableEntries.entrySet()) {
            newVariablesMap.put(entry.getKey(), entry.getValue().Clone());
        }

        var newFunctionsMap = new HashMap<String, FunctionEntry>();
        for (var entry : _functionEntries.entrySet()) {
            newFunctionsMap.put(entry.getKey(), entry.getValue().Clone());
        }

        return new SymbolTable(parentClone, newVariablesMap, newFunctionsMap);
    }
}

