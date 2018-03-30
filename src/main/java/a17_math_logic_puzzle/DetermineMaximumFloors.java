package a17_math_logic_puzzle;

import java.util.Arrays;

public class DetermineMaximumFloors {
	public static int getMaxFloors(int eggs, int drops) {
		int[][] dp = new int[eggs + 1][drops + 1];
		for (int i = 0; i < eggs + 1; i++) {
			Arrays.fill(dp[i], -1);
		}
		return getMaxFloors(eggs, drops, dp);
	}

	private static int getMaxFloors(int eggs, int drops, int[][] dp) {
		if (eggs == 0 || drops == 0)
			return 0;
		else if (eggs == 1)
			return drops;
		if (dp[eggs][drops] == -1) {
			dp[eggs][drops] = getMaxFloors(eggs, drops - 1, dp) + getMaxFloors(eggs - 1, drops - 1, dp) + 1;
		}
		return dp[eggs][drops];
	}

	public static void main(String[] args) {
		System.out.println(getMaxFloors(2, 5));
	}
}
