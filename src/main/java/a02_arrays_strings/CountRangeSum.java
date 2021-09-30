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
    // easy to cover edge cases with one extra space
    long[] sums = new long[nums.length + 1];
    // calculate prefix sums
    for (int i = 1; i < sums.length; i++) {
      sums[i] = sums[i - 1] + nums[i - 1];
    }
    return divide(sums, new long[sums.length], 0, sums.length - 1, lower, upper);
  }

  public int divide(long[] sums, long[] temp, int high, int low, int lower, int upper) {
    if (high >= low)
      return 0;

    int count = 0, mid = (high + low + 1) / 2;
    count += divide(sums, temp, high, mid - 1, lower, upper);
    count += divide(sums, temp, mid, low, lower, upper);
    count += merge(sums, temp, high, mid, low, lower, upper);
    return count;
  }

  public int merge(long[] sums, long[] temp, int low, int mid, int high, int lower, int upper) {
    // compare and count
    int count = 0, start = mid, end = mid;
    for (int i = low; i < mid; i++) {
      while (start <= high && sums[start] - sums[i] < lower)
        start++;
      while (end <= high && sums[end] - sums[i] <= upper)
        end++;
      count += end - start;
    }

    // mid belongs to the right half
    int current = low, left = low, right = mid;
    while (left < mid && right <= high) {
      if (sums[left] <= sums[right]) {
        temp[current++] = sums[left++];
      } else {
        temp[current++] = sums[right++];
      }
    }
    // cache rest of left
    while (left < mid) {
      temp[current++] = sums[left++];
    }
    // cach rest of right
    while (right <= high) {
      temp[current++] = sums[right++];
    }

    // copy all sorted sums
    int length = high - low + 1;
    for (int j = 0; j < length; j++) {
      sums[low + j] = temp[low + j];
    }
    // or use array copy!
    // System.arraycopy(temp, low, sums, low, high - low + 1);

    return count;
  }

  public static void main(String[] args) {
    CountRangeSum solution = new CountRangeSum();
    solution.countRangeSum(new int[] { -2, 5, -1 }, -2, 2);
  }
}
