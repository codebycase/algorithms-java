package c04_stacks_queues;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import util.TreeNode;

/**
 * Given a binary tree, return the level order traversal of its nodes' values. (ie, from left to
 * right, level by level).
 * 
 * <pre>
For example:
Given binary tree [3,9,20,null,null,15,7],
    3
   / \
  9  20
    /  \
   15   7
return its level order traversal as:
[
  [3],
  [9,20],
  [15,7]
]
 * </pre>
 * 
 * @author lchen
 *
 */
public class BinaryTreeLevelOrderTraversal {
	public List<List<Integer>> levelOrderWithIteration(TreeNode root) {
		Queue<TreeNode> queue = new LinkedList<>();
		List<List<Integer>> result = new LinkedList<>();
		if (root == null)
			return result;

		queue.offer(root);
		while (!queue.isEmpty()) {
			int size = queue.size();
			List<Integer> list = new LinkedList<>();
			for (int i = 0; i < size; i++) {
				TreeNode node = queue.poll();
				list.add(node.val);
				if (node.left != null)
					queue.offer(node.left);
				if (node.right != null)
					queue.offer(node.right);
			}
			result.add(0, list);
		}

		return result;
	}

	public List<List<Integer>> levelOrderWithRecursion(TreeNode root) {
		List<List<Integer>> result = new ArrayList<>();
		deepFirstSearch(result, root, 0);
		return result;
	}

	private void deepFirstSearch(List<List<Integer>> result, TreeNode root, int depth) {
		if (root == null)
			return;
		if (depth >= result.size())
			result.add(new ArrayList<Integer>());
		result.get(depth).add(root.val);
		deepFirstSearch(result, root.left, depth + 1);
		deepFirstSearch(result, root.right, depth + 1);
	}

}
