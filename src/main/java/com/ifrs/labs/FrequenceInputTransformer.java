package com.ifrs.labs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FrequenceInputTransformer {

    private static final String VALUES_SEPARATOR = ";";
    private static final String CLASS_FREQUENCY_CHAR = "=";
    private static final String RANGE_CLASS_DIVIDER = "->";

    public static FrequencyData transform(String input) {
        boolean isSample = input.startsWith("SAMPLE[");
        boolean isPopulation = input.startsWith("POPULATION[");

        if (isSample || isPopulation) {
            String[] values = sanitize(input).split(VALUES_SEPARATOR);

            List<FrequencyDescription> frequencyValues = new ArrayList<>();

            for (String value : values) {
                if (value.contains(CLASS_FREQUENCY_CHAR)) { // classes
                    String[] valuesAndFrequency = value.split(CLASS_FREQUENCY_CHAR);
                    if (valuesAndFrequency[0].contains(RANGE_CLASS_DIVIDER)) { // range values class
                        String[] ranges = valuesAndFrequency[0].split(RANGE_CLASS_DIVIDER);
                        if (ranges.length != 2)
                            throw new IllegalArgumentException("Ranges values should only have one lower and upper bound");

                        frequencyValues.add(new RangeValueClassFrequency(
                                Double.valueOf(ranges[0]),
                                Double.valueOf(ranges[1]),
                                Double.valueOf(valuesAndFrequency[1]))
                        );

                    } else { // single value class
                        frequencyValues.add(new SingleValueClassFrequency(
                                Double.valueOf(valuesAndFrequency[0]),
                                Double.valueOf(valuesAndFrequency[1]))
                        );
                    }

                } else { // single values
                    frequencyValues.add(new SingleValueFrequency(Double.valueOf(value)));
                }
            }

            Set<String> differentFrequencyTypes = frequencyValues.stream()
                    .map(fv -> fv.getClass().getSimpleName())
                    .collect(Collectors.toSet());

            if (differentFrequencyTypes.size() > 1)
                throw new IllegalArgumentException("Different frequency types cannot be mixed");

            return new FrequencyData(isSample, frequencyValues);

        } else {
            throw new IllegalArgumentException("String must begin with SAMPLE[ or POPULATION[");
        }
    }

    private static String sanitize(String input) {
        int startOfArray = input.indexOf("[");
        return input.substring(startOfArray + 1, input.length() - 1);
    }

}
