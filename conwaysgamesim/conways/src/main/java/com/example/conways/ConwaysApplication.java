package com.example.conways;

import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Random;

public class ConwaysApplication extends Application {
    public int rectCount = 50;
    public int size = 1000;
    public int rectSize = 1000 / rectCount;
    private Boolean[][] rects = new Boolean[rectCount][rectCount];
    public static int speed = 2;
    private final long INTERVAL_NANOSECONDS = 1_000_000_000L / speed;
    private long lastUpdateTime = 0;
    private int genCount = 0;
    public int genCap;
    public int aliveCount;
    public int probability;
    public int simCount = 1;
    private AnimationTimer timer;


    @Override
    public void start(Stage stage) {
        randomize();

        GridPane gridPane = new GridPane();
        drawGrid(gridPane);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdateTime == 0) {
                    lastUpdateTime = now;
                }
                if (genCount < genCap) {
                    // Check if the specified interval has passed
                    if (now - lastUpdateTime >= INTERVAL_NANOSECONDS) {
                        lastUpdateTime = now;
                        checkRules();
                        genCount++;
                        System.out.println("Generation: "+genCount);
                        drawGrid(gridPane); // Update the simulation
                    }
                } else {
                    ConwaysDataMenu.getInstance().addData(new SimulationData(probability, genCap, aliveCount, simCount));
                    System.err.println("Stop");
                    stopSimulation();
                    stage.close();
                }

            }
        };
        timer.start();

        Scene scene = new Scene(gridPane, size, size);
        Image image = new Image(getClass().getResourceAsStream("/com/example/conways/images/conway.png"));
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.setTitle("Conway's Game of Life Simulation");
        stage.show();
    }
    public void checkRules() {
        for (int i = 0; i < rectCount; i++) {
            for (int j = 0; j < rectCount; j++) {
                if (rects[i][j]) {
                    if(checkAdjacentAlive(i, j) == 2 || checkAdjacentAlive(i, j) == 3) {
                        rects[i][j] = true;
                    } else if (checkAdjacentAlive(i, j) > 3 || checkAdjacentAlive(i, j) < 2) {
                        rects[i][j] = false;
                    }
                } else {
                    if(checkAdjacentAlive(i, j) == 3) {
                        rects[i][j] = true;
                    }
                }

            }
        }
    }

    public int checkAdjacentAlive(int row, int col) {
        int aliveCount = 0;
        int rows = rects.length;
        int cols = rects[0].length;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                // Skip the center cell
                if (i == 0 && j == 0) continue;

                int newRow = row + i;
                int newCol = col + j;

                // Check if the new indices are within the bounds of the array
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                    if (rects[newRow][newCol]) {
                        aliveCount++;
                    }
                }
            }
        }

        return aliveCount;
    }
    public void randomize() {
        for (int i = 0; i < rectCount; i++) {
            for (int j = 0; j < rectCount; j++) {
                Random rand = new Random();
                int range = rand.nextInt(100);
                rects[i][j] = range <= probability;

            }
        }
    }
    public void drawGrid(GridPane gridPane) {
        gridPane.getChildren().clear();
        int aliveCount = 0;
        for (int i = 0; i < rectCount; i++) {
            for (int j = 0; j < rectCount; j++) {
                if(rects[i][j]) {
                    Rectangle rect = new Rectangle(rectSize, rectSize, Color.BLACK);
                    gridPane.add(rect, i, j);
                    aliveCount++;

                } else {
                    Rectangle rect = new Rectangle(rectSize, rectSize, Color.TRANSPARENT);
                    gridPane.add(rect, i, j);
                }

            }
        }
        System.out.println("Lebende:" + aliveCount);
        this.aliveCount = aliveCount;
    }
    public void stopSimulation() {
        timer.stop();
    }
    public void setProbability(int probability) {
        this.probability = probability;
    }
    public void setGeneration(int genCap) {
        this.genCap = genCap;
    }
    public void setWindowSize(int size) {
        this.size = size;
    }
    public void setSimCount(int simCount) {
        this.simCount = simCount;
    }
    public static void main(String[] args) {
        launch();
    }
}

