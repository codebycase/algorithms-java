package a19_tricky_java_snippets;

import java.util.Arrays;

public class MaxAverageDiff {
    public static int[][] maxAverageDiff(int[] nums) {
        if (nums == null || nums.length == 1)
            return null;
        Arrays.sort(nums); // sort it first!
        int sum = Arrays.stream(nums).sum();
        double maxDiff = Integer.MIN_VALUE;
        int pivot = 0;
        // both group should not be empty!
        int sumSoFar = nums[0];
        for (int i = 1; i < nums.length - 1; i++) {
            sumSoFar += nums[i];
            double leftAvg = (double) sumSoFar / i + 1;
            double rightAvg = (double) (sum - sumSoFar) / (nums.length - 1 - i);
            double currDiff = Math.abs(rightAvg - leftAvg);
            if (currDiff > maxDiff) {
                pivot = i;
                maxDiff = currDiff;
            }
        }
        int[][] result = new int[2][];
        result[0] = Arrays.copyOf(nums, pivot + 1);
        result[1] = Arrays.copyOfRange(nums, pivot + 1, nums.length);
        return result;
    }

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

    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(maxAvgDiffGroups(new int[] { 1, 1 })));
        System.out.println(Arrays.deepToString(maxAvgDiffGroups(new int[] { 1, 2, 3, 4, 5, 7 })));
        System.out.println(Arrays.deepToString(maxAvgDiffGroups(new int[] { 1, 7, 10, 45, 50 })));
        System.out.println(Arrays.deepToString(maxAvgDiffGroups(new int[] { 1, 7, 10, 45, 50, 70, 80, 100 })));
    }
}
