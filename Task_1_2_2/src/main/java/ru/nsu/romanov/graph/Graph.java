package ru.nsu.romanov.graph;

public interface Graph<V, E extends Edge> {
    abstract void readFromFile(String fileName);

    abstract VertexIndex addVertex(V val);
    abstract boolean removeVertex(VertexIndex idx);

    abstract E addEdge(VertexIndex from, VertexIndex to, float weight);
    abstract boolean removeEdge(VertexIndex from, VertexIndex to);

    abstract V getVertexValue(VertexIndex idx);
    abstract boolean setVertexValue(VertexIndex idx, V val);

    abstract E getEdge(VertexIndex from, VertexIndex to);
    abstract boolean setEdge(VertexIndex from, VertexIndex to, float weight);
}
