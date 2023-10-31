package ru.nsu.romanov.graph;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import ru.nsu.romanov.graph.interfacegraph.Color;
import ru.nsu.romanov.graph.interfacegraph.Edge;
import ru.nsu.romanov.graph.interfacegraph.Graph;
import ru.nsu.romanov.graph.interfacegraph.VertexIndex;
import ru.nsu.romanov.graph.matrix.Matrix;
import ru.nsu.romanov.graph.matrix.MatrixList;

/**
 * Implementation of graph.
 * In this realization order of edge isn't important.
 *
 * @param <V> type value of vertex.
 */
public class GraphAdjMat<V> implements Graph<V> {

    private final Matrix<Float, Integer, Integer> mat = new MatrixList<>();
    private final List<V> values = new LinkedList<>();

    private void checkIdx(VertexIndex idx) {
        if (idx.idx() < 0 || idx.idx() >= values.size()) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public void readFromFile(String fileName) {
        mat.clear();
        values.clear();
        try (FileReader reader = new FileReader(fileName)) {
            StreamTokenizer tokenizer = new StreamTokenizer(reader);
            tokenizer.nextToken();
            int countVertex;
            countVertex = (int) tokenizer.nval;
            for (int i = 0; i < countVertex; i++) {
                mat.addRow(null, countVertex);
            }
            tokenizer.nextToken();
            int countEdge;
            countEdge = (int) tokenizer.nval;
            for (int i = 0; i < countVertex; i++) {
                tokenizer.nextToken();
                String val = tokenizer.sval;
                values.add((V) val);
            }
            for (int i = 0; i < countEdge; i++) {
                tokenizer.nextToken();
                int from = (int) tokenizer.nval;
                tokenizer.nextToken();
                int to = (int) tokenizer.nval;
                tokenizer.nextToken();
                float weight = (float) tokenizer.nval;
                mat.setVal(from, to, weight);
            }
        } catch (FileNotFoundException e) {
            System.out.println("failed to found file");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public VertexIndex addVertex(V val) {
        int size = mat.getSize();
        mat.addRow(null, size);
        values.add(val);
        mat.addColumnAll(null);
        return new VertexIndex(size);
    }

    @Override
    public void removeVertex(VertexIndex idx) {
        checkIdx(idx);
        mat.removeRow(idx.idx());
        values.remove(idx.idx());
        mat.removeColumn(idx.idx());
    }

    @Override
    public void addEdge(VertexIndex from, VertexIndex to, float weight) {
        checkIdx(from);
        checkIdx(to);
        mat.setVal(from.idx(), to.idx(), weight);
    }

    @Override
    public boolean removeEdge(VertexIndex from, VertexIndex to) {
        checkIdx(from);
        checkIdx(to);
        if (mat.getVal(from.idx(), to.idx()) == null) {
            return false;
        }
        mat.setVal(from.idx(), to.idx(), null);
        return true;
    }

    @Override
    public V getVertexValue(VertexIndex idx) {
        checkIdx(idx);
        return values.get(idx.idx());
    }

    @Override
    public void setVertexValue(VertexIndex idx, V val) {
        checkIdx(idx);
        values.set(idx.idx(), val);
    }

    @Override
    public Edge getEdge(VertexIndex from, VertexIndex to) {
        checkIdx(from);
        checkIdx(to);
        Float weight = mat.getVal(from.idx(), to.idx());
        if (weight == null) {
            return null;
        }
        return new Edge(from, to, weight);
    }

    @Override
    public boolean setEdge(VertexIndex from, VertexIndex to, float weight) {
        checkIdx(from);
        checkIdx(to);
        if (mat.getVal(from.idx(), to.idx()) == null) {
            return false;
        }
        mat.setVal(from.idx(), to.idx(), weight);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GraphAdjMat<?> that = (GraphAdjMat<?>) o;
        return Objects.equals(mat, that.mat) && Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mat, values);
    }

    private int dfs(VertexIndex curNode, Color[] arr, List<VertexIndex> ans) {
        arr[curNode.idx()] = Color.GREY;
        for (int i = 0; i < values.size(); i++) {
            Float weight = mat.getVal(curNode.idx(), i);
            if (weight == null) {
                continue;
            }
            if (arr[i] == Color.GREY) {
                return 1;
            }
            if (arr[i] != Color.BLACK) {
                dfs(new VertexIndex(i), arr, ans);
            }
        }
        arr[curNode.idx()] = Color.BLACK;
        ans.add(curNode);
        return 0;
    }

    @Override
    public List<VertexIndex> topologicalSort(VertexIndex start) {
        checkIdx(start);
        Color[] arr = new Color[values.size()];
        Arrays.fill(arr, Color.WHITE);
        List<VertexIndex> ans = new Stack<>();
        dfs(start, arr, ans);
        for (int i = 0; i < values.size(); i++) {
            if (i == start.idx()) {
                continue;
            }
            if (arr[i] != Color.WHITE) {
                continue;
            }
            if (dfs(new VertexIndex(i), arr, ans) == 1) {
                return null;
            }
        }
        return ans;
    }
}
