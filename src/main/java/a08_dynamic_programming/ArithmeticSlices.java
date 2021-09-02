package a08_dynamic_programming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A sequence of number is called arithmetic if it consists of at least three elements and if the
 * difference between any two consecutive elements is the same.
 * 
 * <pre>
For example, these are arithmetic sequence:

1, 3, 5, 7, 9
7, 7, 7, 7
3, -1, -5, -9
The following sequence is not arithmetic.

1, 1, 2, 5, 7
 * </pre>
 * 
 * <pre>
Example:

A = [1, 2, 3, 4]

return: 3, for 3 arithmetic slices in A: [1, 2, 3], [2, 3, 4] and [1, 2, 3, 4] itself.
 * </pre>
 * 
 * @author lchen
 *
 */
public class ArithmeticSlices {
  public int numberOfArithmeticSlices(int[] A) {
    int[] dp = new int[A.length];
    int sum = 0;
    for (int i = 2; i < dp.length; i++) {
      if (A[i] - A[i - 1] == A[i - 1] - A[i - 2]) {
        dp[i] = 1 + dp[i - 1];
        sum += dp[i];
      }
    }
    return sum;
  }

  // we can also one variable, and update the sum at the very end base on formual n*(n+1)/2
  public int numberOfArithmeticSlices2(int[] A) {
    int count = 0, sum = 0;
    for (int i = 2; i < A.length; i++) {
      if (A[i] - A[i - 1] == A[i - 1] - A[i - 2]) {
        count++;
      } else {
        sum += (count + 1) * (count) / 2;
        count = 0;
      }
    }
    return sum += count * (count + 1) / 2;
  }

  /**
   * Arithmetic Slices II - Subsequence
   * 
   * f[i][A[i] - A[j]] += (f[j][A[i] - A[j]] + 1)
   * 
   * <pre>
   * Example:
  
  Input: [2, 4, 6, 8, 10]
  
  Output: 7
  
  Explanation:
  All arithmetic subsequence slices are:
  [2,4,6]
  [4,6,8]
  [6,8,10]
  [2,4,6,8]
  [4,6,8,10]
  [2,4,6,8,10]
  [2,6,10]
   * </pre>
   */
  public int numberOfArithmeticSlicesII(int[] A) {
    int n = A.length;
    long ans = 0;
    List<Map<Integer, Integer>> counts = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      counts.add(new HashMap<>(i));
      // loop through all possible differences
      for (int j = 0; j < i; j++) {
        // avoid overflow
        long delta = (long) A[i] - (long) A[j];
        if (delta < Integer.MIN_VALUE || delta > Integer.MAX_VALUE) {
          continue;
        }
        int diff = (int) delta;
        // previous found subsequences
        int sum = counts.get(j).getOrDefault(diff, 0);
        // weak subsequences
        int weak = counts.get(i).getOrDefault(diff, 0);
        // cache all new subsequences (include weak ones)
        counts.get(i).put(diff, weak + sum + 1);
        ans += sum;
      }
    }
    return (int) ans;
  }

  /**
   * Given an integer array nums and an integer difference, return the length of the longest
   * subsequence in nums which is an arithmetic sequence such that the difference between adjacent
   * elements in the subsequence equals difference.
   * 
   * Solution:
   * 
   * Traverse from the right of the array and consider it as the starting element of the AP. Determine
   * if the nextElement of the AP is present in the Map or not. If No then put the currElement into
   * the Map and mark the length of AP considering currElement as the stating point as 1. Else if the
   * next element is present in the Map the update the length of the AP considering currElem as
   * starting point.
   * 
   */
  public int longestSubsequence(int[] nums, int diff) {
    int n = nums.length;
    HashMap<Integer, Integer> map = new HashMap<>();
    map.put(nums[n - 1], 1);
    // dp[i] represents the start of the AP Sequence..
    int[] dp = new int[n];
    dp[n - 1] = 1;
    for (int i = n - 2; i >= 0; i--) {
      int next = nums[i] + diff;
      dp[i] = 1 + map.getOrDefault(next, 0);
      map.put(nums[i], dp[i]);
    }
    int ans = 0;
    for (int i : dp) {
      ans = Math.max(ans, i);
    }
    return ans;
  }

  /**
   * Given an array nums of integers, return the length of the longest arithmetic subsequence (not
   * have to be adjacent) in nums.
   */
  public int longestArithSeqLength(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 0;
    }
    // Up to previous num's diff->length map
    Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
    int ans = 1;
    for (int num : nums) {
      Map<Integer, Integer> subMap = new HashMap<>();
      for (Map.Entry<Integer, Map<Integer, Integer>> entry : map.entrySet()) {
        int prevNum = entry.getKey();
        int delta = num - prevNum;
        int len = 1 + entry.getValue().getOrDefault(delta, 1);
        ans = Math.max(ans, len);
        subMap.put(delta, len);
      }
      if (!map.containsKey(num)) {
        map.put(num, new HashMap<>());
      }
      map.get(num).putAll(subMap);
    }
    return ans;
  }

  public static void main(String[] args) {
    ArithmeticSlices solution = new ArithmeticSlices();
    int result = solution.numberOfArithmeticSlicesII(new int[] { 2, 4, 6, 8, 10 });
    assert result == 7;
    result = solution.numberOfArithmeticSlicesII(new int[] { 1, 1, 2, 3, 4, 5 });
    assert result == 11;
  }
}
