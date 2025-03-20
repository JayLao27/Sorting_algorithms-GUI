package com.example.demo;

public class ShellSort extends SortingAlgorithm {
    @Override
    public void sort(int[] array) {
        int n = array.length;
        int[] gaps = generateGaps(n);

        for (int gap : gaps) {
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

    private int[] generateGaps(int n) {
        int k = 1, gap;
        while ((gap = (int) (n / Math.pow(2, k))) > 0) {
            k++;
        }
        int[] gaps = new int[k - 1];
        for (int i = 0; i < gaps.length; i++) {
            gaps[i] = (int) (n / Math.pow(2, i + 1));
        }
        return gaps;
    }

    private void visualize(int[] array) {
        try {
            Thread.sleep(50); // Delay for visualization
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
