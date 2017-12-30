package a11_parallel_computing;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Where a Lock replaces the use of synchronized methods and statements, <br>
 * a Condition replaces the use of the Object monitor methods (wait, notify and notifyAll)
 * 
 * @author lchen
 *
 * @param <E>
 */
public class LinkedBlockingQueue<E> implements BlockingQueue<E> {
	private int capacity;
	private Queue<E> queue;

	private Lock lock = new ReentrantLock();
	private Lock putLock = new ReentrantLock();
	private Condition notFull = lock.newCondition();
	private Condition notEmpty = lock.newCondition();

	@Override
	public void init(int capacity) throws Exception {
		lock.lock();
		try {
			if (capacity <= 0)
				throw new IllegalArgumentException();
			if (queue != null)
				throw new IllegalStateException();
			this.capacity = capacity;
			this.queue = new LinkedList<>();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public E take() throws Exception {
		lock.lock();
		try {
			while (queue.size() == 0)
				notEmpty.await();
			E result = queue.poll();
			notFull.signal();
			return result;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void put(E obj) throws Exception {
		checkNotNull(obj);
		putLock.lock();
		lock.lock();
		try {
			while (capacity == queue.size())
				notFull.await(); // wake by singal
			queue.add(obj);
			notEmpty.signal();
		} finally {
			lock.unlock();
			putLock.unlock();
		}
	}

	@Override
	public void putList(List<E> objs) throws Exception {
		checkNotNull(objs);
		putLock.lock();
		lock.lock();
		try {
			for (E obj : objs) {
				while (queue.size() == capacity)
					notFull.await();
				queue.add(obj);
				notEmpty.signal();
			}
		} finally {
			lock.unlock();
			putLock.unlock();
		}
	}

	private void checkNotNull(Object v) {
		if (v == null)
			throw new NullPointerException();
	}
}
