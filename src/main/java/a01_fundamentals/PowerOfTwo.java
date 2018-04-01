package a01_fundamentals;

/**
 * Write a program to check if an integer is a power of two. How many ways can you provide?
 * 
 * @author lchen
 *
 */
public class PowerOfTwo {
	// Divides by 2 until the quotient is odd or less than 1
	public static boolean isPowerOfTwo1(int x) {
		while ((x % 2 == 0) && x > 1) {
			x /= 2;
		}
		return x == 1;
	}

	// Compute each power of two incrementally until x is less than or equal to the value
	public static boolean isPowerOfTwo2(int x) {
		int powerOfTwo = 1;
		while (powerOfTwo < x && powerOfTwo < Integer.MAX_VALUE / 2) {
			powerOfTwo *= 2;
		}
		return x == powerOfTwo;
	}

	// Binary search of precomputed powers of two stored in an array
	// To improve multiple calls, declare powersOfTwo as global variable
	public static boolean isPowerOfTwo3(int x) {
		int[] powersOfTwo = { 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536,
				131072, 262144, 524288, 1048576, 2097152, 4194304, 8388608, 16777216, 33554432, 67108864, 134217728,
				268435456, 536870912, 1073741824 };
		if (x == 0)
			return false;
		if (x == 1)
			return true;
		int left = 0, right = powersOfTwo.length - 1;
		while (left <= right) {
			int middle = left + (right - left) / 2;
			if (x == powersOfTwo[middle])
				return true;
			else if (x < powersOfTwo[middle])
				right = middle - 1;
			else
				left = middle + 1;
		}
		return false;
	}

	// Counting 1 bits until more than 1 bit found
	public static boolean isPowerOfTwo4(int x) {
		int numOfOneBits = 0;
		while (x > 0 && numOfOneBits <= 1) {
			if ((x & 1) == 1)
				numOfOneBits++;
			x >>= 1;
		}
		return numOfOneBits == 1;
	}

	// Remove all right 0 bits, equivalent of the divide by two
	public static boolean isPowerOfTwo5(int x) {
		while ((x & 1) == 0 && x > 1) {
			x >>= 1;
		}
		return x == 1;
	}

	// Shortcut: Decrement and Compare
	public static boolean isPowerOfTwo6(int x) {
		return (x != 0) && (x & (x - 1)) == 0;
	}

	// Shortcut: Complement and Compare
	public static boolean isPowerOfTwo7(int x) {
		return (x != 0) && (x & (~x + 1)) == x;
	}

	public static void main(String[] args) {
		assert isPowerOfTwo1(524288) == true;
		assert isPowerOfTwo1(16392) == false;
		assert isPowerOfTwo2(524288) == true;
		assert isPowerOfTwo2(16392) == false;
		assert isPowerOfTwo3(524288) == true;
		assert isPowerOfTwo3(16392) == false;
		assert isPowerOfTwo4(524288) == true;
		assert isPowerOfTwo4(16392) == false;
		assert isPowerOfTwo5(524288) == true;
		assert isPowerOfTwo5(16392) == false;
		assert isPowerOfTwo6(524288) == true;
		assert isPowerOfTwo6(16392) == false;
		assert isPowerOfTwo7(524288) == true;
		assert isPowerOfTwo7(16392) == false;
	}
}
