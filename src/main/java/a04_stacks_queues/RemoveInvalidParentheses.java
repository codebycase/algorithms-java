package a04_stacks_queues;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class RemoveInvalidParentheses {
	public List<String> removeInvalidParentheses(String s) {
		List<String> res = new ArrayList<>();
		if (s == null)
			return res;

		Set<String> visited = new HashSet<>();
		Queue<String> queue = new LinkedList<>();

		queue.add(s);
		visited.add(s);

		boolean found = false;
		while (!queue.isEmpty()) {
			s = queue.poll();

			if (isValid(s)) {
				// found an answer, add to the result
				res.add(s);
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

		return res;
	}

	// helper function checks if string s contains valid parantheses
	boolean isValid(String s) {
		int count = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '(')
				count++;
			if (c == ')' && count-- == 0)
				return false;
		}
		return count == 0;
	}

	public List<String> removeInvalidParentheses2(String s) {
		int rmL = 0, rmR = 0; // how many parentheses to remove to make it valid!
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '(') {
				rmL++;
			} else if (s.charAt(i) == ')') {
				if (rmL != 0) {
					rmL--;
				} else {
					rmR++;
				}
			}
		}
		Set<String> res = new HashSet<>();
		dfs(s, 0, res, new StringBuilder(), rmL, rmR, 0);
		return new ArrayList<String>(res);
	}

	public void dfs(String s, int i, Set<String> res, StringBuilder sb, int rmL, int rmR, int open) {
		if (rmL < 0 || rmR < 0 || open < 0) {
			return;
		}
		if (i == s.length()) {
			if (rmL == 0 && rmR == 0 && open == 0)
				res.add(sb.toString());
			return;
		}

		char c = s.charAt(i);
		int len = sb.length();

		if (c == '(') {
			dfs(s, i + 1, res, sb, rmL - 1, rmR, open); // not use (
			dfs(s, i + 1, res, sb.append(c), rmL, rmR, open + 1); // use (

		} else if (c == ')') {
			dfs(s, i + 1, res, sb, rmL, rmR - 1, open); // not use )
			dfs(s, i + 1, res, sb.append(c), rmL, rmR, open - 1); // use )

		} else {
			dfs(s, i + 1, res, sb.append(c), rmL, rmR, open);
		}

		sb.setLength(len); // backtracking
	}
}
