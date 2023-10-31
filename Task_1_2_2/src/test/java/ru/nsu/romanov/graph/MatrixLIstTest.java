package ru.nsu.romanov.graph;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.romanov.graph.matrix.Matrix;
import ru.nsu.romanov.graph.matrix.MatrixList;

/**
 * tests for matrix list.
 */
public class MatrixLIstTest {
    @Test
    void checkAddRow() {
        Matrix<String, Integer, Integer> mat = new MatrixList<>();
        Assertions.assertEquals(0, mat.getSize());
        mat.addRow();
        Assertions.assertEquals(1, mat.getSize());
    }

    @Test
    void checkAddRowWithOneNode() {
        Matrix<String, Integer, Integer> mat = new MatrixList<>();
        mat.addRow("element");
        Assertions.assertEquals("element", mat.getVal(0, 0));
    }

    @Test
    void checkAddRowWithSomeNodes() {
        Matrix<String, Integer, Integer> mat = new MatrixList<>();
        mat.addRow("element", 5);
        for (int i = 0; i < 5; i++) {
            Assertions.assertEquals("element", mat.getVal(0, i));
        }
    }

    @Test
    void checkSetVal() {
        Matrix<String, Integer, Integer> mat = new MatrixList<>();
        mat.addRow("element");
        Assertions.assertEquals("element", mat.getVal(0, 0));
        mat.setVal(0, 0, "wow");
        Assertions.assertEquals("wow", mat.getVal(0, 0));
    }

    @Test
    void checkGetVal() {
        Matrix<Integer, Integer, Integer> mat = new MatrixList<>();
        mat.addRow(5);
        Assertions.assertEquals(5, mat.getVal(0, 0));
    }

    @Test
    void checkSetEdge() {
        Matrix<Integer, Integer, Integer> mat = new MatrixList<>();
        mat.addRow(5);
        mat.addRow(6);
        mat.addRow(5, 5);
        mat.clear();
        Assertions.assertEquals(0, mat.getSize());
    }

    @Test
    void checkGetSize() {
        Matrix<Integer, Integer, Integer> mat = new MatrixList<>();
        mat.addRow(5);
        mat.addRow(6);
        mat.addRow(5, 5);
        Assertions.assertEquals(3, mat.getSize());
    }

    @Test
    void checkRemoveRow() {
        Matrix<Integer, Integer, Integer> mat = new MatrixList<>();
        mat.addRow(5);
        mat.removeRow(0);
        Assertions.assertEquals(0, mat.getSize());
    }

    @Test
    void checkRemoveColumn() {
        Matrix<Integer, Integer, Integer> mat = new MatrixList<>();
        mat.addRow(5, 5);
        mat.removeColumn(3);
        Assertions.assertEquals(4, mat.getColumnSize(0));
    }

    @Test
    void checkAddColumn() {
        Matrix<Integer, Integer, Integer> mat = new MatrixList<>();
        mat.addRow(5, 5);
        mat.addColumn(0, 4);
        Assertions.assertEquals(4, mat.getVal(0, 5));
    }

    @Test
    void checkAddColumnAll() {
        Matrix<Integer, Integer, Integer> mat = new MatrixList<>();
        mat.addRow(5, 5);
        mat.addRow(5, 5);
        mat.addRow(5, 5);
        mat.addColumnAll(4);
        Assertions.assertEquals(4, mat.getVal(0, 5));
        Assertions.assertEquals(4, mat.getVal(1, 5));
        Assertions.assertEquals(4, mat.getVal(1, 5));
    }

    @Test
    void checkSetColumn() {
        Matrix<Integer, Integer, Integer> mat = new MatrixList<>();
        mat.addRow(5, 5);
        mat.addRow(5, 5);
        mat.addRow(5, 5);
        mat.setColumn(3, 4);
        Assertions.assertEquals(4, mat.getVal(0, 3));
        Assertions.assertEquals(4, mat.getVal(1, 3));
        Assertions.assertEquals(4, mat.getVal(1, 3));
    }

    @Test
    void checkSetRow() {
        Matrix<Integer, Integer, Integer> mat = new MatrixList<>();
        mat.addRow(5, 5);
        mat.addRow(5, 5);
        mat.addRow(5, 5);
        mat.setRows(1, 0);
        for (int i = 0; i < 5; i++) {
            Assertions.assertEquals(0, mat.getVal(1, i));
        }
    }

    @Test
    void checkSetMatrix() {
        Matrix<Integer, Integer, Integer> mat = new MatrixList<>();
        mat.addRow(5, 5);
        mat.addRow(5, 5);
        mat.addRow(5, 5);
        mat.setMatrix(0);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                Assertions.assertEquals(0, mat.getVal(i, j));
            }
        }
    }

    @Test
    void checkEquals() {
        Matrix<Integer, Integer, Integer> mat = new MatrixList<>();
        mat.addRow(5, 5);
        mat.addRow(5, 5);
        mat.addRow(5, 5);
        Matrix<Integer, Integer, Integer> mat2 = new MatrixList<>();
        mat2.addRow(5, 5);
        mat2.addRow(5, 5);
        mat2.addRow(5, 5);
        Assertions.assertEquals(mat, mat2);
        Assertions.assertEquals(mat2, mat);
    }

    @Test
    void checkHashCode() {
        Matrix<Integer, Integer, Integer> mat = new MatrixList<>();
        mat.addRow(5, 5);
        mat.addRow(5, 5);
        mat.addRow(5, 5);
        Matrix<Integer, Integer, Integer> mat2 = new MatrixList<>();
        mat2.addRow(5, 5);
        mat2.addRow(5, 5);
        mat2.addRow(5, 5);
        Assertions.assertEquals(mat.hashCode(), mat2.hashCode());
    }

    @Test
    void checkEqualsEmpty() {
        Matrix<Integer, Integer, Integer> mat = new MatrixList<>();
        Matrix<Integer, Integer, Integer> mat2 = new MatrixList<>();
        Assertions.assertEquals(mat, mat2);
    }

    @Test
    void checkHashcodeEmpty() {
        Matrix<Integer, Integer, Integer> mat = new MatrixList<>();
        Matrix<Integer, Integer, Integer> mat2 = new MatrixList<>();
        Assertions.assertEquals(mat.hashCode(), mat2.hashCode());
    }
}
