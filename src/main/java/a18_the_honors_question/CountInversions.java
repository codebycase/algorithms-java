package a18_the_honors_question;

import java.util.ArrayList;
import java.util.List;

public class CountInversions {
	/**
	 * We have some permutation A of [0, 1, ..., N - 1], where N is the length of A.
	 * 
	 * The number of (global) inversions is the number of i < j with 0 <= i < j < N and A[i] > A[j].
	 * 
	 * The number of local inversions is the number of i with 0 <= i < N and A[i] > A[i+1].
	 * 
	 * Return true if and only if the number of global inversions is equal to the number of local
	 * inversions.
	 * 
	 * <pre>
	Example 1:
	
	Input: A = [1,0,2]
	Output: true
	Explanation: There is 1 global inversion, and 1 local inversion.
	
	Example 2:
	
	Input: A = [1,2,0]
	Output: false
	Explanation: There are 2 global inversions, and 1 local inversion.
	 * </pre>
	 */
	public static boolean isIdealPermutation(int[] A) {
		int max = -1;
		for (int i = 0; i < A.length - 2; i++) {
			max = Math.max(max, A[i]);
			if (max > A[i + 2])
				return false;
		}
		return true;
	}

	public static int countInversions(List<Integer> nums) {
		return countInversions(nums, 0, nums.size() - 1);
	}

	private static int countInversions(List<Integer> nums, int low, int high) {
		if (low >= high)
			return 0;

		int mid = low + (high - low) / 2;
		int count = countInversions(nums, low, mid); // exclude mid
		count += countInversions(nums, mid + 1, high); // include mid
		count += mergeSortAndCountInversions(nums, low, mid, high);
		return count;
	}

	/**
	 * Merge two sorted sublists and count inversions across the two sublists.
	 */
	private static int mergeSortAndCountInversions(List<Integer> nums, int low, int mid, int high) {
		List<Integer> sortedNums = new ArrayList<>();
		int leftStart = low, rightStart = mid + 1, inversionCount = 0;

		while (leftStart <= mid && rightStart <= high) {
			if (Integer.compare(nums.get(leftStart), nums.get(rightStart)) <= 0) {
				sortedNums.add(nums.get(leftStart++));
			} else {
				// nums[leftStart, mid) are the inversions of nums[rightStart]
				inversionCount += mid - leftStart;
				sortedNums.add(nums.get(rightStart++));
			}
		}

		// add all the rest items
		sortedNums.addAll(nums.subList(leftStart, mid));
		sortedNums.addAll(nums.subList(rightStart, high));

		// update with sorted list
		for (Integer num : sortedNums) {
			nums.set(low++, num);
		}

		return inversionCount;
	}

	public static void main(String[] args) {
		assert isIdealPermutation(new int[] { 1, 0, 2 }) == true;
		assert isIdealPermutation(new int[] { 1, 2, 0 }) == false;

		/*
		Random r = new Random();
		for (int times = 0; times < 10; ++times) {
			int n;
			if (args.length == 1) {
				n = Integer.parseInt(args[0]);
			} else {
				n = r.nextInt(10000) + 1;
			}
			List<Integer> A = new ArrayList<>();
			for (int i = 0; i < n; ++i) {
				A.add(r.nextInt(2000001) - 1000000);
			}
			assert (n2Check(A) == countInversions(A));
		}
		*/
	}
}
