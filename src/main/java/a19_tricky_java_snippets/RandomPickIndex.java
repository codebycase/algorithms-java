package a19_tricky_java_snippets;

import java.util.Random;

public class RandomPickIndex {
	int[] nums;
	Random random;

	public RandomPickIndex(int[] nums) {
		this.nums = nums;
		this.random = new Random();
	}

	public int pick(int target) {
		int result = -1;
		int count = 0;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] == target) {
				count++;
				if (random.nextInt(count) == 0) {
					// don't return here due to nextInt(1) is always zero!
					result = i;
				}
			}
		}
		return result;
	}

	public static void main(String[] args) {
		RandomPickIndex solution = new RandomPickIndex(new int[] { 1, 2, 3, 3, 3 });
		for (int i = 0; i < 10; i++) {
			System.out.println(solution.pick(3));
		}
	}
}
