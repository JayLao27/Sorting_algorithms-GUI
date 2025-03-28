package com.example.demo;

public class ModifiedHeapsort extends SortingAlgorithm {
    @Override
    public void sort(int[] array) {
        hybridHeapSort(array);
    }

    private void hybridHeapSort(int[] array) {
        int n = array.length;

        if (n <= 18) { // Use insertion sort for small arrays
            insertionSort(array, 0, n - 1);
            visualize(array);
            return;
        }

        // Build max heap properly
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }
        visualize(array);

        // Extract elements from the heap
        for (int i = n - 1; i > 0; i--) {
            swap(array, 0, i);
            heapify(array, i, 0);
            visualize(array);
        }
    }

    private void heapify(int[] array, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && array[left] > array[largest]) {
            largest = left;
        }

        if (right < n && array[right] > array[largest]) {
            largest = right;
        }

        if (largest != i) {
            swap(array, i, largest);
            heapify(array, n, largest);
        }
    }

    private void insertionSort(int[] array, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= left && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
            visualize(array);
        }
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private void visualize(int[] array) {
        try {
            Thread.sleep(20); // Delay for visualization
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}