package org.flowsoft.flowg.nodes;

import java_cup.runtime.ComplexSymbolFactory;
import org.flowsoft.flowg.nodes.base.StatementNode;
import org.flowsoft.flowg.nodes.base.UnaryNode;
import org.flowsoft.flowg.visitors.IVisitor;

public class IncludeUserNode extends UnaryNode<UserStringNode> implements StatementNode {
    public IncludeUserNode(UserStringNode userStringNode, ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right) {
        super(userStringNode, left, right);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
