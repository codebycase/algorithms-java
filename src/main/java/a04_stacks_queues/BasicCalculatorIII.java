package a04_stacks_queues;

import java.util.ArrayDeque;
import java.util.Deque;

public class BasicCalculatorIII {
	public static int calculate(String s) {
		if (s == null || s.length() == 0)
			return 0;
		Deque<Integer> nums = new ArrayDeque<>();
		Deque<Character> ops = new ArrayDeque<>();

		int num = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == ' ')
				continue;
			if (Character.isDigit(c)) {
				num = c - '0';
				// convert continuous digits to number
				while (i < s.length() - 1 && Character.isDigit(s.charAt(i + 1))) {
					num = num * 10 + (s.charAt(i + 1) - '0');
					i++;
				}
				nums.push(num);
			} else if (c == '(') {
				ops.push(c);
			} else if (c == ')') {
				// calculate backward until '('
				while (ops.peek() != '(') {
					nums.push(operation(ops.pop(), nums.pop(), nums.pop()));
				}
				ops.pop(); // remove the '('
			} else if (c == '+' || c == '-' || c == '*' || c == '/') {
				// calculate if higher precedence in stack
				while (!ops.isEmpty() && precedence(c, ops.peek())) {
					nums.push(operation(ops.pop(), nums.pop(), nums.pop()));
				}
				ops.push(c);
			}
		}
		while (!ops.isEmpty()) {
			nums.push(operation(ops.pop(), nums.pop(), nums.pop()));
		}
		return nums.pop();
	}

	private static int operation(char op, int b, int a) {
		switch (op) {
		case '+':
			return a + b;
		case '-':
			return a - b;
		case '*':
			return a * b;
		case '/':
			return a / b; // assume b is not 0
		}
		return 0;
	}

	private static boolean precedence(char current, char previous) {
		if (previous == '(' || previous == ')')
			return false;
		if ((current == '*' || current == '/') && (previous == '+' || previous == '-'))
			return false;
		return true;
	}

	public static void main(String[] args) {
		assert calculate(" 6-4 / 2 ") == 4;
		assert calculate("2*(5+5*2)/3+(6/2+8)") == 21;
		assert calculate("(2+6* 3+5- (3*14/7+2)*5)+3") == -12;
	}
}
