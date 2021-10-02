package a18_the_honors_question;

import util.ListNode;
import util.TreeNode;

/**
 * SLL: Singly Linked List <br>
 * DLL: Doubly Linked List <br>
 * BST: Binary Search Tree
 * 
 * @author lchen
 *
 */
public class LinkedListAndBinaryTree {
	public TreeNode sortedSLLToBalancedBST(ListNode head) {
		if (head == null)
			return null;
		return sortedSLLToBalancedST(head, null);
	}

	private TreeNode sortedSLLToBalancedST(ListNode head, ListNode tail) {
		if (head == tail)
			return null;

		ListNode slow = head, fast = head;
		while (fast != tail && fast.next != tail) {
			slow = slow.next;
			fast = fast.next.next;
		}

		TreeNode root = new TreeNode(slow.val);
		root.left = sortedSLLToBalancedST(head, slow);
		root.right = sortedSLLToBalancedST(slow.next, tail);

		return root;
	}

	// must use a global head anchor track the current location!
	private ListNode headAnchor;

	public ListNode sortedDLLToBalancedBST(ListNode head) {
		if (head == null)
			return null;
		// caculate length of list
		int length = 0;
		ListNode node = head;
		while (node != null) {
			length++;
			node = node.next;
		}
		this.headAnchor = head;
		return sortedDLLToBalancedBST(0, length);
	}

	private ListNode sortedDLLToBalancedBST(int start, int end) {
		if (start >= end)
			return null;
		int mid = start + (end - start) / 2;
		ListNode left = sortedDLLToBalancedBST(start, mid);
		ListNode curr = new ListNode(headAnchor.val, left, null);
		headAnchor = headAnchor.next;
		curr.next = sortedDLLToBalancedBST(mid + 1, end);
		return curr;
	}

	// Transform a BST into a circular sorted DLL!
	// left subtree + node + right subtree -> make it circular
	public TreeNode balancedBSTToSortedDDL(TreeNode node) {
		if (node == null)
			return null;

		TreeNode lHead = balancedBSTToSortedDDL(node.left);
		TreeNode rHead = balancedBSTToSortedDDL(node.right);

		// append node to the list from the left subtree
		TreeNode lTail = null;
		if (lHead != null) {
			lTail = lHead.left;
			lTail.right = node; // Add node after lTail
			node.left = lTail; // Double link it
			lTail = node; // Update lTail to node
		} else {
			lHead = lTail = node;
		}

		// append the list from right substree to node
		TreeNode rTail = null;
		if (rHead != null) {
			rTail = rHead.left;
			lTail.right = rHead; // Add rHead after lTail
			rHead.left = lTail; // Double link it
		} else {
			rTail = lTail;
		}

		// make it circular
		rTail.right = lHead;
		lHead.left = rTail;

		return lHead;
	}

	public TreeNode mergeTwoBSTs(TreeNode treeA, TreeNode treeB) {
		treeA = balancedBSTToSortedDDL(treeA);
		treeB = balancedBSTToSortedDDL(treeB);
		// break the circular first!
		treeA.left.right = null;
		treeB.left.right = null;
		treeA.left = null;
		treeB.left = null;
		return sortedDLLToBalancedBST(mergeTwoSortedDLLs(treeA, treeB));
	}

	private TreeNode headPointer = null;

	private TreeNode sortedDLLToBalancedBST(TreeNode node) {
		headPointer = node;
		return sortedDLLToBalancedBST2(0, countLength(node));
	}

	private TreeNode sortedDLLToBalancedBST2(int start, int end) {
		if (start >= end)
			return null;
		int mid = start + (end - start) / 2;
		TreeNode left = sortedDLLToBalancedBST2(start, mid);
		TreeNode curr = new TreeNode(headPointer.val, left, null);
		headPointer = headPointer.right;
		curr.right = sortedDLLToBalancedBST2(mid + 1, end);
		return curr;
	}

	private TreeNode mergeTwoSortedDLLs(TreeNode A, TreeNode B) {
		TreeNode dummyHead = new TreeNode();
		TreeNode current = dummyHead;
		TreeNode p1 = A, p2 = B;

		while (p1 != null && p2 != null) {
			if (Integer.compare(p1.val, p2.val) < 0) {
				current.right = p1;
				p1 = p1.right;
			} else {
				current.right = p2;
				p2 = p2.right;
			}
			current = current.right;
		}

		if (p1 != null) {
			current.right = p1;
		}
		if (p2 != null) {
			current.right = p2;
		}

		return dummyHead.right;
	}

	private static int countLength(TreeNode node) {
		int len = 0;
		while (node != null) {
			len++;
			node = node.right;
		}
		return len;
	}

	private static void inorderTraversal(ListNode node, int prev, int depth) {
		if (node != null) {
			inorderTraversal(node.prev, prev, depth + 1);
			assert node.val >= prev;
			System.out.println(node.val + "; depth = " + depth);
			inorderTraversal(node.next, prev, depth + 1);
		}
	}

	private static void inorderTraversal(TreeNode node, int prev, int depth) {
		if (node != null) {
			inorderTraversal(node.left, prev, depth + 1);
			assert node.val >= prev;
			System.out.println(node.val + "; depth = " + depth);
			inorderTraversal(node.right, node.val, depth + 1);
		}
	}

	public static void main(String[] args) {
		LinkedListAndBinaryTree solution = new LinkedListAndBinaryTree();

		// DLL to BST
		ListNode temp0 = new ListNode(0, null, null);
		ListNode temp1 = new ListNode(1, null, null);
		ListNode temp2 = new ListNode(2, null, null);
		ListNode temp3 = new ListNode(3, null, null);
		temp0.next = temp1;
		temp1.next = temp2;
		temp2.next = temp3;
		temp3.next = null;
		temp0.prev = null;
		temp1.prev = temp0;
		temp2.prev = temp1;
		temp3.prev = temp2;

		ListNode result = solution.sortedDLLToBalancedBST(temp0);
		inorderTraversal(result, Integer.MIN_VALUE, 0);

		// BST to DLL
		TreeNode tree = new TreeNode(3);
		tree.left = new TreeNode(2);
		tree.left.left = new TreeNode(1);
		tree.right = new TreeNode(5);
		tree.right.left = new TreeNode(4);
		tree.right.right = new TreeNode(6);
		TreeNode list = solution.balancedBSTToSortedDDL(tree);
		// break the circular
		list.left.right = null;
		list.left = null;
		TreeNode node = list;
		int prev = Integer.MIN_VALUE;
		while (node != null) {
			assert node.val >= prev;
			System.out.print(node.val);
			prev = node.val;
			node = node.right;
		}

		// merge two BSTs
		// 3
		// 2 5
		// 1 4 6
		TreeNode L = new TreeNode(3);
		L.left = new TreeNode(2);
		L.left.left = new TreeNode(1);
		L.right = new TreeNode(5);
		L.right.left = new TreeNode(4);
		L.right.right = new TreeNode(6);
		// 7
		// 2 8
		// 0
		TreeNode R = new TreeNode(7);
		R.left = new TreeNode(2);
		R.left.left = new TreeNode(0);
		R.right = new TreeNode(8);

		TreeNode root = solution.mergeTwoBSTs(L, R);
		// should output 0 1 2 2 3 4 5 6 7 8
		inorderTraversal(root, Integer.MIN_VALUE, 0);
	}
}
