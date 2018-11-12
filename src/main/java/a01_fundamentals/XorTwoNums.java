package a01_fundamentals;

public class XorTwoNums {
	class TreeNode {
		int val;
		TreeNode zero, one;
		boolean isEnd;
	}

	class TrieTree {
		TreeNode root;

		public TrieTree() {
			this.root = new TreeNode();
		}

		public void insert(int num) {
			TreeNode current = root;
			int j = 1 << 30;
			while (j > 0) {
				int b = (j & num) == 0 ? 0 : 1;
				if (b == 0 && current.zero == null)
					current.zero = new TreeNode();
				else if (b == 1 && current.one == null)
					current.one = new TreeNode();
				current = b == 0 ? current.zero : current.one;
				j >>= 1;
			}
			current.val = num;
			current.isEnd = true;
		}

	}

	public int findMaximumXOR(int[] nums) {
		if (nums.length < 2)
			return 0;
		TrieTree tree = new TrieTree();
		for (int num : nums) {
			tree.insert(num);
		}
		TreeNode now = tree.root;
		while (now.one == null || now.zero == null)
			now = now.one == null ? now.zero : now.one;
		return findMaximumXOR(now.zero, now.one);
	}

	public int findMaximumXOR(TreeNode zero, TreeNode one) {
		if (zero.isEnd && one.isEnd)
			return one.val ^ zero.val;
		if (zero.zero == null)
			return findMaximumXOR(zero.one, one.zero == null ? one.one : one.zero);
		else if (zero.one == null)
			return findMaximumXOR(zero.zero, one.one == null ? one.zero : one.one);
		else if (one.zero == null)
			return findMaximumXOR(zero.zero, one.one);
		else if (one.one == null)
			return findMaximumXOR(zero.one, one.zero);
		else // split to 2 branches
			return Math.max(findMaximumXOR(zero.zero, one.one), findMaximumXOR(zero.one, one.zero));
	}

	public static void main(String[] args) {
		XorTwoNums solution = new XorTwoNums();
		assert solution.findMaximumXOR(new int[] { 8, 10, 2 }) == 10;
	}
}
