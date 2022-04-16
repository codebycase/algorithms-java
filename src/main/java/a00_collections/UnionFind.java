package a00_collections;

/**
 * The UnionFind class represents a union–find data type (also known as the disjoint-sets data
 * type). It supports the union and find operations, along with a connected operation for
 * determining whether two sites are in the same component and a count operation that returns the
 * total number of components.
 */
public class UnionFind {
	private int[] parent; // parent[i] = parent of i
	private byte[] rank; // rank[i] = rank of subtree rooted at i (never more than 31)
	private int count; // number of components

	public UnionFind(int n) {
		if (n < 0)
			throw new IllegalArgumentException();
		count = n;
		parent = new int[n];
		rank = new byte[n];
		for (int i = 0; i < n; i++) {
			parent[i] = i;
			rank[i] = 0;
		}
	}

	public int find(int p) {
		validate(p);
		while (p != parent[p]) {
			parent[p] = parent[parent[p]]; // path compression by halving
			p = parent[p];
		}
		return p;
	}

	public int count() {
		return count;
	}

	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}

	public void union(int p, int q) {
		int rootP = find(p);
		int rootQ = find(q);
		if (rootP == rootQ)
			return;

		if (rank[rootP] < rank[rootQ])
			parent[rootP] = rootQ;
		else if (rank[rootP] > rank[rootQ])
			parent[rootQ] = rootP;
		else {
			parent[rootQ] = rootP;
			rank[rootP]++;
		}

		count--;
	}

	private void validate(int p) {
		if (p < 0 || p >= parent.length)
			throw new IllegalArgumentException();
	}
}
