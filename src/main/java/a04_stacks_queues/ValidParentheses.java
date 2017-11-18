package a04_stacks_queues;

import java.util.Stack;

/**
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the
 * input string is valid.
 * 
 * The brackets must close in the correct order, "()" and "()[]{}" are all valid but "(]" and "([)]"
 * are not.
 * 
 * @author lchen
 *
 */
public class ValidParentheses {
	public static boolean isValidParentheses(String s) {
		Stack<Character> stack = new Stack<Character>();
		for (char c : s.toCharArray()) {
			if (c == '(')
				stack.push(')');
			else if (c == '{')
				stack.push('}');
			else if (c == '[')
				stack.push(']');
			else if (stack.isEmpty() || stack.pop() != c)
				return false;
		}
		return stack.isEmpty();
	}

	public static void main(String[] args) {
		assert isValidParentheses("([]){()}");
		assert isValidParentheses("([]){([})}") == false;		
	}
}
