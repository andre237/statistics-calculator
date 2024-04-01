package com.ifrs.labs;

import java.util.List;

public class StatisticsCalculator {

    public static void main(String[] args) {


        FrequencyData example1 = FrequenceInputTransformer.transform("SAMPLE[22;22;26;24]");
        FrequencyData example2 = FrequenceInputTransformer.transform("POPULATION[0=5;1=10;2=20;3=10;4=5]");
        FrequencyData example3 = FrequenceInputTransformer.transform("POPULATION[" +
                "1.50->1.55=5;" +
                "1.55->1.60=11;" +
                "1.60->1.65=16;" +
                "1.65->1.70=7;" +
                "1.70->1.75=3;" +
                "]");
        FrequencyData example4 = FrequenceInputTransformer.transform("SAMPLE[8;14;7;12;9;10;15;18;23;17;15;13;16;20;22;19;25;27;21;24;10;28;26;30;32]");

        FrequencyData example5 = FrequenceInputTransformer.transform("POPULATION[12;15;12;16;14;15]");
        FrequencyData example6 = FrequenceInputTransformer.transform("POPULATION[12;11;18;9;19;15]");
        FrequencyData example7 = FrequenceInputTransformer.transform("POPULATION[48;54;50;46;44;52;49]");

        FrequencyData example8 = FrequenceInputTransformer.transform("POPULATION[0->20=5;20->40=2;40->60=13;60->80=5;80->100=4]");
        FrequencyData example9 = FrequenceInputTransformer.transform("POPULATION[0->20=2;20->40=3;40->60=7;60->80=7;80->100=19]");


        printResults(example1);
        printResults(example2);
        printResults(example3);
        printResults(example4);
        printResults(example5);
        printResults(example6);
        printResults(example7);
        printResults(example8);
        printResults(example9);
    }

    private static void printResults(FrequencyData frequencyData) {
        Double average = calculateAverage(frequencyData.getFrequencyDescriptionList());
        Double standardDeviation = calculateStandardDeviation(frequencyData);

        System.out.printf("Average = %.2f | Standard deviation = %.2f\n", average, standardDeviation);
    }

    private static Double calculateStandardDeviation(FrequencyData frequencyData) {
        List<FrequencyDescription> classes = frequencyData.getFrequencyDescriptionList();
        if (classes.size() == 1) return 0d;
        Double average = calculateAverage(classes);

        double deviationsSquared = classes.stream().mapToDouble(fc -> {
            double deviationSquared = Math.pow(fc.frequencyApproximation() - average, 2d);
            return deviationSquared * fc.frequency();
        }).sum();

        double frequencySum = classes.stream().mapToDouble(FrequencyDescription::frequency).sum();
        double variance = deviationsSquared / (frequencySum - (frequencyData.isSample() ? 1 : 0));
        double standardDeviation = Math.sqrt(variance);

        return Math.floor(standardDeviation * 100) / 100; // truncate to 2 decimals
    }

    private static Double calculateAverage(List<FrequencyDescription> classes) {
        double classesSum = classes.stream().mapToDouble(f -> f.frequencyApproximation() * f.frequency()).sum();
        double frequencySum = classes.stream().mapToDouble(FrequencyDescription::frequency).sum();
        double average = classesSum / frequencySum;
        return Math.floor(average * 100) / 100; // truncate to 2 decimals
    }

}
