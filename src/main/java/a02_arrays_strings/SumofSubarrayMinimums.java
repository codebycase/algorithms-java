package a02_arrays_strings;

import java.util.Deque;
import java.util.LinkedList;

/**
 * https://leetcode.com/problems/sum-of-subarray-minimums/
 */
public class SumofSubarrayMinimums {

  public int sumSubarrayMins(int[] arr) {
    if (arr == null || arr.length == 0) {
      return 0;
    }

    Deque<int[]> stack = new LinkedList<>();
    int total = 0, minPreSum = 0;
    for (int i = 0; i < arr.length; i++) {
      int count = 1;
      while (!stack.isEmpty() && stack.peek()[0] > arr[i]) {
        int[] cur = stack.pop();
        count += cur[1];
        // Deduct invalid numbers
        minPreSum -= cur[0] * cur[1];
      }
      stack.push(new int[] { arr[i], count });
      minPreSum += count * arr[i];
      total += minPreSum;
      total %= 1000000007;
    }

    return total % 1000000007;
  }
}
