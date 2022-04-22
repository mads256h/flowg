package org.flowsoft.flowg.nodes;

import java_cup.runtime.ComplexSymbolFactory.Location;
import org.flowsoft.flowg.nodes.base.ExpressionNode;
import org.flowsoft.flowg.nodes.base.UnaryNode;
import org.flowsoft.flowg.visitors.IVisitor;

public class IdentifierExpressionNode extends UnaryNode<IdentifierNode> implements ExpressionNode {
    public IdentifierExpressionNode(IdentifierNode identifierNode, Location left, Location right) {
        super(identifierNode, left, right);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
