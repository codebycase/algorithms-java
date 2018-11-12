package a08_dynamic_programming;

public class BurstBalloons {
	public int maxCoins(int[] iNums) {
		int[] nums = new int[iNums.length + 2];
		int n = 1;
		for (int x : iNums) {
			if (x > 0) // remove zero balloons
				nums[n++] = x;
		}
		// add 2 imaged balloons!
		nums[0] = nums[n++] = 1;

		int[][] memo = new int[n][n];
		return burstBalloons(memo, nums, 0, n - 1);
	}

	public int burstBalloons(int[][] memo, int[] nums, int left, int right) {
		if (memo[left][right] > 0)
			return memo[left][right];
		int max = 0;
		for (int i = left + 1; i < right; i++) {
			int coins = nums[left] * nums[i] * nums[right];
			max = Math.max(max, burstBalloons(memo, nums, left, i) + coins + burstBalloons(memo, nums, i, right));
		}
		memo[left][right] = max;
		return max;
	}

	public int maxCoins2(int[] iNums) {
		int[] nums = new int[iNums.length + 2];
		int n = 1;
		for (int x : iNums) {
			if (x > 0)
				nums[n++] = x;
		}
		nums[0] = nums[n++] = 1;

		int[][] dp = new int[n][n];
		for (int k = 2; k < n; k++)
			for (int left = 0; left < n - k; left++) {
				int right = left + k;
				for (int i = left + 1; i < right; ++i) {
					int coins = nums[left] * nums[i] * nums[right];
					dp[left][right] = Math.max(dp[left][right], dp[left][i] + coins + dp[i][right]);
				}
			}

		return dp[0][n - 1];
	}
}
