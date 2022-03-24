package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.visitors.IVisitor;

public interface INode {
    <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException;
}
