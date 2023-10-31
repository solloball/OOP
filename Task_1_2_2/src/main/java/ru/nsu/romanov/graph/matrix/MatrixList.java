package ru.nsu.romanov.graph.matrix;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * realization of matrix which is ArrayList * ArrayList.
 *
 * @param <V> type of value.
 */
public class MatrixList<V> implements Matrix<V, Integer, Integer> {

    private final List<List<V>> matrix = new ArrayList<>();

    private void checkI(Integer i) {
        if (i == null || i < 0 || i >= matrix.size()) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkJ(Integer j) {
        if (j == null || j < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public void setVal(Integer i, Integer j, V val) {
        checkI(i);
        if (j < 0 || j >= matrix.get(i).size()) {
            throw new IndexOutOfBoundsException();
        }
        matrix.get(i).set(j, val);
    }

    @Override
    public V getVal(Integer i, Integer j) {
        checkI(i);
        if (j < 0 || j >= matrix.get(i).size()) {
            throw new IndexOutOfBoundsException();
        }
        return matrix.get(i).get(j);
    }

    @Override
    public void clear() {
        matrix.clear();
    }

    @Override
    public void remove(Integer i, Integer j) {
        checkI(i);
        if (j < 0 || j >= matrix.get(i).size()) {
            throw new IndexOutOfBoundsException();
        }
        matrix.get(i).remove((int) j);
    }

    @Override
    public void removeRow(Integer i) {
        checkI(i);
        matrix.remove((int) i);
    }

    @Override
    public void removeColumn(Integer j) {
        checkJ(j);
        for (var row : matrix) {
            if (j >= row.size()) {
                throw new IndexOutOfBoundsException();
            }
            row.remove((int) j);
        }
    }

    @Override
    public void addColumn(Integer i, V val) {
        checkI(i);
        matrix.get(i).add(val);
    }

    @Override
    public void addColumnAll(V val) {
        matrix.forEach(row -> row.add(val));
    }

    @Override
    public void addRow() {
        matrix.add(new ArrayList<>());
    }

    @Override
    public void addRow(V val) {
        matrix.add(new ArrayList<>());
        matrix.get(matrix.size() - 1).add(val);
    }

    @Override
    public void addRow(V val, int size) {
        matrix.add(new ArrayList<>(Collections.nCopies(size, val)));
    }

    @Override
    public void setColumn(Integer j, V val) {
        checkJ(j);
        for (var row : matrix) {
            if (j >= row.size()) {
                throw new IndexOutOfBoundsException();
            }
            row.set(j, val);
        }
    }

    @Override
    public void setRows(Integer i, V val) {
        checkI(i);
        Collections.fill(matrix.get(i), val);
    }

    @Override
    public void setMatrix(V val) {
        for (var row : matrix) {
            Collections.fill(row, val);
        }
    }

    @Override
    public int getSize() {
        return matrix.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MatrixList<?> that = (MatrixList<?>) o;
        return Objects.equals(matrix, that.matrix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matrix);
    }

    @Override
    public int getColumnSize(Integer i) {
        checkI(i);
        return matrix.get(i).size();
    }
}
