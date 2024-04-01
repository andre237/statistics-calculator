package com.ifrs.labs;

public record SingleValueFrequency(Double value) implements FrequencyDescription {
    @Override
    public Double frequency() {
        return 1d;
    }

    @Override
    public Double frequencyApproximation() {
        return value;
    }

    @Override
    public boolean supportsSampling() {
        return true;
    }
}
