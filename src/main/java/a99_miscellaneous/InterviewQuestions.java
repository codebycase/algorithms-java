package a99_miscellaneous;

public class InterviewQuestions {
	/**
	 * Given a contiguous sequence of numbers in which each number repeats thrice, there is exactly
	 * one missing number. <br>
	 * Find the missing number. <br>
	 * eg: 11122333 : Missing number 2 <br>
	 * 11122233344455666 Missing number 5
	 */
	public int tripleBinarySearch(int[] nums) {
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
		InterviewQuestions solution = new InterviewQuestions();
		int[] nums = new int[] { 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 6 };
		assert solution.tripleBinarySearch(nums) == 5;
	}
}
