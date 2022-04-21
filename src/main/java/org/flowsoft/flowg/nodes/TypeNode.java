package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.nodes.base.NullaryNode;
import org.flowsoft.flowg.visitors.IVisitor;
import org.flowsoft.flowg.Type;

public class TypeNode extends NullaryNode<Type> {
    public TypeNode(Type type) {
        super(type);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
