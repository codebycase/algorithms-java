package a03_linked_lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.com/problems/largest-divisible-subset/submissions/
 */
public class LargestDivisibleSubset {

  public List<Integer> largestDivisibleSubset(int[] nums) {
    // The length of largestDivisibleSubset that ends with element i
    int[] lens = new int[nums.length];
    // The previous index of element i in the largestDivisibleSubset ends with element i
    int[] prev = new int[nums.length];

    Arrays.sort(nums);

    int max = 0, index = -1;
    for (int i = 0; i < nums.length; i++) {
      lens[i] = 1; // itself
      prev[i] = -1; // null value
      // Look back for any bigger length
      for (int j = i - 1; j >= 0; j--) {
        if (nums[i] % nums[j] == 0 && lens[i] < lens[j] + 1) {
          lens[i] = lens[j] + 1;
          prev[i] = j;
        }
      }
      if (lens[i] > max) {
        max = lens[i];
        index = i;
      }
    }

    List<Integer> result = new ArrayList<Integer>();
    while (index != -1) {
      result.add(nums[index]);
      index = prev[index];
    }

    return result;
  }

}
