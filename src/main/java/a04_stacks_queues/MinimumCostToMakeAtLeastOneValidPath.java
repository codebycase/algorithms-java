package a04_stacks_queues;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class MinimumCostToMakeAtLeastOneValidPath {
  // matches defined right, left, lower, upper
  int[][] dirs = new int[][] { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

  public int minCost(int[][] grid) {
    int m = grid.length, n = grid[0].length, cost = 0;
    int[][] dp = new int[m][n];
    for (int[] d : dp) {
      Arrays.fill(d, Integer.MAX_VALUE);
    }
    Queue<int[]> queue = new LinkedList<>();
    dfs(grid, 0, 0, cost, dp, queue);
    while (!queue.isEmpty()) {
      cost++;
      for (int size = queue.size(); size > 0; size--) {
        int[] top = queue.poll();
        int r = top[0], c = top[1];
        for (int[] dir : dirs) {
          dfs(grid, r + dir[0], c + dir[1], cost, dp, queue);
        }
      }
    }
    return dp[m - 1][n - 1];
  }

  private void dfs(int[][] grid, int r, int c, int cost, int[][] dp, Queue<int[]> queue) {
    if (r >= 0 && r < grid.length && c >= 0 && c < grid[0].length && dp[r][c] == Integer.MAX_VALUE) {
      dp[r][c] = cost;
      queue.offer(new int[] { r, c });
      int nextDir = grid[r][c] - 1; // offset starts from 1
      dfs(grid, r + dirs[nextDir][0], c + dirs[nextDir][1], cost, dp, queue);
    }
  }
}
