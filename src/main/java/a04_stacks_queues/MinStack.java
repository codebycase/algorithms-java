package a04_stacks_queues;

import java.util.ArrayDeque;
import java.util.Deque;

public class MinStack {
	Deque<Node> stack = new ArrayDeque<>();

	public void push(int x) {
		int min = stack.isEmpty() ? x : Math.min(x, stack.peek().min);
		stack.push(new Node(min, x));
	}

	public void pop() {
		stack.pop();
	}

	public int top() {
		return stack.peek().value;
	}

	public int getMin() {
		return stack.peek().min;
	}

	class Node {
		int min;
		int value;

		public Node(int min, int value) {
			this.min = min;
			this.value = value;
		}
	}
}
