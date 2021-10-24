package a05_graphs_trees_heaps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

import util.Interval;
import util.ListNode;
import util.TreeNode;

public class BinarySearchTreeBootCamp {
	/**
	 * Given a binary search tree (BST), find the lowest common ancestor (LCA) of two given nodes in
	 * the BST.
	 */
	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
		if (root.val > p.val && root.val > q.val) {
			return lowestCommonAncestor(root.left, p, q);
		} else if (root.val < p.val && root.val < q.val) {
			return lowestCommonAncestor(root.right, p, q);
		} else {
			return root;
		}
	}

	/**
	 * <pre>
	Given a binary tree, determine if it is a valid binary search tree (BST).
	
	Assume a BST is defined as follows:
	
	The left subtree of a node contains only nodes with keys less than the node's key.
	The right subtree of a node contains only nodes with keys greater than the node's key.
	Both the left and right subtrees must also be binary search trees.
	Example 1:
	    2
	   / \
	  1   3
	Binary tree [2,1,3], return true.
	Example 2:
	    1
	   / \
	  2   3
	Binary tree [1,2,3], return false.
	 * </pre>
	 * 
	 * @author lchen
	 *
	 */
	public boolean isValidBST(TreeNode root) {
		return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
	}

	private boolean isValidBST(TreeNode root, long minVal, long maxVal) {
		if (root == null)
			return true;
		if (root.val >= maxVal || root.val <= minVal)
			return false;
		return isValidBST(root.left, minVal, root.val) && isValidBST(root.right, root.val, maxVal);
	}

	/**
	 * Given a binary search tree with non-negative values, find the minimum absolute difference
	 * between values of any two nodes.
	 */
	private TreeNode prev;

	public int minDifference(TreeNode node) {
		if (node == null)
			return Integer.MAX_VALUE;
		int minDiff = minDifference(node.left);
		if (prev != null)
			minDiff = Math.min(minDiff, Math.abs(node.val - prev.val));
		prev = node;
		minDiff = Math.min(minDiff, minDifference(node.right));
		return minDiff;
	}

	/**
	 * Write a program that takes as input a BST and an interval and returns the BST keys that lie
	 * in the interval.
	 */
	public List<Integer> rangeLookupInBST(TreeNode tree, Interval interval) {
		List<Integer> result = new ArrayList<>();
		rangeLookupInBST(tree, interval, result);
		return result;
	}

	private void rangeLookupInBST(TreeNode tree, Interval interval, List<Integer> result) {
		if (tree == null)
			return;
		if (interval.start <= tree.val && tree.val <= interval.end) {
			rangeLookupInBST(tree.left, interval, result);
			result.add(tree.val);
			rangeLookupInBST(tree.right, interval, result);
		} else if (interval.start > tree.val) {
			rangeLookupInBST(tree.right, interval, result);
		} else {
			rangeLookupInBST(tree.left, interval, result);
		}
	}

	/**
	 * Given a non-empty binary search tree and a target value, find the value in the BST that is
	 * closest to the target.
	 */
	public int closestValue(TreeNode root, double target) {
		int result = root.val;
		while (root != null) {
			if (Math.abs(target - root.val) < Math.abs(target - result))
				result = root.val;
			root = root.val > target ? root.left : root.right;
		}
		return result;
	}

	/**
	 * Given a non-empty binary search tree and a target value, find k values in the BST that are
	 * closest to the target.
	 */
	public List<Integer> closestKValues(TreeNode root, double target, int k) {
		LinkedList<Integer> list = new LinkedList<Integer>();
		closestKValues(list, root, target, k);
		return list;
	}

	// in-order traverse
	private boolean closestKValues(LinkedList<Integer> list, TreeNode node, double target, int k) {
		if (node == null)
			return false;

		if (closestKValues(list, node.left, target, k))
			return true;

		if (list.size() == k) {
			if (Math.abs(list.getFirst() - target) < Math.abs(node.val - target))
				return true;
			else
				list.removeFirst();
		}

		list.addLast(node.val);
		return closestKValues(list, node.right, target, k);
	}

	/**
	 * <pre>
	Given a binary tree, find the largest subtree which is a Binary Search Tree (BST), where largest means subtree with largest number of nodes in it.
	
	Note:
	A subtree must include all of its descendants.
	Here's an example:
	10
	/ \
	5  15
	/ \   \ 
	1   8   7
	The Largest BST Subtree in this case is the highlighted one. 
	The return value is the subtree's size, which is 3.
	 * </pre>
	 */
	public int largestBSTSubtree(TreeNode root) {
		if (root == null)
			return 0;
		if (root.left == null && root.right == null)
			return 1;
		if (isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE))
			return countTreeNode(root);
		return Math.max(largestBSTSubtree(root.left), largestBSTSubtree(root.right));
	}

	private int countTreeNode(TreeNode root) {
		if (root == null)
			return 0;
		if (root.left == null && root.right == null)
			return 1;
		return 1 + countTreeNode(root.left) + countTreeNode(root.right);
	}

	/**
	 * Given a singly linked list where elements are sorted in ascending order, convert it to a
	 * height balanced BST.
	 * 
	 */
	public TreeNode sortedListToBST(ListNode head) {
		if (head == null)
			return null;
		return convertToBST(head, null);
	}

	private TreeNode convertToBST(ListNode head, ListNode tail) {
		ListNode slow = head;
		ListNode fast = head;
		if (head == tail)
			return null;

		while (fast != tail && fast.next != tail) {
			fast = fast.next.next;
			slow = slow.next;
		}
		TreeNode thead = new TreeNode(slow.val);
		thead.left = convertToBST(head, slow);
		thead.right = convertToBST(slow.next, tail);
		return thead;
	}

	/**
	 * Given a BST which may be unbalanced. convert it into a balanced BST that has minimum possible
	 * height.
	 * 
	 * <pre>
	Input:
	         4
	        /
	       3
	      /
	     2
	    /
	   1
	Output:
	      3            3           2
	    /  \         /  \        /  \
	   1    4   OR  2    4  OR  1    3   OR ..
	    \          /                   \
	     2        1                     4
	
	Input:
	          4
	        /   \
	       3     5
	      /       \
	     2         6
	    /           \
	   1             7
	Output:
	       4
	    /    \
	   2      6
	 /  \    /  \
	1    3  5    7
	 * </pre>
	 * 
	 * @param root
	 * @return
	 */
	public TreeNode convertToBalancedTree(TreeNode root) {
		List<TreeNode> nodes = new ArrayList<>();
		storeBSTNodes(root, nodes);
		return buildBalancedTree(nodes, 0, nodes.size() - 1);
	}

	// in-order traverse
	private void storeBSTNodes(TreeNode node, List<TreeNode> nodes) {
		if (node == null)
			return;
		storeBSTNodes(node.left, nodes);
		nodes.add(node);
		storeBSTNodes(node.right, nodes);
	}

	private TreeNode buildBalancedTree(List<TreeNode> nodes, int start, int end) {
		if (start > end)
			return null;
		// get mid node and make it root
		int mid = start + (end - start) / 2;
		TreeNode node = nodes.get(mid);

		// use index in in-order traverse
		node.left = buildBalancedTree(nodes, start, mid - 1);
		node.right = buildBalancedTree(nodes, mid + 1, end);

		return node;
	}
	
	/**
	 * Suppose you are given the sequence in which keys are visited in an preorder traversal of a
	 * BST, and all keys are distinct. Can you reconstruct the BST from the sequence?
	 * 
	 * The complexity is O(n)
	 */
	private Integer rootIdx;

	public TreeNode rebuildBSTFromPreorder(List<Integer> preorderSequence) {
		rootIdx = 0;
		return rebuildBSTFromPreorderOnValueRange(preorderSequence, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	private TreeNode rebuildBSTFromPreorderOnValueRange(List<Integer> preorderSequence, Integer lowerBound,
			Integer upperBound) {
		if (rootIdx == preorderSequence.size())
			return null;
		Integer root = preorderSequence.get(rootIdx);
		if (root < lowerBound || root > upperBound)
			return null;
		rootIdx++;
		TreeNode leftSubtree = rebuildBSTFromPreorderOnValueRange(preorderSequence, lowerBound, root);
		TreeNode rightSubtree = rebuildBSTFromPreorderOnValueRange(preorderSequence, root, upperBound);
		return new TreeNode(root, leftSubtree, rightSubtree);
	}

	/**
	 * For example, if the three arrays are [5, 10, 15], [3, 6, 9, 12, 15] and [8, 16, 24], then 15,
	 * 15, 16 lie in the smallest possible interval which is 1.
	 * 
	 * <br>
	 * 
	 * Idea: We can begin with the first element of each arrays, [5, 3, 8]. The smallest interval
	 * whose left end point is 3 has length 8 - 3 = 5. The element after 3 is 6, so we continue with
	 * the triple (5, 6, 8). The smallest interval whose left end point is 5 has length 8 - 5 = 3.
	 * The element after 5 is 10, so we continue with the triple (10, 6, 8)...
	 */	
  public int minDistanceInKSortedArrays(List<List<Integer>> sortedArrays) {
    int result = Integer.MAX_VALUE;
    // int[3]: arrayIdx, valueIdx, value
    NavigableSet<int[]> currentHeads = new TreeSet<>((a, b) -> (a[2] - b[2] == 0 ? a[0] - b[0] : a[2] - b[2]));

    for (int i = 0; i < sortedArrays.size(); i++) {
      currentHeads.add(new int[] { i, 0, sortedArrays.get(i).get(0) });
    }

    while (true) {
      result = Math.min(result, currentHeads.last()[2] - currentHeads.first()[2]);
      int[] data = currentHeads.pollFirst();
      // Return if some array has no remaining elements.
      int nextValueIdx = data[1] + 1;
      if (nextValueIdx >= sortedArrays.get(data[0]).size()) {
        return result;
      }
      currentHeads.add(new int[] { data[0], nextValueIdx, sortedArrays.get(data[0]).get(nextValueIdx) });
    }
  }
	
	public static void main(String[] args) {
		BinarySearchTreeBootCamp bootCamp = new BinarySearchTreeBootCamp();
		List<Integer> preorder = Arrays.asList(3, 2, 1, 5, 4, 6);
		TreeNode tree = bootCamp.rebuildBSTFromPreorder(preorder);
		assert (3 == tree.val);
		assert (2 == tree.left.val);
		assert (1 == tree.left.left.val);
		assert (5 == tree.right.val);
		assert (4 == tree.right.left.val);
		assert (6 == tree.right.right.val);

		List<List<Integer>> sortedArrays = new ArrayList<>();
		sortedArrays.add(Arrays.asList(5, 10, 15));
		sortedArrays.add(Arrays.asList(3, 6, 9, 12, 15));
		sortedArrays.add(Arrays.asList(8, 16, 24));
		int result = bootCamp.minDistanceInKSortedArrays(sortedArrays);
		assert result == 1;
		
		tree = new TreeNode(10);
		tree.left = new TreeNode(8);
		tree.left.left = new TreeNode(7);
		tree.left.left.left = new TreeNode(6);
		tree.left.left.left.left = new TreeNode(5);
		tree = bootCamp.convertToBalancedTree(tree);
		assert tree.val == 7;
	}

}
