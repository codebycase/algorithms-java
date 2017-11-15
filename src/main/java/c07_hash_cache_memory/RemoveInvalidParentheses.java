package c07_hash_cache_memory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Remove the minimum number of invalid parentheses in order to make the input string valid. Return
 * all possible results.
 * 
 * Note: The input string may contain letters other than the parentheses ( and ).
 * 
 * Examples: "()())()" -> ["()()()", "(())()"]; "(a)())()" -> ["(a)()()", "(a())()"] ")(" -> [""]
 * 
 * @author lchen
 *
 */
public class RemoveInvalidParentheses {
	public List<String> removeInvalidParentheses(String s) {
		List<String> result = new ArrayList<>();

		// sanity check
		if (s == null)
			return result;

		Set<String> visited = new HashSet<>();
		Queue<String> queue = new LinkedList<>();

		// initialize
		queue.add(s);
		visited.add(s);

		boolean found = false;
		while (!queue.isEmpty()) {
			s = queue.poll();

			if (isValid(s)) {
				// found an answer, add to the result
				result.add(s);
				found = true;
			}

			if (found)
				continue;

			// generate all possible states
			for (int i = 0; i < s.length(); i++) {
				// we only try to remove left or right paren
				if (s.charAt(i) != '(' && s.charAt(i) != ')')
					continue;
				String t = s.substring(0, i) + s.substring(i + 1);
				if (!visited.contains(t)) {
					// for each state, if it's not visited, add it to the queue
					queue.add(t);
					visited.add(t);
				}
			}
		}

		return result;
	}

	// helper function checks if string s contains valid parantheses
	private boolean isValid(String s) {
		int count = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '(')
				count++;
			else if (c == ')') {
				if (count == 0)
					return false;
				count--;
			}
		}
		return count == 0;
	}

	public List<String> removeInvalidParentheses2(String s) {
		List<String> results = new ArrayList<>();
		remove(s, results, 0, 0, new char[] { '(', ')' });
		return results;
	}

	public void remove(String s, List<String> results, int last_i, int last_j, char[] par) {
		for (int stack = 0, i = last_i; i < s.length(); ++i) {
			if (s.charAt(i) == par[0])
				stack++;
			if (s.charAt(i) == par[1])
				stack--;
			if (stack >= 0)
				continue;
			for (int j = last_j; j <= i; ++j)
				if (s.charAt(j) == par[1] && (j == last_j || s.charAt(j - 1) != par[1]))
					remove(s.substring(0, j) + s.substring(j + 1, s.length()), results, i, j, par);
			return;
		}
		String reversed = new StringBuilder(s).reverse().toString();
		if (par[0] == '(') // finished left to right
			remove(reversed, results, 0, 0, new char[] { ')', '(' });
		else // finished right to left
			results.add(reversed);
	}
}
