package org.flowsoft.flowg.symboltables;

import org.flowsoft.flowg.Cloneable;
import org.flowsoft.flowg.Type;
import org.flowsoft.flowg.nodes.StatementListNode;
import org.flowsoft.flowg.nodes.functions.FormalParameterNode;
import org.flowsoft.flowg.nodes.functions.GCodeCodeNode;
import org.flowsoft.flowg.nodes.functions.GCodeListNode;

import java.util.ArrayList;

public class FunctionEntry implements Cloneable<FunctionEntry> {
    private final Type _returnType;
    private final String _identifier;
    private final ArrayList<FormalParameterNode> _formalParameters;
    private final StatementListNode _functionBody;
    private final GCodeListNode _gCodeBody;
    private final SymbolTable _symbolTable;

    public FunctionEntry(Type returnType, String identifier, ArrayList<FormalParameterNode> formalParameters, StatementListNode functionBody, SymbolTable symbolTable) {
        _returnType = returnType;
        _identifier = identifier;
        _formalParameters = formalParameters;
        _functionBody = functionBody;
        _gCodeBody = null;
        _symbolTable = symbolTable;
    }

    public FunctionEntry(String identifier, ArrayList<FormalParameterNode> formalParameters, GCodeListNode functionBody, SymbolTable parent) {
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

    public GCodeListNode GetGCode() { return _gCodeBody; }

    public SymbolTable GetSymbolTable() { return _symbolTable; }

    @Override
    public FunctionEntry Clone() {
        if (GetFunctionBody() == null){
            return new FunctionEntry(GetIdentifier(), GetFormalParameters(), GetGCode(), GetSymbolTable());
        } else{
            return new FunctionEntry(GetReturnType(), GetIdentifier(), GetFormalParameters(), GetFunctionBody(), GetSymbolTable());
        }

    }
}