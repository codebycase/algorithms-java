package a04_stacks_queues;

import java.util.ArrayDeque;
import java.util.Deque;

public class MinStack2 {
	int min = Integer.MAX_VALUE;
	Deque<Integer> stack = new ArrayDeque<>();

	public void push(int x) {
		// always cache the previous min value!
		if (x <= min) {
			stack.push(min);
			min = x;
		}
		stack.push(x);
	}

	public void pop() {
		if (stack.pop() == min)
			min = stack.pop();
	}

	public int top() {
		return stack.peek();
	}

	public int getMin() {
		return min;
	}
	
}
