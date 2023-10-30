package ru.nsu.romanov.graph;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.Vector;

/**
 * Implementation of Graph.
 * In this realization order of edge is important.
 *
 * @param <V> type value if vertex.
 */
public class GraphIncidenceMat<V> implements Graph<V> {

    private final List<V> values = new LinkedList<>();

    private final List<List<Float>> mat = new LinkedList<>();

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
        FileReader reader;
        try {
            reader = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("failed to found file");
            throw new RuntimeException(e);
        }
        StreamTokenizer tokenizer = new StreamTokenizer(reader);
        int countVertex;
        try {
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
                mat.get(from).set(i, weight);
                mat.get(to).set(i, -weight);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public VertexIndex addVertex(V val) {
        values.add(val);
        mat.add(new LinkedList<>(Collections.nCopies(countEdge, null)));
        return new VertexIndex(values.size());
    }

    @Override
    public void removeVertex(VertexIndex idx) {
        checkIdx(idx);
        values.remove(idx.idx());
        Stack<Integer> st = new Stack<>();
        for (int i = 0; i < countEdge; i++) {
            if (mat.get(idx.idx()).get(i) != null) {
                st.push(i);
            }
        }
        int size = st.size();
        for (var i = 0; i < size; i++) {
            int index = st.pop();
            for (var it : mat) {
                it.remove(index);

            }
        }
        countEdge -= size;
        mat.remove(idx.idx());
    }

    @Override
    public void addEdge(VertexIndex from, VertexIndex to, float weight) {
        checkIdx(from);
        checkIdx(to);
        for (int i = 0; i < values.size(); i++) {
            if (i == to.idx()) {
                mat.get(i).add(-weight);
            } else if (i == from.idx()) {
                mat.get(i).add(weight);
            } else {
                mat.get(i).add(null);
            }
        }
        countEdge++;
    }

    @Override
    public boolean removeEdge(VertexIndex from, VertexIndex to) {
        for (int i = 0; i < countEdge; i++) {
            Float weightFrom = mat.get(from.idx()).get(i);
            Float weightTo = mat.get(to.idx()).get(i);
            if (weightFrom != null && weightTo != null && weightFrom > 0) {
                for (var it : mat) {
                    it.remove(i);
                }
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
            Float weightFrom = mat.get(from.idx()).get(i);
            Float weightTo = mat.get(to.idx()).get(i);
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
            Float weightFrom = mat.get(from.idx()).get(i);
            Float weightTo = mat.get(to.idx()).get(i);
            if (weightFrom != null && weightTo != null
                    && weightFrom > 0 && weightTo < 0) {
                mat.get(from.idx()).set(i, weight);
                mat.get(to.idx()).set(i, -weight);
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

    private int dfs(VertexIndex curNode, Color[] arr, Vector<VertexIndex> ans) {
        arr[curNode.idx()] = Color.Grey;
        for (int i = 0; i < countEdge; i++) {
            Float weight = mat.get(curNode.idx()).get(i);
            if (weight != null && weight > 0) {
                for (int j = 0; j < values.size(); j++) {
                    Float newWeight = mat.get(j).get(i);
                    if (newWeight == null || newWeight != -weight) {
                        continue;
                    }
                    if (arr[j] == Color.Grey) {
                        return 1;
                    }
                    if (arr[j] != Color.Black) {
                        dfs(new VertexIndex(j), arr, ans);
                    }
                }
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
        if (dfs(start, arr, ans) == 1) {
            return null;
        }
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
