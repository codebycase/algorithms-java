package a05_graphs_trees_heaps;

public class RedBlackBST<Key extends Comparable<Key>, Value> {
	private static final boolean RED = true;
	private static final boolean BLACK = false;

	private Node root; // root of the BST

	private class Node {
		private Key key;
		private Value val;
		private Node left, right;
		private boolean color;
		private int size;

		public Node(Key key, Value val, boolean color, int size) {
			this.key = key;
			this.val = val;
			this.color = color;
			this.size = size;
		}
	}

	public RedBlackBST() {
	}

	private boolean isRed(Node node) {
		return node == null ? false : node.color == RED;
	}

	private int size(Node node) {
		return node == null ? 0 : node.size;
	}

	public int size() {
		return size(root);
	}

	public boolean isEmpty() {
		return root == null;
	}

	/* Standard BST search */

	public Value get(Key key) {
		if (key == null)
			throw new IllegalArgumentException();
		return get(root, key);
	}

	private Value get(Node node, Key key) {
		while (node != null) {
			int cmp = key.compareTo(node.key);
			if (cmp < 0)
				node = node.left;
			else if (cmp > 0)
				node = node.right;
			else
				return node.val;
		}
		return null;
	}

	public boolean contains(Key key) {
		return get(key) != null;
	}

	/* Red-blank tree insertion */

	public void put(Key key, Value val) {
		if (key == null)
			throw new IllegalArgumentException();
		if (val == null) {
			// delete(key);
			return;
		}
		root = put(root, key, val);
		root.color = BLACK;
	}

	private Node put(Node node, Key key, Value val) {
		if (node == null)
			return new Node(key, val, RED, 1);

		int cmp = key.compareTo(node.key);
		if (cmp < 0)
			node.left = put(node.left, key, val);
		else if (cmp > 0)
			node.right = put(node.right, key, val);
		else
			node.val = val;

		// fix-up any right-leaning links
		if (isRed(node.right) && !isRed(node.left))
			node = rotateLeft(node);
		if (isRed(node.left) && isRed(node.left.left))
			node = rotateRight(node);
		if (isRed(node.left) && isRed(node.right))
			flipColors(node);

		node.size = size(node.left) + size(node.right) + 1;

		return node;
	}

	private Node rotateLeft(Node node) {
		Node temp = node.right;
		node.right = temp.left;
		temp.left = node;
		temp.color = temp.left.color;
		temp.left.color = RED;
		temp.size = node.size;
		node.size = size(node.left) + size(node.right) + 1;
		return temp;
	}

	private Node rotateRight(Node node) {
		Node temp = node.left;
		node.left = temp.right;
		temp.right = node;
		temp.color = temp.right.color;
		temp.right.color = RED;
		temp.size = node.size;
		node.size = size(node.left) + size(node.right) + 1;
		return temp;
	}

	private void flipColors(Node node) {
		node.color = !node.color;
		node.left.color = !node.left.color;
		node.right.color = !node.right.color;
	}
}
