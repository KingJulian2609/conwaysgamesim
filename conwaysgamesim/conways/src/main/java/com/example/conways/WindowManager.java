package com.example.conways;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;
import java.util.List;

public class WindowManager {

    private static WindowManager instance;
    private final List<Stage> openStages = new ArrayList<>();
    private int stageWidth = 250;
    private int stageHeight = 250;


    private WindowManager() {}



    public Stage openNewWindow(int stageWidth, int stageHeight, boolean mainStage) {
        this.stageWidth = stageWidth;
        this.stageHeight = stageHeight;
        Stage newStage = new Stage();

        if (mainStage) {
            newStage.centerOnScreen();
        } else {
            positionWindow(newStage);
        }

        openStages.add(newStage);

        newStage.setOnCloseRequest(event -> {
            openStages.remove(newStage); // Remove from the list on close
        });

        return newStage;
    }

    private void positionWindow(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        // Loop through the screen width and height to find an available position
        for (double y = 0; y + stageHeight <= screenBounds.getHeight(); y += 10) { // Step vertically
            for (double x = 0; x + stageHeight <= screenBounds.getWidth(); x += 10) { // Step horizontally
                if (isPositionAvailable(x, y, stageWidth, stageHeight)) {
                    // Set the position of the new stage
                    stage.setX(x);
                    stage.setY(y);
                    return; // Exit after placing the window
                }
            }
        }
        // If no position was found (unlikely), center it as a fallback
        stage.setX(screenBounds.getWidth() / 2 - stageWidth / 2);
        stage.setY(screenBounds.getHeight() / 2 - stageHeight / 2);
    }

    private boolean isPositionAvailable(double x, double y, double width, double height) {
        // Check if the position overlaps with any open stages
        for (Stage stage : openStages) {
            if (stage.isShowing()) {
                double stageX = stage.getX();
                double stageY = stage.getY();
                double stageWidth = stage.getWidth();
                double stageHeight = stage.getHeight();

                // Check for overlap
                if (x < stageX + stageWidth &&
                        x + width > stageX &&
                        y < stageY + stageHeight &&
                        y + height > stageY) {
                    return false; // Overlap found
                }
            }
        }
        return true; // No overlap found
    }

    public static WindowManager getInstance() {
        if (instance == null) {
            instance = new WindowManager();
        }
        return instance;
    }

    public static void main(String[] args) {
    }
}
