package c08_dynamic_programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.junit.Assert;

import util.Point;

/**
 * <ul>
 * <li>When DP is implemented recursively the cache is typically a dynamic data structure such as a
 * hash table or a BST; when it is implemented iteratively the cache is usually a one- or
 * multidimensional array.</li>
 * <li>All recursive algorithms can be implemented iteratively, although sometimes the code to do so
 * is much more complex. Each recursive call adds a new layer to the stack, which means that if your
 * algorithm recurses to a depth of n, it uses at least O(n) memory.</li>
 * <li>Dynamic programming is mostly just a matter of taking a recursive algorithm and finding the
 * overlapping subproblems (that is, the repeated calls). You then cache those results for future
 * recursive calls.</li>
 * <li>The best algorithms for performing aggregation queries on log file data are often streaming
 * algorithms.</li>
 * </ul>
 * 
 * @author lchen
 *
 */
public class DynamicProgramingCollection {

	/**
	 * You are climbing a stair case. It takes n steps to reach to the top.
	 * 
	 * Each time you can climb 1 to k steps. In how many distinct ways can you climb to the top?
	 * 
	 * Time complexity is O(kn), the space complexity is O(n)
	 * 
	 * @param n
	 * @param k
	 * @return
	 */
	public static int climbStairs(int n, int k) {
		return climbStairs(n, k, new int[n + 1]);
	}

	private static int climbStairs(int n, int k, int[] memo) {
		if (n < 0)
			return 0;
		else if (n == 0)
			return 1; // use one or zero?
		else if (n == 1)
			return 1;

		if (memo[n] == 0) {
			// for (int i = 1; i <= k; i++) {
			// use n - i >= 0 to reduce call times!
			for (int i = 1; i <= k && n - i >= 0; i++) {
				memo[n] += climbStairs(n - i, k, memo);
			}
		}

		return memo[n];
	}

	/**
	 * Given a non-empty array containing only positive integers, find if the array can be
	 * partitioned into two subsets such that the sum of elements in both subsets is equal.
	 * 
	 * Note: Each of the array element will not exceed 100. The array size will not exceed 200.
	 * Example 1:
	 * 
	 * Input: [1, 5, 11, 5]
	 * 
	 * Output: true
	 * 
	 * Base case: dp[0][0] is true; (zero number consists of sum 0 is true)
	 * 
	 * Transition function: For each number, if we don't pick it, dp[i][j] = dp[i-1][j], which means
	 * if the first i-1 elements has made it to j, dp[i][j] would also make it to j (we can just
	 * ignore nums[i]). If we pick nums[i]. dp[i][j] = dp[i-1][j-nums[i]], which represents that j
	 * is composed of the current value nums[i] and the remaining composed of other previous
	 * numbers. <br>
	 * Thus, the transition function is dp[i][j] = dp[i-1][j] || dp[i-1][j-nums[i]]
	 * 
	 * @author lchen
	 *
	 *
	 */
	public boolean canPartitionArray(int[] nums) {
		int subgroups = 2;
		int sum = Arrays.stream(nums).sum();

		if (sum % subgroups != 0)
			return false;

		int target = sum / subgroups;
		boolean[] dp = new boolean[target + 1];
		dp[0] = true;

		for (int num : nums) {
			for (int i = target; i >= num; i--) {
				dp[i] = dp[i] || dp[i - num]; // not pick it or pick it!
			}
			// System.out.println(num + ": " + Arrays.toString(dp));
		}

		return dp[target];
	}

	/**
	 * Write a program that takes a final score and scores for individual plays, and returns the
	 * number of combinations of plays that result in the final score.
	 * 
	 * @author lchen
	 * 
	 */
	public static int combinationsForFinalScore(int finalScore, List<Integer> playScores) {
		int[] dp = new int[finalScore + 1];
		dp[0] = 1; // One way to reach 0.

		for (int i = 0; i < playScores.size(); ++i) {
			for (int j = 1; j <= finalScore; ++j) {
				int withoutThisPlay = i - 1 >= 0 ? dp[j] : 0;
				int withThisPlay = j >= playScores.get(i) ? dp[j - playScores.get(i)] : 0;
				dp[j] = withoutThisPlay + withThisPlay;
			}
		}

		return dp[finalScore];
	}

	/**
	 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram
	 * below).
	 * 
	 * The robot can only move either down or right at any point in time. The robot is trying to
	 * reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).
	 * 
	 * How many possible unique paths are there?
	 * 
	 * @param grid
	 * @return
	 */
	public int findHowManyUniquePathsInGrid(int m, int n) {
		int[] dp = new int[n];
		dp[0] = 1;
		for (int i = 0; i < m; i++) {
			for (int j = 1; j < n; j++) {
				dp[j] += dp[j - 1];
			}
		}
		return dp[n - 1];
	}

	// use traditional 2 dimensional array
	public int findHowManyUniquePathsInGrid2(int m, int n) {
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
	public int findHowManyUniquePathsInGridWithObstacles(int[][] obstacleGrid) {
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
	 * Design an algorithm to find a path in a Maze/Grid
	 * 
	 * @param maze
	 * @return
	 */
	public ArrayList<Point> findOnePathInGrid(boolean[][] maze) {
		if (maze == null || maze.length == 0)
			return null;
		ArrayList<Point> path = new ArrayList<Point>();
		HashSet<Point> failedPoints = new HashSet<Point>();
		if (findOnePathInGrid(maze, maze.length - 1, maze[0].length - 1, path, failedPoints))
			return path;
		return null;
	}

	private boolean findOnePathInGrid(boolean[][] maze, int row, int col, ArrayList<Point> path,
			HashSet<Point> failedPoints) {
		if (col < 0 || row < 0 || !maze[row][col]) // Out of bounds or not available
			return false;
		Point p = new Point(row, col);
		if (failedPoints.contains(p)) // Already visited this cell
			return false;
		boolean isAtOrigin = (row == 0) && (col == 0);
		// If there's a path from the start to my current location, add my location.
		if (isAtOrigin || findOnePathInGrid(maze, row, col - 1, path, failedPoints)
				|| findOnePathInGrid(maze, row - 1, col, path, failedPoints)) {
			path.add(p);
			return true;
		}
		failedPoints.add(p); // Cache result
		return false;
	}

	/**
	 * There is a ball in a maze with empty spaces and walls. The ball can go through empty spaces
	 * by rolling up, down, left or right, but it won't stop rolling until hitting a wall. When the
	 * ball stops, it could choose the next direction.
	 * 
	 * Given the ball's start position, the destination and the maze, determine whether the ball
	 * could stop at the destination.
	 * 
	 * The maze is represented by a binary 2D array. 1 means the wall and 0 means the empty space.
	 * You may assume that the borders of the maze are all walls. The start and destination
	 * coordinates are represented by row and column indexes.
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
	public boolean hasPathInMaze(int[][] maze, int[] start, int[] destination) {
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

	/**
	 * Find the shortest distance for the ball to stop at the destination. The distance is defined
	 * by the number of empty spaces traveled by the ball from the start position (excluded) to the
	 * destination (included). If the ball cannot stop at the destination, return -1.
	 * 
	 * Use Dijkstra Algorithm with PriorityQueue to track which is the unvisited node at the
	 * shortest distance from the start node.
	 * 
	 * Time complexity: O(mn*log(mn)); Space complexity: O(mn)
	 */
	public int shortestDistanceInMaze(int[][] maze, int[] start, int[] destination) {
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
				x -= dir[0];
				y -= dir[1];
				l--;
				queue.offer(new int[] { x, y, l });
			}
		}

		return lens[destination[0]][destination[1]] == Integer.MAX_VALUE ? -1 : lens[destination[0]][destination[1]];
	}

	/**
	 * Given an unsorted array of integers, find the number of longest increasing subsequence.
	 * 
	 * <pre>
	Example 1:
	Input: [1,3,5,4,7]
	Output: 2
	Explanation: The two longest increasing subsequence are [1, 3, 4, 7] and [1, 3, 5, 7].
	
	Example 2:
	Input: [2,2,2,2,2]
	Output: 5
	Explanation: The length of longest continuous increasing subsequence is 1, 
	and there are 5 subsequences' length is 1, so output 5.
	 * </pre>
	 * 
	 * The idea is to use two arrays len[n] and cnt[n] to record the maximum length of Increasing
	 * Subsequence and the coresponding number of these sequence which ends with nums[i],
	 * respectively. O(n2) complexity.
	 * 
	 * @param nums
	 * @return
	 */
	public int findNumberOfLongestIncreasingSequence(int[] nums) {
		int result = 0, maxLen = 0;
		int[] lengths = new int[nums.length];
		int[] counts = new int[nums.length];
		for (int i = 0; i < nums.length; i++) {
			lengths[i] = counts[i] = 1;
			for (int j = 0; j < i; j++) {
				if (nums[i] > nums[j]) {
					int newLen = lengths[j] + 1;
					if (lengths[i] == newLen)
						counts[i] += counts[j];
					else if (lengths[i] < newLen) {
						lengths[i] = newLen;
						counts[i] = counts[j];
					}
				}
			}
			if (maxLen == lengths[i])
				result += counts[i];
			else if (maxLen < lengths[i]) {
				maxLen = lengths[i];
				result = counts[i];
			}
		}
		return result;
	}

	/**
	 * Given two words word1 and word2, find the minimum number of steps required to convert word1
	 * to word2. (each operation is counted as 1 step.)
	 * 
	 * You have the following 3 operations permitted on a word:
	 * 
	 * a) Insert a character b) Delete a character c) Replace a character
	 * 
	 * @author lchen
	 *
	 */
	public int minEditDistance(String s, String t) {
		int[][] distances = new int[s.length()][t.length()];
		for (int[] row : distances)
			Arrays.fill(row, -1);
		return computeEditDistance(s, s.length() - 1, t, t.length() - 1, distances);
	}

	private int computeEditDistance(String w1, int i, String w2, int j, int[][] distances) {
		if (i < 0)
			return j + 1;
		else if (j < 0)
			return i + 1;
		if (distances[i][j] == -1) {
			if (w1.charAt(i) == w2.charAt(j)) {
				distances[i][j] = computeEditDistance(w1, i - 1, w2, j - 1, distances);
			} else {
				int insert = computeEditDistance(w1, i, w2, j - 1, distances);
				int delete = computeEditDistance(w1, i - 1, w2, j, distances);
				int replace = computeEditDistance(w1, i - 1, w2, j - 1, distances);
				distances[i][j] = 1 + Math.min(insert, Math.min(delete, replace));
			}
		}
		return distances[i][j];
	}

	/**
	 *
	 * Given a triangle, find the minimum path sum from top to bottom. Each step you may move to
	 * adjacent numbers on the row below.
	 * 
	 * <pre>
	For example, given the following triangle
	[
	     [2],
	    [3,4],
	   [6,5,7],
	  [4,1,8,3]
	]
	The minimum path sum from top to bottom is 11 (i.e., 2 + 3 + 5 + 1 = 11).
	 * </pre>
	 * 
	 * Note: Bonus point if you are able to do this using only O(n) extra space, where n is the
	 * total number of rows in the triangle.
	 * 
	 * @author lchen
	 *
	 */
	public int minimumTotalInTriangle(List<List<Integer>> triangle) {
		int[] minLens = new int[triangle.size() + 1];
		for (int layer = triangle.size() - 1; layer >= 0; layer--) {
			for (int i = 0; i < triangle.get(layer).size(); i++) {
				minLens[i] = Math.min(minLens[i], minLens[i + 1]) + triangle.get(layer).get(i);
			}
		}
		return minLens[0];
	}

	public static void main(String[] args) {
		DynamicProgramingCollection solution = new DynamicProgramingCollection();
		Assert.assertEquals(climbStairs(4, 2), 5);
		Assert.assertEquals(climbStairs(10, 5), 464);
		List<Integer> playScores = Arrays.asList(2, 3, 7);
		Assert.assertTrue(4 == combinationsForFinalScore(12, playScores));
		Assert.assertTrue(1 == combinationsForFinalScore(5, playScores));
		Assert.assertTrue(solution.canPartitionArray(new int[] { 1, 5, 11, 5 }));

	}

}
