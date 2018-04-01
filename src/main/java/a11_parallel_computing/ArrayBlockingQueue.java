package a11_parallel_computing;

import java.util.List;
import java.util.Objects;

/**
 * Use standard synchronized and (wait, notify and notifyAll) to implement.
 * 
 * @author lchen
 *
 * @param <E>
 */
public class ArrayBlockingQueue<E> implements BlockingQueue<E> {
	private int count;
	private int putIndex;
	private int takeIndex;
	private Object[] elements;

	@Override
	public synchronized void init(int capacity) throws Exception {
		if (elements != null)
			throw new IllegalStateException();
		if (capacity <= 0)
			throw new IllegalArgumentException();
		count = 0;
		putIndex = 0;
		takeIndex = 0;
		elements = new Object[capacity];
		notifyAll();
	}

	@Override
	public synchronized E take() throws Exception {
		while (isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// do nothing!
			}
		}
		final E val = dequeue();
		notifyAll();
		return val;
	}

	@Override
	public synchronized void put(E obj) throws Exception {
		Objects.requireNonNull(obj);
		while (isFull()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// do nothing!
			}
		}
		enqueue(obj);
		notifyAll();
	}

	@Override
	public synchronized void putList(List<E> objs) throws Exception {
		Objects.requireNonNull(objs);
		for (E obj : objs) {
			while (isFull()) {
				try {
					wait();
				} catch (InterruptedException e) {
					// do nothing!
				}
			}
			enqueue(obj);
			notifyAll();
		}
	}

	private synchronized boolean isEmpty() {
		return (count == 0);
	}

	private synchronized boolean isFull() {
		return (count == elements.length);
	}

	private synchronized void enqueue(E x) {
		elements[putIndex] = x;
		if (++putIndex == elements.length)
			putIndex = 0;
		count++;
	}

	private synchronized E dequeue() {
		@SuppressWarnings("unchecked")
		E x = (E) elements[takeIndex];
		elements[takeIndex] = null; // avoid loitering
		if (++takeIndex == elements.length)
			takeIndex = 0;
		count--;
		return x;
	}
}
