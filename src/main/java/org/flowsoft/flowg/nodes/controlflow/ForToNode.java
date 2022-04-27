package org.flowsoft.flowg.nodes.controlflow;

import java_cup.runtime.ComplexSymbolFactory.Location;
import org.flowsoft.flowg.nodes.DeclarationNode;
import org.flowsoft.flowg.nodes.StatementListNode;
import org.flowsoft.flowg.nodes.base.ExpressionNode;
import org.flowsoft.flowg.nodes.base.StatementNode;
import org.flowsoft.flowg.nodes.base.TernaryNode;
import org.flowsoft.flowg.visitors.IVisitor;

public class ForToNode extends TernaryNode<DeclarationNode, ExpressionNode, StatementListNode> implements StatementNode {

    public ForToNode(DeclarationNode declarationNode, ExpressionNode expressionNode, StatementListNode statementListNode, Location left, Location right) {
        super(declarationNode, expressionNode, statementListNode, left, right);
    }

    @Override
    public <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException {
        return visitor.Visit(this);
    }
}
