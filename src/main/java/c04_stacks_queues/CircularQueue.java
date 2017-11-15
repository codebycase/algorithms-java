package c04_stacks_queues;

import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;

/**
 * Implement a circular queue API using an array for storing elements. Your API should include a
 * constructor function, which takes as argument the initial capacity of the queue, enqueue and
 * dequeue functions, and a function which returns the number of elements stored. Implement dynamic
 * resizing to support storing an arbitrarily large number of elements.
 */
public class CircularQueue {
	private static final int SCALE_FACTOR = 2;
	private int head = 0, tail = 0, size = 0;
	private Integer[] entries;

	public CircularQueue(int capacity) {
		if (capacity < 0)
			throw new IllegalArgumentException();
		entries = new Integer[capacity];
	}

	public void enqueue(Integer x) {
		if (size == entries.length) { // need to resize
			// make the queue elements appear consecutively
			Collections.rotate(Arrays.asList(entries), -head);
			// reset head and tail indices
			head = 0;
			tail = size;
			entries = Arrays.copyOf(entries, size * SCALE_FACTOR);
		}
		entries[tail] = x;
		tail = (tail + 1) % entries.length;
		size++;
	}

	public Integer dequeue() {
		if (size != 0) {
			size--;
			Integer result = entries[head];
			head = (head + 1) % entries.length;
			return result;
		}
		throw new NoSuchElementException("Dequeue called on an empty queue.");
	}

	public int size() {
		return size;
	}

	public static void main(String[] args) {
		CircularQueue q = new CircularQueue(8);
		q.enqueue(1);
		q.enqueue(2);
		q.enqueue(3);
		q.enqueue(4);
		q.enqueue(5);
		q.enqueue(6);
		q.enqueue(7);
		q.enqueue(8);
		// Now head = 0 and tail = 0

		assertDequeue(q, 1);
		assertDequeue(q, 2);
		assertDequeue(q, 3);
		// Now head = 3 and tail = 0

		q.enqueue(11);
		q.enqueue(12);
		q.enqueue(13);
		// Ok till here. Now head = 3 and tail = 3

		q.enqueue(14);
		// Now the vector (entries) is resized; but the head and tail.
		// (or elements) does not change accordingly.

		q.enqueue(15);
		q.enqueue(16);
		q.enqueue(17);
		q.enqueue(18);
		// The elements starting from head=3 are over-written!

		assertDequeue(q, 4);
		assertDequeue(q, 5);
		assertDequeue(q, 6);
		assertDequeue(q, 7);
		assertDequeue(q, 8);
		assertDequeue(q, 11);
		assertDequeue(q, 12);
	}

	private static void assertDequeue(CircularQueue q, Integer t) {
		Integer dequeue = q.dequeue();
		assert (t.equals(dequeue));
	}
}
