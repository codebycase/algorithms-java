package a02_arrays_strings;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * <pre>
Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.

Note: The solution set must not contain duplicate triplets.

For example, given array S = [-1, 0, 1, 2, -1, -4],

A solution set is:
[
  [-1, 0, 1],
  [-1, -1, 2]
]
 * </pre>
 * 
 * @author lchen
 *
 */
public class ThreeSumEquals {
	// skip equal elements to avoid duplicates
	public List<List<Integer>> threeSum(int[] nums) {
		Arrays.sort(nums);
		List<List<Integer>> list = new LinkedList<>();
		for (int i = 0; i < nums.length - 2; i++) {
			if (i > 0 && nums[i] == nums[i - 1])
				continue;
			int lo = i + 1, hi = nums.length - 1;
			while (lo < hi) {
				int sum = nums[i] + nums[lo] + nums[hi];
				if (sum == 0) {
					list.add(Arrays.asList(nums[i], nums[lo], nums[hi]));
					while (lo < hi && nums[lo] == nums[lo + 1])
						lo++;
					while (lo < hi && nums[hi] == nums[hi - 1])
						hi--;
					lo++;
					hi--;
				} else if (sum < 0)
					lo++;
				else
					hi--;
			}
		}
		return list;
	}
}
