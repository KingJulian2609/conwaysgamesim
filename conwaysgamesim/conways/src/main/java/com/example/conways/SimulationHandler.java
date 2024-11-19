package com.example.conways;

import javafx.stage.Stage;

public class SimulationHandler {
    private final int prob;
    private final int gen;
    public static int windowSize = 250;

    ConwaysApplication simulation = new ConwaysApplication();

    public SimulationHandler(int prob, int gen, int simCount) {
        this.prob = prob;
        this.gen = gen;
        simulation.setWindowSize(windowSize);
        simulation.setSimCount(simCount);
        simulation.setProbability(prob);
        simulation.setGeneration(gen);
        simulation.start(WindowManager.getInstance().openNewWindow(windowSize, windowSize, false));
    }
}
