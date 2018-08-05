package a11_parallel_computing;

import java.util.concurrent.atomic.AtomicLong;

public class StopThread {
	// You can also use AtomicBoolean directly!
	private static volatile boolean stopRequested;

	// Lock-free, thread-safe primitives within java.util.concurrent.atomic
	// Declare as final to prevent from getting catastrophic unsynchronized access
	private static final AtomicLong nextSerialNum = new AtomicLong();

	public static void main(String[] args) throws InterruptedException {
		Thread backgroundThread = new Thread(() -> {
			int i = 0;
			while (!stopRequested) {
				System.out.println(i + ": " + nextSerialNum.getAndIncrement());
				i++;
			}
		});

		backgroundThread.start();
		Thread.sleep(10);
		stopRequested = true;
	}
}
