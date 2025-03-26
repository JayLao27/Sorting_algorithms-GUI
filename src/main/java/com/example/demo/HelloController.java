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

import java.util.Random;

public class HelloController {
    @FXML
    private Label title;
    @FXML
    private Label welcomeText;
    @FXML
    private VBox root;
    @FXML
    private Canvas canvas;
    @FXML
    private ComboBox<String> algorithmBox;
    @FXML
    private ComboBox<Integer> sizeBox;
    @FXML
    private ComboBox<String> algorithmBox1;
    @FXML
    private Button sortButton;
    @FXML
    private Button clearButton;
    @FXML
    private Label complexityLabel;
    @FXML
    private Label timeLabel;

    private static final int WIDTH = 1500;
    private static final int HEIGHT = 300;
    private int[] array;
    private int[] initialArray;
    private GraphicsContext gc;
    private Timeline timeline;

    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        algorithmBox.getItems().addAll("TimSort", "QuickSort", "MergeSort", "HeapSort", "ShellSort");
        algorithmBox.setValue("TimSort");
        sizeBox.getItems().addAll(20, 50, 100);
        sizeBox.setValue(20);

        algorithmBox1.getItems().addAll("Original", "Modified");
        algorithmBox1.setValue("Original");

        sortButton.setOnAction(e -> runSort(algorithmBox.getValue()));
        clearButton.setOnAction(e -> resetArray());
        sizeBox.setOnAction(e -> generateArray(sizeBox.getValue()));
        algorithmBox.setOnAction(e -> updateTitle());
        algorithmBox1.setOnAction(e -> updateTitle());

        generateArray(sizeBox.getValue());
        updateTitle();
    }

    private void updateTitle() {
        title.setText(algorithmBox.getValue() + " (" + algorithmBox1.getValue() + ")");
    }

    private void generateArray(int size) {
        Random rand = new Random();
        array = new int[size];
        initialArray = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(HEIGHT - 50) + 10;
            initialArray[i] = array[i];
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
        updateTitle();
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(50), e -> drawArray()));
        timeline.play();

        new Thread(() -> {
            SortingAlgorithm sorter;
            String complexity;
            String mode = algorithmBox1.getValue();
            long startTime = System.currentTimeMillis();

            switch (algorithm) {
                case "QuickSort" -> sorter = mode.equals("Modified") ? new ModifiedQuicksort() : new QuickSort();
                case "MergeSort" -> sorter = mode.equals("Modified") ? new ModifiedMerge() : new MergeSort();
                case "HeapSort" -> sorter = mode.equals("Modified") ? new ModifiedHeapsort() : new HeapSort();
                case "ShellSort" -> sorter = mode.equals("Modified") ? new ModifiedShellsort() : new ShellSort();
                default -> sorter = mode.equals("Modified") ? new ModifiedTimsort() : new TimSort();
            }

            complexity = switch (algorithm + mode) {
                case "QuickSortOriginal" -> "O(n log n) average, O(n^2) worst case";
                case "QuickSortModified" -> "O(n log n) optimized with better partitioning";
                case "MergeSortOriginal" -> "O(n log n) in all cases";
                case "MergeSortModified" -> "O(n log n) with in-place merge optimization";
                case "HeapSortOriginal" -> "O(n log n)";
                case "HeapSortModified" -> "O(n log n) with improved heap restructuring";
                case "ShellSortOriginal" -> "O(n log n) best, O(n^2) worst case";
                case "ShellSortModified" -> "O(n^(3/2)) with optimized gap sequence";
                case "TimSortOriginal" -> "O(n log n)";
                case "TimSortModified" -> "O(n log n) with adaptive merging and optimal runs";
                default -> "Unknown complexity";
            };

            sorter.sort(array);
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;

            timeline.stop();

            Timeline delayTimeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {
                drawArray();
                javafx.application.Platform.runLater(() -> {
                    complexityLabel.setText("Time Complexity: " + complexity);
                    timeLabel.setText("Execution Time: " + elapsedTime + " ms");
                });
            }));
            delayTimeline.setCycleCount(1);
            delayTimeline.play();
        }).start();
    }

    private void resetArray() {
        System.arraycopy(initialArray, 0, array, 0, initialArray.length);
        drawArray();
        complexityLabel.setText("");
        timeLabel.setText("");
        timeline.stop();
        updateTitle();
    }
}
