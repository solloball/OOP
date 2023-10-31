package ru.nsu.romanov.graph;

import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import ru.nsu.romanov.graph.interfaceGraph.Color;
import ru.nsu.romanov.graph.interfaceGraph.Edge;
import ru.nsu.romanov.graph.interfaceGraph.Graph;
import ru.nsu.romanov.graph.interfaceGraph.VertexIndex;
import ru.nsu.romanov.graph.matrix.Matrix;
import ru.nsu.romanov.graph.matrix.MatrixList;

/**
 * Implementation of Graph.
 * In this realization order of edge is important.
 *
 * @param <V> type value if vertex.
 */
public class GraphIncidenceMat<V> implements Graph<V> {

    private final List<V> values = new LinkedList<>();

    private final Matrix<Float, Integer, Integer> mat = new MatrixList<>();

    private int countEdge = 0;

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
            int countVertex;
            tokenizer.nextToken();
            countVertex = (int) tokenizer.nval;
            tokenizer.nextToken();
            countEdge = (int) tokenizer.nval;
            for (int i = 0; i < countVertex; i++) {
                tokenizer.nextToken();
                String val  = tokenizer.sval;
                addVertex((V) val);
            }
            for (int i = 0; i < countEdge; i++) {
                tokenizer.nextToken();
                int from = (int) tokenizer.nval;
                tokenizer.nextToken();
                int to = (int) tokenizer.nval;
                tokenizer.nextToken();
                float weight = (float) tokenizer.nval;
                mat.setVal(from, i, weight);
                mat.setVal(to, i, -weight);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public VertexIndex addVertex(V val) {
        values.add(val);
        mat.addRow(null, countEdge);
        return new VertexIndex(values.size());
    }

    @Override
    public void removeVertex(VertexIndex idx) {
        checkIdx(idx);
        values.remove(idx.idx());
        Stack<Integer> st = new Stack<>();
        for (int i = 0; i < countEdge; i++) {
            if (mat.getVal(idx.idx(), i) != null) {
                st.push(i);
            }
        }
        int size = st.size();
        for (var i = 0; i < size; i++) {
            int index = st.pop();
            mat.removeColumn(index);
        }
        countEdge -= size;
        mat.removeRow(idx.idx());
    }

    @Override
    public void addEdge(VertexIndex from, VertexIndex to, float weight) {
        checkIdx(from);
        checkIdx(to);
        mat.addColumnAll(null);
        int size = mat.getColumnSize(from.idx());
        mat.setVal(to.idx(), size - 1, -weight);
        mat.setVal(from.idx(), size - 1, weight);
        countEdge++;
    }

    @Override
    public boolean removeEdge(VertexIndex from, VertexIndex to) {
        for (int i = 0; i < countEdge; i++) {
            Float weightTo = mat.getVal(to.idx(), i);
            Float weightFrom = mat.getVal(from.idx(), i);

            if (weightFrom != null && weightTo != null && weightFrom > 0) {
                mat.removeColumn(i);
                countEdge--;
                return true;
            }
        }
        return false;
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
        for (int i = 0; i < countEdge; i++) {
            Float weightFrom = mat.getVal(from.idx(), i);
            Float weightTo = mat.getVal(to.idx(), i);
            if (weightFrom != null && weightTo != null
                    && weightFrom > 0 && weightTo < 0) {
                return new Edge(from, to, weightFrom);
            }
        }
        return null;
    }

    @Override
    public boolean setEdge(VertexIndex from, VertexIndex to, float weight) {
        for (int i = 0; i < countEdge; i++) {
            Float weightFrom = mat.getVal(from.idx(), i);
            Float weightTo = mat.getVal(to.idx(), i);
            if (weightFrom != null && weightTo != null
                    && weightFrom > 0 && weightTo < 0) {
                mat.setVal(from.idx(), i, weight);
                mat.setVal(to.idx(), i, -weight);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GraphIncidenceMat<?> that = (GraphIncidenceMat<?>) o;
        return countEdge == that.countEdge
                && Objects.equals(values, that.values)
                && Objects.equals(mat, that.mat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values, mat, countEdge);
    }

    private int dfs(VertexIndex curNode, Color[] arr, List<VertexIndex> ans) {
        arr[curNode.idx()] = Color.GREY;
        for (int i = 0; i < countEdge; i++) {
            Float weight = mat.getVal(curNode.idx(), i);
            if (weight != null && weight > 0) {
                for (int j = 0; j < values.size(); j++) {
                    Float newWeight = mat.getVal(j, i);
                    if (newWeight == null || newWeight != -weight) {
                        continue;
                    }
                    if (arr[j] == Color.GREY) {
                        return 1;
                    }
                    if (arr[j] != Color.BLACK) {
                        dfs(new VertexIndex(j), arr, ans);
                    }
                }
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
        if (dfs(start, arr, ans) == 1) {
            return null;
        }
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
