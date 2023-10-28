package ru.nsu.romanov.graph;

import java.util.Objects;

public class Edge {

    Edge(VertexIndex from, VertexIndex to, float weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
    public VertexIndex from;
    public VertexIndex to;
    public float weight;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Edge edge = (Edge) o;
        return Float.compare(weight, edge.weight) == 0 && Objects.equals(from, edge.from) && Objects.equals(to, edge.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, weight);
    }
}
