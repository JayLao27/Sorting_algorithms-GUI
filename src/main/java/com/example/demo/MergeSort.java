package com.example.demo;

public class MergeSort extends SortingAlgorithm {
    @Override
    public void sort(int[] array) {
        mergeSort(array, 0, array.length - 1);
    }

    private void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
            merge(array, left, mid, right);
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
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
            visualize(array);
        }

        while (i < leftSize) {
            array[k] = leftArray[i];
            i++;
            k++;
            visualize(array);
        }

        while (j < rightSize) {
            array[k] = rightArray[j];
            j++;
            k++;
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
