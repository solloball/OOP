package ru.nsu.romanov.graph.interfaceGraph;

import java.util.List;

/**
 * Interface for working with graph.
 * Every vertex has VertexIndex which identifies them.
 * If you call to invalid VertexIndex,
 * you will have IndexBoundException.
 *
 * @param <V> type of vertex.
 */
public interface Graph<V> {

    /**
     * read from fileName graph.
     * This graph should be with definite structure.
     * generic V must be String.
     *
     * @param fileName path to the file, which begin from Task_1_2_2.
     */
    void readFromFile(String fileName);

    /**
     * add new Vertex to graph.
     *
     * @param val val of new vertex.
     * @return VertexIndex of new vertex.
     */
    VertexIndex addVertex(V val);

    /**
     * remove vertex and all incidents edge from graph.
     *
     * @param idx index of this vertex.
     */
    void removeVertex(VertexIndex idx);

    /**
     * add new edge to graph.
     * if the edge exists then replaces it.
     *
     * @param from component "from" from new edge.
     * @param to component "to" from new edge.
     * @param weight component weight of new edge.
     */
    void addEdge(VertexIndex from, VertexIndex to, float weight);

    /**
     * remove edge of thus graph.
     *
     * @param from component "from" from edge.
     * @param to component "to" from edge.
     * @return true if this edge existed, and we deleted it,
     *     false if this edge didn't exist.
     */
    boolean removeEdge(VertexIndex from, VertexIndex to);

    /**
     * return vertex if IndexVertex = idx.
     *
     * @param idx VertexIndex of vertex.
     * @return value of vertex.
     */
    V getVertexValue(VertexIndex idx);

    /**
     * set new value to vertex.
     * which has VertexIndex = idx.
     *
     * @param idx VertexIndex of vertex.
     * @param val newValue of vertex.
     */
    void setVertexValue(VertexIndex idx, V val);

    /**
     * return edge.
     *
     * @param from component from of edge.
     * @param to component to of edge.
     * @return Edge.
     */
    Edge getEdge(VertexIndex from, VertexIndex to);

    /**
     * setEdge new Edge.
     *
     * @param from component from of new edge.
     * @param to component to of new edge.
     * @param weight weight from of new edge.
     * @return true if this edge existed and edge was changed,
     *     false if this edge didn't exist.
     */
    boolean setEdge(VertexIndex from, VertexIndex to, float weight);

    /**
     * make Topological Sort and return vector.
     * return null if graph can't have Topological Sort.
     *
     * @param start from what vertex we start.
     * @return list with vertex in topological sort order.
     */
    List<VertexIndex> topologicalSort(VertexIndex start);
}
