package ru.nsu.romanov.graph;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.lang.reflect.Type;
import java.util.*;


public class GraphList<V> implements Graph<V> {
    private final List<Set<Edge>> list =
            new ArrayList<>();
    private final List<V> values = new LinkedList<>();

    private void checkIdx(VertexIndex idx) {
        if (idx.idx() < 0 || idx.idx() >= values.size()) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public void readFromFile(String fileName) {
        list.clear();
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
                list.add(new HashSet<>());
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
                Edge edge = new Edge(new VertexIndex(from), new VertexIndex(to), weight);
                list.get(from).add(edge);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public VertexIndex addVertex(V val) {
        values.add(val);
        list.add(new HashSet<>());
        return new VertexIndex(values.size() - 1);
    }

    @Override
    public void removeVertex(VertexIndex idx) {
        checkIdx(idx);
        list.remove(idx.idx());
        values.remove(idx.idx());
        for (var iterator : list) {
            iterator.removeIf(edge -> edge.to.equals(idx));
            iterator.forEach(edge -> {
                if (edge.to.idx() > idx.idx()) {
                    edge.to = new VertexIndex(edge.to.idx() - 1);
                }
            });
        }
    }

    @Override
    public void addEdge(VertexIndex from, VertexIndex to, float weight) {
        checkIdx(from);
        checkIdx(to);
        Edge edge = new Edge(from, to, weight);
        list.get(from.idx()).add(edge);
    }

    @Override
    public boolean removeEdge(VertexIndex from, VertexIndex to) {
        checkIdx(from);
        checkIdx(to);
        return list.get(from.idx()).removeIf(edge -> edge.to.equals(to));
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
            if (it.to.equals(to)) {
                return it;
            }
        }
        return null;
    }

    @Override
    public boolean setEdge(VertexIndex from, VertexIndex to, float weight) {
        checkIdx(from);
        checkIdx(to);
        for (var it : list.get(from.idx())) {
            if (it.to.equals(to)) {
                it.weight = weight;
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


