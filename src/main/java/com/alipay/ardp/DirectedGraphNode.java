package com.alipay.ardp;

import java.util.Collection;

/**
 * Node that conform a DirectedGraph It is used by TopologicalSorter
 */
public interface DirectedGraphNode {
    /**
     * empty collection if no predecessors
     * 
     * @return
     */
    public Collection<? extends DirectedGraphNode> getPredecessors();
}