package a08_dynamic_programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given a phone keypad as below:
 * 
 * <pre>
1 2 3
4 5 6
7 8 9
  0
 * </pre>
 * 
 * Let's start with 1, and you can only make the movement as the Knight in a chess game. E.g. if we
 * are at 1 then the next digit can be eight 6 or 8, if we are at 6 then the next digit can be 1, 7
 * or 0.
 * 
 * Repetition of digits are allowed - 1616161616 is a valid number.
 * 
 * The question is how many different 10-digit numbers can be formed starting from 1?
 *
 * 
 */
public class KnightOnKeypad {
	// The valid movements from any number 0 through 9!
	int[][] nexts = { { 4, 6 }, { 6, 8 }, { 7, 9 }, { 4, 8 }, { 0, 3, 9 }, {}, { 1, 7, 0 }, { 2, 6 }, { 1, 3 },
			{ 2, 4 } };

	// iterative, needs to calculate all digits for each length
	public int countIterative(int digit, int length) {
		int[][] matrix = new int[length][10];
		Arrays.fill(matrix[0], 1);

		for (int len = 1; len < length; len++) {
			for (int dig = 0; dig <= 9; dig++) {
				int sum = 0;
				for (int i : nexts[dig]) {
					sum += matrix[len - 1][i];
				}
				matrix[len][dig] = sum;
			}
		}

		return matrix[length - 1][digit];
	}

	// recursive, DFS, with memorization, just calculate the reached ones.
	// NOTE: the length is between [1, 10] instead of [0, 9]!
	public int countRecursive(int digit, int length, int[][] matrix) {
		if (length == 1)
			return 1;
		// already reached and cached
		if (matrix[length - 1][digit] > 0)
			return matrix[length - 1][digit];
		int sum = 0;
		for (int i : nexts[digit]) {
			sum += countRecursive(i, length - 1, matrix);
		}
		matrix[length - 1][digit] = sum;
		return sum;
	}

	// BFS
	public void permuteRecursive(int digit, int length, List<String> temp, List<String> results) {
		if (length == 0) {
			results.addAll(temp);
			return;
		}
		for (int dig : nexts[digit]) {
			List<String> list = new ArrayList<>();
			for (String number : temp) {
				list.add(number + dig);
			}
			permuteRecursive(dig, length - 1, list, results);
		}
	}

	// BFS
	public void permuteRecursive2(int digit, int length, List<String> temp, List<String> results) {
		List<String> list = new ArrayList<>();
		for (String number : temp) {
			list.add(number + digit);
		}
		length = length - 1;

		if (length == 0) {
			results.addAll(list);
			return;
		}

		for (int dig : nexts[digit]) {
			permuteRecursive2(dig, length, list, results);
		}
	}

	// DFS
	public List<String> permuteRecursive3(int digit, int length) {
		List<String> results = new ArrayList<>();

		if (length == 1) {
			results.add("" + digit);
			return results;
		}

		for (int dig : nexts[digit]) {
			for (String number : permuteRecursive3(dig, length - 1)) {
				results.add(digit + number);
			}
		}

		return results;
	}

	public static void main(String[] args) {
		KnightOnKeypad solution = new KnightOnKeypad();
		assert solution.countIterative(1, 10) == 1424;
		assert solution.countRecursive(1, 10, new int[10][10]) == 1424;
		List<String> results = new ArrayList<>();
		solution.permuteRecursive(1, 10 - 1, Arrays.asList("1"), results);
		assert results.size() == 1424;
		results = new ArrayList<>();
		solution.permuteRecursive2(1, 10, Arrays.asList(""), results);
		assert results.size() == 1424;
		results = solution.permuteRecursive3(1, 10);
		assert results.size() == 1424;
	}

}
