package a11_parallel_computing;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelComputingBootCamp {

	/**
	 * A semaphore is a very powerful synchronization construct, which maintains a set of permits.
	 * Semaphores are often used to restrict the number of threads that can access some resource.
	 */
	public class Semaphore {
		private final int maxAvailable;
		private int taken;

		public Semaphore(int maxAvailable) {
			this.maxAvailable = maxAvailable;
			this.taken = 0;
		}

		public synchronized void acquire() throws InterruptedException {
			while (taken == maxAvailable) {
				wait();
			}
			taken++;
		}

		public synchronized void release() throws InterruptedException {
			taken--;
			notifyAll();
		}
	}

	class SleepingCallable implements Callable<String> {
		final String name;
		final long period;

		SleepingCallable(final String name, final long period) {
			this.name = name;
			this.period = period;
		}

		public String call() {
			try {
				Thread.sleep(period);
			} catch (InterruptedException ex) {
			}
			return name;
		}
	}

	public void demonstrateInvokeAll() {
		final ExecutorService pool = Executors.newFixedThreadPool(2);
		final List<? extends Callable<String>> callables = Arrays.asList(
			    new SleepingCallable("quick", 500),
			    new SleepingCallable("slow", 5000));
		try {
			for (final Future<String> future : pool.invokeAll(callables)) {
				System.out.println(future.get());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		pool.shutdown();
	}

	public void demonstrateCompleteService() {
		final ExecutorService pool = Executors.newFixedThreadPool(2);
		final CompletionService<String> service = new ExecutorCompletionService<>(pool);
		service.submit(new SleepingCallable("slow", 5000));
		service.submit(new SleepingCallable("quick", 500));
		pool.shutdown();
		try {
			while (!pool.isTerminated()) {
				Future<String> future = service.take();
				System.out.println(future.get());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ParallelComputingBootCamp solution = new ParallelComputingBootCamp();
		solution.demonstrateInvokeAll();
		solution.demonstrateCompleteService();		
	}

}
