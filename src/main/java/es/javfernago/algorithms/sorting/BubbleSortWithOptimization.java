package es.javfernago.algorithms.sorting;

public class BubbleSortWithOptimization implements SortingAlgorithm {

    @Override
    public void sort(int[] values) {
        int swapped;
        int modifications;

        for (int i= 0; i < values.length -1; i++) {
            modifications = 0;
            for (int j = 0; j < values.length -1 -i; j++) {
                if (values[j] > values[j+1]) {
                    swapped = values[j];
                    values[j] = values[j+1];
                    values[j+1] = swapped;
                    modifications++;
                }
            }
            if (modifications == 0) {
                return;
            }
        }
    }

}
