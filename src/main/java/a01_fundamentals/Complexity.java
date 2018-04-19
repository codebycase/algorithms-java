package a01_fundamentals;

public class Complexity {
	// linear (N + N/2 + N/4 + ...)
	public int codeOne(int n) {
		int sum = 0;
		for (int k = n; k > 0; k /= 2) {
			for (int i = 0; i < k; i++)
				sum++;
		}
		return sum;
	}

	// linear (1 + 2 + 4 + 8 + ...)
	public int codeTwo(int n) {
		int sum = 0;
		for (int i = 1; i < n; i *= 2) {
			for (int j = 0; j < i; j++)
				sum++;
		}
		return sum;
	}

	// linearithmic (the outer loop loops lgN times)
	public int codeThree(int n) {
		int sum = 0;
		for (int i = 1; i < n; i *= 2) {
			for (int j = 0; j < n; j++)
				sum++;
		}
		return sum;
	}
}
