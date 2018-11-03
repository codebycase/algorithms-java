package a19_tricky_java_snippets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

	public static int gcd(int a, int b) {
		if (b == 0)
			return a;
		return gcd(b, a % b);
	}

	// e.g. given n = 10, return 36 (10 = 3 + 3 + 4).
	// We should choose integers that are closer to e.
	// The potential candidates are 3 and 2 since 3 > e > 2
	public int integerBreak(int n) {
		if (n == 2)
			return 1;
		if (n == 3)
			return 2;
		if (n == 4)
			return 4;
		int ans = 1;
		while (n > 4) {
			n = n - 3;
			ans = ans * 3;
		}
		return ans * n;
	}

	/**
	 * Given a non-negative integer, you could swap two digits at most once to get the maximum valued number. Return the maximum valued number you
	 * could get.
	 * 
	 * Example 1: Input: 2736 Output: 7236 Explanation: Swap the number 2 and the number 7.
	 * 
	 * @param num
	 * @return
	 */
	public int maximumSwap(int num) {
		char[] A = Integer.toString(num).toCharArray();
		int[] last = new int[10];
		for (int i = 0; i < A.length; i++) {
			last[A[i] - '0'] = i;
		}

		for (int i = 0; i < A.length; i++) {
			for (int d = 9; d > A[i] - '0'; d--) {
				if (last[d] > i) {
					char tmp = A[i];
					A[i] = A[last[d]];
					A[last[d]] = tmp;
					return Integer.valueOf(new String(A));
				}
			}
		}
		return num;
	}

	public static String countAndSay(int n) {
		StringBuilder curr = new StringBuilder("1");
		StringBuilder prev;
		int count;
		char say;
		for (int i = 1; i < n; i++) {
			System.out.println(curr);
			prev = curr;
			curr = new StringBuilder();
			count = 1;
			say = prev.charAt(0);

			for (int j = 1, len = prev.length(); j < len; j++) {
				if (prev.charAt(j) != say) {
					curr.append(count).append(say);
					count = 1;
					say = prev.charAt(j);
				} else
					count++;
			}
			curr.append(count).append(say);
		}
		return curr.toString();
	}

	public double myPow(double x, int n) {
		long N = n;
		if (N < 0) {
			x = 1 / x;
			N = -N;
		}
		double ans = 1;
		double current_product = x;
		for (long i = N; i > 0; i /= 2) {
			if ((i % 2) == 1) {
				ans = ans * current_product;
			}
			current_product = current_product * current_product;
		}
		return ans;
	}

	public int longestSubstring(String s, int k) {
		return helper(s.toCharArray(), 0, s.length(), k);
	}

	public int helper(char[] s, int left, int right, int k) {
		if (right - left < k)
			return 0;
		int[] count = new int[26];
		for (int i = left; i < right; i++)
			count[s[i] - 'a']++;
		for (int i = left; i < right; i++) {
			if (count[s[i] - 'a'] < k) {
				int j = i + 1;
				while (j < right && count[s[j] - 'a'] < k)
					j++;
				return Math.max(helper(s, left, i, k), helper(s, j, right, k));
			}
		}
		return right - left;
	}

	public List<String> letterCombinations(String digits) {
		LinkedList<String> result = new LinkedList<String>();
		if (digits == null || digits.trim().length() == 0)
			return result;

		String[] keyMap = new String[] { "0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };

		result.add("");
		for (int i = 0; i < digits.length(); i++) {
			int x = digits.charAt(i) - '0';
			while (result.peek().length() == i) {
				String t = result.poll();
				for (char s : keyMap[x].toCharArray()) {
					result.offer(t + s);
				}
			}
		}

		return result;
	}

	public void rotate(int[][] matrix) {
		int m = matrix.length;
		int n = matrix[0].length;
		// first swap symmetry
		for (int i = 0; i < m; i++) {
			for (int j = i; j < n; j++) {
				int temp = matrix[i][j];
				matrix[i][j] = matrix[j][i];
				matrix[j][i] = temp;
			}
		}
		// second reverse left to right
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n / 2; j++) {
				int temp = matrix[i][j];
				matrix[i][j] = matrix[i][n - 1 - j];
				matrix[i][m - 1 - j] = temp;
			}
		}
	}

	int min1;
	long ans = Long.MAX_VALUE;

	public void dfs(TreeNode root) {
		if (root != null) {
			if (min1 < root.val && root.val < ans) {
				ans = root.val;
			} else if (min1 == root.val) {
				dfs(root.left);
				dfs(root.right);
			}
		}
	}

	public int robHouses(int[] nums) {
		int prevMax = 0;
		int currMax = 0;
		for (int x : nums) {
			int temp = currMax;
			currMax = Math.max(prevMax + x, currMax);
			prevMax = temp;
		}
		return currMax;
	}

	public int findSecondMinimumValue(TreeNode root) {
		min1 = root.val;
		dfs(root);
		return ans < Long.MAX_VALUE ? (int) ans : -1;
	}

	public String getPermutation(int n, int k) {
		List<Integer> samples = new ArrayList<>();
		for (int i = 1; i <= n; i++) {
			samples.add(i);
		}
		return helper(samples, k);
	}

	public String helper(List<Integer> samples, int k) {
		if (samples.size() == 0)
			return "";
		int size = samples.size();
		int product = 1, i = 1;
		while (i < size) {
			product *= i;
			i++;
		}
		int index = (k - 1) / product;
		int remain = k - product * index;
		return samples.remove(index) + "" + helper(samples, remain);
	}

	public int largestPalindrome(int n) {
		if (n == 1)
			return 9;
		int max = (int) Math.pow(10, n) - 1;
		for (int v = max - 1; v > max / 10; v--) {
			long u = Long.valueOf(v + new StringBuilder().append(v).reverse().toString());
			for (long x = max; x * x >= u; x--)
				if (u % x == 0)
					return (int) (u % 1337);
		}
		return 0;
	}

	public static List<String> subStringsKDist(String inputStr, int num) {
		List<String> result = new ArrayList<>();
		Set<String> words = new LinkedHashSet<>();
		if (inputStr == null || inputStr.length() < num)
			return result;
		for (int i = 0; i <= inputStr.length() - num; i++) {
			String word = inputStr.substring(i, i + num);
			if (isValidWord(word, num))
				words.add(word);
		}
		result.addAll(words);
		return result;
	}

	public static boolean isValidWord(String word, int num) {
		int[] counts = new int[26];
		for (char c : word.toCharArray()) {
			if (counts[c - 'a'] > 0)
				return false;
			counts[c - 'a']++;
		}
		return true;
	}

	public static int bstDistance(int[] values, int n, int node1, int node2) {
		TreeNode tree = constructBST(values);
		return distanceBetween(tree, node1, node2);
	}

	static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;

		public TreeNode(int val) {
			this.val = val;
		}
	}

	private static TreeNode constructBST(int[] values) {
		if (values == null || values.length == 0)
			return null;
		TreeNode root = new TreeNode(values[0]);
		TreeNode node = root;
		for (int i = 1; i < values.length; i++) {
			TreeNode next = new TreeNode(values[i]);
			if (node.val > values[i]) {
				node.left = next;
			} else {
				node.right = next;
			}
			node = next;
		}
		return root;
	}

	private static int distanceBetween(TreeNode root, int p, int q) {
		if (root.val > p && root.val > q) {
			return distanceBetween(root.left, p, q);
		} else if (root.val < p && root.val < q) {
			return distanceBetween(root.right, p, q);
		} else {
			return distanceFromRoot(root, p) + distanceFromRoot(root, q);
		}
	}

	private static int distanceFromRoot(TreeNode root, int x) {
		if (root.val == x)
			return 0;
		else
			return 1 + (root.val > x ? distanceFromRoot(root.left, x) : distanceFromRoot(root.right, x));
	}

	public int totalFruit(int[] tree) {
		Map<Integer, Integer> counter = new HashMap<>();
		int ans = 0, l = 0;
		for (int r = 0; r < tree.length; r++) {
			counter.compute(tree[r], (k, v) -> v == null ? 1 : v + 1);
			while (counter.size() > 2) {
				counter.compute(tree[l], (k, v) -> v - 1);
				if (counter.get(tree[l]) == 0)
					counter.remove(tree[l]);
				l++;
			}
			ans = Math.max(ans, r - l + 1);
		}
		return ans;
	}

	public int kEmptySlots(int[] flowers, int k) {
		int[] days = new int[flowers.length];
		for (int i = 0; i < flowers.length; i++)
			days[flowers[i] - 1] = i + 1;
		int left = 0, right = k + 1, minDay = Integer.MAX_VALUE;
		for (int i = 0; right < days.length; i++) {
			if (days[i] < days[left] || days[i] <= days[right]) {
				if (i == right)
					minDay = Math.min(minDay, Math.max(days[left], days[right])); // we get a valid subarray
				left = i;
				right = left + k + 1;
			}
		}
		return (minDay == Integer.MAX_VALUE) ? -1 : minDay;
	}

	public static void main(String[] args) {
		// System.out.println(subStringsKDist("abcdefabc", 3));

		printTerrian();
	}

	public static void printTerrian() {
		int[] heights = { 5, 4, 2, 1, 2, 3, 2, 1, 0, 1, 2, 4 };
		int m = Arrays.stream(heights).max().getAsInt() + 1;
		int n = heights.length;
		char[][] grid = new char[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (i >= m - heights[j] - 1)
					grid[i][j] = '+';
				else
					grid[i][j] = ' ';
			}
		}

		int units = 88, index = 2;
		while (units-- > 0) {
			droplet: {
				for (int dir : new int[] { 1, -1 }) {
					// two pointers shit together
					int i = index, j = index + dir, best = index;
					while (0 <= j && j < heights.length && heights[j] <= heights[i]) {
						if (heights[j] < heights[i])
							best = j;
						i = j;
						j += dir;
					}
					if (best != index) {
						heights[best]++;
						break droplet;
					}
				}
				heights[index]++;
			}
		}

		for (char[] row : grid) {
			System.out.println(Arrays.toString(row));
		}

		System.out.println(Arrays.toString(heights));
	}

	public static void printTerrain2() {
		int[] terrain = { 5, 4, 2, 1, 2, 3, 2, 1, 0, 1, 2, 4 };
		int m = Arrays.stream(terrain).max().getAsInt();
		int n = terrain.length;
		m += 1;
		char[][] grid = new char[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (i > m - terrain[j])
					grid[i][j] = '+';
				else
					grid[i][j] = ' ';
			}
		}
		for (char[] row : grid) {
			System.out.println(row);
		}
	}

}
