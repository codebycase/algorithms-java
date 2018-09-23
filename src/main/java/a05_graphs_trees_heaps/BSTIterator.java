package a05_graphs_trees_heaps;

import java.util.ArrayDeque;
import java.util.Deque;

import util.TreeNode;

public class BSTIterator {
	final Deque<TreeNode> path;

	public BSTIterator(TreeNode root) {
		path = new ArrayDeque<>();
		buildPathToLeftmostChild(root);
	}

	public boolean hasNext() {
		return !path.isEmpty();
	}

	public int next() {
		TreeNode node = path.pop();
		buildPathToLeftmostChild(node.right);
		return node.val;
	}

	private void buildPathToLeftmostChild(TreeNode node) {
		TreeNode cur = node;
		while (cur != null) {
			path.push(cur);
			cur = cur.left;
		}
	}
}
