package a08_dynamic_programming;

/**
 * 
 * There is a row of m houses in a small city, each house must be painted with one of the n colors
 * (labeled from 1 to n), some houses that have been painted last summer should not be painted
 * again.
 * 
 * A neighborhood is a maximal group of continuous houses that are painted with the same color.
 * 
 * For example: houses = [1,2,2,3,3,2,1,1] contains 5 neighborhoods [{1}, {2,2}, {3,3}, {2}, {1,1}].
 * 
 * Given an array houses, an m x n matrix cost and an integer target where:
 * 
 * houses[i]: is the color of the house i, and 0 if the house is not painted yet. <br>
 * cost[i][j]: is the cost of paint the house i with the color j + 1. <br>
 * Return the minimum cost of painting all the remaining houses in such a way that there are exactly
 * target neighborhoods. If it is not possible, return -1. <br>
 * 
 * <pre>
 * Example 1:
 *
 * Input: houses = [0,0,0,0,0], cost = [[1,10],[10,1],[10,1],[1,10],[5,1]], m = 5, n = 2, target = 3
 * Output: 9
 * Explanation: Paint houses of this way [1,2,2,1,1]
 * This array contains target = 3 neighborhoods, [{1}, {2,2}, {1,1}].
 * Cost of paint all houses (1 + 1 + 1 + 1 + 5) = 9.
 * </pre>
 * 
 * https://leetcode.com/problems/paint-house-iii/
 */
public class PaintHouseIII {
  public int minCost(int[] houses, int[][] costs, int target) {
    int[][][] memo = new int[costs.length][target + 1][costs[0].length + 1];
    return minCost(houses, costs, 0, -1, target, memo);
  }

  public int minCost(int[] houses, int[][] costs, int currentHouse, int prevColor, int target, int[][][] memo) {
    if (currentHouse >= houses.length)
      return target == 0 ? 0 : -1;
    if (target < 0)
      return -1;
    if (prevColor != -1 && memo[currentHouse][target][prevColor] != 0) {
      return memo[currentHouse][target][prevColor];
    }

    int minCost = -1;
    int currentColor = houses[currentHouse];
    if (currentColor == 0) {
      // Try out all different colors
      for (int chosenColor = 1; chosenColor <= costs[currentHouse].length; chosenColor++) {
        int nextCost = minCost(houses, costs, currentHouse + 1, chosenColor, target - (chosenColor == prevColor ? 0 : 1), memo);
        // If chosenColor can reach target eventually
        if (nextCost != -1) {
          nextCost = (currentColor != 0 ? 0 : costs[currentHouse][chosenColor - 1]) + nextCost;
          minCost = minCost == -1 ? nextCost : Math.min(minCost, nextCost);
        }
      }
    } else {
      int nextCost = minCost(houses, costs, currentHouse + 1, currentColor, target - (currentColor == prevColor ? 0 : 1), memo);
      minCost = minCost == -1 ? nextCost : Math.min(minCost, nextCost);
    }
    if (prevColor != -1) {
      memo[currentHouse][target][prevColor] = minCost;
    }

    return minCost;
  }

  public static void main(String[] args) {
    PaintHouseIII solution = new PaintHouseIII();
    int[] houses = { 0, 0, 0, 0, 0 };
    int[][] costs = { { 1, 10 }, { 10, 1 }, { 10, 1 }, { 1, 10 }, { 5, 1 } };
    System.out.println(solution.minCost(houses, costs, 3));
    houses = new int[] { 0, 2, 1, 2, 0 };
    costs = new int[][] { { 1, 10 }, { 10, 1 }, { 10, 1 }, { 1, 10 }, { 5, 1 } };
    System.out.println(solution.minCost(houses, costs, 3));
  }
}
