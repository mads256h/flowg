package org.flowsoft.flowg.nodes;

import org.flowsoft.flowg.IVisitor;

public interface INode {
    <T> T Accept(IVisitor<T> visitor);
}
