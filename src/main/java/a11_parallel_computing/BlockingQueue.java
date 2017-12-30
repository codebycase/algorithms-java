package a11_parallel_computing;

import java.util.List;

/**
 * Given an unbounded non-block queue, implement a blocking bounded queue.
 * 
 * @author lchen
 *
 * @param <E>
 */
public interface BlockingQueue<E> {
	// Only initialize this queue once and throws Exception if the user is trying to initialize it multiple times.
	public void init(int capacity) throws Exception;

	// Retrieves and removes the head of this queue, waiting if necessary until an element becomes available.
	public E take() throws Exception;

	// Inserts the specified element into this queue, waiting if necessary for space to become available.
	public void put(E obj) throws Exception;

	// Implement an atomic putList function which can put a list of object atomically.
	// By atomically it means the objs in the list should be next to each other in the queue.
	// The size of the list could be larger than the queue capacity.
	public void putList(List<E> objs) throws Exception;
}
