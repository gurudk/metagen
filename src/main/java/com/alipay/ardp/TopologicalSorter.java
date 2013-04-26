package com.alipay.ardp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * Sorts a directed graph, obtaining a visiting sequence ("sorted" list) that
 * respects the "Predecessors" (as in a job/task requirements list). (when there
 * is freedom, the original ordering is preferred)
 * 
 * The behaviour in case of loops (cycles) depends on the "mode": permitLoops ==
 * false : loops are detected, but result is UNDEFINED (simpler) permitLoops ==
 * true : loops are detected, result a "best effort" try, original ordering is
 * privileged
 * 
 * http://en.wikipedia.org/wiki/Topological_sort
 */
public class TopologicalSorter<T extends DirectedGraphNode> {

    private final boolean permitLoops;
    private final Collection<T> graph; // original graph. this is not touched.
    private final List<T> sorted = new ArrayList<T>(); // result
    private final Set<T> visited = new HashSet<T>(); // auxiliar list
    private final Set<T> withLoops = new HashSet<T>();

    // auxiliar: all succesors (also remote) of each node; this is only used if
    // permitLoops==true
    private HashMap<T, Set<T>> succesors = null;

    public TopologicalSorter(Collection<T> graph, boolean permitLoops) {
        this.graph = graph;
        this.permitLoops = permitLoops;
    }

    public void sort() {
        init();
        for (T n : graph) {
            if (permitLoops)
                visitLoopsPermitted(n);
            else
                visitLoopsNoPermitted(n, new HashSet<T>());
        }
    }

    private void init() {
        sorted.clear();
        visited.clear();
        withLoops.clear();
        // build succesors map: only it permitLoops == true
        if (permitLoops) {
            succesors = new HashMap<T, Set<T>>();
            HashMap<T, Set<T>> addTo = new HashMap();
            for (T n : graph) {
                succesors.put(n, new HashSet<T>());
                addTo.put(n, new HashSet<T>());
            }
            for (T n2 : graph) {
                for (DirectedGraphNode n1 : n2.getPredecessors()) {
                    succesors.get(n1).add(n2);
                }
            }
            boolean change = false;
            do {
                change = false;
                for (T n : graph) {
                    addTo.get(n).clear();
                    for (T ns : succesors.get(n)) {
                        for (T ns2 : succesors.get(ns)) {
                            if (!succesors.get(n).contains(ns2)) {
                                change = true;
                                addTo.get(n).add(ns2);
                            }
                        }
                    }
                }
                for (DirectedGraphNode n : graph) {
                    succesors.get(n).addAll(addTo.get(n));
                }
            } while (change);
        }
    }

    private void visitLoopsNoPermitted(T n, Set<T> visitedInThisCallStack) { // this
                                                                             // is
                                                                             // simpler
                                                                             // than
                                                                             // visitLoopsPermitted
        if (visited.contains(n)) {
            if (visitedInThisCallStack.contains(n)) {
                withLoops.add(n); // loop!
            }
            return;
        }
        // System.out.println("visiting " + n.toString());
        visited.add(n);
        visitedInThisCallStack.add(n);
        for (DirectedGraphNode n1 : n.getPredecessors()) {
            visitLoopsNoPermitted((T) n1, visitedInThisCallStack);
        }
        sorted.add(n);
    }

    private void visitLoopsPermitted(T n) {
        if (visited.contains(n))
            return;
        // System.out.println("visiting " + n.toString());
        visited.add(n);
        for (DirectedGraphNode n1 : n.getPredecessors()) {
            if (succesors.get(n).contains(n1)) {
                withLoops.add(n);
                withLoops.add((T) n1);
                continue;
            } // loop!
            visitLoopsPermitted((T) n1);
        }
        sorted.add(n);
    }

    public boolean hadLoops() {
        return withLoops.size() > 0;
    }

    public List<T> getSorted() {
        return sorted;
    }

    public Set<T> getWithLoops() {
        return withLoops;
    }

    public void showResult() { // for debugging
        for (DirectedGraphNode node : sorted) {
            System.out.println(node.toString());
        }
        if (hadLoops()) {
            System.out.println("LOOPS!:");
            for (DirectedGraphNode node : withLoops) {
                System.out.println("  " + node.toString());
            }
        }
    }
}
