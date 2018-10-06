package a02_arrays_strings;

import java.util.Arrays;

public class SquareSort {

	/*
	 * To execute Java, please define "static void main" on a class
	 * named Solution.
	 * Given a sorted array of integers, return the sorted squares of those integers
	 * Ex: [-4,1,3,5] -> [1,9,16,25]
	 *
	 * If you need more classes, simply define them inline.
	 */

	public static int[] squareSort(int[] array) {
		if (array == null || array.length == 0)
			return new int[0];
		int[] result = new int[array.length];
		int i = 0, j = 0;
		// while (i < array.length && array[i] < 0)
		// i++;
		i = binarySearch(array, 0);
		j = i - 1;
		int index = 0;
		while (j >= 0 || i < array.length) {
			if (j < 0) {
				result[index++] = array[i] * array[i];
				i++;
			} else if (i >= array.length) {
				result[index++] = array[j] * array[j];
				j--;
			} else if (Math.abs(array[j]) > array[i]) {
				result[index++] = array[i] * array[i];
				i++;
			} else {
				result[index++] = array[j] * array[j];
				j--;
			}
		}
		return result;
	}

	private static int binarySearch(int[] array, int target) {
		int left = 0, right = array.length - 1;
		// find the value >= target
		while (left <= right) {
			int mid = left + (right - left) / 2;
			if (target == array[mid])
				return mid;
			else if (target < array[mid])
				right = mid - 1;
			else
				left = mid + 1;
		}
		return left;
	}

	public static void main(String[] args) {
		assert Arrays.equals(squareSort(new int[] { -4, 1, 3, 5 }), new int[] { 1, 9, 16, 25 });
		assert Arrays.equals(squareSort(new int[] { 0, 1, 3, 5 }), new int[] { 0, 1, 9, 25 });
		assert Arrays.equals(squareSort(new int[] { -5, -4, -1 }), new int[] { 1, 16, 25 });
		assert Arrays.equals(squareSort(new int[] { -4, -1, 0, 3, 5, 5 }), new int[] { 0, 1, 9, 16, 25, 25 });
	}
}
