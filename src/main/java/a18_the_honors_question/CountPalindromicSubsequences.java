package a18_the_honors_question;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Given a string S, find the number of different non-empty palindromic subsequences in S, and
 * return that number modulo 10^9 + 7.
 * 
 * A subsequence of a string S is obtained by deleting 0 or more characters from S.
 * 
 * A sequence is palindromic if it is equal to the sequence reversed.
 * 
 * Two sequences A_1, A_2, ... and B_1, B_2, ... are different if there is some i for which A_i !=
 * B_i.
 * 
 * <pre>
Example 1:
Input: 
S = 'bccb'
Output: 6
Explanation: 
The 6 different non-empty palindromic subsequences are 'b', 'c', 'bb', 'cc', 'bcb', 'bccb'.
Note that 'bcb' is counted only once, even though it occurs twice.
 * </pre>
 * 
 * @author lchen
 *
 */
public class CountPalindromicSubsequences {
	int div = 1000000007;

	public int countPalindromicSubsequences2(String S) {
		List<TreeSet<Integer>> characters = new ArrayList<>(26);
		int len = S.length();

		for (int i = 0; i < 26; i++) {
			characters.add(new TreeSet<>());
		}

		for (int i = 0; i < len; i++) {
			characters.get(S.charAt(i) - 'a').add(i);
		}

		Integer[][] dp = new Integer[len + 1][len + 1];
		return memo(S, characters, dp, 0, len);
	}

	public int memo(String S, List<TreeSet<Integer>> characters, Integer[][] dp, int start, int end) {
		if (start >= end)
			return 0;

		if (dp[start][end] != null)
			return dp[start][end];

		long ans = 0;
		for (int i = 0; i < 26; i++) {
			Integer new_start = characters.get(i).ceiling(start);
			Integer new_end = characters.get(i).lower(end);
			if (new_start == null || new_start >= end)
				continue;
			ans++;
			if (new_start != new_end)
				ans++;
			ans += memo(S, characters, dp, new_start + 1, new_end);

		}

		dp[start][end] = (int) (ans % div);
		return dp[start][end];
	}

	public int countPalindromicSubsequences(String S) {
		int n = S.length();
		int mod = 1000000007;
		int[][][] dp = new int[4][n][n];

		for (int i = n - 1; i >= 0; i--) {
			for (int j = i; j < n; j++) {
				for (int k = 0; k < 4; k++) {
					char c = (char) ('a' + k);
					if (i == j) {
						dp[k][i][j] = S.charAt(i) == c ? 1 : 0;
					} else { // j > i
						if (S.charAt(i) != c)
							dp[k][i][j] = dp[k][i + 1][j];
						else if (S.charAt(j) != c)
							dp[k][i][j] = dp[k][i][j - 1];
						else {
							dp[k][i][j] = 2;
							if (j != i + 1) {
								for (int m = 0; m < 4; m++) { // count each one within subwindows [i+1][j-1]
									dp[k][i][j] += dp[m][i + 1][j - 1];
									dp[k][i][j] %= mod;
								}
							}
						}
					}
				}
			}
		}

		int ans = 0;
		for (int k = 0; k < 4; k++) {
			ans += dp[k][0][n - 1];
			ans %= mod;
		}

		return ans;

	}
}
