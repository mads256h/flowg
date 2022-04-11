package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.visitors.IVisitor;

public class ReturnNode extends UnaryNode<ExpressionNode> implements StatementNode {
    public ReturnNode(ExpressionNode expressionNode) {
        super(expressionNode);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
