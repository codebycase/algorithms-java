package a05_graphs_trees_heaps;

public class BinaryIndexedTree {
	final static int MAX = 1000;
	static int[] tree = new int[MAX];

	void constructTree(int arr[]) {
		for (int i = 1; i < tree.length; i++)
			tree[i] = 0;
		for (int i = 0; i < arr.length; i++)
			update(i, arr[i]);
	}

	int getSum(int index) {
		int sum = 0;
		index = index + 1; // index is 1 more than arr[]
		while (index > 0) {
			sum += tree[index];
			index -= index & -index;
		}
		return sum;
	}

	// Add val to tree[i] and all of its ancestores
	public static void update(int index, int val) {
		index = index + 1;
		while (index <= tree.length) {
			tree[index] += val;
			index += index & -index;
		}
	}

	public static void main(String args[]) {
		int freq[] = { 2, 1, 1, 3, 2, 3, 4, 5, 6, 7, 8, 9 };
		BinaryIndexedTree tree = new BinaryIndexedTree();
		tree.constructTree(freq);
		System.out.println("Sum of elements in arr[0..5]" + " is " + tree.getSum(5));
		// Update value for both array and tree
		freq[3] += 6;
		update(3, 6);
		System.out.println("Sum of elements in arr[0..5]" + " after update is " + tree.getSum(5));
	}
}
