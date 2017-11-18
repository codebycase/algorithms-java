package a08_dynamic_programming;

/**
 * To illustrate the idea underlying DP, consider the problem of computing Fibonacci numbers. <br>
 * Mathematically, the nth Fibonacci number is given by the equation: F(n) = F(n-1) + F(n-2) with F(0) = 0 and F(1) = 1. <br>
 * The first few Fibonacci numbers are 0, 1, 1, 2, 3, 5, 8, 13, 21,... <br>
 * 
 * Let's walk through different approaches to compute the nth Fibonacci number.
 * 
 * @author lchen
 *
 */
public class FibonacciNumber {
	/**
	 * We can start with a simple recursive implementation. <br>
	 * This gives us a runtime of roughly O(2^n), an exponential runtime.
	 * 
	 * @param n
	 * @return
	 */
	public static int fibonacciI(int n) {
		if (n == 0)
			return 0;
		if (n == 1)
			return 1;
		return fibonacciI(n - 1) + fibonacciI(n - 2);
	}

	/**
	 * We still use top-down dynamic programming, but with memorization this time! <br>
	 * The runtime is roughly O(n) since we are caching the result and use it later.
	 * 
	 * @param i
	 * @return
	 */
	public static int fibonacciII(int i) {
		return fibonacciII(i, new int[i + 1]);
	}

	public static int fibonacciII(int n, int[] memo) {
		if (n == 0)
			return 0;
		if (n == 1)
			return 1;
		if (memo[n] == 0) {
			memo[n] = fibonacciII(n - 1, memo) + fibonacciII(n - 2, memo);
		}
		return memo[n];
	}

	/**
	 * Let's change it to bottom-up dynamic programming, with memorization too! <br>
	 * This give us the same O(n) runtime.
	 * 
	 * @param n
	 * @return
	 */
	public static int fibonacciIII(int n) {
		if (n == 0)
			return 0;
		int[] memo = new int[n + 1];
		memo[0] = 0;
		memo[1] = 1;
		for (int i = 2; i <= n; i++) {
			memo[i] = memo[i - 1] + memo[i - 2];
		}
		return memo[n];
	}

	/**
	 * We can even get rid of the memo table, to achieve O(n) time and O(1) space. <br>
	 * 
	 * @param n
	 * @return
	 */
	public static int fibonacciVI(int n) {
		if (n == 0)
			return 0;
		int a = 0;
		int b = 1;
		for (int i = 2; i <= n; i++) {
			int c = a + b;
			a = b;
			b = c;
		}
		return b;
	}

	public static void main(String[] args) {
		System.out.println(fibonacciI(35));
		System.out.println(fibonacciII(35));
		System.out.println(fibonacciIII(35));
		System.out.println(fibonacciVI(35));
	}

}
