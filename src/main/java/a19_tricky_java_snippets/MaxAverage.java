package a19_tricky_java_snippets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaxAverage {
  public static int[][] maxAvgDiffGroups(int[] nums) {
    if (nums == null || nums.length == 1)
      throw new IllegalArgumentException();
    // Don't need to sort if we need continous sub arrays.
    Arrays.sort(nums); // sort it first! O(nlog(n))
    int total = Arrays.stream(nums).sum();
    double maxDiff = Double.MIN_VALUE;
    double sumSoFar = nums[0];
    int pivot = 0; // included
    for (int i = 1; i < nums.length - 1; i++) {
      sumSoFar += nums[i];
      double currDiff = Math.abs(sumSoFar / (i + 1) - (total - sumSoFar) / (nums.length - (i + 1)));
      if (currDiff > maxDiff) {
        pivot = i;
        maxDiff = currDiff;
      }
    }
    int[][] ans = new int[2][];
    ans[0] = Arrays.copyOf(nums, pivot + 1);
    ans[1] = Arrays.copyOfRange(nums, pivot + 1, nums.length);
    return ans;
  }

  public static List<List<Integer>> maxAvgDiffGroups2(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> groups = new ArrayList<>();
    groups.add(new ArrayList<>()); // min group
    groups.add(new ArrayList<>()); // max group
    if (nums == null || nums.length == 0)
      return groups;
    groups.get(0).add(nums[0]);
    if (nums.length == 1)
      return groups;
    groups.get(1).add(nums[nums.length - 1]);
    int i = 1, j = nums.length - 2;
    while (i <= j) {
      int iDiff = nums[i] - nums[i - 1];
      int jDiff = nums[j + 1] - nums[j];
      if (iDiff == jDiff) {
        groups.get(0).add(nums[i++]);
        groups.get(1).add(nums[j--]);
      } else if (iDiff < jDiff) {
        groups.get(0).add(nums[i++]);
      } else {
        groups.get(1).add(nums[j--]);
      }
    }
    System.out.println(nums[i]);
    System.out.println(average(groups.get(0)) + ":" + average(groups.get(1)));
    return groups;
  }

  public static List<List<Integer>> maxAvgDiffGroups3(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> groups = new ArrayList<>();
    groups.add(new ArrayList<>()); // min group
    groups.add(new ArrayList<>()); // max group
    if (nums == null || nums.length == 0)
      return groups;
    groups.get(0).add(nums[0]);
    if (nums.length == 1)
      return groups;
    groups.get(1).add(nums[nums.length - 1]);
    int i = 1, j = nums.length - 2;
    while (i <= j) {
      int iDiff = nums[i] - nums[i - 1];
      int jDiff = nums[j + 1] - nums[j];
      if (iDiff == jDiff) {
        groups.get(0).add(nums[i++]);
        groups.get(1).add(nums[j--]);
      } else if (iDiff < jDiff) {
        groups.get(0).add(nums[i++]);
      } else {
        groups.get(1).add(nums[j--]);
      }
    }
    System.out.println(nums[i]);
    System.out.println(average(groups.get(0)) + ":" + average(groups.get(1)));
    return groups;
  }

  private static double average(List<Integer> list) {
    double total = 0;
    for (int l : list) {
      total += l;
    }
    return total / list.size();
  }

  public static void main(String[] args) {
    System.out.println(Arrays.deepToString(maxAvgDiffGroups(new int[] { 1, 1 })));
    System.out.println(Arrays.deepToString(maxAvgDiffGroups(new int[] { 1, 2, 3, 4, 5, 7 })));
    System.out.println(Arrays.deepToString(maxAvgDiffGroups(new int[] { 1, 7, 10, 45, 50 })));
    System.out.println(Arrays.deepToString(maxAvgDiffGroups(new int[] { 1, 7, 10, 45, 50, 70, 80, 100 })));
  }
}
