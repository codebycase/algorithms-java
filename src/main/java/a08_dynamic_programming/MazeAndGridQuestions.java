package a08_dynamic_programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

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

    Point p = new Point(row, col);

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
      if (lens[p[0]][p[1]] <= p[2]) // already found shorter route
        continue;
      lens[p[0]][p[1]] = p[2];
      for (int[] dir : dirs) {
        int x = p[0], y = p[1], l = p[2];
        while (x >= 0 && x < m && y >= 0 && y < n && maze[x][y] == 0) {
          x += dir[0];
          y += dir[1];
          l++;
        }
        // Minus over stepped one
        x -= dir[0];
        y -= dir[1];
        l--;
        queue.offer(new int[] { x, y, l });
      }
    }

    return lens[destination[0]][destination[1]] == Integer.MAX_VALUE ? -1 : lens[destination[0]][destination[1]];
  }

  class Point implements Comparable<Point> {
    int x, y, l;
    String s;

    public Point(int x, int y) {
      this(x, y, Integer.MAX_VALUE, "");
    }

    public Point(int x, int y, int l, String s) {
      this.x = x;
      this.y = y;
      this.l = l;
      this.s = s;
    }

    public int compareTo(Point p) {
      return l == p.l ? s.compareTo(p.s) : l - p.l;
    }
  }

  public String findShortestWay(int[][] maze, int[] ball, int[] hole) {
    int m = maze.length, n = maze[0].length;
    Point[][] points = new Point[m][n];
    for (int i = 0; i < m * n; i++)
      points[i / n][i % n] = new Point(i / n, i % n);
    int[][] dirs = new int[][] { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
    String[] directions = new String[] { "u", "r", "d", "l" };
    Queue<Point> queue = new PriorityQueue<>(); // using priority queue
    queue.offer(new Point(ball[0], ball[1], 0, ""));
    while (!queue.isEmpty()) {
      Point p = queue.poll();
      if (points[p.x][p.y].compareTo(p) <= 0)
        continue; // if we have already found a route shorter
      points[p.x][p.y] = p;
      for (int i = 0; i < dirs.length; i++) {
        int x = p.x, y = p.y, l = p.l;
        while (x >= 0 && x < m && y >= 0 && y < n && maze[x][y] == 0 && (x != hole[0] || y != hole[1])) {
          x += dirs[i][0];
          y += dirs[i][1];
          l++;
        }
        if (x != hole[0] || y != hole[1]) { // check the hole
          x -= dirs[i][0];
          y -= dirs[i][1];
          l--;
        }
        queue.offer(new Point(x, y, l, p.s + directions[i]));
      }
    }
    return points[hole[0]][hole[1]].l == Integer.MAX_VALUE ? "impossible" : points[hole[0]][hole[1]].s;
  }

  public int shortestDistance2(int[][] maze, int[] start, int[] dest) {
    int[][] distance = new int[maze.length][maze[0].length];
    for (int[] row : distance)
      Arrays.fill(row, Integer.MAX_VALUE);
    distance[start[0]][start[1]] = 0;
    dijkstra(maze, start, distance);
    return distance[dest[0]][dest[1]] == Integer.MAX_VALUE ? -1 : distance[dest[0]][dest[1]];
  }

  public void dijkstra(int[][] maze, int[] start, int[][] distance) {
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
  }
}
