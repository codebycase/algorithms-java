package a08_dynamic_programming;

import java.util.Arrays;

public class PaintHouse {
	public int minCost(int[][] costs) {
		if (costs == null || costs.length == 0 || costs[0].length == 0)
			return 0;
		int n = costs.length, k = costs[0].length;
		int[][] dp = new int[n][k];
		for (int i = 0; i < n; i++)
			Arrays.fill(dp[i], Integer.MAX_VALUE);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < k; j++) {
				if (i == 0) {
					dp[i][j] = costs[i][j];
				} else {
					for (int l = 0; l < k; l++) {
						if (l == j)
							continue;
						dp[i][j] = Math.min(dp[i][j], dp[i - 1][l] + costs[i][j]);
					}
				}
			}
		}
		return Arrays.stream(dp[n - 1]).min().getAsInt();
	}

	public int minCost2(int[][] costs) {
		if (costs == null || costs.length == 0)
			return 0;
		for (int i = 1; i < costs.length; i++) {
			costs[i][0] += Math.min(costs[i - 1][1], costs[i - 1][2]);
			costs[i][1] += Math.min(costs[i - 1][0], costs[i - 1][2]);
			costs[i][2] += Math.min(costs[i - 1][1], costs[i - 1][0]);
		}
		int n = costs.length - 1;
		return Math.min(Math.min(costs[n][0], costs[n][1]), costs[n][2]);
	}
}
