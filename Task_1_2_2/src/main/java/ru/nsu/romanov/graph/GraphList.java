package ru.nsu.romanov.graph;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.*;
import java.util.stream.Collectors;


public class GraphList<V> implements Graph<V> {
    private final List<LinkedList<Map.Entry<VertexIndex, Float>>> list =
            new ArrayList<>();
    private final List<V> values = new ArrayList<>();

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
                list.add(new LinkedList<>());
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
                list.get(from).add(new AbstractMap.SimpleEntry<>(new VertexIndex(to), weight));
            }
        } catch (IOException e) {
            System.out.println("file " + fileName + " has invalid structure");
            throw new RuntimeException(e);
        }
    }

    @Override
    public VertexIndex addVertex(V val) {
        values.add(val);
        list.add(new LinkedList<>());
        return new VertexIndex(values.size() - 1);
    }

    @Override
    public void removeVertex(VertexIndex idx) {
        checkIdx(idx);
        list.remove(idx.idx());
        values.remove(idx.idx());
        for (var it : list) {
            it.removeIf(edge -> edge.getKey().equals(idx));
        }
    }

    @Override
    public void addEdge(VertexIndex from, VertexIndex to, float weight) {
        checkIdx(from);
        checkIdx(to);
        list.get(from.idx()).
                add(new AbstractMap.SimpleEntry<>(new VertexIndex(to.idx()), weight));
    }

    @Override
    public boolean removeEdge(VertexIndex from, VertexIndex to) {
        checkIdx(from);
        checkIdx(to);
        return list.get(from.idx()).removeIf(edge -> edge.getKey().equals(to));
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
        for (var it: list.get(from.idx())) {
            if (it.getKey().equals(to)) {
                return new Edge(from, to, it.getValue());
            }
        }
        return null;
    }

    @Override
    public boolean setEdge(VertexIndex from, VertexIndex to, float weight) {
        checkIdx(from);
        checkIdx(to);
        for (var it : list.get(from.idx())) {
            if (it.getKey().equals(to)) {
                it.setValue(weight);
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
        GraphList<?> graphList = (GraphList<?>) o;
        return Objects.equals(list, graphList.list) && Objects.equals(values, graphList.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list, values);
    }
}


