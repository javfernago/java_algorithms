package es.javfernago.problems;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {

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
