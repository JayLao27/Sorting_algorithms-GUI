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
    private ComboBox<Integer> sizeBox;
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
        sizeBox.setValue(50);
        sortButton.setOnAction(e -> runSort(algorithmBox.getValue()));
        clearButton.setOnAction(e -> resetArray());
        sizeBox.setOnAction(e -> generateArray(sizeBox.getValue()));
        generateArray(sizeBox.getValue());
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
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(50), e -> drawArray()));
        timeline.play();

        new Thread(() -> {
            SortingAlgorithm sorter;
            String complexity;
            long startTime = System.currentTimeMillis();
            switch (algorithm) {
                case "QuickSort" -> {
                    sorter = new QuickSort();
                    complexity = "O(n log n) average, O(n^2) worst case";
                }
                case "MergeSort" -> {
                    sorter = new MergeSort();
                    complexity = "O(n log n) in all cases";
                }
                case "HeapSort" -> {
                    sorter = new HeapSort();
                    complexity = "O(n log n)";
                }
                case "ShellSort" -> {
                    sorter = new ShellSort();
                    complexity = "O(n log n) best, O(n^2) worst case/average";
                }
                default -> {
                    sorter = new TimSort();
                    complexity = "O(n log n)";
                }
            }
            sorter.sort(array);
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;

            timeline.stop();

            // Add a delay before updating the UI
            Timeline delayTimeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {
                drawArray();
                final String finalComplexity = complexity;
                javafx.application.Platform.runLater(() -> {
                    complexityLabel.setText("Time Complexity: " + finalComplexity);
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
    }
}