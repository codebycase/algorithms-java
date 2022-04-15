package a00_collections;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ArraysAndStrings {
  public int[] shuffleAnArray(int[] nums) {
    if (nums == null)
      return null;
    Random random = new Random();
    int[] result = nums.clone();
    for (int j = 0; j < result.length; j++) {
      int i = random.nextInt(j + 1);
      if (i != j) {
        int temp = result[i];
        result[i] = result[j];
        result[j] = temp;
      }
    }
    return result;
  }

  /**
   * Given an input string, reverse the string word by word. A word is defined as a sequence of
   * non-space characters. The input string does not contain leading or trailing spaces and the words
   * are always separated by a single space. For example, Given s = "the sky is blue", return "blue is
   * sky the".
   */
  public void reverseWords(char[] str) {
    reverse(str, 0, str.length - 1);
    int start = 0, end = 0;
    while (end < str.length) {
      if (str[end] == ' ') {
        reverse(str, start, end - 1);
        start = end + 1;
      }
      end++;
    }
    reverse(str, start, end - 1);
  }

  private void reverse(char[] str, int start, int end) {
    while (start < end) {
      char tmp = str[start];
      str[start] = str[end];
      str[end] = tmp;
      start++;
      end--;
    }
  }

  /**
   * Given an array of integers nums and an integer k, return the total number of subarrays whose sum
   * equals to k.
   * 
   * Example 2:
   * 
   * <pre>
   * Input: nums = [1,2,3], k = 3
   * Output: 2
   * </pre>
   */

  // Use map to count different's frequency
  public int subarraySumEqualsK(int[] nums, int k) {
    int count = 0, sum = 0;
    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, 1); // init a start point!
    for (int i = 0; i < nums.length; i++) {
      sum += nums[i];
      if (map.containsKey(sum - k))
        count += map.get(sum - k);
      map.put(sum, map.getOrDefault(sum, 0) + 1);
    }
    return count;
  }

  /**
   * Given an integer array nums, return the length of the longest strictly increasing subsequence.
   * 
   * A subsequence is a sequence that can be derived from an array by deleting some or no elements
   * without changing the order of the remaining elements. For example, [3,6,2,7] is a subsequence of
   * the array [0,3,1,6,2,2,7].
   * 
   * Example 1:
   * 
   * <pre>
   * Input: nums = [10,9,2,5,3,7,101,18] 
   * Output: 4 
   * Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.
   * </pre>
   * 
   * Solution: Use DP, dp[i] represents the length of the longest increasing subsequence that ends
   * with the element at index i.
   */

  public int longestIncreasingSubsequence(int[] nums) {
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
