package com.example.conways;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javafx.stage.WindowEvent.WINDOW_SHOWING;


public class ConwaysDataMenu extends Application {
    private static ConwaysDataMenu instance;

    private ObservableList<SimulationData> data;
    private final StackPane pane = new StackPane();

    private ConwaysDataMenu() {}

    @Override
    public void start(Stage stage) {
        createTable();

        Scene scene = new Scene(pane, 500, 300);
        stage.setScene(scene);
        Image image = new Image(getClass().getResourceAsStream("/com/example/conways/images/conway.png"));
        stage.getIcons().add(image);
        stage.setTitle("Conway's Game of Life DataMenu");
//        stage.setX(1480);
//        stage.setY(40);
        stage.show();
    }

    public void createTable() {
        TableView<SimulationData> table = new TableView<>();

        TableColumn<SimulationData, Number> simCount = new TableColumn<>("Simulation");
        simCount.setCellValueFactory(cellData -> cellData.getValue().simCount());

        TableColumn<SimulationData, Number> probCol = new TableColumn<>("Wahrscheinlichkeit");
        probCol.setCellValueFactory(cellData -> cellData.getValue().prob());

        TableColumn<SimulationData, Number> genCount = new TableColumn<>("Generationen");
        genCount.setCellValueFactory(cellData -> cellData.getValue().gen());

        TableColumn<SimulationData, Number> aliveCount = new TableColumn<>("Lebende Figuren");
        aliveCount.setCellValueFactory(cellData -> cellData.getValue().aliveCount());

        table.getColumns().addAll(simCount, probCol, genCount, aliveCount);

        int colWidth = 125;
        simCount.setMinWidth(colWidth);
        probCol.setMinWidth(colWidth);
        genCount.setMinWidth(colWidth);
        aliveCount.setMinWidth(colWidth);

        data = FXCollections.observableArrayList();
        table.setItems(data);
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().add(table);
    }

    public void addData(SimulationData simData) {
        if (simData == null) {
            throw new IllegalArgumentException("SimulationData cannot be null");
        }
        data.add(simData);  // Now `data` is properly initialized
    }

    public String convertTableViewToString() {
        StringBuilder sb = new StringBuilder();

        // Assuming there is a table in the pane
        TableView<SimulationData> table = (TableView<SimulationData>) pane.getChildren().get(0);

        // Add header row
        for (TableColumn<SimulationData, ?> column : table.getColumns()) {
            sb.append(column.getText()).append("\t"); // Use tab as a separator
        }
        sb.append("\n");

        // Iterate through each row
        for (SimulationData simData : table.getItems()) {
            sb.append(simData.simCount().getValue()).append("\t\t\t")
                    .append(simData.prob().getValue()).append("%\t\t\t\t\t")
                    .append(simData.gen().getValue()).append("\t\t\t\t")
                    .append(simData.aliveCount().getValue()).append("\n");
        }

        return sb.toString();
    }


    public static ConwaysDataMenu getInstance() {
        if (instance == null) {
            instance = new ConwaysDataMenu();
        }
        return instance;
    }

//    private void addSampleData() {
//        // Example data to test
//        data.add(new SimulationData(60, 150, 40));
//        data.add(new SimulationData(70, 200, 35));
//    }
}
