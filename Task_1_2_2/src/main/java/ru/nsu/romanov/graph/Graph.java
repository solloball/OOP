package ru.nsu.romanov.graph;

public interface Graph<V> {
    abstract void readFromFile(String fileName);

    abstract VertexIndex addVertex(V val);
    abstract void removeVertex(VertexIndex idx);

    abstract void addEdge(VertexIndex from, VertexIndex to, float weight);
    abstract boolean removeEdge(VertexIndex from, VertexIndex to);

    abstract V getVertexValue(VertexIndex idx);
    abstract void setVertexValue(VertexIndex idx, V val);

    abstract Edge getEdge(VertexIndex from, VertexIndex to);
    abstract boolean setEdge(VertexIndex from, VertexIndex to, float weight);
}
