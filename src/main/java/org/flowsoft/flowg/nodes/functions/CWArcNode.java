package org.flowsoft.flowg.nodes.functions;

import java_cup.runtime.ComplexSymbolFactory.Location;
import org.flowsoft.flowg.nodes.base.StatementNode;
import org.flowsoft.flowg.nodes.base.UnaryNode;
import org.flowsoft.flowg.visitors.IVisitor;

public class CWArcNode extends UnaryNode<ActualParameterListNode> implements StatementNode {
    public CWArcNode(ActualParameterListNode actualParameterListNode, Location left, Location right) {
        super(actualParameterListNode, left, right);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
