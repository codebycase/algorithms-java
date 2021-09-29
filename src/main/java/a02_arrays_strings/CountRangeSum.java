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
    long[] sums = new long[nums.length + 1]; // easier with one extra space
    // calculate prefix sums
    for (int i = 1; i < sums.length; i++) {
      sums[i] = sums[i - 1] + nums[i - 1];
    }
    return divide(sums, new long[sums.length], 0, sums.length - 1, lower, upper);
  }

  public int divide(long[] sums, long[] temp, int left, int right, int lower, int upper) {
    if (left >= right)
      return 0;

    int count = 0, mid = left + (right + 1 - left) / 2;
    count += divide(sums, temp, left, mid - 1, lower, upper);
    count += divide(sums, temp, mid, right, lower, upper);
    count += merge(sums, temp, left, mid, right, lower, upper);
    return count;
  }

  public int merge(long[] sums, long[] temp, int low, int mid, int high, int lower, int upper) {
    int count = 0, start = mid, end = mid;
    for (int i = low; i < mid; i++) {
      while (start <= high && sums[start] - sums[i] < lower)
        start++;
      while (end <= high && sums[end] - sums[i] <= upper)
        end++;
      count += end - start;
    }

    // mid belongs to the right half
    int i = low, l = low, h = mid;
    while (l < mid && h <= high) {
      if (sums[l] <= sums[h]) {
        temp[i++] = sums[l++];
      } else {
        temp[i++] = sums[h++];
      }
    }
    while (l < mid) {
      temp[i++] = sums[l++];
    }
    while (h <= high) {
      temp[i++] = sums[h++];
    }

    System.arraycopy(temp, low, sums, low, high - low + 1);

    return count;
  }
}
