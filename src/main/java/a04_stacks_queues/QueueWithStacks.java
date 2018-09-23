package a04_stacks_queues;

import java.util.Stack;

public class QueueWithStacks {
	Stack<Integer> stack1, stack2;

	/** Initialize your data structure here. */
	public QueueWithStacks() {
		stack1 = new Stack<>();
		stack2 = new Stack<>();
	}

	/** Push element x to the back of queue. */
	public void push(int x) {
		stack1.push(x);
	}

	/** Removes the element from in front of queue and returns that element. */
	public int pop() {
		rotateStacks();
		return stack2.pop();
	}

	/** Get the front element. */
	public int peek() {
		rotateStacks();
		return stack2.peek();
	}

	/** Returns whether the queue is empty. */
	public boolean empty() {
		return stack1.isEmpty() && stack2.isEmpty();
	}

	private void rotateStacks() {
		if (stack2.isEmpty()) {
			while (!stack1.isEmpty()) {
				stack2.push(stack1.pop());
			}
		}
	}
}
