package a11_parallel_computing;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Java program to solve Producer Consumer problem using wait and notify method in Java. Producer Consumer is also a popular conccurrency design
 * pattern.
 * 
 * @author lchen
 *
 */
public class ProducerConsumerSolution {
	public static void main(String args[]) throws Exception {
		int queueCapacity = 5;
		BlockingQueue<Integer> sharedQueue = new LinkedBlockingQueue<Integer>(queueCapacity);
		Thread prodThread1 = new Thread(new Producer(sharedQueue), "Producer1");
		Thread prodThread2 = new Thread(new Producer(sharedQueue), "Producer2");
		Thread consThread1 = new Thread(new Consumer(sharedQueue), "Consumer1");
		Thread consThread2 = new Thread(new Consumer(sharedQueue), "Consumer2");
		prodThread1.start();
		prodThread2.start();
		Thread.sleep(10000);
		consThread1.start();
		consThread2.start();
	}
}

class Producer implements Runnable {
	private final BlockingQueue<Integer> sharedQueue;

	public Producer(BlockingQueue<Integer> sharedQueue) {
		this.sharedQueue = sharedQueue;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				System.out.println(Thread.currentThread().getName() + ": " + i);
				sharedQueue.put(i);
				Thread.sleep(500); // simulate a processing...
			} catch (InterruptedException ex) {
				Logger.getLogger(Producer2.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}

class Consumer implements Runnable {
	private final BlockingQueue<Integer> sharedQueue;

	public Consumer(BlockingQueue<Integer> sharedQueue) {
		this.sharedQueue = sharedQueue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println(Thread.currentThread().getName() + ": " + sharedQueue.take());
			} catch (InterruptedException ex) {
				Logger.getLogger(Consumer2.class.getName()).log(Level.SEVERE, null, ex);
			}

		}
	}
}
