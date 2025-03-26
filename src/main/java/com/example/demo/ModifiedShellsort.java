package com.example.demo;

public class ModifiedShellsort extends SortingAlgorithm {
    @Override
    public void sort(int[] array) {
        optimizedShellSort(array);
    }

    private void optimizedShellSort(int[] array) {
        int n = array.length;
        int[] gaps = {1, 4, 18, 23, 57, 132, 301, 701}; // Tokuda's sequence

        for (int g = gaps.length - 1; g >= 0; g--) {
            int gap = gaps[g];
            if (gap >= n) continue; // Skip gaps larger than array size

            for (int i = gap; i < n; i++) {
                int temp = array[i];
                int j = i;
                while (j >= gap && array[j - gap] > temp) {
                    array[j] = array[j - gap];
                    j -= gap;
                    visualize(array); // Visualization step
                }
                array[j] = temp;
                visualize(array); // Visualization step after insertion
            }
        }
    }

    private void visualize(int[] array) {
        try {
            Thread.sleep(20); // Delay for visualization
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
