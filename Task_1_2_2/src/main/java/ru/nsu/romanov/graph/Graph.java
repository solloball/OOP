package ru.nsu.romanov.graph;

import java.util.Vector;

public interface Graph<V> {

    void readFromFile(String fileName);

    VertexIndex addVertex(V val);
    void removeVertex(VertexIndex idx);

    void addEdge(VertexIndex from, VertexIndex to, float weight);
    boolean removeEdge(VertexIndex from, VertexIndex to);

    V getVertexValue(VertexIndex idx);
    void setVertexValue(VertexIndex idx, V val);

    Edge getEdge(VertexIndex from, VertexIndex to);
    boolean setEdge(VertexIndex from, VertexIndex to, float weight);

    public Vector<VertexIndex> topologicalSort(VertexIndex start);
}
