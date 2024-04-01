package com.ifrs.labs;

import java.util.List;
import java.util.stream.Stream;

public class FrequencyData {

    private final boolean isSample;
    private final List<FrequencyDescription> frequencyDescriptionList;

    FrequencyData(boolean isSample, List<FrequencyDescription> frequencyDescriptionList) {
        this.isSample = isSample;
        this.frequencyDescriptionList = frequencyDescriptionList;
        this.validateIfSampleable();
    }

    public static FrequencyData fromSample(FrequencyDescription first, FrequencyDescription... rest) {
        List<FrequencyDescription> frequencies = Stream.concat(Stream.of(first), Stream.of(rest)).toList();
        return new FrequencyData(true, frequencies);
    }

    public static FrequencyData fromPopulation(FrequencyDescription first, FrequencyDescription... rest) {
        List<FrequencyDescription> frequencies = Stream.concat(Stream.of(first), Stream.of(rest)).toList();
        return new FrequencyData(false, frequencies);
    }

    public boolean isSample() {
        return isSample;
    }

    public List<FrequencyDescription> getFrequencyDescriptionList() {
        return frequencyDescriptionList;
    }

    private void validateIfSampleable() {
        if (isSample && frequencyDescriptionList.stream().anyMatch(fd -> !fd.supportsSampling())) {
            String frequencyTypeName = frequencyDescriptionList.get(0).getClass().getSimpleName();
            throw new IllegalArgumentException("Frequency of type %s is not acceptable as sample".formatted(frequencyTypeName));
        }
    }
}
