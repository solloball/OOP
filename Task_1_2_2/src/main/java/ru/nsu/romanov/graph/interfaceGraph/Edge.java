package ru.nsu.romanov.graph.interfaceGraph;

import java.util.Objects;

/**
 * Edge represent edge in graph.
 */
public class Edge {
    /**
     * from which vertex the edge.
     */
    public VertexIndex from;

    /**
     * to which vertex the edge.
     */
    public VertexIndex to;

    /**
     * weight of edge.
     */
    public float weight;

    /**
     * constructor of edge.
     *
     * @param from set this.from.
     * @param to set this.to.
     * @param weight set this.weight.
     */
    public Edge(VertexIndex from, VertexIndex to, float weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    /**
     * overriding equals.
     *
     * @param o object with which we compare.
     * @return true if equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Edge edge = (Edge) o;
        return Float.compare(weight, edge.weight) == 0
                && Objects.equals(from, edge.from)
                && Objects.equals(to, edge.to);
    }

    /**
     * overriding hashcode.
     *
     * @return hashcode of this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(from, to, weight);
    }
}
