package a02_arrays_strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ShuffleAnArray {
	private Random random;

	private int[] array;
	private int[] original;

	private int[] runningSample;
	private int numSeenSoFar;

	public ShuffleAnArray(int[] nums, int sampleSize) {
		this.random = new Random();

		this.array = nums;
		this.original = nums.clone();

		this.runningSample = new int[sampleSize];
	}

	public int[] reset() {
		array = original;
		original = original.clone();

		numSeenSoFar = 0;
		Arrays.fill(runningSample, 0);
		return array;
	}

	public int[] shuffle() {
		for (int i = 0; i < array.length; i++) {
			swapAt(i, i + random.nextInt(array.length - i));
		}
		return array;
	}

	public int[] shuffle(int k) {
		for (int i = 0; i < k; i++) {
			swapAt(i, i + random.nextInt(array.length - i));
		}
		return Arrays.copyOfRange(array, 0, k);
	}

	public int[] streamShuffle(Iterator<Integer> sequence) {
		while (sequence.hasNext()) {
			int x = sequence.next();
			numSeenSoFar++;
			// fill up the running sample first
			if (numSeenSoFar < runningSample.length) {
				runningSample[numSeenSoFar - 1] = x;
			} else {
				int i = random.nextInt(numSeenSoFar);
				if (i < runningSample.length)
					runningSample[i] = x;
			}
		}
		return runningSample;
	}

	private void swapAt(int i, int j) {
		int tmp = array[i];
		array[i] = array[j];
		array[j] = tmp;
	}

	public static void main(String[] args) {
		int[] nums = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		ShuffleAnArray solution = new ShuffleAnArray(nums, 5);
		System.out.println(Arrays.toString(solution.shuffle()));
		System.out.println(Arrays.toString(solution.shuffle(3)));
		List<Integer> sequence = new ArrayList<>();
		sequence.addAll(Arrays.asList(0, 1, 2, 3, 4));
		System.out.println(Arrays.toString(solution.streamShuffle(sequence.iterator())));
		sequence.clear();
		sequence.addAll(Arrays.asList(7, 8, 9, 3, 4));
		System.out.println(Arrays.toString(solution.streamShuffle(sequence.iterator())));
	}

}