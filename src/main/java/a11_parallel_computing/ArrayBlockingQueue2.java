package a11_parallel_computing;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayBlockingQueue2<E> implements BlockingQueue<E> {
	private int count;
	private int putIndex;
	private int takeIndex;
	private Object[] items;

	private ReentrantLock lock = new ReentrantLock();
	private Condition notEmpty = lock.newCondition();
	private Condition notFull = lock.newCondition();

	@Override
	public void init(int capacity) throws Exception {
		lock.lock();
		try {
			if (capacity <= 0)
				throw new IllegalArgumentException();
			if (items != null)
				throw new IllegalStateException();
			items = new Object[capacity];
		} finally {
			lock.unlock();
		}
	}

	@Override
	public E take() throws Exception {
		lock.lockInterruptibly();
		try {
			while (count == 0)
				notEmpty.await();
			return dequeue();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void put(E obj) throws Exception {
		checkNotNull(obj);
		final ReentrantLock lock = this.lock;
		lock.lockInterruptibly();
		try {
			while (count == items.length)
				notFull.await();
			enqueue(obj);
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void putList(List<E> objs) throws Exception {
		checkNotNull(objs);
		final ReentrantLock lock = this.lock;
		lock.lockInterruptibly();
		try {
			for (E obj : objs) {
				while (count == items.length)
					notFull.await();
				enqueue(obj);
			}
		} finally {
			lock.unlock();
		}
	}

	private void enqueue(E x) {
		items[putIndex] = x;
		if (++putIndex == items.length)
			putIndex = 0;
		count++;
		notEmpty.signal();
	}

	private E dequeue() {
		@SuppressWarnings("unchecked")
		E x = (E) items[takeIndex];
		items[takeIndex] = null;
		if (++takeIndex == items.length)
			takeIndex = 0;
		count--;
		notFull.signal();
		return x;
	}

	private void checkNotNull(Object v) {
		if (v == null)
			throw new NullPointerException();
	}
}
