package a02_arrays_strings;

import java.util.ArrayDeque;
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

  // Use monotonic stack to track nums
  public int sumSubarrayMins(int[] arr) {
    Deque<int[]> stack = new LinkedList<>();
    long totalSum = 0, minPreSum = 0;
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
    }
    return (int) (totalSum % (1e9 + 7));
  }

  // Use monotonic stack to track indices
  public int sumSubarrayMins2(int[] arr) {
    long sum = 0;
    Deque<Integer> stack = new ArrayDeque<>();
    for (int i = 0; i <= arr.length; i++) {
      while (!stack.isEmpty() && (i == arr.length || arr[stack.peek()] > arr[i])) {
        int mid = stack.pop(); // middle min pilliar
        int left = mid - (stack.isEmpty() ? -1 : stack.peek());
        int right = i - mid;
        sum += (long) arr[mid] * left * right;
      }
      stack.push(i);
    }
    return (int) (sum % (1e9 + 7));
  }

  // Use monotonic stack to track indices
  public int sumSubarrayMins3(int[] arr) {
    long sum = 0;
    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(-1); // leverage a dummy index
    for (int i = 0; i <= arr.length; i++) {
      while (stack.peek() != -1 && (i == arr.length || arr[stack.peek()] > arr[i])) {
        int mid = stack.pop(); // middle min pilliar
        int left = mid - stack.peek();
        int right = i - mid;
        sum += (long) arr[mid] * left * right;
      }
      stack.push(i);
    }
    return (int) (sum % (1e9 + 7));
  }

}
