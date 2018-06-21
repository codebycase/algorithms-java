package a01_fundamentals;

import java.util.Random;

/**
 * <h2>A few bit manipulation samples:</h2>
 * 
 * <pre>
 * 0110 + 0110 = 1100
 * 0100 * 0011 = 4 * 0011 = 0011 << 2 = 1100
 * 1101 ^ (~1101) = 1101 ^ 0010 = 1111 // a ^ (~a) = 1
 * </pre>
 * 
 * <h2>To get the negation of a number in two's complement.</h2> <br>
 * Invert all the bits through the number, and add one.
 * 
 * <pre>
 * 1. ~0010 -> 1101
 * 2. 1101 + 1 -> 1110
 * </pre>
 * 
 * <h2>Arithmetic vs. Logical Right Shift
 * <h2>-75 (10110101) >>> 1 = 90 (01011010) // logical shift, put a 0 in the most significant bit.
 * <br>
 * -75 (10110101) >> 1 = -38 (11011010) // arithmetic shift, fill in the new bits with the sign
 * bit.<br>
 * 
 * 
 * <h2>Character Encoding</h2>
 * <p>
 * Unicode can be implemented by different character encodings. The Unicode standard defines UTF-8,
 * UTF-16, and UTF-32, and several other encodings are in use.
 * </p>
 * <p>
 * UTF-8, the most widely used by web sites, uses one byte for the first 128 code points, and <b>up
 * to 4 bytes</b> for other characters. The first 128 Unicode code points are the ASCII characters;
 * so an ASCII text is a UTF-8 text.
 * </p>
 * <p>
 * The same character converted to UTF-8 becomes the byte sequence {@code BF BB BF}. The Unicode
 * Standard allows that the BOM "can serve as signature for UTF-8 encoded text where the character
 * set is unmarked".
 * </p>
 * 
 * @author lchen
 *
 */
public class BitManipulation {
	private static Random random = new Random();

	public boolean getBit(int num, int i) {
		return (num & (1 << i)) != 0;
	}

	public int setBit(int num, int i) {
		return num | (1 << i);
	}

	public int clearBit(int num, int i) {
		return num & ~(1 << i);
		// Clear all bits from the most significant bit through i (inclusive)
		// return num & ((1 << i) - 1);
		// Clear all bits from i through 0 (inclusive)
		// return num & ((-1 << i + 1)); // NOTE: a sequence of 1 is -1
	}

	public int updateBit(int num, int i, boolean bitIs1) {
		int value = bitIs1 ? 1 : 0;
		int mask = ~(1 << i);
		return (num & mask) | (value << i);
	}

	public long swapBits(long x, int i, int j) {
		// extract the i-th and j-th bits, and see if they differ.
		if (((x >>> i) & 1) != ((x >>> j) & 1)) {
			long mask = (1L << i) | (1L << j); // combine
			x ^= mask; // flip their values with XOR
		}
		return x;
	}

	public static int countBits(long x) {
		int count = 0;
		while (x != 0) {
			x &= (x - 1);
			count++;
		}
		return count;
	}

	/**
	 * Given a non negative integer number num. For every numbers i in the range 0 ≤ i ≤ num calculate
	 * the number of 1's in their binary representation and return them as an array.
	 */
	public static int[] countBits2(int x) {
		int[] ans = new int[x + 1];
		// solution 1: last set bit
		for (int i = 1; i <= x; i++) {
			ans[i] = ans[i & (i - 1)] + 1;
		}
		// solution 2: least significant bit
		/*
		for (int i = 0; i <= x; i++) {
			ans[i] = ans[i >> 1] + (i & 1); // P(x) = P(x/2) + (x mod 2)
		}
		*/
		return ans;
	}

	/**
	 * 16 + 10 + 36 = 62 bits in total!
	 * 
	 * @param shardId
	 *            contains 16 bits
	 * @param typeId
	 *            contains 10 bits
	 * @param localId
	 *            contains 36 bits
	 * @return 64 bits ID
	 */
	public long encodeId(long shardId, long typeId, long localId) {
		return shardId << (10 + 36) | typeId << 36 | localId;
	}

	/**
	 * @param id
	 * @return shardId, typeId, localId
	 */
	public long[] decodeId(long id) {
		long[] result = new long[3];
		result[0] = (id >> 46) & 0xFFFF; // 1111,1111,1111,1111
		result[1] = (id >> 36) & 0x3FF; // 11,1111,1111
		result[2] = id & 0xFFFFFFFF;
		return result;
	}

	public static long multiply(long x, long y) {
		long sum = 0;

		while (x != 0) {
			if ((x & 1) != 0) {
				sum = add(sum, y);
			}
			x >>>= 1;
			y <<= 1;
		}

		return sum;
	}

	private static long add(long a, long b) {
		long sum = 0, carryin = 0, k = 1, tempA = a, tempB = b;
		while (tempA != 0 || tempB != 0) {
			long ak = a & k, bk = b & k;
			long carryout = (ak & bk) | (ak & carryin) | (bk & carryin);
			sum |= (ak ^ bk ^ carryin);
			carryin = carryout << 1;
			k <<= 1;
			tempA >>>= 1;
			tempB >>>= 1;
		}
		return sum | carryin;
	}

	/**
	 * Given two positive integers, compute their quotient, using only the addition, subtraction, and
	 * shifting operators.
	 */
	public static int divide(int x, int y) {
		if (y == 0)
			throw new IllegalArgumentException();
		int result = 0;
		int power = 32;
		long yPower = y << power;
		while (x >= y) {
			while (yPower > x) {
				yPower >>>= 1;
				power--;
			}

			result += 1 << power;
			x -= yPower;
		}
		return result;
	}

	/**
	 * Write a program that takes a double x and an integer y and returns $$x^y$$. You can ignore
	 * overflow and underflow.
	 */

	// bottom-up recursive
	public static double power(double x, int y) {
		double result = 1.0;

		if (y < 0) {
			y = -y;
			x = 1.0 / x;
		}

		while (y != 0) {
			if ((y & 1) == 1) // will run twice for odd number, and only once for even number!
				result *= x;
			x *= x;
			y >>>= 1;
		}

		return result;
	}

	// top-down recursive
	public static double powerII(double x, int y) {
		if (y == 0)
			return 1;
		if (y == 1)
			return x;

		if (y < 0) {
			y = -y;
			x = 1.0 / x;
		}

		int s = y >> 1; // divide by 2
		double half = powerII(x, s);

		if ((y & 1) == 0)
			return half * half;
		else
			return half * half * x;

	}

	/**
	 * Write a program which takes an integer and returns the integer corresponding to the digits of the
	 * input written in reverse order. For example, the reverse of 42 is 24, and the reverse of -314 is
	 * -413.
	 */
	public static long reverseDigits(int x) {
		long result = 0;
		int remain = Math.abs(x);
		while (remain > 0) {
			result = result * 10 + remain % 10;
			remain /= 10;
		}
		return x < 0 ? -result : result;
	}

	/**
	 * Find a closest integer with the same weight (number of bits set to 1)
	 * 
	 * @param x
	 * @return
	 */
	public static long closestIntSameBitCount(long x) {
		final int NUM_UNSIGNED_BITS = 63;
		// x is assumed to be non-negative so we know the leading bit is 0. We
		// restrict to our attention to 63 LSBs.
		for (int i = 0; i < NUM_UNSIGNED_BITS - 1; ++i) {
			if ((((x >>> i) & 1) != ((x >>> (i + 1)) & 1))) {
				x ^= (1L << i) | (1L << (i + 1)); // swaps bit-i and bit-(i + 1).
				return x;
			}
		}
		// throw error if all bits of x are 0 or 1.
		throw new IllegalArgumentException("All bits are 0 or 1");
	}

	/**
	 * Write a program that takes an integer and determines if that integer's representation as a
	 * decimal string is a palindrome.
	 * 
	 * For example, return true for the inputs 0, 1, 7, 11, 121, 333, and 2147447412, and false for the
	 * inputs -1, 12, 100.
	 * 
	 * @param x
	 * @return
	 */
	public static boolean isPalindromeNumber(int x) {
		if (x <= 0)
			return x == 0;

		final int numDigits = (int) (Math.floor(Math.log10(x))) + 1;
		int msdMask = (int) Math.pow(10, numDigits - 1);
		for (int i = 0; i < (numDigits / 2); i++) {
			if (x / msdMask != x % 10)
				return false;
			x %= msdMask; // remove the most significant digit of x.
			x /= 10; // remove the least significant digit of x.
			msdMask /= 100;
		}

		return true;
	}

	public static int uniformRandom(int lowerBound, int upperBound) {
		int result, numberOfOutcomes = upperBound - lowerBound + 1;

		do {
			result = 0;
			for (int i = 0; (1 << i) < numberOfOutcomes; i++) {
				result = (result << 1) | random.nextInt(2);
			}
		} while (result >= numberOfOutcomes);

		return result + lowerBound;
	}

	public int findIntegers(int num) {
		int[] f = new int[32];
		f[0] = 1;
		f[1] = 2;
		for (int i = 2; i < f.length; i++)
			f[i] = f[i - 1] + f[i - 2];
		int i = 30, sum = 0, prev_bit = 0;
		while (i >= 0) {
			if ((num & (1 << i)) != 0) {
				sum += f[i];
				if (prev_bit == 1) {
					sum--;
					break;
				}
				prev_bit = 1;
			} else
				prev_bit = 0;
			i--;
		}
		return sum + 1;
	}

	
	public static void main(String[] args) {
		assert closestIntSameBitCount(6L) == 5L;
		assert isPalindromeNumber(6) == true;
		assert isPalindromeNumber(12) == false;
		assert isPalindromeNumber(2147447412) == true;
		assert reverseDigits(-314) == -413;
		assert uniformRandom(1, 6) >= 1 && uniformRandom(1, 6) <= 6;
		assert power(2.0, 3) == powerII(2.0, 3);
		assert power(2.0, 4) == powerII(2.0, 4);
		assert power(2.0, -2) == powerII(2.0, -2);
		assert divide(10, 2) == 5;
		assert multiply(2, 3) == 6;
	}

}
