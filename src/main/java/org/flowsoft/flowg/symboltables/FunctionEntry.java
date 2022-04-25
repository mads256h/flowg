package org.flowsoft.flowg.symboltables;

import java_cup.runtime.Symbol;
import org.flowsoft.flowg.Type;
import org.flowsoft.flowg.nodes.GCodeCodeNode;
import org.flowsoft.flowg.nodes.functions.FormalParameterNode;
import org.flowsoft.flowg.nodes.StatementListNode;
import org.flowsoft.flowg.Cloneable;

import java.util.ArrayList;

public class FunctionEntry implements Cloneable<FunctionEntry> {
    private final Type _returnType;
    private final String _identifier;
    private final ArrayList<FormalParameterNode> _formalParameters;
    private final StatementListNode _functionBody;
    private final GCodeCodeNode _gCodeBody;
    private final SymbolTable _symbolTable;

    public FunctionEntry(Type returnType, String identifier, ArrayList<FormalParameterNode> formalParameters, StatementListNode functionBody, SymbolTable symbolTable) {
        _returnType = returnType;
        _identifier = identifier;
        _formalParameters = formalParameters;
        _functionBody = functionBody;
        _gCodeBody = null;
        _symbolTable = symbolTable;
    }

    public FunctionEntry(String identifier, ArrayList<FormalParameterNode> formalParameters, GCodeCodeNode functionBody, SymbolTable parent) {
        _returnType = Type.Void;
        _identifier = identifier;
        _formalParameters = formalParameters;
        _functionBody = null;
        _gCodeBody = functionBody;
        _symbolTable = parent;
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

    public GCodeCodeNode GetGCode() { return _gCodeBody; }

    public SymbolTable GetSymbolTable() { return _symbolTable; }

    @Override
    public FunctionEntry Clone() {
        return new FunctionEntry(GetReturnType(), GetIdentifier(), GetFormalParameters(), GetFunctionBody(), GetSymbolTable());
    }
}