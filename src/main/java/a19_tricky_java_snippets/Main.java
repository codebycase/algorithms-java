package a19_tricky_java_snippets;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import util.TreeNode;

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
	 * Given a non-negative integer, you could swap two digits at most once to get the maximum valued
	 * number. Return the maximum valued number you could get.
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

	public static void main(String[] args) {
		System.out.println(gcd(3, 8));
		System.out.println(countAndSay(6));
		System.out.println((new Date()).getTime());

		List<List<Integer>> list = new ArrayList<>();
		list.add(Arrays.asList(1, 2, 3));
		System.out.println(list);

		Map<String, String> map = new HashMap<>();
		map.put("wo", "wo");
		map.put("ta", "ta");
		System.out.println(map);

		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[20];
		random.nextBytes(bytes);
		System.out.println(Arrays.toString(bytes));

		Queue<int[]> queue = new PriorityQueue<>((a, b) -> (b[0] - a[0]));

	}
}
