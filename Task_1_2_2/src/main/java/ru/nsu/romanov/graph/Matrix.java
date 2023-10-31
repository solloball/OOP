package ru.nsu.romanov.graph;

public interface Matrix<V, I, J> {

    void setVal(I i, J j, V val);

    V getVal(I i, J j);

    void setColumn(I j, V val);

    void setRows(I i, V val);

    void setMatrix(V val);

    void clear();

    void remove(I i, J j);

    void removeRow(I i);

    void removeColumn(J j);

    void addColumn(I i, V val);

    void addColumnAll(V val);

    void addRow();

    void addRow(V val);

    void addRow(V val, int size);

    int getSize();

    int getColumnSize(Integer i);
}
