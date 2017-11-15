package c04_stacks_queues;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Evaluate the value of an arithmetic expression in Reverse Polish Notation.
 * 
 * Valid operators are +, -, *, /. Each operand may be an integer or another expression.
 * 
 * Some examples:
 * 
 * ["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9
 * 
 * ["4", "13", "5", "/", "+"] -> (4 + (13 / 5)) -> 6
 * 
 * @author lchen
 *
 */
public class EvaluateRPNExpression {
	public static int evalRPN(String[] tokens) {
		Deque<Integer> stack = new LinkedList<>();
		for (String t : tokens) {
			if (t.equals("+")) {
				stack.push(stack.pop() + stack.pop());
			} else if (t.equals("-")) {
				int a = stack.pop(), b = stack.pop();
				stack.push(b - a);
			} else if (t.equals("*")) {
				stack.push(stack.pop() * stack.pop());
			} else if (t.equals("/")) {
				int a = stack.pop(), b = stack.pop();
				stack.push(b / a);
			} else {
				stack.push(Integer.valueOf(t));
			}
		}
		return stack.pop();
	}

	public static void main(String[] args) {
		assert evalRPN(new String[] { "4", "13", "5", "/", "+" }) == 6;
	}
}
