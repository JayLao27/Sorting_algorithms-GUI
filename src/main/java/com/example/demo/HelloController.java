package com.example.demo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.Random;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private VBox root;
    @FXML
    private Canvas canvas;
    @FXML
    private ComboBox<String> algorithmBox;
    @FXML
    private Button sortButton;

    private static final int WIDTH = 500;
    private static final int HEIGHT = 300;
    private int[] array;
    private GraphicsContext gc;
    private Timeline timeline;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        algorithmBox.getItems().addAll("TimSort", "QuickSort", "MergeSort", "HeapSort", "ShellSort");
        algorithmBox.setValue("TimSort");
        sortButton.setOnAction(e -> runSort(algorithmBox.getValue()));
        generateArray(50);
    }

    private void generateArray(int size) {
        Random rand = new Random();
        array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(HEIGHT - 50) + 10;
        }
        drawArray();
    }

    private void drawArray() {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        double barWidth = (double) WIDTH / array.length;
        for (int i = 0; i < array.length; i++) {
            gc.fillRect(i * barWidth, HEIGHT - array[i], barWidth - 2, array[i]);
        }
    }

    private void runSort(String algorithm) {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(50), e -> drawArray()));
        timeline.play();

        new Thread(() -> {
            switch (algorithm) {
                case "TimSort":
                    Arrays.sort(array);
                    break;
                case "QuickSort":
                    quickSort(0, array.length - 1);
                    break;
                case "MergeSort":
                    mergeSort(0, array.length - 1);
                    break;
                case "HeapSort":
                    heapSort();
                    break;
                case "ShellSort":
                    shellSort();
                    break;
            }
            timeline.stop();
            drawArray();
        }).start();
    }

    private void quickSort(int low, int high) {
        if (low < high) {
            int pi = partition(low, high);
            quickSort(low, pi - 1);
            quickSort(pi + 1, high);
        }
    }

    private int partition(int low, int high) {
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return i + 1;
    }

    private void mergeSort(int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(left, mid);
            mergeSort(mid + 1, right);
            merge(left, mid, right);
        }
    }

    private void merge(int left, int mid, int right) {
        int[] temp = Arrays.copyOfRange(array, left, right + 1);
        int i = 0, j = mid - left + 1, k = left;
        while (i <= mid - left && j < temp.length) {
            array[k++] = (temp[i] < temp[j]) ? temp[i++] : temp[j++];
        }
        while (i <= mid - left) {
            array[k++] = temp[i++];
        }
        while (j < temp.length) {
            array[k++] = temp[j++];
        }
    }

    private void heapSort() {
        int n = array.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(n, i);
        }
        for (int i = n - 1; i > 0; i--) {
            swap(0, i);
            heapify(i, 0);
        }
    }

    private void heapify(int n, int i) {
        int largest = i, left = 2 * i + 1, right = 2 * i + 2;
        if (left < n && array[left] > array[largest]) largest = left;
        if (right < n && array[right] > array[largest]) largest = right;
        if (largest != i) {
            swap(i, largest);
            heapify(n, largest);
        }
    }

    private void shellSort() {
        for (int gap = array.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < array.length; i++) {
                int temp = array[i], j = i;
                while (j >= gap && array[j - gap] > temp) {
                    array[j] = array[j - gap];
                    j -= gap;
                }
                array[j] = temp;
            }
        }
    }

    private void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
