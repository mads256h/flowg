package org.flowsoft.flowg.visitors;

import org.flowsoft.flowg.Type;
import org.flowsoft.flowg.nodes.FormalParameterNode;
import org.flowsoft.flowg.nodes.StatementListNode;

import java.util.ArrayList;

public class FunctionEntry implements Cloneable<FunctionEntry> {
    private final Type _returnType;
    private final String _identifier;
    private final ArrayList<FormalParameterNode> _formalParameters;
    private final StatementListNode _functionBody;
    private final SymbolTable _symbolTable;

    public FunctionEntry(Type returnType, String identifier, ArrayList<FormalParameterNode> formalParameters, StatementListNode functionBody, SymbolTable symbolTable) {
        _returnType = returnType;
        _identifier = identifier;
        _formalParameters = formalParameters;
        _functionBody = functionBody;
        _symbolTable = symbolTable;
    }

    public Type GetReturnType() {
        return _returnType;
    }

    public String GetIdentifier() {
        return _identifier;
    }

    public ArrayList<FormalParameterNode> GetFormalParameters() {
        return _formalParameters;
    }

    public StatementListNode GetFunctionBody() {
        return _functionBody;
    }

    public SymbolTable GetSymbolTable() { return _symbolTable; }

    @Override
    public FunctionEntry Clone() {
        return new FunctionEntry(GetReturnType(), GetIdentifier(), GetFormalParameters(), GetFunctionBody(), GetSymbolTable());
    }
}
