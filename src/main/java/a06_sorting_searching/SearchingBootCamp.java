package a06_sorting_searching;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

public class SearchingBootCamp {
	/**
	 * Write a method that takes a sorted array and a key and returns the index of the first
	 * occurrence of that key in the array.
	 */
	public int searchFirstOccurance(List<Integer> A, int k) {
		int left = 0, right = A.size() - 1, result = -1;
		while (left <= right) {
			int mid = left + (right - left) / 2;
			if (A.get(mid) > k) {
				right = mid - 1;
			} else if (A.get(mid) == k) {
				result = mid;
				// nothing to the right of mid can be the first occurrence of k
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}
		return result;
	}

	/**
	 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you
	 * beforehand.
	 * 
	 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
	 * 
	 * You are given a target value to search. If found in the array return its index, otherwise
	 * return -1.
	 * 
	 * You may assume no duplicate exists in the array.
	 * 
	 * What if duplicates are allowed?
	 */
	public int searchInRotatedSortedArray(int[] nums, int target) {
		int start = 0;
		int end = nums.length - 1;
		while (start <= end) {
			int mid = (start + end) / 2;
			if (nums[mid] == target)
				return mid;

			if (nums[start] <= nums[mid]) {
				if (target < nums[mid] && target >= nums[start])
					end = mid - 1;
				else
					start = mid + 1;
			} else if (nums[mid] <= nums[end]) {
				if (target > nums[mid] && target <= nums[end])
					start = mid + 1;
				else
					end = mid - 1;
			}
		}
		return -1;
	}

	// if has duplicates, like nums = [1, 3, 1, 1, 1]; target = 3
	// worest case O(n), otherwise O(log(n))
	public boolean searchInRotatedSortedArray2(int[] nums, int target) {
		int start = 0;
		int end = nums.length - 1;
		while (start <= end) {
			int mid = (start + end) / 2;
			if (nums[mid] == target)
				return true;
			// exceptional case due to duplicates
			if (nums[start] == nums[mid] && nums[mid] == nums[end]) {
				end--; // break the tie
			} else if (nums[start] <= nums[mid]) {
				if (target < nums[mid] && target >= nums[start])
					end = mid - 1;
				else
					start = mid + 1;
			} else if (nums[mid] <= nums[end]) {
				if (target > nums[mid] && target <= nums[end])
					start = mid + 1;
				else
					end = mid - 1;
			}
		}
		return false;
	}

	// if has duplicates, like nums = [1, 3, 1, 1, 1]; target = 3
	public int searchInRotatedSortedArray3(int[] nums, int target) {
		int start = 0;
		int end = nums.length - 1;
		while (start <= end) {
			int mid = (start + end) / 2;
			if (nums[mid] == target)
				return mid;

			if (nums[start] <= nums[mid] || nums[mid] > nums[end]) {
				if (target < nums[mid] && target >= nums[start])
					end = mid - 1;
				else
					start = mid + 1;
			} else if (nums[mid] <= nums[end] || nums[start] > nums[mid]) {
				if (target > nums[mid] && target <= nums[end])
					start = mid + 1;
				else
					end = mid - 1;
			} else
				end--;
		}
		return -1;
	}

	/**
	 * Write a program which takes a nonnegative integer and returns the largest integer whose
	 * square is less than or equal to the given integer. For example, if the input is 16, return 4;
	 * if the input is 300, return 17, since 17^2 = 289 < 300.
	 */
	public int computeSquareRootInt(int k) {
		long left = 0, right = k;
		// Candidate interval [left, right] where everything before left has square <= k, and
		// everything after right has square > k.
		while (left <= right) {
			long mid = left + (right - left) / 2;
			long midSquare = mid * mid;
			if (midSquare <= k)
				left = mid + 1;
			else
				right = mid - 1;
		}
		return (int) left - 1;
	}

	/**
	 * Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has
	 * the following properties:
	 * 
	 * Integers in each row are sorted in ascending from left to right. Integers in each column are
	 * sorted in ascending from top to bottom.
	 * 
	 * For example,
	 * 
	 * Consider the following matrix:
	 * 
	 * <pre>
	[
	  [1,   4,  7, 11, 15],
	  [2,   5,  8, 12, 19],
	  [3,   6,  9, 16, 22],
	  [10, 13, 14, 17, 24],
	  [18, 21, 23, 26, 30]
	]
	 * </pre>
	 * 
	 * Given target = 5, return true.
	 * 
	 * Given target = 20, return false.
	 */
	public boolean searchIn2DSortedMatrix(int[][] matrix, int target) {
		if (matrix == null || matrix.length < 1 || matrix[0].length < 1)
			return false;

		int row = 0;
		int col = matrix[0].length - 1;

		while (row < matrix.length && col >= 0) {
			if (target == matrix[row][col])
				return true;
			else if (target < matrix[row][col])
				col--;
			else
				row++;
		}

		return false;
	}

	/**
	 * Design an algorithm to find the min and max elements in an array. For example, if A = [3, 2,
	 * 5, 1, 2, 4], you should return 1 for the min and 5 for the max.
	 */
	public static MinMax findMinMax(List<Integer> A) {
		if (A.size() == 1)
			return new MinMax(A.get(0), A.get(0));

		MinMax globalMinMax = MinMax.minMax(A.get(0), A.get(1));
		// Process two elements at a time.
		for (int i = 2; i + 1 < A.size(); i += 2) {
			MinMax localMinMax = MinMax.minMax(A.get(i), A.get(i + 1));
			globalMinMax = new MinMax(Math.min(globalMinMax.smallest, localMinMax.smallest),
					Math.max(globalMinMax.largest, localMinMax.largest));
		}
		// If there is odd number of elements in the array, we still
		// need to compare the last element with the existing answer.
		if ((A.size() % 2) != 0) {
			globalMinMax = new MinMax(Math.min(globalMinMax.smallest, A.get(A.size() - 1)),
					Math.max(globalMinMax.largest, A.get(A.size() - 1)));
		}

		return globalMinMax;
	}

	private static class MinMax {
		public Integer smallest;
		public Integer largest;

		public MinMax(Integer smallest, Integer largest) {
			this.smallest = smallest;
			this.largest = largest;
		}

		private static MinMax minMax(Integer a, Integer b) {
			return Integer.compare(b, a) < 0 ? new MinMax(b, a) : new MinMax(a, b);
		}
	}

	/**
	 * Find the kth largest element in an unsorted array. Note that it is the kth largest element in
	 * the sorted order, not the kth distinct element.
	 * 
	 * For example, Given [3,2,1,5,6,4] and k = 2, return 5.
	 * 
	 * Note: You may assume k is always valid, 1 ? k ? array's length.
	 *
	 */
	public int findKthLargest(int[] nums, int k) {
		if (nums == null || k < 1 || k > nums.length)
			return Integer.MAX_VALUE;
		return findKthSmallest(nums, 0, nums.length - 1, nums.length - k);
	}

	private int findKthSmallest(int[] nums, int start, int end, int k) {
		if (start > end)
			return Integer.MAX_VALUE;
		int left = start;
		// Pick the last value as pivot or randomize a pivot index and swap with the end index.
		int pivot = nums[end];
		for (int i = start; i <= end; i++) {
			if (nums[i] < pivot)
				swap(nums, left++, i);
		}
		swap(nums, left, end); // swap the pivot

		if (left == k)
			return nums[left];
		else if (left < k)
			return findKthSmallest(nums, left + 1, end, k);
		else
			return findKthSmallest(nums, start, left - 1, k);
	}

	private void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}

	public int findMissingIPAddress(Iterable<Integer> sequence) {
		final int NUM_BUCKET = 1 << 16;
		int[] counter = new int[NUM_BUCKET];
		Iterator<Integer> s = sequence.iterator();
		while (s.hasNext()) {
			int idx = s.next() >>> 16;
			++counter[idx];
		}

		for (int i = 0; i < counter.length; ++i) {
			// Look for a bucket that contains less than NUM_BUCKET elements.
			if (counter[i] < NUM_BUCKET) {
				BitSet bitSet = new BitSet(NUM_BUCKET);
				s = sequence.iterator(); // Search from the beginning again.
				while (s.hasNext()) {
					int x = s.next();
					if (i == (x >>> 16)) {
						bitSet.set(((NUM_BUCKET) - 1) & x); // Gets the lower 16 bits of x.
					}
				}

				for (int j = 0; j < NUM_BUCKET; ++j) {
					if (!bitSet.get(j)) {
						return (i << 16) | j;
					}
				}
			}
		}
		throw new IllegalArgumentException("no missing ip address.");
	}

	/**
	 * You are given an array of n integers, each between 0 and n - 1, inclusive. Exactly one
	 * element appears twice, implying that exactly one number between 0 and n - 1 is missing from
	 * the array. How would you compute the duplicate and missing numbers?
	 * 
	 */
	public int[] findDupicateMissingNumber(int[] A) {
		// Compute the XOR of all numbers from 0 to |A| - 1 and all entries in A.
		int missXorDup = 0;
		for (int i = 0; i < A.length; i++) {
			missXorDup ^= i ^ A[i];
		}

		// The bit-fiddling assignment below sets all of bits in differBit to 0 except for the least
		// significatn bit in missXorDup that's 1.
		int differBit = missXorDup & (~(missXorDup - 1));

		// Compute the XOR of all numbers in which the differBit-th bit is 1.
		int missOrDup = 0;
		for (int i = 0; i < A.length; i++) {
			if ((i & differBit) != 0)
				missOrDup ^= i;
			if ((A[i] & differBit) != 0)
				missOrDup ^= A[i];
		}

		// missOrDup is either the missing value or the duplicated entry.
		for (int a : A) {
			if (a == missOrDup)
				return new int[] { missOrDup, missOrDup ^ missXorDup };
		}

		return new int[] { missOrDup ^ missXorDup, missOrDup };
	}

	public static void main(String[] args) {
		SearchingBootCamp solution = new SearchingBootCamp();
		List<Integer> A = new ArrayList<>(Arrays.asList(-14, -10, 2, 108, 108, 243, 285, 285, 285, 401));
		assert solution.searchFirstOccurance(A, 285) == 6;
		assert (solution.computeSquareRootInt(64) == 8);
		assert (solution.computeSquareRootInt(300) == 17);
		assert (solution.computeSquareRootInt(Integer.MAX_VALUE) == 46340);
		int[] nums = new int[] { 3, 2, 1, 5, 6, 4 };
		assert solution.findKthLargest(nums, 2) == 5;
		nums = new int[] { 5, 3, 0, 3, 1, 2 };
		int[] result = solution.findDupicateMissingNumber(nums);
		assert Arrays.equals(result, new int[] { 3, 4 });
	}
}
