package es.javfernago.algorithms.sorting;

public class InsertionSort implements SortingAlgorithm {

    @Override
    public void sort(int[] values) {

        for (int i = 1; i < values.length; i++) {
            int key = values[i];
            int j = i-1;

            while (j >=0 && values[j] > key) {
                values[j+1] = values[j];
                j--;
            }
            values[j+1] = key;
        }
    }
}
