package a05_graphs_trees_heaps;

/**
 * Given an integer array nums, find the sum of the elements between indices i and j (i ≤ j),
 * inclusive.
 * 
 * The update(i, val) function modifies nums by updating the element at index i to val.
 * 
 * Example:
 * 
 * Given nums = [1, 3, 5]
 * 
 * sumRange(0, 2) -> 9 <br>
 * update(1, 2) <br>
 * sumRange(0, 2) -> 8
 * 
 * Note:
 * 
 * The array is only modifiable by the update function. <br>
 * You may assume the number of calls to update and sumRange function is distributed evenly.
 * 
 * @author lchen
 *
 */
public class RangeSumQuery {
	private int[] nums;
	private int length;
	private int[] blocks;

	public RangeSumQuery(int[] nums) {
		this.nums = nums;
		double l = Math.sqrt(nums.length);
		length = (int) Math.ceil(nums.length / l);
		blocks = new int[length];
		for (int i = 0; i < nums.length; i++) {
			blocks[i / length] += nums[i];
		}
	}

	public int sumRange(int i, int j) {
		int sum = 0;
		int startBlock = i / length;
		int endBlock = j / length;
		if (startBlock == endBlock) {
			for (int k = i; k <= j; k++)
				sum += nums[k];
		} else {
			for (int k = i; k <= (startBlock + 1) * length - 1; k++)
				sum += nums[k];
			for (int k = startBlock + 1; k <= endBlock - 1; k++)
				sum += blocks[k];
			for (int k = endBlock * length; k <= j; k++)
				sum += nums[k];
		}
		return sum;
	}

	public void update(int i, int val) {
		blocks[i / length] += val - nums[i];
		nums[i] = val;
	}

	public static void main(String[] args) {
		int[] nums = { 1, 4, 3, 6, 7, 5, 2, 0, 9, 8 };
		RangeSumQuery solution = new RangeSumQuery(nums);
		assert solution.sumRange(1, 7) == 27;
		solution.update(2, 5);
		assert solution.sumRange(1, 7) == 29;
	}
}
