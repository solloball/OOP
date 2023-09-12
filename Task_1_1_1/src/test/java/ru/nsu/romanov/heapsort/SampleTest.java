package ru.nsu.romanov.heapsort;

import java.util.Arrays;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;


class SampleTest {

    @Test
    void firstTest() {
        int[] arr;
        int[] ans;
        arr = new int[] {5, 4, 3, 2, 1};
        ans = new int[] {1, 2, 3, 4, 5};
        Sample.heapsort(arr);
        assertArrayEquals(arr, ans);
    }

    @Test
    void testWithoutElements() {
        int[] arr;
        int[] ans;
        arr = new int[] {};
        ans = new int[] {};
        Sample.heapsort(arr);
        assertArrayEquals(arr, ans);
    }

    @Test
    void testWithOneElement() {
        int[] arr;
        int[] ans;
        arr = new int[] {1};
        ans = new int[] {1};
        Sample.heapsort(arr);
        assertArrayEquals(arr, ans);
    }

    @Test
    void testWithMaxAvailableValue() {
        int[] arr;
        int[] ans;
        arr = new int[] {Integer.MAX_VALUE, 5, 6, 4, Integer.MIN_VALUE, 6, -56, 0, 43, -5};
        ans = new int[] {Integer.MIN_VALUE, -56, -5, 0, 4, 5, 6, 6, 43, Integer.MAX_VALUE};
        Sample.heapsort(arr);
        assertArrayEquals(arr, ans);
    }

    @Test
    void testWithRandArr() {
        Random rd = new Random(); // creating Random object
        int[] sortArr;
        int[] arr = new int[100];
        for (int elem: arr) {
            elem = rd.nextInt(); // storing random integers in an array
        }
        sortArr = arr;
        Arrays.sort(sortArr);
        Sample.heapsort(arr);
        assertArrayEquals(arr, sortArr);
    }

    @Test
    void testMain() {
        Sample.main(new String[] {});
        assertTrue(true);
    }

    @Test
    void testSwap() {
        int []arr;
        arr = new int[] {5, 4, 3, 2, 1};
        Sample.swap(arr, 0, 3);
        assertEquals(2, arr[0]);
        assertEquals(5, arr[3]);
    }

    @Test
    void testHeapify() {
        int []arr;
        arr = new int[] {4, 7, 3, 5, 1};
        Sample.heapify(arr, 0, 0);
        assertArrayEquals(arr, new int[] {4, 7, 3, 5, 1});

        Sample.heapify(arr, 3, 0);
        assertArrayEquals(arr, new int[] {7, 4, 3, 5, 1});
    }
}