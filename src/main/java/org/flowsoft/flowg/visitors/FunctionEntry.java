package org.flowsoft.flowg.visitors;

import org.flowsoft.flowg.Type;
import org.flowsoft.flowg.nodes.FormalParameterNode;
import org.flowsoft.flowg.nodes.StatementListNode;

import java.util.ArrayList;

public class FunctionEntry {
    private final Type _returnType;
    private final String _identifier;
    private final ArrayList<FormalParameterNode> _formalParameters;
    private final StatementListNode _functionBody;

    public FunctionEntry(Type returnType, String identifier, ArrayList<FormalParameterNode> formalParameters, StatementListNode functionBody) {
        _returnType = returnType;
        _identifier = identifier;
        _formalParameters = formalParameters;
        _functionBody = functionBody;
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
}
