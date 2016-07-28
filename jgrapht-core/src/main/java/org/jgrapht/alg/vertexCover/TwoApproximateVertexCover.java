/* ==========================================
 * JGraphT : a free Java graph-theory library
 * ==========================================
 *
 * Project Info:  http://jgrapht.sourceforge.net/
 * Project Creator:  Barak Naveh (http://sourceforge.net/users/barak_naveh)
 *
 * (C) Copyright 2003-2008, by Barak Naveh and Contributors.
 *
 * This program and the accompanying materials are dual-licensed under
 * either
 *
 * (a) the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation, or (at your option) any
 * later version.
 *
 * or (per the licensee's choosing)
 *
 * (b) the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation.
 */
/* -----------------
 * TwoApproximateVertexCover.java
 * -----------------
 * (C) Copyright 2003-2008, by Linda Buisman and Contributors.
 *
 * Original Author:  Linda Buisman
 * Contributor(s):   Barak Naveh
 *                   Christian Hammer
 *                   Joris Kinable
 *
 * $Id$
 *
 * Changes
 * -------
 * 06-Nov-2003 : Initial revision (LB);
 * 07-Jun-2005 : Made generic (CH);
 * 28-Jul-2016 : Moved to dedicated package (JK)
 *
 */
package org.jgrapht.alg.vertexCover;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.interfaces.VertexCoverAlgorithm;
import org.jgrapht.graph.Subgraph;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Finds a 2-approximation for a minimum vertex cover A vertex cover is a set of
 * vertices that touches all the edges in the graph. The graph's vertex set is a
 * trivial cover. However, a <i>minimal</i> vertex set (or at least an
 * approximation for it) is usually desired. Finding a true minimal vertex cover
 * is an NP-Complete problem. For more on the vertex cover problem, see <a
 * href="http://mathworld.wolfram.com/VertexCover.html">
 * http://mathworld.wolfram.com/VertexCover.html</a>
 *
 * @author Linda Buisman
 * @since Nov 6, 2003
 */
public class TwoApproximateVertexCover<V,E> implements VertexCoverAlgorithm<V,E> {

    private final UndirectedGraph<V,E> graph;

    public TwoApproximateVertexCover(UndirectedGraph<V,E> graph){
        this.graph=graph;
    }

    /**
     * Finds a 2-approximation for a minimal vertex cover of the specified
     * graph. The algorithm promises a cover that is at most double the size of
     * a minimal cover. The algorithm takes O(|E|) time.
     *
     * Note: every invocation of this method will recompute the cover!
     *
     * <p>For more details see Jenny Walter, CMPU-240: Lecture notes for
     * Language Theory and Computation, Fall 2002, Vassar College, <a
     * href="http://www.cs.vassar.edu/~walter/cs241index/lectures/PDF/approx.pdf">
     * http://www.cs.vassar.edu/~walter/cs241index/lectures/PDF/approx.pdf</a>.
     * </p>
     *
     *
     * @return a set of vertices which is a vertex cover for the specified
     * graph.
     */
    @Override
    public VertexCover<V> getVertexCover() {
        // C <-- {}
        Set<V> cover = new LinkedHashSet<>();

        // G'=(V',E') <-- G(V,E)
        Subgraph<V, E, Graph<V, E>> sg =
                new Subgraph<>(
                        graph,
                        null,
                        null);

        // while E' is non-empty
        while (!sg.edgeSet().isEmpty()) {
            // let (u,v) be an arbitrary edge of E'
            E e = sg.edgeSet().iterator().next();

            // C <-- C U {u,v}
            V u = graph.getEdgeSource(e);
            V v = graph.getEdgeTarget(e);
            cover.add(u);
            cover.add(v);

            // remove from E' every edge incident on either u or v
            sg.removeVertex(u);
            sg.removeVertex(v);
        }

        return new VertexCover<>(cover, cover.size());
    }

    /**
     * TODO: requires implementation.
     * @param vertexWeightMap
     * @return
     */
    @Override
    public VertexCover<V> getVertexCover(Map<V, Integer> vertexWeightMap) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
