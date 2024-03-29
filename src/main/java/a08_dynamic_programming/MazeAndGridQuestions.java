package a08_dynamic_programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
 * 
 * The robot can only move either down or right at any point in time. The robot is trying to reach
 * the bottom-right corner of the grid (marked 'Finish' in the diagram below).
 * 
 * How many possible unique paths are there?
 * 
 * @author lchen
 *
 */
public class MazeAndGridQuestions {
  public int uniquePaths(int m, int n) {
    int[][] grid = new int[m][n];
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (i == 0 || j == 0)
          grid[i][j] = 1;
        else
          grid[i][j] = grid[i - 1][j] + grid[i][j - 1];
      }
    }
    return grid[m - 1][n - 1];
  }

  // use one dimension array!
  public int uniquePaths2(int m, int n) {
    int[] dp = new int[n];
    dp[0] = 1;
    for (int i = 0; i < m; i++) {
      for (int j = 1; j < n; j++) {
        dp[j] += dp[j - 1];
      }
    }
    return dp[n - 1];
  }

  /**
   * 
   * Now consider if some obstacles are added to the grids. How many unique paths would there be?
   * 
   * An obstacle and empty space is marked as 1 and 0 respectively in the grid.
   * 
   * 
   * @param obstacleGrid
   * @return
   */
  public int uniquePathsWithObstacles(int[][] obstacleGrid) {
    int width = obstacleGrid[0].length;
    int[] dp = new int[width];
    dp[0] = 1;
    for (int[] row : obstacleGrid) {
      for (int j = 0; j < width; j++) {
        if (row[j] == 1)
          dp[j] = 0;
        else if (j > 0)
          dp[j] += dp[j - 1];
      }
    }
    return dp[width - 1];
  }

  /**
   * Design an algorithm to find a path.
   * 
   * @param maze
   * @return
   */
  public ArrayList<Point> findPath(boolean[][] maze) {
    if (maze == null || maze.length == 0)
      return null;
    ArrayList<Point> path = new ArrayList<Point>();
    HashSet<Point> failedPoints = new HashSet<Point>();
    if (findPath(maze, maze.length - 1, maze[0].length - 1, path, failedPoints))
      return path;
    return null;
  }

  public boolean findPath(boolean[][] maze, int row, int col, ArrayList<Point> path, HashSet<Point> failedPoints) {
    if (col < 0 || row < 0 || !maze[row][col]) // Out of bounds or not available
      return false;

    Point p = new Point(row, col, Integer.MAX_VALUE, "");

    if (failedPoints.contains(p)) // Already visited this cell
      return false;

    boolean isAtOrigin = (row == 0) && (col == 0);

    // If there's a path from the start to my current location, add my location.
    if (isAtOrigin || findPath(maze, row, col - 1, path, failedPoints) || findPath(maze, row - 1, col, path, failedPoints)) {
      path.add(p);
      return true;
    }

    failedPoints.add(p); // Cache result
    return false;
  }

  /**
   * 
   * There is a ball in a maze with empty spaces and walls. The ball can go through empty spaces by
   * rolling up, down, left or right, but it won't stop rolling until hitting a wall. When the ball
   * stops, it could choose the next direction.
   * 
   * Given the ball's start position, the destination and the maze, determine whether the ball could
   * stop at the destination.
   * 
   * The maze is represented by a binary 2D array. 1 means the wall and 0 means the empty space. You
   * may assume that the borders of the maze are all walls. The start and destination coordinates are
   * represented by row and column indexes.
   * 
   * <pre>
  Example 1
  
  Input 1: a maze represented by a 2D array
  
  0 0 1 0 0
  0 0 0 0 0
  0 0 0 1 0
  1 1 0 1 1
  0 0 0 0 0
  
  Input 2: start coordinate (rowStart, colStart) = (0, 4)
  Input 3: destination coordinate (rowDest, colDest) = (4, 4)
  
  Output: true
  Explanation: One possible way is : left -> down -> left -> down -> right -> down -> right.
   * </pre>
   * 
   * @param maze
   * @param start
   * @param destination
   * @return
   */
  public boolean hasPath(int[][] maze, int[] start, int[] destination) {
    if (start[0] == destination[0] && start[1] == destination[1])
      return true;
    int m = maze.length, n = maze[0].length;
    int[][] dirs = new int[][] { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
    boolean[][] visited = new boolean[m][n];
    Queue<int[]> queue = new LinkedList<>();
    visited[start[0]][start[1]] = true;
    queue.offer(start);
    while (!queue.isEmpty()) {
      int[] p = queue.poll();
      for (int[] dir : dirs) {
        int x = p[0], y = p[1];
        // keep rolling on this direction until hit a wall!
        while (x >= 0 && x < m && y >= 0 && y < n && maze[x][y] == 0) {
          x += dir[0];
          y += dir[1];
        }
        // back to empty space
        x -= dir[0];
        y -= dir[1];
        if (visited[x][y])
          continue;
        if (x == destination[0] && y == destination[1])
          return true;
        queue.offer(new int[] { x, y });
        visited[x][y] = true;
      }
    }
    return false;
  }

  public int shortestDistance2(int[][] maze, int[] start, int[] dest) {
    int[][] distance = new int[maze.length][maze[0].length];
    for (int[] row : distance)
      Arrays.fill(row, Integer.MAX_VALUE);
    distance[start[0]][start[1]] = 0;
    int[][] dirs = { { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 0 } };
    PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> a[2] - b[2]);
    queue.offer(new int[] { start[0], start[1], 0 });
    while (!queue.isEmpty()) {
      int[] s = queue.poll();
      if (distance[s[0]][s[1]] < s[2])
        continue;
      for (int[] dir : dirs) {
        int x = s[0] + dir[0];
        int y = s[1] + dir[1];
        int count = 0;
        while (x >= 0 && y >= 0 && x < maze.length && y < maze[0].length && maze[x][y] == 0) {
          x += dir[0];
          y += dir[1];
          count++;
        }
        if (distance[s[0]][s[1]] + count < distance[x - dir[0]][y - dir[1]]) {
          distance[x - dir[0]][y - dir[1]] = distance[s[0]][s[1]] + count;
          queue.offer(new int[] { x - dir[0], y - dir[1], distance[x - dir[0]][y - dir[1]] });
        }
      }
    }
    return distance[dest[0]][dest[1]] == Integer.MAX_VALUE ? -1 : distance[dest[0]][dest[1]];
  }

  public int shortestDistance(int[][] maze, int[] start, int[] destination) {
    int m = maze.length, n = maze[0].length;
    int[][] lens = new int[m][n];
    for (int i = 0; i < m * n; i++)
      lens[i / n][i % n] = Integer.MAX_VALUE;

    int[][] dirs = new int[][] { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
    Queue<int[]> queue = new PriorityQueue<>((a, b) -> (a[2] - b[2]));
    queue.offer(new int[] { start[0], start[1], 0 });

    while (!queue.isEmpty()) {
      int[] p = queue.poll();
      if (lens[p[0]][p[1]] <= p[2]) // Already found shorter route
        continue;
      lens[p[0]][p[1]] = p[2];
      for (int[] dir : dirs) {
        int x = p[0], y = p[1], l = p[2];
        while (x >= 0 && x < m && y >= 0 && y < n && maze[x][y] == 0) {
          x += dir[0];
          y += dir[1];
          l++;
        }
        // Retreat an overstepped one
        x -= dir[0];
        y -= dir[1];
        l--;
        if (l < lens[x][y]) {
          queue.offer(new int[] { x, y, l });
        }
      }
    }

    return lens[destination[0]][destination[1]] == Integer.MAX_VALUE ? -1 : lens[destination[0]][destination[1]];
  }

  class Point implements Comparable<Point> {
    int x, y, len;
    String path;

    public Point(int x, int y, int len, String path) {
      this.x = x;
      this.y = y;
      this.len = len;
      this.path = path;
    }

    public int compareTo(Point p) {
      return len == p.len ? path.compareTo(p.path) : len - p.len;
    }
  }

  public String findShortestWay(int[][] maze, int[] ball, int[] hole) {
    int m = maze.length, n = maze[0].length;
    Point[][] points = new Point[m][n];
    for (int i = 0; i < m * n; i++)
      points[i / n][i % n] = new Point(i / n, i % n, Integer.MAX_VALUE, "");
    int[][] dirs = { { -1, 0 }, { 0, 1 }, { 1, 0, }, { 0, -1 } };
    String[] directions = { "u", "r", "d", "l" };

    Queue<Point> queue = new PriorityQueue<>(); // using priority queue
    queue.offer(new Point(ball[0], ball[1], 0, ""));
    while (!queue.isEmpty()) {
      Point point = queue.poll();
      if (points[point.x][point.y].compareTo(point) <= 0)
        continue; // Already found a route shorter
      points[point.x][point.y] = point;
      for (int i = 0; i < dirs.length; i++) {
        int[] dir = dirs[i];
        int x = point.x, y = point.y, len = point.len;
        while (x >= 0 && x < m && y >= 0 && y < n && maze[x][y] == 0 && (x != hole[0] || y != hole[1])) {
          x += dir[0];
          y += dir[1];
          len++;
        }
        // Retreat an overstepped one
        if (x != hole[0] || y != hole[1]) { // Check not in the hole yet
          x -= dir[0];
          y -= dir[1];
          len--;
        }
        if (len < points[x][y].len) {
          queue.offer(new Point(x, y, len, point.path + directions[i]));
        }
      }
    }
    return points[hole[0]][hole[1]].len == Integer.MAX_VALUE ? "impossible" : points[hole[0]][hole[1]].path;
  }

  /**
   * You are given an m x n integer array grid where grid[i][j] could be:
   * 
   * <pre>
  1 representing the starting square. There is exactly one starting square.
  2 representing the ending square. There is exactly one ending square.
  0 representing empty squares we can walk over.
  -1 representing obstacles that we cannot walk over.
   * </pre>
   * 
   * Return the number of 4-directional walks from the starting square to the ending square, that walk
   * over every non-obstacle square exactly once.
   */
  public int uniquePathsIII(int[][] grid) {
    AtomicInteger count = new AtomicInteger(0);
    int remain = 0, startRow = 0, startCol = 0;

    // Find the start cell
    for (int row = 0; row < grid.length; ++row)
      for (int col = 0; col < grid[0].length; ++col) {
        int cell = grid[row][col];
        if (cell >= 0)
          remain += 1;
        if (cell == 1) {
          startRow = row;
          startCol = col;
        }
      }

    backtrack(grid, startRow, startCol, remain, count);

    return count.get();
  }

  protected void backtrack(int[][] grid, int row, int col, int remain, AtomicInteger pathCount) {
    if (grid[row][col] == 2 && remain == 1) {
      pathCount.addAndGet(1); // Reached the destination
      return;
    }

    int temp = grid[row][col];

    grid[row][col] = -4; // Visited
    remain -= 1; // One less square to visit

    int[][] dirs = { { -1, 0 }, { 0, 1 }, { 1, 0, }, { 0, -1 } };
    for (int[] dir : dirs) {
      int i = row + dir[0];
      int j = col + dir[1];

      if (0 > i || i >= grid.length || 0 > j || j >= grid[row].length || grid[i][j] < 0)
        continue;

      backtrack(grid, i, j, remain, pathCount);
    }

    grid[row][col] = temp;
  }
}
