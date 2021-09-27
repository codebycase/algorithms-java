package a18_the_honors_question;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MaxMinSubarrayQuestions {
  /**
   * Given an integer array, find three numbers whose product is maximum and output the maximum
   * product.
   * 
   * For example: if A = [1, 2, 3, 4], the result is 24.
   */
  public int maximumProductOfThree(int[] nums) {
    int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE, max3 = Integer.MIN_VALUE, min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
    for (int n : nums) {
      if (n > max1) {
        max3 = max2;
        max2 = max1;
        max1 = n;
      } else if (n > max2) {
        max3 = max2;
        max2 = n;
      } else if (n > max3) {
        max3 = n;
      }
      if (n < min1) {
        min2 = min1;
        min1 = n;
      } else if (n < min2) {
        min2 = n;
      }
    }
    return Math.max(max1 * max2 * max3, max1 * min1 * min2);
  }

  /**
   * Find the contiguous subarray within an array (containing at least one number) which has the
   * largest product.
   * 
   * For example, given the array [2,3,-2,4], the contiguous subarray [2,3] has the largest product =
   * 6.
   */
  public static int maxProductOfSubarray(int[] nums) {
    int maxSoFar = nums[0];

    for (int i = 1, iMax = maxSoFar, iMin = maxSoFar; i < nums.length; i++) {
      // swap them if multiplied by a negative
      if (nums[i] < 0) {
        int temp = iMin;
        iMin = iMax;
        iMax = temp;
      }

      iMax = Math.max(nums[i], iMax * nums[i]);
      iMin = Math.min(nums[i], iMin * nums[i]);

      maxSoFar = Math.max(maxSoFar, iMax);
    }
    return maxSoFar;
  }

  /**
   * Given an array A of length n whose entries are integers, compute the largest product that can
   * made using n - 1 entries in A. Array entries may be positive, negative or 0.
   * 
   * For example: if A = {3, 2, -1, 4, -1, 6}, the result is 3 * -1 * 4 * -1 * 6 = 72.
   */
  public static int maxProductExceptOne(int[] nums) {
    int numOfNegatives = 0;
    int leastNegativeIdx = -1, leastNonNegativeIdx = -1, greatestNegativeIdx = -1;

    for (int i = 0; i < nums.length; i++) {
      if (nums[i] < 0) {
        numOfNegatives++;
        if (leastNegativeIdx == -1 || nums[i] > nums[leastNegativeIdx]) {
          leastNegativeIdx = i;
        }
        if (greatestNegativeIdx == -1 || nums[i] < nums[greatestNegativeIdx]) {
          greatestNegativeIdx = i;
        }
      } else {
        if (leastNonNegativeIdx == -1 || nums[i] < nums[leastNonNegativeIdx]) {
          leastNonNegativeIdx = i;
        }
      }
    }

    int idxToSkip = numOfNegatives % 2 == 0 ? (leastNonNegativeIdx == -1 ? greatestNegativeIdx : leastNonNegativeIdx) : leastNegativeIdx;

    int product = 1;
    for (int i = 0; i < nums.length; i++) {
      if (i != idxToSkip)
        product *= nums[i];
    }

    return product;
  }

  /**
   * Given an array of positive integers numbers. Count and print the number of (contiguous) subarrays
   * where the product of all the elements in the subarray is less than k.
   */
  public static int numSubarrayProductLessThanK(int[] nums, int k) {
    if (k <= 1) // must be greater than 1
      return 0;
    int prod = 1, left = 0, result = 0;
    for (int right = 0; right < nums.length; right++) {
      prod *= nums[right];
      while (prod >= k) {
        prod /= nums[left++];
      }
      result += right - left + 1;
    }
    return result;
  }

  /**
   * Given an unsorted array of integers, find the length of longest continuous increasing subsequence
   * (subarray).
   * 
   * For example, if A = [1,3,5,4,7], The longest continuous increasing subsequence is [1,3,5], its
   * length is 3.
   */
  public static int[] longestContinuousIncreasingSubarray(int[] nums) {
    int start = 0, end = 0;
    int max = 0, anchor = 0;
    for (int i = 0; i < nums.length; i++) {
      if (i > 0 && nums[i - 1] >= nums[i])
        anchor = i;
      if (max < i - anchor + 1) {
        max = i - anchor + 1;
        start = anchor;
        end = i;
      }
    }
    return new int[] { start, end };
  }

  /**
   * Given a list of non-negative numbers and a target integer k, write a function to check if the
   * array has a continuous subarray of size at least 2 that sums up to the multiple of k, that is,
   * sums up to n*k where n is also an integer.
   * 
   * For example: if A = [1, 23, 2, 6, 4, 7, 9] and k = 6, because [23, 2, 6, 4, 7] is an continuous
   * subarray of size 5 and sums up to 42, which is the multiple of 6.
   */
  public static boolean continousSubarraySum(int[] nums, int k) {
    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, -1);
    int runningSum = 0;
    for (int i = 0; i < nums.length; i++) {
      runningSum += nums[i];
      if (k != 0)
        runningSum %= k;
      if (map.containsKey(runningSum)) {
        if (i - map.get(runningSum) > 1)
          return true;
      } else
        map.put(runningSum, i);
    }
    return false;
  }

  /**
   * Given an integer array nums and an integer k, return the number of non-empty subarrays that have
   * a sum divisible by k.
   * 
   * A subarray is a contiguous part of an array.
   * 
   * Example 1:
   * 
   * Input: nums = [4,5,0,-2,-3,1], k = 5 <br>
   * Output: 7 <br>
   * Explanation: There are 7 subarrays with a sum divisible by k = 5: [4, 5, 0, -2, -3, 1], [5], [5,
   * 0], [5, 0, -2, -3], [0], [0, -2, -3], [-2, -3]
   * 
   */
  public int subarraysDivByK(int[] nums, int K) {
    if (nums == null || nums.length == 0 || K == 0)
      return 0;

    int ans = 0, sum = 0, remainder;
    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, 1); // 0 as remainder to be 1

    for (int i = 0; i < nums.length; i++) {
      sum += nums[i];
      remainder = sum % K;
      if (remainder < 0)
        remainder += K; // convert to positive
      if (map.containsKey(remainder)) {
        ans += map.get(remainder);
      }
      map.put(remainder, map.getOrDefault(remainder, 0) + 1);
    }
    return ans;
  }

  /**
   * Find the contiguous subarray within an array (containing at least one number) which has the
   * largest sum.
   * 
   * For example, given the array [-2,1,-3,4,-1,2,1,-5,4], the contiguous subarray [4,-1,2,1] has the
   * largest sum = 6.
   */
  public static int maxSubArraySum(int[] nums) {
    int max = Integer.MIN_VALUE, sum = 0;
    for (int i = 0; i < nums.length; i++) {
      // break point if sum <= 0
      sum = Math.max(nums[i], sum + nums[i]);
      max = Math.max(max, sum);
    }
    return max;
  }

  // Just need to adjust: Subarray Sum Equals K
  public int subarraySumEqualsK(int[] nums, int k) {
    int count = 0, sum = 0;
    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, 1);
    for (int i = 0; i < nums.length; i++) {
      sum += nums[i];
      if (map.containsKey(sum - k))
        count += map.get(sum - k);
      map.put(sum, map.getOrDefault(sum, 0) + 1);
    }
    return count;
  }

  /**
   * Given an array nums and a target value k, find the maximum length of a subarray that sums to k.
   * If there isn't one, return 0 instead.
   * 
   * Note: The sum of the entire nums array is guaranteed to fit within the 32-bit signed integer
   * range.
   * 
   * Example 1: Given nums = [1, -1, 5, -2, 3], k = 3, return 4. (because the subarray [1, -1, 5, -2]
   * sums to 3 and is the longest)
   * 
   */
  public static int maxSubArraySumEqualsK(int[] nums, int k) {
    int sum = 0, max = 0;
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
      sum = sum + nums[i];
      if (sum == k)
        max = i + 1;
      else if (map.containsKey(sum - k))
        max = Math.max(max, i - map.get(sum - k));
      if (!map.containsKey(sum))
        map.put(sum, i);
    }
    return max;
  }

  /**
   * Design an algorithm that takes as input an array A of n numbers and a key k, and return the
   * length of a longest subarray of A for which the subarray sum is less than or equal to k.
   */
  public static int maxSubArraySumLessEqualsK(int[] nums, int k) {
    // build the prefix sum
    int[] prefixSum = new int[nums.length];
    int sum = 0;
    for (int i = 0; i < nums.length; i++) {
      sum += nums[i];
      prefixSum[i] = sum;
    }
    // early returns if sum <= k
    if (prefixSum[nums.length - 1] <= k)
      return nums.length;
    // build the min prefix sum
    int[] minPrefixSum = Arrays.copyOf(prefixSum, prefixSum.length);
    for (int i = nums.length - 2; i >= 0; i--) {
      minPrefixSum[i] = Math.min(minPrefixSum[i], minPrefixSum[i + 1]);
    }
    // minPrefixSum[b] - prefixSum[a - 1]
    int a = 0, b = 0, maxLen = 0;
    while (a < nums.length && b < nums.length) {
      int minCurSum = a == 0 ? minPrefixSum[b] : minPrefixSum[b] - prefixSum[a - 1];
      if (minCurSum <= k) {
        int curLen = b - a + 1;
        maxLen = Math.max(maxLen, curLen);
        b++;
      } else {
        a++;
      }
    }
    return maxLen;
  }

  public static void main(String[] args) {
    int[] nums = { 3, 2, -1, 4, -1, 6 };
    assert maxProductExceptOne(nums) == 72;
    assert maxProductExceptOne(new int[] { 3, 2, -1, 4 }) == 24;
    assert maxProductOfSubarray(new int[] { 2, 3, -2, 4 }) == 6;
    assert numSubarrayProductLessThanK(new int[] { 10, 5, 2, 6 }, 100) == 8;
    assert Arrays.toString(longestContinuousIncreasingSubarray(new int[] { 1, 3, 5, 4, 7 })).equals("[0, 2]");
    assert maxSubArraySumEqualsK(new int[] { 1, -1, 5, -2, 3 }, 3) == 4;
    nums = new int[] { 431, -15, 639, 342, -14, 565, -924, 635, 167, -70 };
    assert maxSubArraySumLessEqualsK(nums, 184) == 4;
    assert maxSubArraySum(new int[] { -2, 1, -3, 4, -1, 2, 1, -5, 4 }) == 6;
  }
}
