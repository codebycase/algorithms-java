package util;

public class TreeNode {
	public int val;
	public TreeNode left;
	public TreeNode right;
	public TreeNode parent;
	public TreeNode next;

	public TreeNode(int val) {
		this.val = val;
	}

	public TreeNode(int val, TreeNode left, TreeNode right) {
		this(val);
		this.left = left;
		this.right = right;
	}
}
