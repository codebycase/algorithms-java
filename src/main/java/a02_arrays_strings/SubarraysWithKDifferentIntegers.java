package a02_arrays_strings;

/**
 * Given an integer array nums and an integer k, return the number of good subarrays of nums.
 * 
 * A good array is an array where the number of different integers in that array is exactly k.
 * 
 * For example, [1,2,3,1,2] has 3 different integers: 1, 2, and 3. A subarray is a contiguous part
 * of an array.
 * 
 * 
 * <pre>
 * Example 1:
 * 
 * Input: nums = [1,2,1,2,3], k = 2
 * Output: 7
 * Explanation: Subarrays formed with exactly 2 different integers: [1,2], [2,1], [1,2], [2,3], [1,2,1], [2,1,2], [1,2,1,2]
 * </pre>
 *
 */
public class SubarraysWithKDifferentIntegers {
  /**
   * https://leetcode.com/problems/subarrays-with-k-different-integers/discuss/235235/C%2B%2BJava-with-picture-prefixed-sliding-window
   * 
   * Intuition: If the subarray [left, i] contains K unique numbers, and first prefix numbers also
   * appear in [left + prefix, i] subarray, we have total 1 + prefix good subarrays. For example,
   * there are 3 unique numers in [1, 2, 1, 2, 3]. First two numbers also appear in the remaining
   * subarray [1, 2, 3], so we have 1 + 2 good subarrays: [1, 2, 1, 2, 3], [2, 1, 2, 3] and [1, 2, 3].
   */
  public int subarraysWithKDistinct(int[] nums, int k) {
    int result = 0, prefix = 0;
    int[] counts = new int[nums.length + 1];
    int left = 0, count = 0;
    for (int i = 0; i < nums.length; i++) {
      // Only count for the first time
      if (counts[nums[i]]++ == 0) {
        count++;
      }
      // Remove the only left one when exceeded windows size
      if (count > k) {
        counts[nums[left++]]--;
        count--;
        prefix = 0;
      }
      if (count == k) {
        // Remove until the number on the left appears only once
        while (counts[nums[left]] > 1) {
          counts[nums[left++]]--;
          prefix++; // increase prefix
        }
        // Collect all good subarrys
        result += prefix + 1;
      }
    }
    return result;
  }
}
