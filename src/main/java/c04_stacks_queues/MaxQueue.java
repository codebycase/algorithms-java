package c04_stacks_queues;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Implement a queue with enqueue, dequeue, and max operations. The max operation returns the
 * maximum element currently stored in the queue.
 * 
 * @author lchen
 *
 */
public class MaxQueue<T extends Comparable<T>> {
	private Queue<T> entries = new ArrayDeque<>();
	private Deque<T> maxElements = new ArrayDeque<>();

	public void enqueue(T x) {
		entries.add(x);
		while (!maxElements.isEmpty() && maxElements.peekLast().compareTo(x) < 0) {
			// evict this unqualified element
			maxElements.removeLast();
		}
		maxElements.addLast(x);
	}

	public T dequeue() {
		if (!entries.isEmpty()) {
			T entry = entries.remove();
			if (entry.equals(maxElements.peekFirst()))
				maxElements.removeFirst();
			return entry;
		}
		throw new NoSuchElementException();
	}

	public T max() {
		if (!maxElements.isEmpty())
			return maxElements.peekFirst();
		throw new NoSuchElementException();
	}

	public static void main(String[] args) {
		MaxQueue<Integer> Q = new MaxQueue<Integer>();
		Q.enqueue(1);
		Q.enqueue(2);
		assert (2 == Q.max());
		assertDequeue(Q, 1);
		assert (2 == Q.max());
		assertDequeue(Q, 2);
		Q.enqueue(3);
		assert (3 == Q.max());
		assertDequeue(Q, 3);
		try {
			Q.max();
			assert (false);
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
		try {
			Q.dequeue();
			assert (false);
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void assertDequeue(MaxQueue<Integer> q, Integer t) {
		Integer dequeue = q.dequeue();
		assert (t.equals(dequeue));
	}
	
	
}
