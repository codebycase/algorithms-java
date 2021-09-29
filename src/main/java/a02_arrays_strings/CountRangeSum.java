package a02_arrays_strings;

/**
 * Given an integer array nums and two integers lower and upper, return the number of range sums
 * that lie in [lower, upper] inclusive.
 * 
 * Range sum S(i, j) is defined as the sum of the elements in nums between indices i and j
 * inclusive, where i <= j.
 * 
 * Example 1:
 * 
 * Input: nums = [-2,5,-1], lower = -2, upper = 2 <br>
 * Output: 3 <br>
 * Explanation: The three ranges are: [0,0], [2,2], and [0,2] and their respective sums are: -2, -1,
 * 2.
 */
public class CountRangeSum {
  // Divide and conquer O(nlog(n))
  public int countRangeSum(int[] nums, int lower, int upper) {
    long[] sums = new long[nums.length + 1];
    // calculate prefix sums
    for (int i = 1; i < sums.length; i++) {
      sums[i] = sums[i - 1] + nums[i - 1];
    }
    return helper(sums, new long[sums.length], 0, sums.length - 1, lower, upper);
  }

  public int helper(long[] sums, long[] temp, int left, int right, int lower, int upper) {
    if (left >= right)
      return 0;

    int count = 0, mid = left + (right + 1 - left) / 2;
    count += helper(sums, temp, left, mid - 1, lower, upper);
    count += helper(sums, temp, mid, right, lower, upper);

    int start = mid, end = mid;
    for (int i = left; i < mid; i++) {
      while (start <= right && sums[start] - sums[i] < lower)
        start++;
      while (end <= right && sums[end] - sums[i] <= upper)
        end++;
      count += end - start;
    }

    merge(sums, temp, left, right, mid);
    return count;
  }

  public void merge(long[] sums, long[] temp, int low, int high, int mid) {
    // mid belongs to the right half
    int index = low, left = low, right = mid;
    while (left < mid && right <= high) {
      if (sums[left] <= sums[right]) {
        temp[index] = sums[left];
        left++;
        index++;
      } else {
        temp[index] = sums[right];
        right++;
        index++;
      }
    }
    while (left < mid) {
      temp[index++] = sums[left++];
    }
    while (right <= high) {
      temp[index++] = sums[right++];
    }

    System.arraycopy(temp, low, sums, low, high - low + 1);
  }
}
