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

	public static void main(String[] args) {
		System.out.println(gcd(3, 8));
	}
}
