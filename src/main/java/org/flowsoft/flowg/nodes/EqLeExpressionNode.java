package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.visitors.IVisitor;

public class EqLeExpressionNode extends BinaryNode<ExpressionNode, ExpressionNode> implements ExpressionNode{
    public EqLeExpressionNode(ExpressionNode leftChild, ExpressionNode rightChild) { super(leftChild, rightChild); }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
