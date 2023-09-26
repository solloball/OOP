package ru.nsu.romanov.heapsort;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



class HeapsortTest {

    @Test
    void firstTest() {
        int[] arr;
        int[] ans;
        arr = new int[] {5, 4, 3, 2, 1};
        ans = new int[] {1, 2, 3, 4, 5};
        Heapsort.heapsort(arr);
        Assertions.assertArrayEquals(arr, ans);
    }

    @Test
    void testWithoutElements() {
        int[] arr;
        int[] ans;
        arr = new int[] {};
        ans = new int[] {};
        Heapsort.heapsort(arr);
        Assertions.assertArrayEquals(arr, ans);
    }

    @Test
    void testWithOneElement() {
        int[] arr;
        int[] ans;
        arr = new int[] {1};
        ans = new int[] {1};
        Heapsort.heapsort(arr);
        Assertions.assertArrayEquals(ans, arr);
    }

    @Test
    void testWithMaxAvailableValue() {
        int[] arr;
        int[] ans;
        arr = new int[] {Integer.MAX_VALUE, 5, 6, 4, Integer.MIN_VALUE, 6, -56, 0, 43, -5};
        ans = new int[] {Integer.MIN_VALUE, -56, -5, 0, 4, 5, 6, 6, 43, Integer.MAX_VALUE};
        Heapsort.heapsort(arr);
        Assertions.assertArrayEquals(ans, arr);
    }

    @Test
    void testWithRandArr() {
        Random rd = new Random(); // creating Random object
        int[] sortArr = new int[1000000];
        int[] arr = new int[1000000];
        for (int elem : arr) {
            elem = rd.nextInt(); // storing random integers in an array
        }
        System.arraycopy(arr, 0, sortArr, 0, 3);
        Arrays.sort(sortArr);
        Heapsort.heapsort(arr);
        Assertions.assertArrayEquals(sortArr, arr);

        Arrays.sort(arr);
        Heapsort.heapsort(arr);
        Assertions.assertArrayEquals(sortArr, arr);
    }


    @Test
    void testMain() {
        Heapsort.main(new String[] {});
        Assertions.assertTrue(true);
    }

    @Test
    void testSwap() {
        int []arr;
        arr = new int[] {5, 4, 3, 2, 1};
        Heapsort.swap(arr, 0, 3);
        Assertions.assertEquals(2, arr[0]);
        Assertions.assertEquals(5, arr[3]);
    }

    @Test
    void testHeapify() {
        int []arr;
        arr = new int[] {4, 7, 3, 5, 1};
        Heapsort.heapify(arr, 0, 0);
        Assertions.assertArrayEquals(new int[] {4, 7, 3, 5, 1}, arr);

        Heapsort.heapify(arr, 3, 0);
        Assertions.assertArrayEquals(new int[] {7, 4, 3, 5, 1}, arr);
    }
}