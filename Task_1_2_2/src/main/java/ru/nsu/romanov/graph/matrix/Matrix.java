package ru.nsu.romanov.graph.matrix;

/**
 * Interface for matrix.
 *
 * @param <V> value which matrix stores.
 */
public interface Matrix<V> {

    /**
     * set new value to position (i, j).
     *
     * @param i key index i.
     * @param j key index j.
     * @param val new Value.
     */
    void setVal(Integer i, Integer j, V val);

    /**
     * get value from position (i, j).
     *
     * @param i key index i.
     * @param j key index j.
     * @return value if it existed, null otherwise.
     */
    V getVal(Integer i, Integer j);

    /**
     * set value for all column with index j.
     *
     * @param j key index j.
     * @param val new value.
     */
    void setColumn(Integer j, V val);

    /**
     * set value for all rows with index i.
     *
     * @param i key index i.
     * @param val new value.
     */
    void setRows(Integer i, V val);

    /**
     * set new value for all matrix.
     *
     * @param val new value.
     */
    void setMatrix(V val);

    /**
     * clear matrix.
     */
    void clear();

    /**
     * remove node in position (i, j).
     *
     * @param i key index i.
     * @param j key index j.
     */
    void remove(Integer i, Integer j);

    /**
     * remove row with index i.
     *
     * @param i key index i.
     */
    void removeRow(Integer i);

    /**
     * remove column with index j.
     *
     * @param j key index j.
     */
    void removeColumn(Integer j);

    /**
     * add new column for all index i.
     *
     * @param i key index i.
     * @param val new value.
     */
    void addColumn(Integer i, V val);

    /**
     * add new column with new value.
     *
     * @param val new value.
     */
    void addColumnAll(V val);

    /**
     * add empty row.
     */
    void addRow();

    /**
     * add row with one node with new value.
     *
     * @param val new value.
     */
    void addRow(V val);

    /**
     * add new row with size node with new value.
     *
     * @param val new value.
     * @param size count of node.
     */
    void addRow(V val, int size);

    /**
     * get size of rows.
     *
     * @return counts of rows.
     */
    int getSize();

    /**
     * get column size with index i.
     *
     * @param i key index i.
     * @return matrix[i] size.
     */
    int getColumnSize(Integer i);
}
