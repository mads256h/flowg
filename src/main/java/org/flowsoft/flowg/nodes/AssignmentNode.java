package org.flowsoft.flowg.nodes;

import java_cup.runtime.ComplexSymbolFactory.Location;
import org.flowsoft.flowg.nodes.base.BinaryNode;
import org.flowsoft.flowg.nodes.base.ExpressionNode;
import org.flowsoft.flowg.nodes.base.StatementNode;
import org.flowsoft.flowg.visitors.IVisitor;

public class AssignmentNode extends BinaryNode<IdentifierNode, ExpressionNode> implements StatementNode {
    public AssignmentNode(IdentifierNode identifierNode, ExpressionNode expressionNode, Location left, Location right) {
        super(identifierNode, expressionNode, left, right);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
