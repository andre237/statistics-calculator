package com.ifrs.labs;

public record SingleValueClassFrequency(
        Double value, Double frequency
) implements FrequencyDescription {

    @Override
    public Double frequency() {
        return frequency;
    }

    @Override
    public Double frequencyApproximation() {
        return value;
    }

    @Override
    public boolean supportsSampling() {
        return false;
    }
}
