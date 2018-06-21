package a08_dynamic_programming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A sequence of number is called arithmetic if it consists of at least three elements and if the
 * difference between any two consecutive elements is the same.
 * 
 * <pre>
For example, these are arithmetic sequence:

1, 3, 5, 7, 9
7, 7, 7, 7
3, -1, -5, -9
The following sequence is not arithmetic.

1, 1, 2, 5, 7
 * </pre>
 * 
 * <pre>
Example:

A = [1, 2, 3, 4]

return: 3, for 3 arithmetic slices in A: [1, 2, 3], [2, 3, 4] and [1, 2, 3, 4] itself.
 * </pre>
 * 
 * @author lchen
 *
 */
public class ArithmeticSlices {
	public int numberOfArithmeticSlices(int[] A) {
		int[] dp = new int[A.length];
		int sum = 0;
		for (int i = 2; i < dp.length; i++) {
			if (A[i] - A[i - 1] == A[i - 1] - A[i - 2]) {
				dp[i] = 1 + dp[i - 1];
				sum += dp[i];
			}
		}
		return sum;
	}

	// we can also one variable, and update the sum at the very end,
	public int numberOfArithmeticSlices2(int[] A) {
		int count = 0, sum = 0;
		for (int i = 2; i < A.length; i++) {
			if (A[i] - A[i - 1] == A[i - 1] - A[i - 2]) {
				count++;
			} else {
				sum += (count + 1) * (count) / 2;
				count = 0;
			}
		}
		return sum += count * (count + 1) / 2;
	}

	/**
	 * Arithmetic Slices II - Subsequence
	 * 
	 * <pre>
	 * Example:
	
	Input: [2, 4, 6, 8, 10]
	
	Output: 7
	
	Explanation:
	All arithmetic subsequence slices are:
	[2,4,6]
	[4,6,8]
	[6,8,10]
	[2,4,6,8]
	[4,6,8,10]
	[2,4,6,8,10]
	[2,6,10]
	 * </pre>
	 */
	public int numberOfArithmeticSlicesII(int[] A) {
		int n = A.length;
		long ans = 0;
		List<Map<Integer, Integer>> counts = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			counts.add(new HashMap<>(i));
			// loop through all possible differences
			for (int j = 0; j < i; j++) {
				// avoid overflow
				long delta = (long) A[i] - (long) A[j];
				if (delta < Integer.MIN_VALUE || delta > Integer.MAX_VALUE) {
					continue;
				}
				int diff = (int) delta;
				// previous found subsequences
				int sum = counts.get(j).getOrDefault(diff, 0);
				int origin = counts.get(i).getOrDefault(diff, 0);
				// cache all new subsequences (include weak ones)
				counts.get(i).put(diff, origin + sum + 1);
				ans += sum;
			}
		}
		return (int) ans;
	}

	public static void main(String[] args) {
		ArithmeticSlices solution = new ArithmeticSlices();
		int result = solution.numberOfArithmeticSlicesII(new int[] { 2, 4, 6, 8, 10 });
		assert result == 7;
		result = solution.numberOfArithmeticSlicesII(new int[] { 1, 1, 2, 3, 4, 5 });
		assert result == 11;
	}
}
