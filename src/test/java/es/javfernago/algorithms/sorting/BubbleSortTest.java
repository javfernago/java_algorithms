package es.javfernago.algorithms.sorting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class BubbleSortTest {

    private final BubbleSort sortingAlgorithm = new BubbleSort();

    @Test
    void sortsEmptyArray() {
        int[] values = {};

        sortingAlgorithm.sort(values);

        assertArrayEquals(new int[] {}, values);
    }

    @Test
    void sortsSingleElementArray() {
        int[] values = {7};

        sortingAlgorithm.sort(values);

        assertArrayEquals(new int[] {7}, values);
    }

    @Test
    void leavesAlreadySortedArrayInOrder() {
        int[] values = {1, 2, 3, 4, 5};

        sortingAlgorithm.sort(values);

        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, values);
    }

    @Test
    void sortsReverseOrderedArray() {
        int[] values = {5, 4, 3, 2, 1};

        sortingAlgorithm.sort(values);

        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, values);
    }

    @Test
    void sortsArrayWithDuplicateValues() {
        int[] values = {3, 1, 2, 3, 1};

        sortingAlgorithm.sort(values);

        assertArrayEquals(new int[] {1, 1, 2, 3, 3}, values);
    }

    @Test
    void sortsArrayWithNegativeAndPositiveValues() {
        int[] values = {0, -4, 7, -1, 3};

        sortingAlgorithm.sort(values);

        assertArrayEquals(new int[] {-4, -1, 0, 3, 7}, values);
    }
}
