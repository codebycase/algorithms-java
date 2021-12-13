package a05_graphs_trees_heaps;

import java.util.LinkedList;
import java.util.Queue;

public class RottenOranges {
  public int orangesRotting(int[][] grid) {
    int m = grid.length;
    int n = grid[0].length;

    int elapsed = 0;
    int count = 0;

    Queue<int[]> queue = new LinkedList<>();

    int[] deltaX = new int[] { -1, 0, 1, 0 };
    int[] deltaY = new int[] { 0, 1, 0, -1 };

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (grid[i][j] == 1) {
          count += 1;
        } else if (grid[i][j] == 2) {
          count += 1;
          queue.add(new int[] { i, j });
        }
      }
    }

    if (count == 0)
      return 0;

    while (!queue.isEmpty()) {
      int length = queue.size();

      for (int i = 0; i < length; i++) {
        int[] pos = queue.poll();
        int x = pos[0];
        int y = pos[1];

        count -= 1;

        for (int j = 0; j < 4; j++) {
          int newX = x + deltaX[j];
          int newY = y + deltaY[j];
          if (newX < 0 || newX >= m || newY < 0 || newY >= n)
            continue;
          if (grid[newX][newY] == 1) {
            grid[newX][newY] = 2;
            queue.add(new int[] { newX, newY });
          }

        }
      }

      elapsed += 1;
    }

    return count == 0 ? elapsed - 1 : -1;

  }
}
