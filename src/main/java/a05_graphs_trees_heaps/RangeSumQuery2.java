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
public class RangeSumQuery2 {
	private int n;
	private int[] tree;

	public RangeSumQuery2(int[] nums) {
		if (nums.length > 0) {
			n = nums.length;
			tree = new int[2 * n]; // 2n extra space
			buildTree(nums);
		}
	}

	private void buildTree(int[] nums) {
		for (int i = n, j = 0; i < 2 * n; i++, j++)
			tree[i] = nums[j];
		for (int i = n - 1; i >= 0; i--)
			tree[i] = tree[i * 2] + tree[i * 2 + 1];
	}

	public void update(int pos, int val) {
		pos += n;
		tree[pos] = val;
		while (pos > 0) {
			int left = pos;
			int right = pos;
			if (pos % 2 == 0)
				right = pos + 1;
			else
				left = pos - 1;
			tree[pos / 2] = tree[left] + tree[right];
			pos /= 2;
		}
	}

	public int sumRange(int l, int r) {
		l += n; // get leaf with value l;
		r += n; // get leaf with value r;
		int sum = 0;
		while (l <= r) {
			if ((l % 2) == 1) {
				sum += tree[l];
				l++;
			}
			if ((r % 2) == 0) {
				sum += tree[r];
				r--;
			}
			l /= 2;
			r /= 2;
		}
		return sum;
	}

	public static void main(String[] args) {
		int[] nums = { 1, 4, 3, 6, 7, 5, 2, 0, 9, 8 };
		RangeSumQuery2 solution = new RangeSumQuery2(nums);
		assert solution.sumRange(1, 7) == 27;
		solution.update(2, 5);
		assert solution.sumRange(1, 7) == 29;
	}
}
