package a14_java_world_topics;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Java8ParallelStream {
	private static final long N = 10_000_000L;
	private static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool();

	public static <T, R> long measurePerf(Function<T, R> function, T input) {
		long fastest = Long.MAX_VALUE;
		for (int i = 0; i < 2; i++) {
			long start = System.nanoTime();
			R result = function.apply(input);
			long duration = (System.nanoTime() - start) / 1_000_000;
			System.out.println("Result: " + result);
			if (duration < fastest)
				fastest = duration;
		}
		return fastest;
	}

	static class ParallelStreams {
		public static long iterativeSum(long n) {
			long result = 0;
			for (long i = 0; i <= n; i++) {
				result += i;
			}
			return result;
		}

		public static long sequentailSum(long n) {
			return Stream.iterate(1L, i -> i + 1).limit(n).reduce(Long::sum).get();
		}

		public static long parallelSum(long n) {
			return Stream.iterate(1L, i -> i + 1).limit(n).parallel().reduce(Long::sum).get();
		}

		public static long rangedSum(long n) {
			return LongStream.rangeClosed(1, n).reduce(Long::sum).getAsLong();
		}

		public static long parallelRangedSum(long n) {
			return LongStream.rangeClosed(1, n).parallel().reduce(Long::sum).getAsLong();
		}

		public static long forkJoinSum(long n) {
			long[] numbers = LongStream.rangeClosed(1, n).toArray();
			ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
			return FORK_JOIN_POOL.invoke(task);
		}

		public static long sideEffectSum(long n) {
			Accumulator accumulator = new Accumulator();
			LongStream.rangeClosed(1, n).forEach(accumulator::add);
			return accumulator.getTotal();
		}

		public static long sideEffectParrallelSum(long n) {
			Accumulator accumulator = new Accumulator();
			LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
			return accumulator.getTotal();
		}
	}

	static class ForkJoinSumCalculator extends RecursiveTask<Long> {
		private static final long serialVersionUID = -2754919233589478904L;

		private static final long THRESHOLD = 10_000;
		private final long[] numbers;
		private final int start, end;

		public ForkJoinSumCalculator(long[] numbers) {
			this(numbers, 0, numbers.length);
		}

		private ForkJoinSumCalculator(long[] numbers, int start, int end) {
			this.numbers = numbers;
			this.start = start;
			this.end = end;
		}

		@Override
		protected Long compute() {
			int length = end - start;
			if (length <= THRESHOLD) {
				return computeSequentially();
			}
			ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length / 2);
			leftTask.fork();
			ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length / 2, end);
			Long rightResult = rightTask.compute();
			Long leftResult = leftTask.join();
			return leftResult + rightResult;
		}

		private long computeSequentially() {
			long sum = 0;
			for (int i = start; i < end; i++) {
				sum += numbers[i];
			}
			return sum;
		}
	}

	static class Accumulator {
		private long total = 0;

		public void add(long value) {
			total += value;
		}

		public long getTotal() {
			return total;
		}
	}

	public static void main(String[] args) {
		/*
		System.out.println("Iterative Sum done in: " + measurePerf(ParallelStreams::iterativeSum, N) + " msecs");
		System.out.println("Sequential Sum done in: " + measurePerf(ParallelStreams::sequentailSum, N) + " msecs");
		System.out.println("Parallel forkJoinSum done in: " + measurePerf(ParallelStreams::parallelSum, N) + " msecs");
		System.out.println("Range forkJoinSum done in: " + measurePerf(ParallelStreams::rangedSum, N) + " msecs");
		System.out.println("Parallel range forkJoinSum done in: " + measurePerf(ParallelStreams::parallelRangedSum, N) + " msecs");
		System.out.println("ForkJoin sum done in: " + measurePerf(ParallelStreams::forkJoinSum, N) + " msecs");
		System.out.println("SideEffect sum done in: " + measurePerf(ParallelStreams::sideEffectSum, N) + " msecs");
		System.out.println("SideEffect parallel sum done in: " + measurePerf(ParallelStreams::sideEffectParrallelSum, N) + " msecs");
		*/
		String[] keys = new String[] { "a", "b", "c" };
		Stream.of(keys).forEach(System.out::println);
	}
}
