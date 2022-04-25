package org.flowsoft.flowg.nodes.math.functions;

import java_cup.runtime.ComplexSymbolFactory.Location;
import org.flowsoft.flowg.nodes.functions.ActualParameterListNode;
import org.flowsoft.flowg.nodes.base.ExpressionNode;
import org.flowsoft.flowg.nodes.base.UnaryNode;
import org.flowsoft.flowg.visitors.IVisitor;

public class ArcsinNode extends UnaryNode<ActualParameterListNode> implements ExpressionNode {
    public ArcsinNode(ActualParameterListNode actualParameterListNode, Location left, Location right) {
        super(actualParameterListNode, left, right);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
