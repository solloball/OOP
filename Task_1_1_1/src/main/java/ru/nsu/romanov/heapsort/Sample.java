package ru.nsu.romanov.heapsort;

/**
 * Sample class to simulate 1.1 task functionality
 */

public class Sample {  /* main class in task 1 */

    /**
     * swap 2 elements in arr
     */
    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * main fun for sort
     */
    public static void heapsort(int[] arr) {
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--) {  /* make heap */
            heapify(arr, n, i);
        }

        for (int i = n - 1; i >= 0; i--) { /* one by obe extract element from heap */
            swap(arr, i, 0);

            heapify(arr, i, 0);  /* length become i because some elements we extract */
        }
    }

    /**
     * make heap
     */
    public static void heapify(int []arr, int n, int idx) {
        int idxLargest = idx; /* the larges index is root index */
        int idxLeft = (idx * 2) + 1; /* index of left son */
        int idxRight = (idx * 2) + 2;  /* index of right son */

        if (idxRight < n && arr[idxLargest] < arr[idxRight]) {
            idxLargest = idxRight;
        }

        if (idxLeft < n && arr[idxLargest] < arr[idxLeft]) {
            idxLargest = idxLeft;
        }

        if (idxLargest != idx) {
            swap(arr, idx, idxLargest);
            /* swap arr[idx] and arr[idx_largest] if they don't similar */

            heapify(arr, n, idxLargest);
            /* we call recursively heapify */
        }
    }

    public static void main(String []args) {

    }
}

