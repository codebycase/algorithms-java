package a18_the_honors_question;

/**
 * Design an efficient algorithm for computing the Greatest Common Divisor (GCD) of two nonnegative
 * integers without using multiplication, division or the modulus operators.
 * 
 * @author lchen
 *
 */
public class GreatestCommonDivisor {
	public static long GCD(long x, long y) {
		if (x > y) {
			return GCD(y, x);
		} else if (x == 0 || x == y) { // base case
			return y;
		} else if ((x & 1) == 0 && (y & 1) == 0) { // x and y are both even
			return GCD(x >>> 1, y >>> 1) << 1;
		} else if ((x & 1) == 0 && (y & 1) == 1) { // x is even, y is odd
			return GCD(x >>> 1, y);
		} else if ((x & 1) == 1 && (y & 1) == 0) { // x is odd, y is even
			return GCD(x, y >>> 1);
		}
		return GCD(x, y - x); // x and y are both odd
	}

	public static void main(String[] args) {
		assert GCD(0, 6) == 6;
		assert GCD(3, 6) == 3;
		assert GCD(2, 6) == 2;
		assert GCD(6, 6) == 6;
	}
}
