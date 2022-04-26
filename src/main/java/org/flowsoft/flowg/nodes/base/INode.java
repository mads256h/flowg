package org.flowsoft.flowg.nodes.base;

import org.flowsoft.flowg.visitors.IVisitor;

import java_cup.runtime.ComplexSymbolFactory.Location;

public interface INode {
    Location GetLeft();
    Location GetRight();
    <T, TException extends Exception> T Accept(IVisitor<T, TException> visitor) throws TException;
}
