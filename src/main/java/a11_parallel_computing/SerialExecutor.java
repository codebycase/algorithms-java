package a11_parallel_computing;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;

import org.junit.runner.Result;

public class SerialExecutor implements Executor {
	final Queue<Runnable> tasks = new ArrayDeque<>();
	final Executor executor;
	Runnable active;

	SerialExecutor(Executor executor) {
		this.executor = executor;
	}

	@Override
	public synchronized void execute(final Runnable r) {
		tasks.offer(new Runnable() {
			@Override
			public void run() {
				try {
					r.run();
				} finally {
					scheduleNext();
				}
			}
		});
		if (active == null) {
			scheduleNext();
		}
	}

	protected synchronized void scheduleNext() {
		if ((active = tasks.poll()) != null) {
			executor.execute(active);
		}
	}

	/**
	 * Wait just for the first task completed and then cancel other tasks!
	 * 
	 * @param e
	 * @param solvers
	 * @throws InterruptedException
	 */
	public void solve(Executor e, Collection<Callable<Result>> solvers) throws InterruptedException {
		CompletionService<Result> ecs = new ExecutorCompletionService<>(e);
		int n = solvers.size();
		List<Future<Result>> futures = new ArrayList<>(n);
		Result result = null;
		try {
			for (Callable<Result> s : solvers)
				futures.add(ecs.submit(s));
			for (int i = 0; i < n; ++i) {
				try {
					Result r = ecs.take().get();
					if (r != null) {
						result = r;
						break;
					}
				} catch (ExecutionException ignore) {
					// do nothing!
				}
			}
		} finally {
			for (Future<Result> f : futures)
				f.cancel(true);
		}

		if (result != null)
			System.out.println("use result!");
	}
}
