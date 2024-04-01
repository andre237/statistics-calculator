package com.ifrs.labs;

public record RangeValueClassFrequency(
        Double lowerBound, Double upperBound, Double frequency
) implements FrequencyDescription {

    @Override
    public Double frequency() {
        return frequency;
    }

    @Override
    public Double frequencyApproximation() {
        return lowerBound + ((upperBound - lowerBound) / 2);
    }

    @Override
    public boolean supportsSampling() {
        return false;
    }
}
