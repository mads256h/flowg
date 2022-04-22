package org.flowsoft.flowg.nodes.controlflow;

import java_cup.runtime.ComplexSymbolFactory.Location;
import org.flowsoft.flowg.nodes.base.ExpressionNode;
import org.flowsoft.flowg.nodes.base.StatementNode;
import org.flowsoft.flowg.nodes.base.UnaryNode;
import org.flowsoft.flowg.visitors.IVisitor;

public class ReturnNode extends UnaryNode<ExpressionNode> implements StatementNode {
    public ReturnNode(ExpressionNode expressionNode, Location left, Location right) {
        super(expressionNode, left, right);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
