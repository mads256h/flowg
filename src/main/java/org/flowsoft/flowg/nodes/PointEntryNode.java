package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.nodes.base.BinaryNode;
import org.flowsoft.flowg.nodes.base.ExpressionNode;
import org.flowsoft.flowg.visitors.IVisitor;

public class PointEntryNode extends BinaryNode<ExpressionNode, IdentifierNode> implements ExpressionNode {
    public PointEntryNode(ExpressionNode expressionNode, IdentifierNode identifierNode) {
        super(expressionNode, identifierNode);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
