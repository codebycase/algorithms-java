package a08_dynamic_programming;

/**
 * https://leetcode.com/problems/cherry-pickup/ <br>
 * https://leetcode.com/problems/cherry-pickup-ii/
 */
public class CherryPickup {

  // Top Down
  public int cherryPickup(int[][] grid) {
    int m = grid.length;
    int n = grid[0].length;
    int[][][] memo = new int[m][n][n];
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        for (int k = 0; k < n; k++) {
          memo[i][j][k] = -1;
        }
      }
    }
    return Math.max(0, dp(0, 0, 0, grid, memo));
  }

  public int dp(int r1, int c1, int c2, int[][] grid, int[][][] memo) {
    int r2 = r1 + c1 - c2;
    int m = grid.length;
    int n = grid[0].length;

    // Check boundaries and blocks
    if (m == r1 || m == r2 || n == c1 || n == c2 || grid[r1][c1] == -1 || grid[r2][c2] == -1) {
      return Integer.MIN_VALUE;
    }

    // Reached the end
    if (r1 == m - 1 && c1 == n - 1) {
      return grid[r1][c1];
    }

    if (memo[r1][c1][c2] != -1) {
      return memo[r1][c1][c2];
    }

    int max = grid[r1][c1];
    if (c1 != c2)
      max += grid[r2][c2];
    max += Math.max(Math.max(dp(r1, c1 + 1, c2 + 1, grid, memo), dp(r1 + 1, c1, c2 + 1, grid, memo)),
        Math.max(dp(r1, c1 + 1, c2, grid, memo), dp(r1 + 1, c1, c2, grid, memo)));
    memo[r1][c1][c2] = max;

    return max;
  }

  // Top Down
  public int cherryPickup2Robots(int[][] grid) {
    int m = grid.length;
    int n = grid[0].length;
    int[][][] memo = new int[m][n][n];
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        for (int k = 0; k < n; k++) {
          memo[i][j][k] = -1;
        }
      }
    }
    return dp2Robots(0, 0, n - 1, grid, memo);
  }

  private int dp2Robots(int row, int col1, int col2, int[][] grid, int[][][] memo) {
    if (col1 < 0 || col1 >= grid[0].length || col2 < 0 || col2 >= grid[0].length) {
      return 0;
    }

    if (memo[row][col1][col2] != -1) {
      return memo[row][col1][col2];
    }

    int result = 0;
    result += grid[row][col1];
    if (col1 != col2) {
      result += grid[row][col2];
    }

    if (row != grid.length - 1) {
      int max = 0;
      for (int newCol1 = col1 - 1; newCol1 <= col1 + 1; newCol1++) {
        for (int newCol2 = col2 - 1; newCol2 <= col2 + 1; newCol2++) {
          max = Math.max(max, dp2Robots(row + 1, newCol1, newCol2, grid, memo));
        }
      }
      result += max;
    }

    memo[row][col1][col2] = result;
    return result;
  }

  // Bottom Up
  public int cherryPickup2Robots2(int[][] grid) {
    int m = grid.length;
    int n = grid[0].length;
    int dp[][][] = new int[m][n][n];

    for (int row = m - 1; row >= 0; row--) {
      for (int col1 = 0; col1 < n; col1++) {
        for (int col2 = 0; col2 < n; col2++) {
          int result = 0;

          result += grid[row][col1];
          if (col1 != col2) {
            result += grid[row][col2];
          }

          if (row != m - 1) {
            int max = 0;
            for (int newCol1 = col1 - 1; newCol1 <= col1 + 1; newCol1++) {
              for (int newCol2 = col2 - 1; newCol2 <= col2 + 1; newCol2++) {
                if (newCol1 >= 0 && newCol1 < n && newCol2 >= 0 && newCol2 < n) {
                  max = Math.max(max, dp[row + 1][newCol1][newCol2]);
                }
              }
            }
            result += max;
          }

          dp[row][col1][col2] = result;
        }
      }
    }

    return dp[0][0][n - 1];
  }
}
