package a05_graphs_trees_heaps;

import java.util.LinkedList;
import java.util.Queue;

/**
 * You are starving and you want to eat food as quickly as possible. You want to find the shortest
 * path to arrive at any food cell.
 * 
 * You are given an m x n character matrix, grid, of these different types of cells:
 * 
 * <pre>
 * '*' is your location. There is exactly one '*' cell.
 * '#' is a food cell. There may be multiple food cells.
 * 'O' is free space, and you can travel through these cells.
 * 'X' is an obstacle, and you cannot travel through these cells.
 * </pre>
 * 
 * You can travel to any adjacent cell north, east, south, or west of your current location if there
 * is not an obstacle.
 * 
 * Return the length of the shortest path for you to reach any food cell. If there is no path for
 * you to reach food, return -1.
 */
public class ShortestPathToGetFood {
  public int getFood(char[][] grid) {
    if (grid == null || grid.length == 0)
      return -1;

    int[][] dirs = new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
    int rows = grid.length;
    int cols = grid[0].length;

    Queue<int[]> queue = new LinkedList<>();

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (grid[row][col] == '*') {
          queue.offer(new int[] { row, col });
          break;
        }
      }
    }

    int steps = 0;
    while (!queue.isEmpty()) {
      steps++;

      int size = queue.size();
      for (int i = 0; i < size; i++) {
        int[] cell = queue.poll();

        for (int[] dir : dirs) {
          int row = cell[0] + dir[0];
          int col = cell[1] + dir[1];
          if (row >= 0 && row < rows && col >= 0 && col < cols && grid[row][col] != 'X') {
            if (grid[row][col] == '#')
              return steps;
            queue.offer(new int[] { row, col });
            grid[row][col] = 'X';
          }
        }
      }
    }

    return -1;
  }

}
