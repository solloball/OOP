package ru.nsu.romanov.graph;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GraphAdjMatTest {
    private final String path = "src/test/java/ru/nsu/romanov/graph/graph.txt";

    private final String path2 = "src/test/java/ru/nsu/romanov/graph/graphWithout3Vertex.txt";
    @Test
    void checkGraphAdjMatRead() {
        Graph<String> gr = new GraphAdjMat<>();
        gr.readFromFile(path);
    }

    @Test
    void checkGraphAdjMatReadNonExistingPath_expectedRunTimeEx() {
        Graph<String> gr = new GraphAdjMat<>();
        Assertions.assertThrows(RuntimeException.class,
                ()-> gr.readFromFile("/where?"));
    }

    @Test
    void checkGraphAdjMatGetterEdge() {
        Graph<String> gr = new GraphAdjMat<>();
        gr.readFromFile(path);
        Edge e = gr.getEdge(new VertexIndex(1), new VertexIndex(0));
        Assertions.assertEquals(1, e.from.idx());
        Assertions.assertEquals(0, e.to.idx());
        Assertions.assertEquals(5, e.weight);
    }

    @Test
    void checkGraphAdjMatGetterEdgeWithNonexistentEdge() {
        Graph<String> gr = new GraphAdjMat<>();
        gr.readFromFile(path);
        Edge e = gr.getEdge(new VertexIndex(0), new VertexIndex(0));
        Assertions.assertNull(e);
    }

    @Test
    void checkGraphAdjMatGetterEdge_expectedThrowIndexBound() {
        Graph<String> gr = new GraphAdjMat<>();
        gr.readFromFile(path);
        Assertions.assertThrows(IndexOutOfBoundsException.class, ()->
                gr.getEdge(new VertexIndex(0), new VertexIndex(100)));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                gr.getEdge(new VertexIndex(100), new VertexIndex(0)));
        Assertions.assertThrows(IndexOutOfBoundsException.class, ()->
                gr.getEdge(new VertexIndex(-100), new VertexIndex(0)));
        Assertions.assertThrows(IndexOutOfBoundsException.class, ()->
                gr.getEdge(new VertexIndex(0), new VertexIndex(-100)));
    }

    @Test
    void checkGraphAdjMatGetterVertex() {
        Graph<String> gr = new GraphAdjMat<>();
        gr.readFromFile(path);
        String str = gr.getVertexValue(new VertexIndex(0));
        Assertions.assertEquals("a", str);
        str = gr.getVertexValue(new VertexIndex(3));
        Assertions.assertEquals("d", str);
    }

    @Test
    void checkGraphAdjMatGetterVertex_expectedThrowIndexBound() {
        Graph<String> gr = new GraphAdjMat<>();
        gr.readFromFile(path);
        Assertions.assertThrows(IndexOutOfBoundsException.class, ()->
                gr.getVertexValue(new VertexIndex(-1)));
        Assertions.assertThrows(IndexOutOfBoundsException.class, ()->
                gr.getVertexValue(new VertexIndex(5)));
    }

    @Test
    void checkGraphAdjMatAddVertex() {
        Graph<String> gr = new GraphAdjMat<>();
        gr.readFromFile(path);
        String expectedStr = "wow";
        gr.addVertex(expectedStr);
        Assertions.assertEquals(expectedStr, gr.getVertexValue(new VertexIndex(5)));
        String expectedStr2 = "wow2";
        gr.addVertex(expectedStr2);
        Assertions.assertEquals(expectedStr2, gr.getVertexValue(new VertexIndex(6)));
    }

    @Test
    void checkGraphAdjMatAddEdge() {
        Graph<String> gr = new GraphAdjMat<>();
        gr.readFromFile(path);
        VertexIndex from = new VertexIndex(0);
        VertexIndex to = new VertexIndex(3);
        Assertions.assertNull(gr.getEdge(from, to));
        gr.addEdge(from, to, 255);
        Edge expectedEdge = new Edge(from, to, 255);
        Assertions.assertEquals(expectedEdge, gr.getEdge(from, to));
    }

    @Test
    void checkGraphAdjMatRemoveEdge() {
        Graph<String> gr = new GraphAdjMat<>();
        gr.readFromFile(path);
        VertexIndex from = new VertexIndex(1);
        VertexIndex to = new VertexIndex(2);
        Edge expectedEdge = new Edge(from, to, 6);
        Edge edge = gr.getEdge(from, to);
        Assertions.assertEquals(expectedEdge, edge);
        Assertions.assertTrue(gr.removeEdge(from, to));
        Assertions.assertNull(gr.getEdge(from, to));
    }

    @Test
    void checkGraphAdjMatRemoveEdgeNonExistingEdge() {
        Graph<String> gr = new GraphAdjMat<>();
        gr.readFromFile(path);
        Assertions.assertFalse(gr.removeEdge(new VertexIndex(0), new VertexIndex(0)));
        Assertions.assertNull(gr.getEdge(new VertexIndex(0), new VertexIndex(0)));
    }

    @Test
    void checkGraphAdjMatRemoveEdge_expectedThrowIndexBound() {
        Graph<String> gr = new GraphAdjMat<>();
        gr.readFromFile(path);
        Assertions.assertThrows(IndexOutOfBoundsException.class, ()->
                gr.removeEdge(new VertexIndex(5), new VertexIndex(0)));
        Assertions.assertThrows(IndexOutOfBoundsException.class, ()->
                gr.removeEdge(new VertexIndex(0), new VertexIndex(5)));
        Assertions.assertThrows(IndexOutOfBoundsException.class, ()->
                gr.removeEdge(new VertexIndex(0), new VertexIndex(-1)));
        Assertions.assertThrows(IndexOutOfBoundsException.class, ()->
                gr.removeEdge(new VertexIndex(-1), new VertexIndex(0)));
    }

    @Test
    void checkGraphAdjMatSetEdge() {
        Graph<String> gr = new GraphAdjMat<>();
        gr.readFromFile(path);
        VertexIndex from = new VertexIndex(1);
        VertexIndex to = new VertexIndex(0);
        Edge lastEdge = gr.getEdge(from, to);
        Assertions.assertEquals(5, lastEdge.weight);
        Assertions.assertTrue(gr.setEdge(from, to, 552));
        Edge newEdge = gr.getEdge(from, to);
        Assertions.assertEquals(552, newEdge.weight);
    }

    @Test
    void checkGraphAdjMatSetEdgeNonExistingEdge() {
        Graph<String> gr = new GraphAdjMat<>();
        gr.readFromFile(path);
        VertexIndex from = new VertexIndex(1);
        VertexIndex to = new VertexIndex(1);
        gr.getEdge(from, to);
        Assertions.assertNull(gr.getEdge(from, to));
        Assertions.assertFalse(gr.setEdge(from, to, 3));
    }

    @Test
    void checkGraphAdjMatSetEdge_expectedIndexBound() {
        Graph<String> gr = new GraphAdjMat<>();
        gr.readFromFile(path);
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                gr.setEdge(new VertexIndex(0), new VertexIndex(-1), 0));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                gr.setEdge(new VertexIndex(-1), new VertexIndex(0), 0));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                gr.setEdge(new VertexIndex(0), new VertexIndex(5), 0));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                gr.setEdge(new VertexIndex(5), new VertexIndex(0), 0));
    }

    @Test
    void checkGraphAdjMatSetVertex() {
        Graph<String> gr = new GraphAdjMat<>();
        gr.readFromFile(path);
        Assertions.assertEquals("a", gr.getVertexValue(new VertexIndex(0)));
        gr.setVertexValue(new VertexIndex(0), "new");
        Assertions.assertEquals("new", gr.getVertexValue(new VertexIndex(0)));
    }

    @Test
    void checkGraphAdjMatSetVertex_expectedIndexBound() {
        Graph<String> gr = new GraphAdjMat<>();
        gr.readFromFile(path);
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                gr.setVertexValue(new VertexIndex(-1), "wow"));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                gr.setVertexValue(new VertexIndex(5), "wow"));
    }

    @Test
    void checkGraphAdjMatRemoveVertex() {
        Graph<String> gr = new GraphAdjMat<>();
        gr.readFromFile(path);
        gr.removeVertex(new VertexIndex(2));
        Graph<String> expectedGr = new GraphAdjMat<>();
        expectedGr.readFromFile(path2);
        Assertions.assertEquals(expectedGr, gr);
    }
}