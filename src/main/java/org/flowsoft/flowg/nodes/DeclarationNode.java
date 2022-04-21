package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.nodes.base.ExpressionNode;
import org.flowsoft.flowg.nodes.base.StatementNode;
import org.flowsoft.flowg.nodes.base.TernaryNode;
import org.flowsoft.flowg.visitors.IVisitor;

public class DeclarationNode extends TernaryNode<TypeNode, IdentifierNode, ExpressionNode> implements StatementNode {

    public DeclarationNode(TypeNode type, IdentifierNode identifier, ExpressionNode expression) {
        super(type, identifier, expression);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
