package a10_recursion_iteration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThreeFourSumProblems {
	/**
	 * Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0? Find
	 * all unique triplets in the array which gives the sum of zero.
	 * 
	 * Note: The solution set must not contain duplicate triplets.
	 * 
	 * <pre>
	For example, given array S = [-1, 0, 1, 2, -1, -4],
	
	A solution set is:
	[
	  [-1, 0, 1],
	  [-1, -1, 2]
	]
	 * </pre>
	 * 
	 */
	public static List<List<Integer>> threeSumEquals(int[] nums, int target) {
		List<List<Integer>> result = new ArrayList<>();
		if (nums == null || nums.length < 3)
			return result;
		Arrays.sort(nums);
		for (int i = 0; i < nums.length - 2; i++) {
			// skip duplicates
			if (i > 0 && nums[i] == nums[i - 1])
				continue;
			int lo = i + 1, hi = nums.length - 1;
			while (lo < hi) {
				int sum = nums[i] + nums[lo] + nums[hi];
				if (sum == target) {
					result.add(Arrays.asList(nums[i], nums[lo], nums[hi]));
					// skip duplicates
					while (lo < hi && nums[lo] == nums[lo + 1])
						lo++;
					// skip duplicates
					while (lo < hi && nums[hi] == nums[hi - 1])
						hi--;
					lo++;
					hi--;
				} else if (sum > target) {
					hi--;
				} else {
					lo++;
				}
			}
		}

		return result;
	}

	/**
	 * Given an array S of n integers, find three integers in S such that the sum is closest to a
	 * given number, target. Return the sum of the three integers. You may assume that each input
	 * would have exactly one solution.
	 * 
	 * For example, given array S = {-1 2 1 -4}, and target = 1.
	 * 
	 * The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
	 * 
	 */
	public static int threeSumClosest(int[] nums, int target) {
		if (nums == null || nums.length < 3)
			return 0;
		Arrays.sort(nums);
		int result = nums[0] + nums[1] + nums[2];
		for (int i = 0; i < nums.length - 2; i++) {
			int lo = i + 1, hi = nums.length - 1;
			while (lo < hi) {
				int sum = nums[i] + nums[lo] + nums[hi];
				if (sum > target)
					hi--;
				else
					lo++;
				if (Math.abs(sum - target) < Math.abs(result - target))
					result = sum;
			}
		}
		return result;
	}

	/**
	 * Given an array of n integers nums and a target, find the number of index triplets i, j, k
	 * with 0 <= i < j < k < n that satisfy the condition nums[i] + nums[j] + nums[k] < target.
	 * 
	 * <pre>
	For example, given nums = [-2, 0, 1, 3], and target = 2.
	
	Return 2. Because there are two triplets which sums are less than 2:
	
	[-2, 0, 1]
	[-2, 0, 3]
	 * </pre>
	 */
	public static int threeSumSmaller(int[] nums, int target) {
		if (nums == null || nums.length < 3)
			return 0;

		Arrays.sort(nums);

		int result = 0;
		for (int i = 0; i < nums.length - 2; i++) {
			int lo = i + 1, hi = nums.length - 1;
			while (lo < hi) {
				int sum = nums[i] + nums[lo] + nums[hi];
				if (sum < target) {
					result += hi - lo;
					lo++;
				} else {
					hi--;
				}
			}
		}

		return result;
	}

	/**
	 * <pre>
	Given an array S of n integers, are there elements a, b, c, and d in S such that a + b + c + d = target? Find all unique quadruplets in the array which gives the sum of target.
	
	Note: The solution set must not contain duplicate quadruplets.
	
	For example, given array S = [1, 0, -1, 0, -2, 2], and target = 0.
	
	A solution set is:
	[
	[-1,  0, 0, 1],
	[-2, -1, 1, 2],
	[-2,  0, 0, 2]
	]
	 * </pre>
	 * 
	 */
	public List<List<Integer>> fourSum(int[] nums, int target) {
		List<List<Integer>> result = new ArrayList<>();
		if (nums == null || nums.length < 4)
			return result;
		Arrays.sort(nums);
		int len = nums.length;

		for (int i = 0; i < nums.length - 3; i++) {
			if (nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3] > target)
				break;
			if (nums[i] + nums[len - 1] + nums[len - 2] + nums[len - 3] < target)
				continue;
			if (i > 0 && nums[i] == nums[i - 1])
				continue;
			for (int j = i + 1; j < nums.length - 2; j++) {
				if (nums[i] + nums[j] + nums[j + 1] + nums[j + 2] > target)
					break;
				if (nums[i] + nums[j] + nums[len - 1] + nums[len - 2] < target)
					continue;
				if (j > i + 1 && nums[j] == nums[j - 1])
					continue;
				int lo = j + 1, hi = nums.length - 1;
				while (lo < hi) {
					int sum = nums[i] + nums[j] + nums[lo] + nums[hi];
					if (sum == target) {
						result.add(Arrays.asList(nums[i], nums[j], nums[lo], nums[hi]));
						while (lo < hi && nums[lo] == nums[lo + 1])
							lo++;
						while (lo < hi && nums[hi] == nums[hi - 1])
							hi--;
						lo++;
						hi--;
					} else if (sum > target)
						hi--;
					else
						lo++;
				}
			}
		}

		return result;
	}

	/**
	 * Given four lists A, B, C, D of integer values, compute how many tuples (i, j, k, l) there are
	 * such that A[i] + B[j] + C[k] + D[l] is zero.
	 * 
	 * To make problem a bit easier, all A, B, C, D have same length of N where 0 ≤ N ≤ 500. All
	 * integers are in the range of -228 to 228 - 1 and the result is guaranteed to be at most 231 -
	 * 1.
	 * 
	 */
	public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
		Map<Integer, Integer> map = new HashMap<>();

		for (int i = 0; i < C.length; i++) {
			for (int j = 0; j < D.length; j++) {
				int sum = C[i] + D[j];
				map.put(sum, map.getOrDefault(sum, 0) + 1);
			}
		}

		int result = 0;
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < B.length; j++) {
				int sum = A[i] + B[j];
				result += map.getOrDefault(-1 * sum, 0);
			}
		}

		return result;
	}

	public static void main(String[] args) {
		int[] nums = { -1, 0, 1, 2, -1, -4 };
		List<List<Integer>> result = threeSumEquals(nums, 0);
		assert result.toString().equals("[[-1, -1, 2], [-1, 0, 1], [-1, 0, 1]]");
		nums = new int[] { -1, 2, 1, -4 };
		assert threeSumClosest(nums, 1) == 2;
		nums = new int[] { -2, 0, 1, 3 };
		assert threeSumSmaller(nums, 2) == 2;
	}
}
