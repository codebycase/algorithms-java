package c08_dynamic_programming;

import java.util.List;

public class CoinQuestions {
	/**
	 * <pre>
	You are given coins of different denominations and a total amount of money. 
	Write a function to compute the number of combinations that make up that amount. 
	You may assume that you have infinite number of each kind of coin.
	
	Example 1:
	Input: amount = 5, coins = [1, 2, 5]
	Output: 4
	Explanation: there are four ways to make up the amount:
	5=5
	5=2+2+1
	5=2+1+1+1
	5=1+1+1+1+1
	
	Example 2:
	Input: amount = 3, coins = [2]
	Output: 0
	Explanation: the amount of 3 cannot be made up just with coins of 2.
	
	Example 3:
	Input: amount = 10, coins = [10] 
	Output: 1
	 * </pre>
	 * 
	 * @param coins
	 * @param amount
	 * @return
	 */
	// dp[i][j]: the number of combinations to make up amount j by using the first i types of coins.
	// dp[i][j] only rely on dp[i-1][j] and dp[i][j-coins[i]], we can just using one-dimension
	// array.
	public int coinChangeCombinations(int[] coins, int amount) {
		int[] dp = new int[amount + 1];
		dp[0] = 1;
		for (int coin : coins) {
			for (int i = 1; i <= amount; i++) {
				if (i >= coin)
					dp[i] += dp[i - coin];
			}
		}
		return dp[amount];
	}

	/**
	 * <pre>
	You are given coins of different denominations and a total amount of money amount. 
	Write a function to compute the fewest number of coins that you need to make up that amount. 
	If that amount of money cannot be made up by any combination of the coins, return -1.
	
	Example 1:
	coins = [1, 2, 5], amount = 11
	return 3 (11 = 5 + 5 + 1)
	
	Example 2:
	coins = [2], amount = 3
	return -1.
	 * </pre>
	 * 
	 * @param coins
	 * @param amount
	 * @return
	 */
	public int coinChangeFewestCoins(int[] coins, int amount) {
		if (amount < 1)
			return 0;
		return coinChangeFewestCoins(coins, amount, new int[amount + 1]);
	}

	private int coinChangeFewestCoins(int[] coins, int remain, int[] counts) {
		if (remain < 0)
			return -1;
		if (remain == 0)
			return 0;
		if (counts[remain] != 0)
			return counts[remain];
		int min = Integer.MAX_VALUE;
		for (int coin : coins) {
			int count = coinChangeFewestCoins(coins, remain - coin, counts);
			if (count >= 0)
				min = Math.min(min, count + 1);
		}
		counts[remain] = (min == Integer.MAX_VALUE) ? -1 : min;
		return counts[remain];
	}

	public int coinChangeFewestCoins2(int[] coins, int amount) {
		if (amount < 1)
			return 0;
		int[] dp = new int[amount + 1];
		int sum = 0;

		while (++sum <= amount) {
			int min = -1;
			for (int coin : coins) {
				if (sum >= coin && dp[sum - coin] != -1) {
					int temp = dp[sum - coin] + 1;
					min = min < 0 ? temp : (temp < min ? temp : min);
				}
			}
			dp[sum] = min;
		}
		return dp[amount];
	}

	/**
	 * Pick up coins for maximum gain <br>
	 * Two players take turns at choosing one coin each, they can only choose from the 2 ends.
	 * 
	 * @param coins
	 * @return
	 */
	public static int pickUpCoins(List<Integer> coins) {
		return computeMaximum(coins, 0, coins.size() - 1, new int[coins.size()][coins.size()]);
	}

	private static int computeMaximum(List<Integer> coins, int a, int b, int[][] maximumRevenue) {
		if (a > b) {
			// No coins left.
			return 0;
		}
		if (maximumRevenue[a][b] == 0) {
			int maximumRevenueA = coins.get(a) + Math.min(computeMaximum(coins, a + 2, b, maximumRevenue),
					computeMaximum(coins, a + 1, b - 1, maximumRevenue));
			int maximumRevenueB = coins.get(b) + Math.min(computeMaximum(coins, a + 1, b - 1, maximumRevenue),
					computeMaximum(coins, a, b - 2, maximumRevenue));
			maximumRevenue[a][b] = Math.max(maximumRevenueA, maximumRevenueB);
		}
		return maximumRevenue[a][b];
	}

	public static void main(String[] args) {
		CoinQuestions coinQuestions = new CoinQuestions();
		System.out.println(coinQuestions.coinChangeCombinations(new int[] { 1, 2, 5 }, 5));
	}

}
