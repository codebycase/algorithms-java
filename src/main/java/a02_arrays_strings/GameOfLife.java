package a02_arrays_strings;

/**
 * Game of Life
 * 
 * https://leetcode.com/problems/game-of-life/
 */
public class GameOfLife {

  // Use two bits to preserve the old and new value
  public void gameOfLife(int[][] board) {
    int m = board.length, n = board[0].length;

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        int lives = liveNeighbors(board, m, n, i, j);
        // Only need to set when the 2nd bit becomes 1.
        if (board[i][j] == 1 && lives >= 2 && lives <= 3) {
          board[i][j] ^= 1 << 1; // 01 -> 11
        }
        if (board[i][j] == 0 && lives == 3) {
          board[i][j] ^= 1 << 1; // 00 -> 10
        }
      }
    }

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        board[i][j] >>= 1; // Drop the 1st bit
      }
    }
  }

  public int liveNeighbors(int[][] board, int m, int n, int i, int j) {
    int lives = 0;
    for (int x = Math.max(i - 1, 0); x <= Math.min(i + 1, m - 1); x++) {
      for (int y = Math.max(j - 1, 0); y <= Math.min(j + 1, n - 1); y++) {
        // Skip itself!
        if (x != i || y != j) {
          lives += board[x][y] & 1;
        }
      }
    }
    return lives;
  }

}
