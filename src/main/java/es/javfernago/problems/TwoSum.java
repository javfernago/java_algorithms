package es.javfernago.problems;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {

	// Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
	
    public int[] twoSum(int[] nums, int target) {
    	Map<Integer, Integer> cache = new HashMap<Integer, Integer>();
    	for (int i=0; i < nums.length; i++) {
    		int complement = target - nums[i];
    		if (cache.containsKey(complement)) {
    			return new int[] {cache.get(complement),i};
    		}
    		cache.put(nums[i], i);
    	}
    	
    	return null;
    	
    }
}
