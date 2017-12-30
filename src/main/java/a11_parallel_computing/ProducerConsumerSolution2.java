package a11_parallel_computing;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Java program to solve Producer Consumer problem using wait and notify method in Java. Producer Consumer is also a popular conccurrency design
 * pattern.
 * 
 * @author lchen
 *
 */
public class ProducerConsumerSolution2 {
	public static void main(String args[]) {
		int size = 4;
		Vector<Integer> sharedQueue = new Vector<Integer>();
		Thread prodThread1 = new Thread(new Producer2(sharedQueue, size), "Producer1");
		Thread prodThread2 = new Thread(new Producer2(sharedQueue, size), "Producer2");
		Thread consThread1 = new Thread(new Consumer2(sharedQueue), "Consumer1");
		Thread consThread2 = new Thread(new Consumer2(sharedQueue), "Consumer2");
		prodThread1.start();
		consThread1.start();
		prodThread2.start();
		consThread2.start();
	}
}

class Producer2 implements Runnable {
	private final int capacity;
	private final Vector<Integer> sharedQueue;

	public Producer2(Vector<Integer> sharedQueue, int capacity) {
		this.capacity = capacity;
		this.sharedQueue = sharedQueue;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				// wait if queue is full
				while (sharedQueue.size() == capacity) {
					synchronized (sharedQueue) {
						System.out.println("Queue is full, " + Thread.currentThread().getName() + " is waiting , size: " + sharedQueue.size());
						sharedQueue.wait();
					}
				}
				// producing element and notify consumers
				synchronized (sharedQueue) {
					sharedQueue.add(i);
					sharedQueue.notifyAll();
					System.out.println(Thread.currentThread().getName() + ": " + i);
				}
				Thread.sleep(500);
			} catch (InterruptedException ex) {
				Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}

class Consumer2 implements Runnable {
	private final Vector<Integer> sharedQueue;

	public Consumer2(Vector<Integer> sharedQueue) {
		this.sharedQueue = sharedQueue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				// wait if queue is empty
				while (sharedQueue.isEmpty()) {
					synchronized (sharedQueue) {
						System.out.println("Queue is empty, " + Thread.currentThread().getName() + " is waiting , size: " + sharedQueue.size());
						sharedQueue.wait();
					}
				}
				// Otherwise consume element and notify waiting producer
				synchronized (sharedQueue) {
					sharedQueue.notifyAll();
					System.out.println(Thread.currentThread().getName() + ": " + sharedQueue.remove(0));
				}
				Thread.sleep(50);
			} catch (InterruptedException ex) {
				Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
			}

		}
	}
}
