package a05_graphs_trees_heaps;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import util.TreeNode;

/**
 * <ul>
 * <li>Recursive algorithm are well-suited to problems on trees. Remember to include space
 * implicitly allocated on the function call stack when doing space complexity analysis.</li>
 * <li>If each node has a parent field, use it to make your code simpler, and to reduce time and
 * space complexity.</li>
 * </ul>
 * 
 * @author lchen
 *
 */
public class BinaryTreeBootCamp {
	/**
	 * Given a binary tree, determine if it is height-balanced. A height-balanced binary tree is
	 * defined as a binary tree in which the depth of the two subtrees of every node never differ by
	 * more than 1.
	 *
	 */
	public boolean isBalanced(TreeNode root) {
		return checkBalanced(root) != Integer.MIN_VALUE;
	}

	private int checkBalanced(TreeNode root) {
		if (root == null)
			return -1; // base case

		int lH = checkBalanced(root.left);
		if (lH == Integer.MIN_VALUE)
			return Integer.MIN_VALUE;

		int rH = checkBalanced(root.right);
		if (rH == Integer.MIN_VALUE)
			return Integer.MIN_VALUE;

		if (Math.abs(lH - rH) > 1)
			return Integer.MIN_VALUE;
		else
			return Math.max(lH, rH) + 1;
	}

	/**
	 * <pre>
	Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).
	
	For example, this binary tree [1,2,2,3,4,4,3] is symmetric:
	
	1
	/ \
	2   2
	/ \ / \
	3  4 4  3
	But the following [1,2,2,null,3,null,3] is not:
	1
	/ \
	2   2
	\   \
	3    3
	 * </pre>
	 * 
	 * @param root
	 * @return
	 */
	public boolean isSymmetric(TreeNode root) {
		return root == null || checkSymmetric(root.left, root.right);
	}

	// Deep-First Search
	private boolean checkSymmetric(TreeNode left, TreeNode right) {
		if (left == null && right == null)
			return true;
		if (left != null && right != null) {
			return left.val == right.val && checkSymmetric(left.left, right.right)
					&& checkSymmetric(left.right, right.left);
		}
		return false;
	}

	// Bread-First Search
	public boolean isSymmetric2(TreeNode root) {
		Queue<TreeNode> queue = new LinkedList<>();
		if (root == null)
			return true;
		queue.add(root.left);
		queue.add(root.right);
		while (queue.size() > 1) {
			TreeNode left = queue.poll(), right = queue.poll();
			if (left == null && right == null)
				continue;
			if (left == null || right == null)
				return false;
			if (left.val != right.val)
				return false;
			queue.add(left.left);
			queue.add(right.right);
			queue.add(left.right);
			queue.add(right.left);
		}
		return true;
	}

	/**
	 * <pre>
	Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
	
	According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between 
	two nodes v and w as the lowest node in T that has both v and w as descendants (where we allow a 
	node to be a descendant of itself).”
	
	        _______3______
	       /              \
	    ___5__          ___1__
	   /      \        /      \
	   6      _2       0       8
	         /  \
	         7   4
	For example, the lowest common ancestor (LCA) of nodes 5 and 1 is 3. Another example is LCA 
	of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition.
	 * </pre>
	 * 
	 * @author lchen
	 *
	 */
	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
		if (root == null || root == p || root == q)
			return root;
		TreeNode left = lowestCommonAncestor(root.left, p, q);
		TreeNode right = lowestCommonAncestor(root.right, p, q);
		if (left != null && right != null)
			return root;
		else
			return left == null ? right : left;
	}

	/**
	 * Given two nodes in a binary tree, design an algorithm that computes their LCA. Assume that
	 * each node has a parent pointer.
	 * 
	 * @param node0
	 * @param node1
	 * @return
	 */
	// If the nodes are at the same depth, we can move up the tree in tandem from both nodes,
	// stopping
	// at the first common node, which is the LCA.
	public TreeNode lowestCommonAncestor(TreeNode node0, TreeNode node1) {
		int depth0 = getDepth(node0);
		int depth1 = getDepth(node1);
		// Make sure node0 as the deeper node to simplify the code.
		if (depth1 > depth0) {
			TreeNode temp = node0;
			node0 = node1;
			node1 = temp;
		}
		// Ascends from the deeper node
		int depthDiff = Math.abs(depth0 - depth1);
		while (depthDiff > 0) {
			node0 = node0.parent;
			depthDiff--;
		}
		// Now ascend both nodes until we reach the LCA
		while (node0 != node1) {
			node0 = node0.parent;
			node1 = node1.parent;
		}
		return node0;
	}

	private int getDepth(TreeNode node) {
		int depth = 0;
		while (node.parent != null) {
			depth++;
			node = node.parent;
		}
		return depth;
	}

	// We can also alternately moving upwards from the two nodes and storing the nodes visited in a
	// hash table. Each time we visited a node we check to see if it has been visited before._
	public TreeNode lowestCommonAncestor2(TreeNode node0, TreeNode node1) {
		Set<TreeNode> hash = new HashSet<>();
		while (node0 != null || node1 != null) {
			if (node0 != null) {
				if (!hash.add(node0))
					return node0;
				node0 = node0.parent;
			}
			if (node1 != null) {
				if (!hash.add(node1))
					return node1;
				node1 = node1.parent;
			}
		}
		throw new IllegalArgumentException("node0 and node1 are not in the same tree");
	}

	/**
	 * Consider a binary tree in which each node contains a binary digit. Design an algorithm to
	 * compute the sum of the binary numbers represented by the root-to-leaf paths.
	 * 
	 * @param root
	 * @return
	 */
	public int sumRootToLeaf(TreeNode root) {
		return sumRootToLeaf(root, 0);
	}

	private int sumRootToLeaf(TreeNode node, int pathSum) {
		if (node == null)
			return 0;

		pathSum = pathSum * 2 + node.val;
		if (node.left == null && node.right == null)
			return pathSum;

		return sumRootToLeaf(node.left, pathSum) + sumRootToLeaf(node.right, pathSum);
	}

	/**
	 * Implement a preorder traversal without recursion
	 * 
	 */
	public List<Integer> preorderTraverse(TreeNode root) {
		List<Integer> result = new ArrayList<>();
		Deque<TreeNode> stack = new LinkedList<>();

		if (root != null) {
			stack.push(root);
		}

		while (!stack.isEmpty()) {
			TreeNode node = stack.pop();
			result.add(node.val); // add before going to children
			if (node.right != null)
				stack.push(node.right);
			if (node.left != null)
				stack.push(node.left);
		}

		return result;
	}

	/**
	 * Implement an in-order traversal without recursion
	 */
	public List<Integer> inorderTraverse(TreeNode root) {
		List<Integer> result = new ArrayList<>();
		Deque<TreeNode> stack = new LinkedList<>();

		TreeNode node = root;
		while (!stack.isEmpty() || node != null) {
			if (node != null) {
				stack.push(node);
				node = node.left;
			} else {
				node = stack.pop();
				result.add(node.val); // add after all left children
				node = node.right;
			}
		}

		return result;
	}

	/**
	 * Implement a postorder traversal without recursion
	 */
	public List<Integer> postorderTraversal(TreeNode root) {
		LinkedList<Integer> result = new LinkedList<>();
		Deque<TreeNode> stack = new ArrayDeque<>();
		TreeNode p = root;
		while (!stack.isEmpty() || p != null) {
			if (p != null) {
				stack.push(p);
				result.addFirst(p.val); // Reverse the process of preorder
				p = p.right; // Reverse the process of preorder
			} else {
				TreeNode node = stack.pop();
				p = node.left; // Reverse the process of preorder
			}
		}
		return result;
	}

	/**
	 * Write a nonrecursive program for computing the inorder traversal sequence for a binary tree.
	 * Assume nodes have parent fields.
	 * 
	 * Idea: To complete this algorithm, we need to know when we return to a parent if the just
	 * completed subtree was the parent's left child (in which case we need to visit the parent and
	 * then traverse its right subtree) or a right substree (in which case we have completed
	 * traversing the parent).
	 * 
	 * @param root
	 * @return
	 */
	public List<Integer> inorderTraversal(TreeNode root) {
		List<Integer> result = new ArrayList<>();
		TreeNode prev = null, curr = root;

		while (curr != null) {
			TreeNode next = null;
			if (curr.parent == prev) {
				// we came down to curr from prev
				if (curr.left != null)
					next = curr.left;
				else {
					result.add(curr.val);
					// done with left, go right or go up
					next = curr.right != null ? curr.right : curr.parent;
				}
			} else if (curr.left == prev) {
				result.add(curr.val);
				next = curr.right != null ? curr.right : curr.parent;
			} else {
				// done with both children , so move up.
				next = curr.parent;
			}

			prev = curr;
			curr = next;
		}

		return result;
	}

	/**
	 * Given a binary tree, compute a linked list from the leaves of the binary tree.
	 * 
	 * @param root
	 * @return
	 */
	public List<TreeNode> createListOfLeaves(TreeNode root) {
		List<TreeNode> leaves = new ArrayList<>();
		addLeavesLeftToRight(root, leaves);
		return leaves;
	}

	private void addLeavesLeftToRight(TreeNode node, List<TreeNode> leaves) {
		if (node == null)
			return;
		if (node.left == null && node.right == null)
			leaves.add(node);
		else {
			addLeavesLeftToRight(node.left, leaves);
			addLeavesLeftToRight(node.right, leaves);
		}
	}

	/**
	 * We can compute the nodes on the path from the root to the leftmost leaf and the leaves in the
	 * left subtree in one traversal. After that, we find the leaves in the right subtree followed
	 * by the nodes from the rightmost leaf to the root with another traversal.
	 * 
	 * @param root
	 * @return
	 */
	public List<TreeNode> findExteriorOfBinaryTree(TreeNode root) {
		List<TreeNode> exterior = new LinkedList<>();
		if (root != null) {
			exterior.add(root);
			exterior.addAll(leftBoundaryAndLeaves(root.left, true));
			exterior.addAll(rightBoundaryAndLeaves(root.right, true));
		}
		return exterior;
	}

	private List<TreeNode> leftBoundaryAndLeaves(TreeNode node, boolean isBoundary) {
		List<TreeNode> result = new LinkedList<>();
		if (node != null) {
			if (isBoundary || isLeaf(node))
				result.add(node);
			result.addAll(leftBoundaryAndLeaves(node.left, isBoundary));
			result.addAll(leftBoundaryAndLeaves(node.right, isBoundary && node.left == null));
		}
		return result;
	}

	private List<TreeNode> rightBoundaryAndLeaves(TreeNode node, boolean isBoundary) {
		List<TreeNode> result = new LinkedList<>();
		if (node != null) {
			result.addAll(rightBoundaryAndLeaves(node.left, isBoundary && node.right == null));
			result.addAll(rightBoundaryAndLeaves(node.right, isBoundary));
			if (isBoundary || isLeaf(node))
				result.add(node);
		}
		return result;
	}

	private boolean isLeaf(TreeNode node) {
		return node.left == null && node.right == null;
	}

	/**
	 * Write a program that takes a perfect binary tree, and set each node's level-next field to the
	 * node on its right, if one exists.
	 * 
	 * @param node
	 */
	public void constructRightSibling(TreeNode node) {
		TreeNode leftStart = node;
		while (leftStart != null && leftStart.left != null) {
			populateLowerLevelNextField(leftStart);
			leftStart = leftStart.left;
		}
	}

	private void populateLowerLevelNextField(TreeNode startNode) {
		TreeNode node = startNode;
		while (node != null) {
			node.left.next = node.right;
			if (node.next != null) {
				node.right.next = node.next.left;
			}
			node = node.next;
		}
	}

	public static void main(String[] args) {

	}
}
