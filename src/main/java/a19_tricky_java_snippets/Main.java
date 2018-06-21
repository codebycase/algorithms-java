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

	public static void main(String[] args) {
		System.out.println(gcd(3, 8));
	}
}
