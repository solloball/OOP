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
        int len = 1000000;
        int[] sortArr = new int[len];
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rd.nextInt(); // storing random integers in an array
        }
        System.arraycopy(arr, 0, sortArr, 0, len);
        Arrays.sort(sortArr);
        Heapsort.heapsort(arr);
        Assertions.assertArrayEquals(sortArr, arr);
    }
}