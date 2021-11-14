package a02_arrays_strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an integer array nums, return the length of the longest strictly increasing subsequence.
 * 
 * A subsequence is a sequence that can be derived from an array by deleting some or no elements
 * without changing the order of the remaining elements. For example, [3,6,2,7] is a subsequence of
 * the array [0,3,1,6,2,2,7].
 * 
 * <br>
 * 
 * Example 1:
 * 
 * Input: nums = [10,9,2,5,3,7,101,18] Output: 4 Explanation: The longest increasing subsequence is
 * [2,3,7,101], therefore the length is 4.
 * 
 * <br>
 * 
 * Example 2:
 * 
 * Input: nums = [0,1,0,3,2,3] Output: 4
 * 
 * <br>
 * 
 * Example 3:
 * 
 * Input: nums = [7,7,7,7,7,7,7] Output: 1
 * 
 * @author lchen676
 *
 */
public class LongestIncreasingSubsequence {
  /**
   * Intelligently build a pile of subsequences with binary search.
   * 
   * Time complexity: O(Nlog(N))
   */
  public int lengthOfLIS(int[] nums) {
    List<Integer> sub = new ArrayList<>();
    sub.add(nums[0]);

    for (int i = 1; i < nums.length; i++) {
      int num = nums[i];
      if (num > sub.get(sub.size() - 1)) {
        sub.add(num);
      } else {
        int j = binarySearch(sub, num);
        sub.set(j, num);
      }
    }

    return sub.size();
  }

  private int binarySearch(List<Integer> sub, int num) {
    int left = 0;
    int right = sub.size() - 1;
    int mid = (left + right) / 2;

    while (left < right) {
      mid = (left + right) / 2;
      if (sub.get(mid) == num) {
        return mid;
      }

      if (sub.get(mid) < num) {
        left = mid + 1;
      } else {
        right = mid;
      }
    }

    return left;
  }

  /**
   * Dynamic programming, time complexity: O(N^2)
   * 
   * dp[i] represents the length of the longest increasing subsequence that ends with the element at
   * index i.
   */
  public int lengthOfLIS2(int[] nums) {
    int[] dp = new int[nums.length];
    Arrays.fill(dp, 1);

    for (int i = 1; i < nums.length; i++) {
      for (int j = 0; j < i; j++) {
        if (nums[i] > nums[j]) {
          dp[i] = Math.max(dp[i], dp[j] + 1);
        }
      }
    }

    int longest = 0;
    for (int c : dp) {
      longest = Math.max(longest, c);
    }

    return longest;
  }

}
