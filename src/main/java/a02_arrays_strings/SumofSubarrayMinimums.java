package a02_arrays_strings;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Given an array of integers arr, find the sum of min(b), where b ranges over every (contiguous)
 * subarray of arr. Since the answer may be large, return the answer modulo 10^9 + 7.
 * 
 * <pre>
 * Example 1:
 * 
 * Input: arr = [3,1,2,4]
 * Output: 17
 * Explanation: 
 * Subarrays are [3], [1], [2], [4], [3,1], [1,2], [2,4], [3,1,2], [1,2,4], [3,1,2,4]. 
 * Minimums are 3, 1, 2, 4, 1, 1, 2, 1, 1, 1.
 * Sum is 17.
 * </pre>
 * 
 * https://leetcode.com/problems/sum-of-subarray-minimums/
 */
public class SumofSubarrayMinimums {

  public int sumSubarrayMins(int[] arr) {
    if (arr == null || arr.length == 0) {
      return 0;
    }

    long mod = (long) 1e9 + 7;
    // Stores counts of pre min values till to current num
    Deque<int[]> stack = new LinkedList<>();
    int totalSum = 0, minPreSum = 0;

    for (int num : arr) {
      int count = 1;
      while (!stack.isEmpty() && stack.peek()[0] > num) {
        int[] cur = stack.pop();
        count += cur[1];
        minPreSum -= cur[0] * cur[1]; // deduct invalid numbers
      }
      stack.push(new int[] { num, count });
      minPreSum += count * num;
      totalSum += minPreSum;
      totalSum %= mod;
    }

    return (int) totalSum;
  }

}
