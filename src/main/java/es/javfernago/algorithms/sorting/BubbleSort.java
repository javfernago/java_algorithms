package es.javfernago.algorithms.sorting;

public class BubbleSort implements SortingAlgorithm {

    @Override
    public void sort(int[] values) {
        int swapped;

        for (int i= 0; i < values.length -1; i++) {
            for (int j = 0; j < values.length -1 -i; j++) {
                if (values[j] > values[j+1]) {
                    swapped = values[j];
                    values[j] = values[j+1];
                    values[j+1] = swapped;
                }
            }
        }
    }
}
