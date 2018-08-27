package a06_sorting_searching;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class KthSmallestInMatrix {
	// Use min heap
	public int kthSmallest(int[][] matrix, int k) {
		int m = matrix.length, n = matrix[0].length;
		Queue<Node> queue = new PriorityQueue<Node>();
		for (int c = 0; c <= n - 1; c++)
			queue.offer(new Node(0, c, matrix[0][c]));
		for (int i = 0; i < k - 1; i++) {
			Node node = queue.poll();
			if (node.x == m - 1)
				continue;
			queue.offer(new Node(node.x + 1, node.y, matrix[node.x + 1][node.y]));
		}
		return queue.poll().val;
	}

	class Node implements Comparable<Node> {
		int x, y, val;

		public Node(int x, int y, int val) {
			this.x = x;
			this.y = y;
			this.val = val;
		}

		@Override
		public int compareTo(Node that) {
			return this.val - that.val;
		}
	}

	// Simulate Binary Search
	public int kthSmallest2(int[][] matrix, int k) {
		int m = matrix.length, n = matrix[0].length;
		int lo = matrix[0][0], hi = matrix[m - 1][n - 1];
		while (lo < hi) {
			int mid = lo + (hi - lo) / 2;
			int count = 0, j = n - 1;
			for (int i = 0; i < m; i++) {
				while (j >= 0 && matrix[i][j] > mid)
					j--;
				count += j + 1;
			}
			if (count < k)
				lo = mid + 1;
			else
				hi = mid;
		}
		return lo;
	}

	public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
		int m = nums1.length, n = nums2.length;
		List<int[]> result = new ArrayList<>();
		if (m == 0 || n == 0 || k <= 0)
			return result;
		Queue<Node> queue = new PriorityQueue<>();
		for (int i = 0; i < n; i++) {
			queue.offer(new Node(0, i, nums1[0] + nums2[i]));
		}
		for (int i = 0; i < Math.min(k, m * n); i++) {
			Node node = queue.poll();
			result.add(new int[] { nums1[node.x], nums2[node.y] });
			if (node.x == m - 1)
				continue;
			queue.offer(new Node(node.x + 1, node.y, nums1[node.x + 1] + nums2[node.y]));
		}
		return result;
	}
}
