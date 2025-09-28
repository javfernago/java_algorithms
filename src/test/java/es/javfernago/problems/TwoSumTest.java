package es.javfernago.problems;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class TwoSumTest {

    @Test
    public void testcase1() {
    	TwoSum twoSum = new TwoSum();
    	int[] solution = twoSum.twoSum(new int[] {2,7,11,15}, 9);
        assertArrayEquals(new int[] {0,1}, solution);
    }
    
    @Test
    public void testcase2() {
    	TwoSum twoSum = new TwoSum();
    	int[] solution = twoSum.twoSum(new int[] {3,2,4}, 6);
        assertArrayEquals(new int[] {1,2}, solution);
    }

    @Test
    public void testcase3() {
    	TwoSum twoSum = new TwoSum();
    	int[] solution = twoSum.twoSum(new int[] {3,3}, 6);
        assertArrayEquals(new int[] {0,1}, solution);
    }
    
    
    @Test
    public void testcase4() {
    	TwoSum twoSum = new TwoSum();
    	int[] solution = twoSum.twoSum(new int[] {2,5,5,11}, 10);
        assertArrayEquals(new int[] {1,2}, solution);
    }

}
