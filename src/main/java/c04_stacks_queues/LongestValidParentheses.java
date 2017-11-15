package c04_stacks_queues;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * Given a string containing just the characters '(' and ')', find the length of the longest valid
 * (well-formed) parentheses substring.
 * 
 * For "(()", the longest valid parentheses substring is "()", which has length = 2.
 * 
 * Another example is ")()())", where the longest valid parentheses substring is "()()", which has
 * length = 4.
 * 
 * @author lchen
 *
 */
public class LongestValidParentheses {
	public boolean isValidParentheses(String s) {
		Deque<Character> stack = new ArrayDeque<Character>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '(')
				stack.push(c);
			else if (c == ')') {
				if (stack.isEmpty())
					return false;
				stack.pop();
			}
		}
		return stack.isEmpty();
	}

	/**
	 * Complexity Analysis
	 * 
	 * Time complexity : O(n^3). Generating every possible substring from a string of length nn
	 * requires O(n^2). Checking validity of a string of length n requires O(n).
	 * 
	 * Space complexity : O(n). A stack of depth nn will be required for the longest substring.
	 * 
	 * @param s
	 * @return
	 */
	public int longestValidParenthese(String s) {
		int maxlen = 0;
		for (int i = 0; i < s.length(); i++) {
			for (int j = i + 2; j <= s.length(); j += 2) {
				if (isValidParentheses(s.substring(i, j))) {
					maxlen = Math.max(maxlen, j - i);
				}
			}
		}
		return maxlen;
	}

	/**
	 * Complexity Analysis
	 * 
	 * Time complexity : O(n). n is the length of the given string.. s Space complexity : O(n). The
	 * size of stack can go up to n.
	 * 
	 * @param s
	 * @return
	 */
	public static int longestValidParenthese2(String s) {
		int maxLen = 0;
		Stack<Integer> stack = new Stack<>();
		stack.push(-1);
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '(') {
				stack.push(i);
			} else {
				stack.pop();
				if (stack.empty()) {
					stack.push(i);
				} else {
					maxLen = Math.max(maxLen, i - stack.peek());
				}
			}
		}
		return maxLen;
	}

	/**
	 * Complexity Analysis
	 * 
	 * Time complexity : O(n). Two traversals of the string.
	 * 
	 * Space complexity : O(1). Only two extra variables left and right are needed.
	 * 
	 * @param s
	 * @return
	 */
	public static int longestValidParenthese3(String s) {
		int left = 0, right = 0, maxlength = 0;
		// sweep from left to right
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '(')
				left++;
			else
				right++;
			if (left == right)
				maxlength = Math.max(maxlength, 2 * right);
			else if (right >= left)
				left = right = 0;
		}
		// sweep from right to left
		left = right = 0;
		for (int i = s.length() - 1; i >= 0; i--) {
			if (s.charAt(i) == '(')
				left++;
			else
				right++;
			if (left == right)
				maxlength = Math.max(maxlength, 2 * left);
			else if (left >= right)
				left = right = 0;
		}
		return maxlength;
	}

	public static void main(String[] args) {
		assert longestValidParenthese3(")()())") == 4;
		assert longestValidParenthese2(")(()()))))") == 6;
	}

}
