package a10_recursion_greedy_invariant;

import java.util.Arrays;

/**
 * Given weights and values of n items, put these items in a knapsack of capacity W to get the
 * maximum total value in the knapsack. In other words, given two integer arrays val[0..n-1] and
 * wt[0..n-1] which represent values and weights associated with n items respectively. Also given an
 * integer W which represents knapsack capacity, find out the maximum value subset of val[] such
 * that sum of the weights of this subset is smaller than or equal to W. You cannot break an item,
 * either pick the complete item or don’t pick it (0-1 property).
 * 
 * <pre>
 * Example:     
 * int[] values = { 60, 100, 120 };
 * int[] weights = { 10, 20, 30 };
 * int capacity = 50;
 * 
 * Answer: 220; Weight = (30 + 20); Value = (120 + 100)
 * </pre>
 */
public class Knapsack {

  /**
   * Let Weights = {1, 2, 3}, Values = {10, 15, 40}, Capacity=6
   * 
   * <pre>
  0   1   2   3   4   5   6
  
  0  0   0   0   0   0   0   0
  
  1  0  10  10  10  10  10  10
  
  2  0  10  15  25  25  25  25
  
  3  0  10  15  40  50  55  65
   * </pre>
   */
  // Bottom up DP: Time complexity (O(n*w)), Space (O(w))
  public static int knapSack(int capacity, int[] weights, int[] values) {
    // From 2D to 1D array
    int[] dp = new int[capacity + 1];
    dp[0] = 0;

    for (int i = 0; i < weights.length; i++) {
      // Bottom up to avoid duplicate
      for (int w = capacity; w >= weights[i]; w--) {
        dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
      }
    }

    return dp[capacity];
  }

  // Memorization Recursive
  public static int knapSack2(int capacity, int weights[], int[] values) {
    int[][] dp = new int[values.length][capacity + 1];
    for (int[] d : dp) {
      Arrays.fill(d, -1);
    }
    return knapSackRec(capacity, weights, values, values.length - 1, dp);
  }

  private static int knapSackRec(int capacity, int[] weights, int[] values, int n, int[][] memo) {
    if (n < 0 || capacity == 0) {
      return 0;
    }

    if (memo[n][capacity] != -1) {
      return memo[n][capacity];
    }

    if (weights[n] > capacity) {
      memo[n][capacity] = knapSackRec(capacity, weights, values, n - 1, memo);
    } else {
      memo[n][capacity] = Math.max(values[n] + knapSackRec(capacity - weights[n], weights, values, n - 1, memo),
          knapSackRec(capacity, weights, values, n - 1, memo));
    }

    return memo[n][capacity];
  }

  public static void main(String[] args) {
    int[] values = { 60, 100, 120 };
    int[] weights = { 10, 20, 30 };
    int capacity = 30;
    System.out.print(knapSack2(capacity, weights, values));
  }

}
