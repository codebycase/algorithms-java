package a18_the_honors_question;

import org.junit.Assert;

/**
 * Given a string s, find the longest palindromic substring in s. You may assume that the maximum
 * length of s is 1000.
 * 
 * Example:
 * 
 * Input: "babad"
 * 
 * Output: "bab"
 * 
 * Note: "aba" is also a valid answer. Example:
 * 
 * Input: "cbbd"
 * 
 * Output: "bb"
 * 
 * @author lchen
 *
 */
public class LongestPalindromicSubstring {
	private int start = 0;
	private int maxLen = 0;

	/**
	 * We observe that a palindrome mirrors around its center. Therefore, a palindrome can be
	 * expanded from its center, and there are only 2n - 1 such centers.
	 */
	public String longestPalindrome(String s) {
		if (s == null || s.length() < 2)
			return s;
		for (int i = 0; i < s.length() - 1; i++) {
			extendPalindrome(s, i, i); // between one, e.g. 'abcba'
			extendPalindrome(s, i, i + 1); // between two, e.g. 'abccba'
		}
		return s.substring(start, start + maxLen);
	}

	private void extendPalindrome(String s, int lo, int hi) {
		while (lo >= 0 && hi < s.length() && s.charAt(lo) == s.charAt(hi)) {
			lo--;
			hi++;
		}
		if (maxLen < hi - lo - 1) {
			start = lo + 1;
			maxLen = hi - lo - 1;
		}
	}

	/** Top bottom recursive method with memoization */
	public int longestPalindromeRecursion(String s) {
		return helper(s, 0, s.length() - 1, new Integer[s.length()][s.length()]);
	}

	private int helper(String s, int i, int j, Integer[][] memo) {
		if (i > j)
			return 0;
		if (i == j)
			return 1;
		if (memo[i][j] != null)
			return memo[i][j];

		if (s.charAt(i) == s.charAt(j))
			memo[i][j] = helper(s, i + 1, j - 1, memo) + 2;
		else
			memo[i][j] = Math.max(helper(s, i + 1, j, memo), helper(s, i, j - 1, memo));

		return memo[i][j];
	}

	/**
	 * Bottom up iterative method with cache too! <br>
	 * State transition: dp[i][j] = dp[i - 1][j + 1] + 2 if s.charAt(i) = s.charAt(j) <br>
	 * Otherwise, dp[i][j] = Math.max(dp[i - 1][j], dp[i][j + 1]
	 */
	public int longestPalindromeIteration(String s) {
		int[][] dp = new int[s.length()][s.length()];

		for (int i = 0; i < s.length(); i++) {
			dp[i][i] = 1;
			for (int j = i - 1; j >= 0; j--) {
				if (s.charAt(i) == s.charAt(j))
					dp[i][j] = dp[i - 1][j + 1] + 2;
				else
					dp[i][j] = Math.max(dp[i - 1][j], dp[i][j + 1]);
			}
		}

		return dp[s.length() - 1][0];
	}

	public static void main(String[] args) {
		LongestPalindromicSubstring solution = new LongestPalindromicSubstring();
		String s = "bbbab";
		Assert.assertTrue(solution.longestPalindrome(s).equals("bbb"));
		Assert.assertTrue(solution.longestPalindromeRecursion(s) == 3);
		Assert.assertTrue(solution.longestPalindromeIteration(s) == 3);
	}
}
