package a18_the_honors_question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class RobotRoomCleaner {
  interface Robot {
    // returns true if next cell is open and robot moves into the cell.
    // returns false if next cell is obstacle and robot stays on the current cell.
    boolean move();

    // Robot will stay on the same cell after calling turnLeft/turnRight.
    // Each turn will be 90 degrees.
    void turnLeft();

    void turnRight();

    // Clean the current cell.
    void clean();
  }

  public void cleanRoom(Robot robot) {
    Set<String> visited = new HashSet<>();
    // clockwise 4 directions: up, right, down, left
    int[][] dirs = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
    cleanRoomBacktrack(robot, visited, dirs, 0, 0, 0);
  }

  // Spiral Backtracking, Time Complexity: 4 * (N - M)
  public void cleanRoomBacktrack(Robot robot, Set<String> visited, int[][] dirs, int row, int col, int dir) {
    robot.clean();
    visited.add(row + "," + col);

    // robot can go four directions
    for (int i = 0; i < dirs.length; ++i) {
      // use new variables for next cell!!!
      int newDir = (dir + i) % 4;
      int newRow = row + dirs[newDir][0];
      int newCol = col + dirs[newDir][1];

      if (!visited.contains(newRow + "," + newCol) && robot.move()) {
        cleanRoomBacktrack(robot, visited, dirs, newRow, newCol, newDir);
        robot.turnRight();
        robot.turnRight();
        robot.move();
        robot.turnRight();
        robot.turnRight();
      }

      // turn to next direction
      robot.turnRight();
    }
  }

  /**
   * The key insight is to iterate over the ratio. Let's say we hire workers with a ratio R or lower.
   * Then, we would want to know the K workers with the lowest quality, and the sum of that quality.
   */
  public double mincostToHireWorkers(int[] quality, int[] wage, int K) {
    int n = quality.length;
    Worker[] workers = new Worker[n];
    for (int i = 0; i < n; i++) {
      workers[i] = new Worker(quality[i], wage[i]);
    }
    Arrays.sort(workers);

    double minCost = Double.MAX_VALUE;
    int qualitySum = 0;
    Queue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());
    for (Worker worker : workers) {
      queue.offer(worker.quality);
      qualitySum += worker.quality;
      if (queue.size() > K)
        qualitySum -= queue.poll();
      if (queue.size() == K) {
        minCost = Math.min(minCost, qualitySum * worker.ratio());
      }
    }

    return minCost;
  }

  class Worker implements Comparable<Worker> {
    public int quality, wage;

    public Worker(int quality, int wage) {
      this.quality = quality;
      this.wage = wage;
    }

    public double ratio() {
      return (double) wage / quality;
    }

    public int compareTo(Worker other) {
      return Double.compare(ratio(), other.ratio());
    }
  }

  public void findSecretWord(String[] wordlist, Master master) {
    for (int i = 0; i < 10; i++) {
      String guess = wordlist[new Random().nextInt(wordlist.length)];
      int x = master.guess(guess);
      List<String> wordlist2 = new ArrayList<>();
      for (String w : wordlist)
        if (matchedLetters(guess, w) == x)
          wordlist2.add(w);
      wordlist = wordlist2.toArray(new String[wordlist2.size()]);
    }
  }

  private int matchedLetters(String a, String b) {
    int matches = 0;
    for (int i = 0; i < a.length(); ++i)
      if (a.charAt(i) == b.charAt(i))
        matches++;
    return matches;
  }

  interface Master {
    int guess(String word);
  }

  private static final int[][] dirs = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

  public int[] hitBricks(int[][] grid, int[][] hits) {
    int n = grid[0].length;
    // remove all hit bricks
    for (int i = 0; i < hits.length; i++) {
      grid[hits[i][0]][hits[i][1]] -= 1;
    }
    // dfs from roof, set all cells to 2 so that we know these cells have been visited
    for (int c = 0; c < n; c++) {
      if (grid[0][c] == 1)
        hitBricksDfs(grid, 0, c);
    }
    int[] ans = new int[hits.length];
    // iterate from last hit to first
    for (int i = hits.length - 1; i >= 0; i--) {
      int r = hits[i][0];
      int c = hits[i][1];
      grid[r][c] += 1; // put brick back
      // if the cell is attathed to the roof (or any cell with value 2)
      ans[i] = grid[r][c] == 1 && isConnectedTop(grid, r, c) ? hitBricksDfs(grid, r, c) - 1 : 0;
    }

    return ans;
  }

  private boolean isConnectedTop(int[][] grid, int r, int c) {
    if (r == 0)
      return true;
    for (int[] dir : dirs) {
      int x = r + dir[0], y = c + dir[1];
      if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length)
        continue;
      if (grid[x][y] == 2)
        return true;
    }
    return false;
  }

  private int hitBricksDfs(int[][] grid, int r, int c) {
    grid[r][c] = 2;
    int size = 1;
    for (int[] dir : dirs) {
      int x = r + dir[0], y = c + dir[1];
      if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] != 1)
        continue;
      size += hitBricksDfs(grid, x, y);
    }

    return size;
  }
}
