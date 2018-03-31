package a06_sorting_searching;

public class BinarySearch {
	public static int binarySearch(int[] a, int x) {
		int low = 0;
		int high = a.length - 1;
		int mid;

		while (low <= high) {
			mid = (low + high) / 2;
			if (a[mid] < x) {
				low = mid + 1;
			} else if (a[mid] > x) {
				high = mid - 1;
			} else {
				return mid;
			}
		}
		return -1;
	}

	public static int binarySearchRecursive(int[] a, int x, int low, int high) {
		if (low > high)
			return -1; // Error

		int mid = (low + high) / 2;
		if (a[mid] < x) {
			return binarySearchRecursive(a, x, mid + 1, high);
		} else if (a[mid] > x) {
			return binarySearchRecursive(a, x, low, mid - 1);
		} else {
			return mid;
		}
	}

	// Recursive algorithm to return the closest element
	public static int binarySearchRecursiveClosest(int[] a, int x, int low, int high) {
		if (low > high) { // high is on the left side now
			if (high < 0)
				return low;
			if (low >= a.length)
				return high;
			if (x - a[high] < a[low] - x) {
				return high;
			}
			return low;
		}

		int mid = (low + high) / 2;
		if (a[mid] < x) {
			return binarySearchRecursiveClosest(a, x, mid + 1, high);
		} else if (a[mid] > x) {
			return binarySearchRecursiveClosest(a, x, low, mid - 1);
		} else {
			return mid;
		}
	}

	/**
	 * Given a contiguous sequence of numbers in which each number repeats thrice, there is exactly
	 * one missing number. <br>
	 * Find the missing number. <br>
	 * eg: 11122333 : Missing number 2 <br>
	 * 11122233344455666 Missing number 5
	 */
	public static int tripleBinarySearch(int[] nums) {
		int i = 0, j = nums.length - 1;
		while (i < j - 1) { // skip loop if less than 3 nums
			int mid = i + (j - i) / 2;
			int inI = mid, inJ = mid;
			while (inI >= 0 && nums[inI] == nums[mid])
				inI--;
			while (inJ < nums.length && nums[inJ] == nums[mid])
				inJ++;
			if (inJ - inI == 3) // 2 nums between
				return nums[mid];
			if (inI > 0 && (inI + 1) % 3 != 0)
				j = inI;
			else
				i = inJ;
		}
		return nums[i];
	}

	public static void main(String[] args) {
		int[] array = { 3, 6, 9, 12, 15, 18 };
		for (int i = 0; i < 20; i++) {
			int loc = binarySearch(array, i);
			int loc2 = binarySearchRecursive(array, i, 0, array.length - 1);
			int loc3 = binarySearchRecursiveClosest(array, i, 0, array.length - 1);
			System.out.println(i + ": " + loc + " " + loc2 + " " + loc3);
		}
		int[] nums = new int[] { 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 6 };
		assert tripleBinarySearch(nums) == 5;
	}
}
