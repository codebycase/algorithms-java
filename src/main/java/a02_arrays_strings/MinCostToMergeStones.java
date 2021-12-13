package a02_arrays_strings;

/**
 * There are n piles of stones arranged in a row. The ith pile has stones[i] stones.
 * 
 * A move consists of merging exactly k consecutive piles into one pile, and the cost of this move
 * is equal to the total number of stones in these k piles.
 * 
 * Return the minimum cost to merge all piles of stones into one pile. If it is impossible, return
 * -1.
 * 
 * <pre>
 * Example 1:
 *
 * Input: stones = [3,2,4,1], k = 2
 * Output: 20
 * Explanation: We start with [3, 2, 4, 1].
 * We merge [3, 2] for a cost of 5, and we are left with [5, 4, 1].
 * We merge [4, 1] for a cost of 5, and we are left with [5, 5].
 * We merge [5, 5] for a cost of 10, and we are left with [10].
 * The total cost was 20, and this is the minimum possible
 * </pre>
 * 
 * https://leetcode.com/problems/minimum-cost-to-merge-stones/
 */
public class MinCostToMergeStones {

  public int mergeStones(int[] stones, int k) {
    int len = stones.length;

    // Check if the input can be merged
    // k - 1 as it's to merge k piles into 1 pile
    // len - 1 as the very last time is to merge k piles
    if ((len - 1) % (k - 1) > 0) {
      return -1;
    }

    // Calculate prefix sum
    int[] preSum = new int[len + 1];
    for (int i = 0; i < len; i++) {
      preSum[i + 1] = preSum[i] + stones[i];
    }

    // Bottom up DP approach where each entry represents the min cost for current sub array
    int[][] dp = new int[len][len];

    // span is the length of current sub array
    for (int span = k; span <= len; span++) {
      for (int left = 0; left + span <= len; left++) {
        int right = left + span - 1; // span/k is 1 based

        // Initialize as max value
        dp[left][right] = Integer.MAX_VALUE;

        // Since k is 1 based and we can merge only k piles.
        for (int split = left; split < right; split += (k - 1)) {
          // Left side to be merged into 1 pile for sure, but right side to 1 + k - 2 piles
          dp[left][right] = Math.min(dp[left][right], dp[left][split] + dp[split + 1][right]);
        }

        // The very last time to merge rest k piles if applicable
        if ((left - right) % (k - 1) == 0) {
          dp[left][right] += (preSum[right + 1] - preSum[left]);
        }
      }
    }

    return dp[0][len - 1];
  }

}
