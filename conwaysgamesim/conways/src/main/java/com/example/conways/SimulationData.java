package com.example.conways;

import javafx.beans.property.SimpleIntegerProperty;

public record SimulationData(SimpleIntegerProperty prob, SimpleIntegerProperty gen, SimpleIntegerProperty aliveCount, SimpleIntegerProperty simCount) {
    public SimulationData(int prob, int gen, int aliveCount, int simCount) {
        this(new SimpleIntegerProperty(prob), new SimpleIntegerProperty(gen), new SimpleIntegerProperty(aliveCount), new SimpleIntegerProperty(simCount));
    }
}