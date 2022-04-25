package org.flowsoft.flowg.nodes.controlflow;

import java_cup.runtime.ComplexSymbolFactory.Location;
import org.flowsoft.flowg.nodes.StatementListNode;
import org.flowsoft.flowg.nodes.base.ExpressionNode;
import org.flowsoft.flowg.nodes.base.StatementNode;
import org.flowsoft.flowg.nodes.base.TernaryNode;
import org.flowsoft.flowg.visitors.IVisitor;

public class IfElseNode extends TernaryNode<ExpressionNode, StatementListNode, StatementListNode> implements StatementNode {
    public IfElseNode(ExpressionNode expressionNode, StatementListNode ifBlock, StatementListNode elseBlock, Location left, Location right) {
        super(expressionNode, ifBlock, elseBlock, left, right);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
