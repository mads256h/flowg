package org.flowsoft.flowg.nodes;

import java_cup.runtime.ComplexSymbolFactory;
import org.flowsoft.flowg.nodes.base.NullaryNode;
import org.flowsoft.flowg.visitors.IVisitor;

public class UserStringNode extends NullaryNode<String> {

    public UserStringNode(String s, ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right) {
        super(s, left, right);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
