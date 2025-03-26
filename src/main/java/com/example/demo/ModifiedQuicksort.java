package com.example.demo;

public class ModifiedQuicksort extends SortingAlgorithm {
    @Override
    public void sort(int[] array) {
        modifiedQuicksort(array, 0, array.length - 1);
    }

    private void modifiedQuicksort(int[] array, int low, int high) {
        if (high - low + 1 <= 10) {
            insertionSort(array, low, high);
            visualize(array);
            return;
        }

        if (low < high) {
            int pi = partition(array, low, high);
            visualize(array);
            modifiedQuicksort(array, low, pi - 1);
            modifiedQuicksort(array, pi + 1, high);
        }
    }

    private int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                swap(array, i, j);
                visualize(array);
            }
        }
        swap(array, i + 1, high);
        visualize(array);
        return i + 1;
    }

    private void insertionSort(int[] array, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= low && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
            visualize(array);
        }
    }

    private void swap(int[] array, int i, int j) {
        if (i != j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    private void visualize(int[] array) {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
