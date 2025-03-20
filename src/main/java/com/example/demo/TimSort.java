package com.example.demo;

import java.util.Arrays;

public class TimSort extends SortingAlgorithm {
    private static final int RUN = 32;

    @Override
    public void sort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n; i += RUN) {
            insertionSort(array, i, Math.min(i + RUN - 1, n - 1));
            visualize(array);
        }

        for (int size = RUN; size < n; size = 2 * size) {
            for (int left = 0; left < n; left += 2 * size) {
                int mid = left + size - 1;
                int right = Math.min(left + 2 * size - 1, n - 1);
                if (mid < right) {
                    merge(array, left, mid, right);
                    visualize(array);
                }
            }
        }
    }

    private void insertionSort(int[] array, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int temp = array[i];
            int j = i - 1;
            while (j >= left && array[j] > temp) {
                array[j + 1] = array[j];
                j--;
                visualize(array);
            }
            array[j + 1] = temp;
            visualize(array);
        }
    }

    private void merge(int[] array, int left, int mid, int right) {
        int leftSize = mid - left + 1;
        int rightSize = right - mid;
        int[] leftArray = new int[leftSize];
        int[] rightArray = new int[rightSize];

        System.arraycopy(array, left, leftArray, 0, leftSize);
        System.arraycopy(array, mid + 1, rightArray, 0, rightSize);

        int i = 0, j = 0, k = left;
        while (i < leftSize && j < rightSize) {
            if (leftArray[i] <= rightArray[j]) {
                array[k++] = leftArray[i++];
            } else {
                array[k++] = rightArray[j++];
            }
            visualize(array);
        }

        while (i < leftSize) {
            array[k++] = leftArray[i++];
            visualize(array);
        }
        while (j < rightSize) {
            array[k++] = rightArray[j++];
            visualize(array);
        }
    }

    private void visualize(int[] array) {
        try {
            Thread.sleep(50); // Delay for visualization
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
