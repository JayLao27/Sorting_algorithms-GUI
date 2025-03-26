package com.example.demo;

public class ModifiedTimsort extends SortingAlgorithm {
    @Override
    public void sort(int[] array) {
        optimizedTimSort(array);
    }

    private void optimizedTimSort(int[] array) {
        int n = array.length;
        int RUN = findOptimalRunSize(n);

        for (int i = 0; i < n; i += RUN) {
            insertionSort(array, i, Math.min(i + RUN - 1, n - 1));
            visualize(array);
        }

        for (int size = RUN; size < n; size = 2 * size) {
            for (int left = 0; left < n; left += 2 * size) {
                int mid = left + size - 1;
                int right = Math.min(left + 2 * size - 1, n - 1);
                if (mid < right) {
                    inPlaceMerge(array, left, mid, right);
                    visualize(array);
                }
            }
        }
    }

    private int findOptimalRunSize(int n) {
        int r = 0;
        while (n >= 64) {
            r |= (n & 1);
            n >>= 1;
        }
        return n + r;
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

    private void inPlaceMerge(int[] array, int left, int mid, int right) {
        int start2 = mid + 1;
        if (array[mid] <= array[start2]) return; // Already sorted

        while (left <= mid && start2 <= right) {
            if (array[left] <= array[start2]) {
                left++;
            } else {
                int value = array[start2];
                int index = start2;

                while (index != left) {
                    array[index] = array[index - 1];
                    index--;
                }
                array[left] = value;

                left++;
                mid++;
                start2++;
            }
            visualize(array);
        }
    }

    private void visualize(int[] array) {
        try {
            Thread.sleep(18); // Delay for visualization
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
