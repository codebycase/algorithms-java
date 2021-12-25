package a02_arrays_strings;

/**
 * 
 * Minimum Cost For Tickets
 * 
 * You have planned some train traveling one year in advance. The days of the year in which you will
 * travel are given as an integer array days. Each day is an integer from 1 to 365.
 * 
 * Train tickets are sold in three different ways:
 * 
 * a 1-day pass is sold for costs[0] dollars, a 7-day pass is sold for costs[1] dollars, and a
 * 30-day pass is sold for costs[2] dollars. The passes allow that many days of consecutive travel.
 * 
 * For example, if we get a 7-day pass on day 2, then we can travel for 7 days: 2, 3, 4, 5, 6, 7,
 * and 8. Return the minimum number of dollars you need to travel every day in the given list of
 * days.
 * 
 * <pre>
 * Example 1:
 *
 * Input: days = [1,4,6,7,8,20], costs = [2,7,15]
 * Output: 11
 * Explanation: For example, here is one way to buy passes that lets you travel your travel plan:
 * On day 1, you bought a 1-day pass for costs[0] = $2, which covered day 1.
 * On day 3, you bought a 7-day pass for costs[1] = $7, which covered days 3, 4, ..., 9.
 * On day 20, you bought a 1-day pass for costs[0] = $2, which covered day 20.
 * In total, you spent $11 and covered all the days of your travel.
 * </pre>
 * 
 * 
 * https://leetcode.com/problems/minimum-cost-for-tickets/
 *
 */
public class MinimumCostForTickets {

  public int mincostTickets(int[] days, int[] costs) {
    int[] durations = { 1, 7, 30 }; // 3 different ways
    return recursiveDP(days, costs, durations, 0, new int[days.length]);
  }

  public int recursiveDP(int[] days, int[] costs, int[] durations, int i, int[] memo) {
    if (i >= days.length) {
      return 0;
    }

    if (memo[i] != 0) {
      return memo[i];
    }

    int ans = Integer.MAX_VALUE, j = i;
    for (int d = 0; d < durations.length; d++) {
      while (j < days.length && days[j] < days[i] + durations[d]) {
        j++;
      }
      ans = Math.min(ans, recursiveDP(days, costs, durations, j, memo) + costs[d]);
    }

    memo[i] = ans;
    return ans;
  }
}
