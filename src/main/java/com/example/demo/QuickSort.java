package com.example.demo;

public class QuickSort extends SortingAlgorithm {
    @Override
    public void sort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    private void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pi = partition(array, low, high);
            visualize(array);
            quickSort(array, low, pi - 1);
            quickSort(array, pi + 1, high);
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

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private void visualize(int[] array) {
        try {
            Thread.sleep(50); // Delay for visualization
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
