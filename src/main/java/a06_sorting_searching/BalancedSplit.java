package a06_sorting_searching;

import org.junit.Assert;

/**
 * Balanced Split
 * 
 * Given a set of integers (which may include repeated integers), determine if there's a way to
 * split the set into two subsets A and B such that the sum of the integers in both sets is the
 * same, and all of the integers in A are strictly smaller than all of the integers in B. Note:
 * Strictly smaller denotes that every integer in A must be less than, and not equal to, every
 * integer in B.
 * 
 * Example: nums = [1, 5, 7, 1], output = true, We can split the set into A = {1, 1, 5} and B = {7}.
 *
 */
public class BalancedSplit {
  public boolean balancedSplit(int[] nums) {
    if (nums == null || nums.length == 0)
      return true;

    long sum = 0;
    for (int num : nums) {
      sum += num;
    }
    if (sum % 2 == 1) {
      return false; // odd sum, impossible split
    }

    return canSplit(nums, sum / 2, 0, 0, nums.length - 1);
  }

  private boolean canSplit(int[] nums, long target, long leftSum, int left, int right) {
    if (left >= right) {
      return false;
    }

    int lastLeft = left, lastRight = right;
    int pivot = nums[left + (right - left) / 2];

    long sum = 0;
    while (left < right) {
      // add pivot to the left sub array!
      while (left < nums.length && nums[left] <= pivot) {
        sum += nums[left];
        left++;
      }
      while (nums[right] > pivot) {
        right--;
      }
      if (left < right) {
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
        sum += nums[left];
        left++;
        right--;
      }
    }

    sum += leftSum; // add left sum from last time

    if (sum == target) {
      return true;
    } else if (sum < target) {
      // the split point should be on the right side
      return canSplit(nums, target, sum, left, lastRight);
    } else {
      if (lastRight == right) {
        return false; // break the cycle
      }
      // the split point should be on the left side
      return canSplit(nums, target, leftSum, lastLeft, right);
    }
  }

  public static void main(String[] args) {
    BalancedSplit solution = new BalancedSplit();
    Assert.assertTrue(solution.balancedSplit(new int[] { 1, 5, 7, 1 }));
    Assert.assertFalse(solution.balancedSplit(new int[] { 12, 7, 6, 7, 6 }));
    Assert.assertTrue(solution.balancedSplit(new int[] {}));
    Assert.assertFalse(solution.balancedSplit(new int[] { 2 }));
    Assert.assertFalse(solution.balancedSplit(new int[] { 20, 2 }));
    Assert.assertTrue(solution.balancedSplit(new int[] { 5, 7, 20, 12, 5, 7, 6, 14, 5, 5, 6 }));
    Assert.assertFalse(solution.balancedSplit(new int[] { 5, 7, 20, 12, 5, 7, 6, 7, 14, 5, 5, 6 }));
    Assert.assertFalse(solution.balancedSplit(new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }));
    Assert.assertTrue(solution.balancedSplit(new int[] { 0, 0, 0 }));
  }
}
