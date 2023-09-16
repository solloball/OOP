package ru.nsu.romanov.polynomial;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestMain {

    int[] getMadeRandomArr(int size) {
        Random rd = new Random(); // creating Random object.
        int[] arr = new int[size];

        for (int elem : arr) {
            elem = rd.nextInt(); // storing random integers in an array.
        }

        return arr;
    }

    @Test
    void checkConstructorWithArr() {
        Random rd = new Random();
        int[] arr = getMadeRandomArr(1000000);
        Main.Polynomial p = new Main.Polynomial(arr);
        assertArrayEquals(p.getArr(), arr);
    }

    @Test
    void checkConstructorWithEmptyArr() {
        Main.Polynomial p = new Main.Polynomial(new int[] {});
        assertEquals(0, p.getArr().length);
    }

    @Test
    void checkFunIsEqual() {
        int[] arr = getMadeRandomArr(10000);
        Main.Polynomial p1 = new Main.Polynomial(arr);
        Main.Polynomial p2 = new Main.Polynomial(arr);
        assertTrue(p1.isEqual(p2));
    }

    @Test
    void checkFunIsEqualWithDifferentLength() {
        int[] arr = new int[100];
        int[] arr2 = getMadeRandomArr(10);
        Arrays.fill(arr, 0);
        Main.Polynomial p1 = new Main.Polynomial(new int[] {});
        Main.Polynomial p2 = new Main.Polynomial(arr);
        assertTrue(p1.isEqual(p2)); // one of them is empty and other has arr in which every coefficient = 0.

        System.arraycopy(arr2, 0, arr, arr.length - arr2.length - 1, arr2.length);
        p1.setArr(arr);
        p2.setArr(arr2);
        assertTrue(p1.isEqual(p2));
    }

    @Test
    void checkFunAdd() {
        Main.Polynomial p1 = new Main.Polynomial(new int[] {4, 5, 7, 8});
        Main.Polynomial p2 = new Main.Polynomial(new int[] {-5, 10, 8});
        assertArrayEquals(new int[] {4, 0, 17, 16}, p1.add(p2).getArr());
        assertArrayEquals(new int[] {4, 0, 17, 16}, p2.add(p1).getArr());
    }

    @Test
    void checkFunMlt() {
        Main.Polynomial p1 = new Main.Polynomial(new int[] {4, 5, 7, 8});
        Main.Polynomial p2 = new Main.Polynomial(new int[] {-5, 10, 8});
        assertArrayEquals(new int[] {4, -25, 70, 64}, p1.mlt(p2).getArr());
        assertArrayEquals(new int[] {4, 0, 17, 16}, p2.mlt(p1).getArr());
    }

    @Test
    void checkFunSub() {
        Main.Polynomial p1 = new Main.Polynomial(new int[] {4, 5, 7, 8});
        Main.Polynomial p2 = new Main.Polynomial(new int[] {-5, 10, 8});
        assertArrayEquals(new int[] {4, 10, -3, 0}, p1.sub(p2).getArr());
        assertArrayEquals(new int[] {-4, -10, 3, 0}, p2.sub(p1).getArr());
    }

    @Test
    void checkDifferentiate() {
        Main.Polynomial p1 = new Main.Polynomial(new int[] {4, 5, 7, 8});
        Main.Polynomial p2 = new Main.Polynomial(new int[] {0, 0, 12, 10, 7});
        Main.Polynomial p3 = new Main.Polynomial(new int[] {0, 0, 24, 10});
        assertTrue(p3.isEqual(p2.differentiate(1)));
        assertTrue(p3.isEqual(p1.differentiate(2)));
        assertTrue(p3.differentiate(100).isEqual(p1.differentiate(100)));
    }

    @Test
    void checkEvaluate() {
        Main.Polynomial p1 = new Main.Polynomial(new int[] {4, 5, 7, 8});
        Main.Polynomial p2 = new Main.Polynomial(new int[] {0, 0, -7, 10, -25});
        assertEquals(4 + 5 + 7 + 8, p1.evaluate(1));
        assertEquals(4 * (-8) + 4 * 5 - 14 + 8, p1.evaluate(-2));
        assertEquals(-7 * 9 + 3 * 10 -25, p2.evaluate(3));
    }

    @Test
    void checkToString() {
        int[] arr = new int[100];
        int[] arr2 = new int[] {4, -5, 0, 6};
        int[] arr3 = new int[] {0, 0, 0, -5, 4, 0};
        Arrays.fill(arr, 0);
        Main.Polynomial p = new Main.Polynomial(arr);
        assertEquals("", p.toString());
        p.setArr(arr2);
        assertEquals("4x^3 - 5x^2 + 6", p.toString());
        p.setArr(arr3);
        assertEquals(" - 5x^2 + 4x", p.toString());
    }
}