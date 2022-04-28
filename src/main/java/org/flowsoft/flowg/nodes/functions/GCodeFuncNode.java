package org.flowsoft.flowg.nodes.functions;
import org.flowsoft.flowg.nodes.IdentifierNode;
import org.flowsoft.flowg.nodes.base.StatementNode;
import org.flowsoft.flowg.nodes.base.TernaryNode;
import org.flowsoft.flowg.visitors.IVisitor;
import java_cup.runtime.ComplexSymbolFactory.Location;

public class GCodeFuncNode extends TernaryNode<IdentifierNode, FormalParameterListNode, GCodeCodeNode> implements StatementNode {
    public GCodeFuncNode(IdentifierNode identifierNode, FormalParameterListNode formalParameterListNode, GCodeCodeNode gCodeCodeNode, Location left, Location right){
        super(identifierNode, formalParameterListNode, gCodeCodeNode, left, right);
    }

    public IdentifierNode GetIdentifierNode() { return GetFirstNode(); }
    public FormalParameterListNode GetFormalParameterListNode() { return GetSecondNode(); }
    public GCodeCodeNode GetGCodeCodeNode() { return GetThirdNode(); }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
