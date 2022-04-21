package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.nodes.base.ArrayNode;
import org.flowsoft.flowg.nodes.base.StatementNode;
import org.flowsoft.flowg.visitors.IVisitor;

import java.util.List;

public class StatementListNode extends ArrayNode<StatementNode> {
    public StatementListNode(List<StatementNode> children) {
        super(children);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
