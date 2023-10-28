package ru.nsu.romanov.graph;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.*;;

public class GraphAdjMat<V> implements Graph<V>{

    private final ArrayList<ArrayList<Float>> mat = new ArrayList<>();
    private final List<V> values = new LinkedList<>();

    private void checkIdx(VertexIndex idx) {
        if (idx.idx() < 0 || idx.idx() >= values.size()) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public void readFromFile(String fileName) {
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
}
