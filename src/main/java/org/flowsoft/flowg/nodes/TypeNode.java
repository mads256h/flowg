package org.flowsoft.flowg.nodes;

import java_cup.runtime.ComplexSymbolFactory.Location;
import org.flowsoft.flowg.nodes.base.NullaryNode;
import org.flowsoft.flowg.visitors.IVisitor;
import org.flowsoft.flowg.Type;

public class TypeNode extends NullaryNode<Type> {
    public TypeNode(Type type, Location left, Location right) {
        super(type, left, right);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
