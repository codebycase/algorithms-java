package a18_the_honors_question;

/**
 * <pre>
Implement regular expression matching with support for '.' and '*'.

'.' Matches any single character.
'*' Matches zero or more of the preceding element.

The matching should cover the entire input string (not partial).

The function prototype should be:
bool isMatch(const char *s, const char *p)

Some examples:
isMatch("aa","a") → false
isMatch("aa","aa") → true
isMatch("aaa","aa") → false
isMatch("aa", "a*") → true
isMatch("aa", ".*") → true
isMatch("ab", ".*") → true
isMatch("aab", "c*a*b") → true
 * </pre>
 * 
 * https://raw.githubusercontent.com/hot13399/leetcode-graphic-answer/master/10.%20Regular%20Expression%20Matching.jpg
 * 
 * @author lchen
 *
 */
// The actual case is using Finite Automata (State Machine) : Thompson NFA
public class RegularExpressionMatching {

	// Top Down DP with Recursion
	public static boolean regExpMatchTopDown(String text, String pattern) {
		Boolean[][] memo = new Boolean[text.length() + 1][pattern.length() + 1];
		return regExpMathRecursion(0, 0, text, pattern, memo);
	}

	private static boolean regExpMathRecursion(int i, int j, String text, String pattern, Boolean[][] memo) {
		if (memo[i][j] != null)
			return memo[i][j];

		// base case, reached the end
		if (j == pattern.length())
			return i == text.length();

		boolean firstMatch = i < text.length() && (pattern.charAt(j) == text.charAt(i) || pattern.charAt(j) == '.');
		if (j + 1 < pattern.length() && pattern.charAt(j + 1) == '*') {
			// skip 2 chars in pattern (ignore) or skip one char in text!
			memo[i][j] = regExpMathRecursion(i, j + 2, text, pattern, memo) || (firstMatch && regExpMathRecursion(i + 1, j, text, pattern, memo));
		} else {
			// skip a char for both pattern and text!
			memo[i][j] = firstMatch && regExpMathRecursion(i + 1, j + 1, text, pattern, memo);
		}

		return memo[i][j];
	}

	// Bottom Up DP with Iteration
	public static boolean regExpMatchBottomUp(String text, String pattern) {
		boolean[][] dp = new boolean[text.length() + 1][pattern.length() + 1];
		dp[text.length()][pattern.length()] = true;

		for (int i = text.length(); i >= 0; i--) {
			for (int j = pattern.length() - 1; j >= 0; j--) {
				boolean firstMatch = i < text.length() && (pattern.charAt(j) == text.charAt(i) || pattern.charAt(j) == '.');
				if (j + 1 < pattern.length() && pattern.charAt(j + 1) == '*') {
					dp[i][j] = dp[i][j + 2] || (firstMatch && dp[i + 1][j]);
				} else {
					dp[i][j] = firstMatch && dp[i + 1][j + 1];
				}
			}
		}
		return dp[0][0];
	}

	public static void main(String[] args) {
		assert regExpMatchTopDown("aa", "a*") == true;
		assert regExpMatchTopDown("ab", ".*") == true;
		assert regExpMatchTopDown("aab", "c*a*b") == true;
		assert regExpMatchBottomUp("aa", "a*") == true;
		assert regExpMatchBottomUp("ab", ".*") == true;
		assert regExpMatchBottomUp("aab", "c*a*b") == true;
	}

}
