package com.example.conways;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ConwaysMenu extends Application {
    public int windowSize = 750;
    public int simCount;
    private BorderPane pane = new BorderPane();



    @Override
    public void start(Stage stage) {

        stage = WindowManager.getInstance().openNewWindow(windowSize, windowSize, true);
        drawMenu(stage);

        Scene scene = new Scene(pane, windowSize, windowSize);
        stage.setScene(scene);
        Image image = new Image(getClass().getResourceAsStream("/com/example/conways/images/conway.png"));
        stage.getIcons().add(image);
        stage.setTitle("Conway's Game of Life Menu");
        stage.show();

        ConwaysDataMenu.getInstance().start(WindowManager.getInstance().openNewWindow(500, 300, false));
    }
    public void drawMenu(Stage stage) {

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(50, 50, 50, 50));
        hbox.setSpacing(15);
        HBox hbox2 = new HBox();
        hbox2.setAlignment(Pos.BOTTOM_CENTER);
        hbox2.setPadding(new Insets(10, 10, 150, 10));
        hbox2.setSpacing(10);
        VBox titleBox = new VBox();
        titleBox.setPadding(new Insets(50, 10, 10, 10));
        titleBox.setAlignment(Pos.TOP_CENTER);

        Label label = new Label("Geschwindigkeit");
        Label l = new Label(" ");
        l.setTextFill(Color.BLACK);

        Label selectedLabel = new Label("Fenstergröße");
        ComboBox<Integer> sizeBox = new ComboBox<>();
        sizeBox.getItems().addAll(100, 200, 300, 400, 1000);
        sizeBox.setValue(100);

        ComboBox<Integer> speedBox = new ComboBox<>();
        speedBox.getItems().addAll(2, 25, 50, 100, 1000);
        speedBox.setValue(1000);

        Button button = new Button("Start");
        button.setAlignment(Pos.BOTTOM_CENTER);
        button.setMinWidth(200);
        button.setFont(Font.font("Arial Black", 30));
        button.setViewOrder(0);

        Button txt = new Button("Daten speichern");
        txt.setAlignment(Pos.BOTTOM_CENTER);
        txt.setMinWidth(100);
        txt.setFont(Font.font("Arial Black", 15));
        txt.setViewOrder(0);

        TextField prob = new TextField();
        prob.setFont(Font.font("Arial Black", 20));
        prob.setPromptText("Wahrscheinlichkeit (%)");
        prob.setViewOrder(0);

        TextField gen = new TextField();
        gen.setFont(Font.font("Arial Black", 20));
        gen.setPromptText("Generationen Anzahl");

        Text title = new Text("Conway's Game of Life");
        title.setFont(Font.font("Arial Black", 45));

        titleBox.getChildren().add(title);
        hbox.getChildren().addAll(prob, button, gen);
        hbox2.getChildren().addAll(label, speedBox, sizeBox, selectedLabel, txt);
        pane.setTop(titleBox);
        pane.setCenter(hbox);
        pane.setBottom(hbox2);


        prob.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                prob.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        gen.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                gen.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int selectedSizeOption = sizeBox.getSelectionModel().getSelectedItem();
                SimulationHandler.windowSize = selectedSizeOption;
                int selectedSpeedOption = speedBox.getSelectionModel().getSelectedItem();
                ConwaysApplication.speed = selectedSpeedOption;
                simCount++;
                SimulationHandler sim = new SimulationHandler(changeProbability(prob), changeGeneration(gen), simCount);
            }
        });
        txt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DataSaver dataSaver = new DataSaver();
                dataSaver.writeData();
            }
        });
    }
    private int changeProbability(TextField prob) { ////Funktioniert nicht richtig
        int savedValue = Integer.parseInt(prob.getText());
    //  simulation.setProbability(savedValue);
        System.out.println("Wahrscheinlichkeit: " + savedValue);
        return savedValue;
    }
    private int changeGeneration(TextField gen) {
        int savedValue = Integer.parseInt(gen.getText());
    //  simulation.setGeneration(savedValue);
        System.out.println("Generation Cap: " + savedValue);
        return savedValue;
    }
}
