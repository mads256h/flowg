package org.flowsoft.flowg.nodes.functions;

import java_cup.runtime.ComplexSymbolFactory.Location;
import org.flowsoft.flowg.nodes.IdentifierNode;
import org.flowsoft.flowg.nodes.base.BinaryNode;
import org.flowsoft.flowg.nodes.base.ExpressionNode;
import org.flowsoft.flowg.nodes.base.StatementNode;
import org.flowsoft.flowg.visitors.IVisitor;

public class FunctionCallNode extends BinaryNode<IdentifierNode, ActualParameterListNode> implements StatementNode, ExpressionNode {
    public FunctionCallNode(IdentifierNode identifierNode, ActualParameterListNode actualParameterListNode, Location left, Location right) {
        super(identifierNode, actualParameterListNode, left, right);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
