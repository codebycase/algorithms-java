package a18_the_honors_question;

import java.util.Stack;

public class LongestValidParentheses {
	/**
	 * We make use of two counters left and right, and traverse from left to right and right to
	 * left. Keep tracking the length of the current valid string.
	 */
	public static int longestValidParentheses(String s) {
		int left = 0, right = 0, maxLen = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '(') {
				left++;
			} else {
				right++;
			}
			if (left == right) {
				maxLen = Math.max(maxLen, 2 * right);
			} else if (right >= left) {
				left = right = 0;
			}
		}
		left = right = 0;
		for (int i = s.length() - 1; i >= 0; i--) {
			if (s.charAt(i) == '(') {
				left++;
			} else {
				right++;
			}
			if (left == right) {
				maxLen = Math.max(maxLen, 2 * left);
			} else if (left >= right) {
				left = right = 0;
			}
		}
		return maxLen;
	}

	/**
	 * We can make use of stack while scanning the given string to check if the string scanned so
	 * far is valid, and also the length of the longest valid string.
	 */
	public static int longestValidParentheses2(String s) {
		int maxLen = 0;
		Stack<Integer> stack = new Stack<>();
		stack.push(-1); // push -1 in favor of easy coding
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '(')
				stack.push(i);
			else {
				stack.pop();
				if (stack.isEmpty()) {
					stack.push(i);
				} else {
					maxLen = Math.max(maxLen, i - stack.peek());
				}
			}
		}
		return maxLen;
	}

	/**
	 * We make use of a dp array where ith element of dp represents the length of the longest valid
	 * substring ending at ith index.
	 */
	public static int longestValidParentheses3(String s) {
		int maxLen = 0;
		int[] dp = new int[s.length()];
		for (int i = 1; i < s.length(); i++) {
			if (s.charAt(i) == ')') {
				if (s.charAt(i - 1) == '(') {
					// s[i] = ')' and s[i-1] = '('
					dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
				} else {
					// s[i] = ')' and s[i-1] = ')'
					int j = i - dp[i - 1];
					if (j > 0 && s.charAt(j - 1) == '(') {
						dp[i] = dp[i - 1] + ((j >= 2 ? dp[j - 2] : 0)) + 2;
					}
				}
				maxLen = Math.max(maxLen, dp[i]);
			}
		}
		return maxLen;
	}

	public static void main(String[] args) {
		String s = ")()())";
		assert longestValidParentheses(s) == 4;
		assert longestValidParentheses2(s) == 4;
		assert longestValidParentheses3(s) == 4;
	}
}