package a19_tricky_java_snippets;

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

	public static void main(String[] args) {
		System.out.println(gcd(3, 8));
		System.out.println(countAndSay(6));
	}
}
