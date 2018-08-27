package a11_parallel_computing;

import java.util.concurrent.TimeUnit;

public class DurationSleeper {
	private final Object monitor = new Object();
	private long durationMillis = 0;

	public DurationSleeper(long duration, TimeUnit timeUnit) {
		setDuration(duration, timeUnit);
	}

	public void sleep() {
		long millisSlept = 0;
		while (true) {
			synchronized (monitor) {
				try {
					long millisToSleep = durationMillis - millisSlept;
					if (millisToSleep <= 0)
						return;
					long sleepStartedInNanos = System.nanoTime();
					// Not using System.currentTimeMillis - it depends on OS time, and may be changed at any moment
					// (e.g. by daylight saving time)
					monitor.wait(millisToSleep);
					millisSlept += TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - sleepStartedInNanos);
				} catch (InterruptedException e) {
					throw new RuntimeException("Execution interrupted.", e);
				}
			}
		}
	}

	public void setDuration(long newDuration, TimeUnit timeUnit) {
		synchronized (monitor) {
			this.durationMillis = timeUnit.toMillis(newDuration);
			monitor.notifyAll();
		}
	}
}
