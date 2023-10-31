package ru.nsu.romanov.graph;

import ru.nsu.romanov.graph.interfaceGraph.Edge;
import ru.nsu.romanov.graph.interfaceGraph.Graph;

import java.util.List;
import java.util.Stack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.romanov.graph.interfaceGraph.VertexIndex;

/**
 * Tests for GraphIncidenceMat.
 */
public class GraphIncidenceMatTest {

    private final String path = "resources/graph.txt";

    private final String path2 = "resources/graphWithout3Vertex.txt";

    @Test
    void checkGraphIncidenceMatRead() {
        Graph<String> gr = new GraphIncidenceMat<>();
        gr.readFromFile(path);
        Graph<String> expectedGr = new GraphIncidenceMat<>();
        expectedGr.addVertex("a");
        expectedGr.addVertex("b");
        expectedGr.addVertex("c");
        expectedGr.addVertex("d");
        expectedGr.addVertex("e");
        expectedGr.addEdge(new VertexIndex(1), new VertexIndex(0), 5);
        expectedGr.addEdge(new VertexIndex(1), new VertexIndex(2), 6);
        expectedGr.addEdge(new VertexIndex(2), new VertexIndex(0), 7);
        expectedGr.addEdge(new VertexIndex(1), new VertexIndex(3), (float) 8.3);
        Assertions.assertEquals(expectedGr, gr);
    }

    @Test
    void checkGraphIncidenceMatReadTwice() {
        Graph<String> gr = new GraphIncidenceMat<>();
        gr.readFromFile(path2);
        gr.readFromFile(path);
        Graph<String> expectedGr = new GraphIncidenceMat<>();
        expectedGr.addVertex("a");
        expectedGr.addVertex("b");
        expectedGr.addVertex("c");
        expectedGr.addVertex("d");
        expectedGr.addVertex("e");
        expectedGr.addEdge(new VertexIndex(1), new VertexIndex(0), 5);
        expectedGr.addEdge(new VertexIndex(1), new VertexIndex(2), 6);
        expectedGr.addEdge(new VertexIndex(2), new VertexIndex(0), 7);
        expectedGr.addEdge(new VertexIndex(1), new VertexIndex(3), (float) 8.3);
        Assertions.assertEquals(expectedGr, gr);
    }

    @Test
    void checkGraphIncidenceMatReadNonExistingPath_expectedRunTimeEx() {
        Graph<String> gr = new GraphIncidenceMat<>();
        Assertions.assertThrows(RuntimeException.class,
                () -> gr.readFromFile("/where?"));
    }

    @Test
    void checkGraphIncidenceMatGetterEdge() {
        Graph<String> gr = new GraphIncidenceMat<>();
        gr.readFromFile(path);
        Edge e = gr.getEdge(new VertexIndex(1), new VertexIndex(0));
        Assertions.assertEquals(1, e.from.idx());
        Assertions.assertEquals(0, e.to.idx());
        Assertions.assertEquals(5, e.weight);
    }

    @Test
    void checkGraphIncidenceMatGetterEdgeWithNonexistentEdge() {
        Graph<String> gr = new GraphIncidenceMat<>();
        gr.readFromFile(path);
        Edge e = gr.getEdge(new VertexIndex(0), new VertexIndex(0));
        Assertions.assertNull(e);
    }

    @Test
    void checkGraphIncidenceMatGetterEdge_expectedThrowIndexBound() {
        Graph<String> gr = new GraphIncidenceMat<>();
        gr.readFromFile(path);
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                gr.getEdge(new VertexIndex(0), new VertexIndex(100)));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                gr.getEdge(new VertexIndex(100), new VertexIndex(0)));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                gr.getEdge(new VertexIndex(-100), new VertexIndex(0)));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                gr.getEdge(new VertexIndex(0), new VertexIndex(-100)));
    }

    @Test
    void checkGraphIncidenceMatGetterVertex() {
        Graph<String> gr = new GraphIncidenceMat<>();
        gr.readFromFile(path);
        String str = gr.getVertexValue(new VertexIndex(0));
        Assertions.assertEquals("a", str);
        str = gr.getVertexValue(new VertexIndex(3));
        Assertions.assertEquals("d", str);
    }

    @Test
    void checkGraphIncidenceMatGetterVertex_expectedThrowIndexBound() {
        Graph<String> gr = new GraphIncidenceMat<>();
        gr.readFromFile(path);
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                gr.getVertexValue(new VertexIndex(-1)));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                gr.getVertexValue(new VertexIndex(5)));
    }

    @Test
    void checkGraphIncidenceMatAddVertex() {
        Graph<String> gr = new GraphIncidenceMat<>();
        gr.readFromFile(path);
        String expectedStr = "wow";
        gr.addVertex(expectedStr);
        Assertions.assertEquals(expectedStr, gr.getVertexValue(new VertexIndex(5)));
        String expectedStr2 = "wow2";
        gr.addVertex(expectedStr2);
        Assertions.assertEquals(expectedStr2, gr.getVertexValue(new VertexIndex(6)));
    }

    @Test
    void checkGraphIncidenceMatAddEdge() {
        Graph<String> gr = new GraphIncidenceMat<>();
        gr.readFromFile(path);
        VertexIndex from = new VertexIndex(0);
        VertexIndex to = new VertexIndex(3);
        Assertions.assertNull(gr.getEdge(from, to));
        gr.addEdge(from, to, 255);
        Edge expectedEdge = new Edge(from, to, 255);
        Assertions.assertEquals(expectedEdge, gr.getEdge(from, to));
    }

    @Test
    void checkGraphIncidenceMatRemoveEdge() {
        Graph<String> gr = new GraphIncidenceMat<>();
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
    void checkGraphIncidenceMatRemoveEdgeNonExistingEdge() {
        Graph<String> gr = new GraphIncidenceMat<>();
        gr.readFromFile(path);
        Assertions.assertFalse(gr.removeEdge(new VertexIndex(0), new VertexIndex(0)));
        Assertions.assertNull(gr.getEdge(new VertexIndex(0), new VertexIndex(0)));
    }

    @Test
    void checkGraphIncidenceMatRemoveEdge_expectedThrowIndexBound() {
        Graph<String> gr = new GraphIncidenceMat<>();
        gr.readFromFile(path);
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                gr.removeEdge(new VertexIndex(5), new VertexIndex(0)));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                gr.removeEdge(new VertexIndex(0), new VertexIndex(5)));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                gr.removeEdge(new VertexIndex(0), new VertexIndex(-1)));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                gr.removeEdge(new VertexIndex(-1), new VertexIndex(0)));
    }

    @Test
    void checkGraphIncidenceMatSetEdge() {
        Graph<String> gr = new GraphIncidenceMat<>();
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
    void checkGraphIncidenceMatSetEdgeNonExistingEdge() {
        Graph<String> gr = new GraphIncidenceMat<>();
        gr.readFromFile(path);
        VertexIndex from = new VertexIndex(1);
        VertexIndex to = new VertexIndex(1);
        gr.getEdge(from, to);
        Assertions.assertNull(gr.getEdge(from, to));
        Assertions.assertFalse(gr.setEdge(from, to, 3));
    }

    @Test
    void checkGraphIncidenceMatSetEdge_expectedIndexBound() {
        Graph<String> gr = new GraphIncidenceMat<>();
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
    void checkGraphIncidenceMatSetVertex() {
        Graph<String> gr = new GraphIncidenceMat<>();
        gr.readFromFile(path);
        Assertions.assertEquals("a", gr.getVertexValue(new VertexIndex(0)));
        gr.setVertexValue(new VertexIndex(0), "new");
        Assertions.assertEquals("new", gr.getVertexValue(new VertexIndex(0)));
    }

    @Test
    void checkGraphIncidenceMatSetVertex_expectedIndexBound() {
        Graph<String> gr = new GraphIncidenceMat<>();
        gr.readFromFile(path);
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                gr.setVertexValue(new VertexIndex(-1), "wow"));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                gr.setVertexValue(new VertexIndex(5), "wow"));
    }

    @Test
    void checkGraphIncidenceMatRemoveVertex() {
        Graph<String> gr = new GraphIncidenceMat<>();
        gr.readFromFile(path);
        gr.removeVertex(new VertexIndex(2));
        Graph<String> expectedGr = new GraphIncidenceMat<>();
        expectedGr.readFromFile(path2);
        Assertions.assertEquals(expectedGr, gr);
    }

    @Test
    void checkGraphIncidenceMattTopologicalSort() {
        Graph<String> gr = new GraphIncidenceMat<>();
        gr.readFromFile(path);
        List<VertexIndex> expectedAns = new Stack<>();
        expectedAns.add(new VertexIndex(0));
        expectedAns.add(new VertexIndex(2));
        expectedAns.add(new VertexIndex(3));
        expectedAns.add(new VertexIndex(1));
        expectedAns.add(new VertexIndex(4));
        List<VertexIndex> ans = gr.topologicalSort(new VertexIndex(1));
        Assertions.assertEquals(expectedAns, ans);
    }

    @Test
    void checkGraphIncidenceMatTopologicalSortWithEmptyGr_expectedThrowIndexBound() {
        Graph<String> gr = new GraphIncidenceMat<>();
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                gr.topologicalSort(new VertexIndex(0)));
    }

    @Test
    void checkGraphIncidenceMatTopologicalSort_expectedThrowIndexBound() {
        Graph<String> gr = new GraphIncidenceMat<>();
        gr.readFromFile(path);
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                gr.topologicalSort(new VertexIndex(5)));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->
                gr.topologicalSort(new VertexIndex(-1)));
    }

    @Test
    void checkGraphIncidenceMatEqualsWithEmptyGraph() {
        Graph<Integer> gr1 = new GraphIncidenceMat<>();
        Graph<Integer> gr2 = new GraphIncidenceMat<>();
        Assertions.assertEquals(gr1, gr2);
    }

    @Test
    void checkGraphIncidenceMatHashcodeWithEmptyGraph() {
        Graph<Integer> gr1 = new GraphIncidenceMat<>();
        Graph<Integer> gr2 = new GraphIncidenceMat<>();
        Assertions.assertEquals(gr1.hashCode(), gr2.hashCode());
    }

    @Test
    void checkGraphIncidenceMatEqualsWithOneNode() {
        Graph<Integer> gr1 = new GraphIncidenceMat<>();
        Graph<Integer> gr2 = new GraphIncidenceMat<>();
        gr1.addVertex(3);
        gr2.addVertex(3);
        Assertions.assertEquals(gr1, gr2);
    }

    @Test
    void checkGraphIncidenceMatHashcodeWithOneNode() {
        Graph<Integer> gr1 = new GraphIncidenceMat<>();
        Graph<Integer> gr2 = new GraphIncidenceMat<>();
        gr1.addVertex(3);
        gr2.addVertex(3);
        Assertions.assertEquals(gr1.hashCode(), gr2.hashCode());
    }

    @Test
    void checkGraphIncidenceMatEqualsWithNull() {
        Graph<Integer> gr = new GraphIncidenceMat<>();
        gr.addVertex(3);
        Assertions.assertNull(null);
    }

    @Test
    void checkGraphIncidenceMatEquals_Reflexivity() {
        Graph<String> gr = new GraphIncidenceMat<>();
        gr.readFromFile(path);
        Assertions.assertEquals(gr, gr);
    }

    @Test
    void checkGraphIncidenceMatHashcode_Reflexivity() {
        Graph<String> gr = new GraphIncidenceMat<>();
        gr.readFromFile(path);
        Assertions.assertEquals(gr.hashCode(), gr.hashCode());
    }

    @Test
    void checkGraphIncidenceMatEqualsSymmetry() {
        Graph<String> gr1 = new GraphIncidenceMat<>();
        gr1.readFromFile(path);
        Graph<String> gr2 = new GraphIncidenceMat<>();
        gr2.addVertex("a");
        gr2.addVertex("b");
        gr2.addVertex("c");
        gr2.addVertex("d");
        gr2.addVertex("e");
        gr2.addEdge(new VertexIndex(1), new VertexIndex(0), 5);
        gr2.addEdge(new VertexIndex(1), new VertexIndex(2), 6);
        gr2.addEdge(new VertexIndex(2), new VertexIndex(0), 7);
        gr2.addEdge(new VertexIndex(1), new VertexIndex(3), (float) 8.3);
        Assertions.assertEquals(gr2, gr1);
        Assertions.assertEquals(gr1, gr2);
    }
}
