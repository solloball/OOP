package ru.nsu.romanov.graph;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

/**
 * Implementation of graph
 * In this realization order of edge isn't important
 *
 * @param <V> type value of vertex
 */
public class GraphAdjMat<V> implements Graph<V> {

    private final ArrayList<ArrayList<Float>> mat = new ArrayList<>();
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
        FileReader reader;
        try {
            reader = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("failed to found file");
            throw new RuntimeException(e);
        }
        StreamTokenizer tokenizer = new StreamTokenizer(reader);
        int countEdge;
        int countVertex;
        try {
            tokenizer.nextToken();
            countVertex = (int)tokenizer.nval;
            for (int i = 0; i < countVertex; i++) {
                mat.add(
                        new ArrayList<>(Collections.nCopies(countVertex, null)));
            }
            tokenizer.nextToken();
            countEdge = (int)tokenizer.nval;
            for (int i = 0; i < countVertex; i++) {
                tokenizer.nextToken();
                String val  = tokenizer.sval;
                values.add((V) val);
            }
            for (int i = 0; i < countEdge; i++) {
                tokenizer.nextToken();
                int from = (int) tokenizer.nval;
                tokenizer.nextToken();
                int to = (int) tokenizer.nval;
                tokenizer.nextToken();
                float weight = (float) tokenizer.nval;
                mat.get(from).set(to, weight);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public VertexIndex addVertex(V val) {
        int size = mat.size();
        mat.add(new ArrayList<>(Collections.nCopies(size + 1, null)));
        values.add(val);
        for (int i = 0; i < size; i++) {
            mat.get(i).add(null);
        }
        return new VertexIndex(size);
    }

    @Override
    public void removeVertex(VertexIndex idx) {
        mat.remove(idx.idx());
        values.remove(idx.idx());
        for (var iterator : mat) {
            iterator.remove(idx.idx());
        }
        checkIdx(idx);
    }

    @Override
    public void addEdge(VertexIndex from, VertexIndex to, float weight) {
        checkIdx(from);
        checkIdx(to);
        mat.get(from.idx()).set(to.idx(), weight);
    }

    @Override
    public boolean removeEdge(VertexIndex from, VertexIndex to) {
        checkIdx(from);
        checkIdx(to);
        if (mat.get(from.idx()).get(to.idx()) == null) {
            return false;
        }
        mat.get(from.idx()).set(to.idx(), null);
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
        Float weight = mat.get(from.idx()).get(to.idx());
        if (weight == null) {
            return null;
        }
        return new Edge(from, to, weight);
    }

    @Override
    public boolean setEdge(VertexIndex from, VertexIndex to, float weight) {
        checkIdx(from);
        checkIdx(to);
        if (mat.get(from.idx()).get(to.idx()) == null) {
            return false;
        }
        mat.get(from.idx()).set(to.idx(), weight);
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

    private int dfs (VertexIndex curNode, Color[] arr, Vector<VertexIndex> ans) {
        arr[curNode.idx()] = Color.Grey;
        for (int i = 0; i < values.size(); i++) {
            Float weight = mat.get(curNode.idx()).get(i);
            if (weight == null) {
                continue;
            }
            if (arr[i] == Color.Grey) {
                return 1;
            }
            if (arr[i] != Color.Black) {
                dfs(new VertexIndex(i), arr, ans);
            }
        }
        arr[curNode.idx()] = Color.Black;
        ans.add(curNode);
        return 0;
    }

    @Override
    public Vector<VertexIndex> topologicalSort(VertexIndex start) {
        checkIdx(start);
        Color[] arr = new Color[values.size()];
        Arrays.fill(arr, Color.White);
        Vector<VertexIndex> ans = new Vector<>();
        dfs(start, arr, ans);
        for (int i = 0; i < values.size(); i++) {
            if (i == start.idx()) {
                continue;
            }
            if (arr[i] != Color.White) {
                continue;
            }
            if (dfs(new VertexIndex(i), arr, ans) == 1) {
                return null;
            }
        }
        return ans;
    }
}
