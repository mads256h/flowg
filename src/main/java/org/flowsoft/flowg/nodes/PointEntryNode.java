package org.flowsoft.flowg.nodes;

import java_cup.runtime.ComplexSymbolFactory.Location;
import org.flowsoft.flowg.nodes.base.BinaryNode;
import org.flowsoft.flowg.nodes.base.ExpressionNode;
import org.flowsoft.flowg.visitors.IVisitor;

public class PointEntryNode extends BinaryNode<ExpressionNode, IdentifierNode> implements ExpressionNode {
    public PointEntryNode(ExpressionNode expressionNode, IdentifierNode identifierNode, Location left, Location right) {
        super(expressionNode, identifierNode, left, right);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
