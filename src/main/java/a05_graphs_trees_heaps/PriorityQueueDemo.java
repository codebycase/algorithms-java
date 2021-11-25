package a05_graphs_trees_heaps;

/**
 * Many applications require that we process items having keys in order, but not necessarily in full
 * sorted order and not necessarily all at once. Often, we collect a set of items, then process the
 * one with the largest key, then perhaps collect more items, then process the one with the current
 * largest key, and so forth. An appropriate data type in such an environment supports two
 * operations: remove the maximum and insert. Such a data type is called a priority queue. <br>
 * 
 * A priority queue is an abstract data type which is like a regular queue or stack data structure,
 * but where additionally each element has a "priority" associated with it. In a priority queue, an
 * element with high priority is served before an element with low priority. If two elements have
 * the same priority, they are served according to their order in the queue. Here priority queue is
 * implemented using a max heap. <br>
 * 
 * A binary heap is a set of nodes with keys arranged in a complete heap-ordered binary tree,
 * represented in level order in an array (not using the first entry). In a heap, the parent of the
 * node in position k is in position k/2; and, conversely, the two children of the node in position
 * k are in positions 2k and 2k + 1. We can travel up and down by doing simple arithmetic on array
 * indices: to move up the tree from a[k] we set k to k/2; to move down the tree we set k to 2*k or
 * 2*k+1. <br>
 * 
 * https://algs4.cs.princeton.edu/24pq/
 * 
 * @author lchen
 *
 */
public class PriorityQueueDemo {
	private Task[] heap;
	private int heapSize, capacity;

	public PriorityQueueDemo(int capacity) {
		this.capacity = capacity + 1; // not using the first entry
		heap = new Task[this.capacity];
		heapSize = 0;
	}

	public void clear() {
		heap = new Task[capacity];
		heapSize = 0;
	}

	public boolean isEmpty() {
		return heapSize == 0;
	}

	public boolean isFull() {
		return heapSize == capacity - 1;
	}

	public int size() {
		return heapSize;
	}

	// Bottom-up reheapify (swim).
	public void insert(String job, int priority) {
		if (isFull()) {
			System.out.println("Heap is full!");
			return;
		}
		Task newJob = new Task(job, priority);
		// start index with 1
		heap[++heapSize] = newJob;
		int pos = heapSize;
		while (pos > 1 && newJob.priority > heap[pos / 2].priority) {
			heap[pos] = heap[pos / 2];
			pos /= 2;
		}
		heap[pos] = newJob;
	}

	// Top-down heapify (sink).
	// Remove the max, exchange task with root, and sink down
	public Task remove() {
		if (isEmpty()) {
			System.out.println("Heap is empty!");
			return null;
		}

		Task item = heap[1];
		heap[1] = heap[heapSize--];
		// Task temp = heap[heapSize--];

		int parent = 1;
		while (parent <= heapSize / 2) { // half
			int child = parent << 1;
			if (child < heapSize && heap[child].priority < heap[child + 1].priority)
				child++;
			if (heap[parent].priority >= heap[child].priority)
				break;
			heap[parent] = heap[child];
			parent = child;
		}

		return item;
	}

	class Task {
		String job;
		int priority;

		public Task(String job, int priority) {
			this.job = job;
			this.priority = priority;
		}

		public String toString() {
			return "Job: " + job + "; priority: " + priority;
		}
	}

	public static void main(String[] args) {
		PriorityQueueDemo queue = new PriorityQueueDemo(3);
		queue.insert("first", 1);
		queue.insert("second", 5);
		queue.insert("third", 3);
		System.out.println(queue.remove());
		queue.insert("fourth", 4);
		System.out.println(queue.remove());
		System.out.println(queue.remove());
		System.out.println(queue.remove());
	}
}
