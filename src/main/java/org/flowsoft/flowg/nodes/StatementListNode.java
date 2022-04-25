package org.flowsoft.flowg.nodes;

import java_cup.runtime.ComplexSymbolFactory.Location;
import org.flowsoft.flowg.nodes.base.ArrayNode;
import org.flowsoft.flowg.nodes.base.StatementNode;
import org.flowsoft.flowg.visitors.IVisitor;

import java.util.List;


public class StatementListNode extends ArrayNode<StatementNode> {
    public StatementListNode(List<StatementNode> children, Location left, Location right) {
        super(children, left, right);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
