package a05_graphs_trees_heaps;

/**
 * For example, finding the sum of all the elements in an array from indices L to R.
 * 
 * @author lchen
 *
 */
public class SegmentTree {
	private int[] array = { 1, 3, 5, 7, 9, 11 };
	private int[] tree = new int[2 * array.length + 1 + 1]; // 1-based tree

	/**
	 * First, figure what needs to be stored in the node, here we store sum of all the elements in
	 * that interval represented by the node. O(N)
	 */
	public void build(int node, int start, int end) {
		if (start == end) {
			// leaf node will have a single element
			tree[node] = array[start];
		} else {
			int mid = (start + end) / 2;
			// recurse on the left child
			build(2 * node, start, mid);
			// recurse on the right child
			build(2 * node + 1, mid + 1, end);
			// internal node will have the sum
			tree[node] = tree[2 * node] + tree[2 * node + 1];
		}
	}

	/**
	 * To update an element, look at the interval in which the element is present and recurse
	 * accordingly on the left or the right child. Log(N)
	 */
	public void update(int node, int start, int end, int idx, int val) {
		if (start == end) {
			array[idx] += val;
			tree[node] += val;
		} else {
			int mid = (start + end) / 2;
			if (start <= idx && idx <= mid) {
				// if idx is in the left child, recurse on the left child
				update(2 * node, start, mid, idx, val);
			} else {
				// if idx is in the right child, recurse on the right child
				update(2 * node + 1, mid + 1, end, idx, val);
			}
			// internal node will have the sum of both children
			tree[node] = tree[2 * node] + tree[2 * node + 1];
		}
	}

	public int query(int node, int start, int end, int l, int r) {
		if (r < start || l > end) {
			// node's range is completely outside the given range
			return 0;
		}
		if (l <= start && end <= r) {
			// node's range is completely inside the given range
			return tree[node];
		}
		// node's range is partially inside and partially outside the given range
		int mid = (start + end) / 2;
		int p1 = query(2 * node, start, mid, l, r);
		int p2 = query(2 * node + 1, mid + 1, end, l, r);
		return p1 + p2;
	}
}
