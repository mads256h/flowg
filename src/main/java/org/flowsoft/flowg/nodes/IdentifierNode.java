package org.flowsoft.flowg.nodes;

import java_cup.runtime.ComplexSymbolFactory.Location;
import org.flowsoft.flowg.nodes.base.NullaryNode;
import org.flowsoft.flowg.visitors.IVisitor;

public class IdentifierNode extends NullaryNode<String> {
    public IdentifierNode(String value, Location left, Location right) {
        super(value, left, right);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
