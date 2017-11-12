package c02_arrays_strings;

/**
 * Given a stream of integers and a window size, calculate the moving average of all integers in the
 * sliding window.
 * 
 * For example,
 * 
 * <pre>
MovingAverage m = new MovingAverage(3);
m.next(1) = 1
m.next(10) = (1 + 10) / 2
m.next(3) = (1 + 10 + 3) / 3
m.next(5) = (10 + 3 + 5) / 3
 * </pre>
 * 
 * @author lchen
 *
 */
public class MovingAverage {
	private int[] window;
	private int count = 0, insert = 0;
	private long sum = 0;

	public MovingAverage(int size) {
		window = new int[size];
	}

	public double next(int value) {
		if (count < window.length)
			count++;
		sum -= window[insert];
		sum += value;
		window[insert] = value;
		insert = (insert + 1) % window.length;
		return (double) sum / count;
	}

	public static void main(String[] args) {
		MovingAverage solution = new MovingAverage(3);
		assert solution.next(1) == 1.00000;
		assert solution.next(10) == 5.50000;
		assert solution.next(3) == 4.666666666666667;
		assert solution.next(5) == 6.00000;
	}
}
