package c04_stacks_queues;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * <pre>
 *  
Design a max stack that supports push, pop, top, peekMax and popMax.

push(x) -- Push element x onto stack.
pop() -- Remove the element on top of the stack and return it.
top() -- Get the element on the top.
peekMax() -- Retrieve the maximum element in the stack.
popMax() -- Retrieve the maximum element in the stack, and remove it. If you find more than one maximum elements, only remove the top-most one.
Example 1:
MaxStack stack = new MaxStack();
stack.push(5); 
stack.push(1);
stack.push(5);
stack.top(); -> 5
stack.popMax(); -> 5
stack.top(); -> 1
stack.peekMax(); -> 5
stack.pop(); -> 1
stack.top(); -> 5
Note:
-1e7 <= x <= 1e7
Number of operations won't exceed 10000.
The last four operations won't be called when stack is empty.
 * </pre>
 * 
 * @author lchen
 *
 */
public class MaxStack {
	private Deque<Integer> elements;
	private Deque<MaxCount> maxCounts;

	public MaxStack() {
		elements = new ArrayDeque<>();
		maxCounts = new ArrayDeque<>();
	}

	public boolean empty() {
		return elements.isEmpty();
	}

	public int top() {
		return elements.peek() == null ? 0 : elements.peek();
	}

	public int peekMax() {
		return maxCounts.peek() == null ? 0 : maxCounts.peek().max;
	}

	public int pop() {
		if (elements.isEmpty())
			throw new IllegalStateException("pop(): empty stack");
		int x = elements.pop();
		if (x == maxCounts.peek().max) {
			maxCounts.peek().count--;
			if (maxCounts.peek().count == 0)
				maxCounts.pop();
		}
		return x;
	}

	public int popMax() {
		if (elements.isEmpty())
			throw new IllegalStateException("popMax(): empty stack");
		MaxCount maxCount = maxCounts.peek();
		// backtrack to closest max and also cache front elements
		List<Integer> cachedItems = new LinkedList<>();
		while (elements.peek() != maxCount.max) {
			cachedItems.add(0, elements.pop());
		}
		elements.pop();
		maxCount.count--;
		// remove it when count down to zero
		if (maxCount.count == 0)
			maxCounts.pop();
		// push back the cached front elements
		for (Integer t : cachedItems) {
			push(t);
		}
		return maxCount.max;
	}

	public void push(int x) {
		elements.push(x);
		if (!maxCounts.isEmpty()) {
			if (maxCounts.peek().max == x)
				maxCounts.peek().count++;
			else if (maxCounts.peek().max < x)
				maxCounts.push(new MaxCount(x, 1));
		} else {
			maxCounts.push(new MaxCount(x, 1));
		}
	}

	private class MaxCount {
		int max;
		int count;

		public MaxCount(int max, int count) {
			this.max = max;
			this.count = count;
		}
	}

	public static void main(String[] args) {
		MaxStack stack = new MaxStack();
		stack.push(5);
		stack.push(1);
		stack.push(5);
		assert stack.top() == 5;
		assert stack.popMax() == 5;
		assert stack.top() == 1;
		assert stack.peekMax() == 5;
		assert stack.popMax() == 5;
		assert stack.peekMax() == 1;
		assert stack.pop() == 1;
	}
}







