package a03_linked_lists;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Snakes and Ladders
 * 
 * https://leetcode.com/problems/snakes-and-ladders/
 *
 */
public class SnakesAndLadders {
  public int snakesAndLadders(int[][] board) {
    int n = board.length;
    Queue<Integer> queue = new LinkedList<>();
    boolean[] visited = new boolean[n * n + 1];
    queue.offer(1);
    for (int move = 0; !queue.isEmpty(); move++) {
      for (int size = queue.size(); size > 0; size--) {
        int num = queue.poll();
        if (visited[num])
          continue;
        visited[num] = true;
        if (num == n * n)
          return move;
        // try through all dice numbers
        for (int i = 1; i <= 6; i++) {
          int next = num + i;
          if (next <= n * n) {
            int value = getBoardValue(board, next);
            if (value > 0)
              next = value; // snakes or ladders
            if (!visited[next])
              queue.offer(next);
          }
        }
      }
    }
    return -1;
  }

  private int getBoardValue(int[][] board, int num) {
    int n = board.length;
    int r = (num - 1) / n; // row id from bottom
    int x = n - 1 - r; // cell (0, 0) is from top-left
    int y = num - 1 - n * r; // col from left to right
    if (r % 2 != 0) {
      y = n - 1 - y; // col from right to left
    }
    return board[x][y];
  }

}
