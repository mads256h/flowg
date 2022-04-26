package org.flowsoft.flowg.nodes.functions;

import java_cup.runtime.ComplexSymbolFactory.Location;
import org.flowsoft.flowg.nodes.base.ArrayNode;
import org.flowsoft.flowg.visitors.IVisitor;

import java.util.List;

public class FormalParameterListNode extends ArrayNode<FormalParameterNode> {
    public FormalParameterListNode(List<FormalParameterNode> children, Location left, Location right) {
        super(children, left, right);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
