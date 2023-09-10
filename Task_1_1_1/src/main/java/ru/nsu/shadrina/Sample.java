package ru.nsu.shadrina;

/**
 * Sample class to simulate 1.1 task functionality
 */
public class Sample {

    public static void swap (int []arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
    public static void heapsort(int[] arr) {
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--)  // make heap
            heapify(arr, n, i);

        for (int i = n - 1; i >= 0; i--) { // one by obe extract element from heap
             swap(arr, i, 0);

            heapify(arr, i, 0);  // length become i because some elements we extract
        }
    }

    public static void heapify(int []arr, int n, int idx) {   // make
        int idx_largest = idx; // the larges index is root index
        int idx_left = (idx * 2) + 1; // index of left son
        int idx_right = (idx * 2) + 2;  // index of right son

        if (idx_right < n && arr[idx_largest] < arr[idx_right])
            idx_largest = idx_right;

        if (idx_left < n && arr[idx_largest] < arr[idx_left])
            idx_largest = idx_left;

        if (idx_largest != idx) {
            swap(arr, idx, idx_largest);   // swap arr[idx] and arr[idx_largest] if they don't similar

            heapify(arr, n, idx_largest);  //  we call recursively heapify
        }
    }
    public static void main (String []args) {

    }
}

