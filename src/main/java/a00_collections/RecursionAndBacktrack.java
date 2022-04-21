package a00_collections;

public class RecursionAndBacktrack {
  // DFS is the most efficient way!
  public boolean isInterleave(String s1, String s2, String s3) {
    if (s1.length() + s2.length() != s3.length())
      return false;
    return dfs(s1, s2, s3, 0, 0, 0, new boolean[s1.length() + 1][s2.length() + 1]);
  }

  public boolean dfs(String s1, String s2, String s3, int i, int j, int k, boolean[][] invalid) {
    if (invalid[i][j])
      return false;
    if (k == s3.length())
      return true;
    boolean valid = (i < s1.length() && s1.charAt(i) == s3.charAt(k) && dfs(s1, s2, s3, i + 1, j, k + 1, invalid))
        || (j < s2.length() && s2.charAt(j) == s3.charAt(k) && dfs(s1, s2, s3, i, j + 1, k + 1, invalid));
    if (!valid)
      invalid[i][j] = true;
    return valid;
  }

  // DP has the stable complexity O(m*n)
  public boolean isInterleave2(String s1, String s2, String s3) {
    if (s3.length() != s1.length() + s2.length())
      return false;
    boolean dp[][] = new boolean[s1.length() + 1][s2.length() + 1];

    dp[0][0] = true;
    for (int i = 1; i < dp.length; i++)
      dp[i][0] = dp[i - 1][0] && s1.charAt(i - 1) == s3.charAt(i - 1);
    for (int j = 1; j < dp[0].length; j++)
      dp[0][j] = dp[0][j - 1] && s2.charAt(j - 1) == s3.charAt(j - 1);

    for (int i = 1; i <= s1.length(); i++) {
      for (int j = 1; j <= s2.length(); j++) {
        dp[i][j] = (dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1)) || (dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1));
      }
    }
    return dp[s1.length()][s2.length()];
  }

  /**
   * 
   * Minimum Cost For Tickets
   * 
   * You have planned some train traveling one year in advance. The days of the year in which you will
   * travel are given as an integer array days. Each day is an integer from 1 to 365.
   * 
   * Train tickets are sold in three different ways:
   * 
   * a 1-day pass is sold for costs[0] dollars, a 7-day pass is sold for costs[1] dollars, and a
   * 30-day pass is sold for costs[2] dollars. The passes allow that many days of consecutive travel.
   * 
   * For example, if we get a 7-day pass on day 2, then we can travel for 7 days: 2, 3, 4, 5, 6, 7,
   * and 8. Return the minimum number of dollars you need to travel every day in the given list of
   * days.
   * 
   * <pre>
   * Example 1:
   *
   * Input: days = [1,4,6,7,8,20], costs = [2,7,15]
   * Output: 11
   * Explanation: For example, here is one way to buy passes that lets you travel your travel plan:
   * On day 1, you bought a 1-day pass for costs[0] = $2, which covered day 1.
   * On day 3, you bought a 7-day pass for costs[1] = $7, which covered days 3, 4, ..., 9.
   * On day 20, you bought a 1-day pass for costs[0] = $2, which covered day 20.
   * In total, you spent $11 and covered all the days of your travel.
   * </pre>
   *
   */
  public int mincostTickets(int[] days, int[] costs) {
    int[] durations = { 1, 7, 30 }; // 3 different ways
    return recursiveDP(days, costs, durations, 0, new int[days.length]);
  }

  public int recursiveDP(int[] days, int[] costs, int[] durations, int i, int[] memo) {
    if (i >= days.length) {
      return 0;
    }

    if (memo[i] != 0) {
      return memo[i];
    }

    int ans = Integer.MAX_VALUE, j = i;
    for (int d = 0; d < durations.length; d++) {
      while (j < days.length && days[j] < days[i] + durations[d]) {
        j++;
      }
      ans = Math.min(ans, recursiveDP(days, costs, durations, j, memo) + costs[d]);
    }

    memo[i] = ans;
    return ans;
  }

  public boolean canPartitionKSubsets(int[] nums, int k) {
    int sum = 0;
    for (int n : nums)
      sum += n;
    if (sum % k != 0)
      return false;
    int target = sum / k;

    return partitionDFS(0, k, 0, target, nums, new boolean[nums.length]);
  }

  // DFS with Backtrack
  public boolean partitionDFS(int i, int k, int sum, int target, int[] nums, boolean[] visited) {
    if (k == 0)
      return true;
    if (target == sum)
      // start a new group
      return partitionDFS(0, k - 1, 0, target, nums, visited);
    if (i == nums.length || sum > target)
      return false;

    // move forward without using current value
    boolean result = partitionDFS(i + 1, k, sum, target, nums, visited);

    if (!result && !visited[i]) {
      // dfs with using current value
      visited[i] = true;
      result = partitionDFS(i + 1, k, sum + nums[i], target, nums, visited);
      visited[i] = false;
    }

    return result;
  }

  public int longestIncreasingPath(int[][] matrix) {
    if (matrix.length == 0)
      return 0;
    int m = matrix.length;
    int n = matrix[0].length;
    int[][] memo = new int[m][n];
    int max = 1;
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        max = Math.max(max, longestIncreasingPathDfs(matrix, i, j, m, n, memo));
      }
    }
    return max;
  }

  private int[][] dirs = new int[][] { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

  private int longestIncreasingPathDfs(int[][] matrix, int i, int j, int m, int n, int[][] memo) {
    if (memo[i][j] != 0)
      return memo[i][j];
    int max = 1;
    for (int[] dir : dirs) {
      int x = i + dir[0];
      int y = j + dir[1];
      if (x >= 0 && x < m && y >= 0 && y < n && matrix[x][y] > matrix[i][j]) {
        max = Math.max(max, 1 + longestIncreasingPathDfs(matrix, x, y, m, n, memo));
      }
    }
    memo[i][j] = max;
    return max;
  }
}
