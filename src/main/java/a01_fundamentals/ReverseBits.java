package a01_fundamentals;

import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

public class ReverseBits {
	/**
	 * Reverse bits of a given 32 bits unsigned integer.
	 * 
	 * For example, given input 43261596 (represented in binary as 00000010100101000001111010011100),
	 * return 964176192 (represented in binary as 00111001011110000010100101000000).
	 * 
	 */
	public int reverseBits(int n) {
		int result = 0;
		for (int i = 0; i < 32; i++) {
			result |= n & 1;
			n >>>= 1; // must do unsigned shift
			if (i < 31) // for last digit, don't shift!
				result <<= 1;
		}
		return result;
	}

	/**
	 * How to optimize if this function is called multiple times? We can divide an int into 4 bytes, and
	 * reverse each byte then combine into an int. For each byte, we can use cache to improve
	 * performance.
	 */
	private final Map<Byte, Integer> cache = new HashMap<Byte, Integer>();

	public int reverseBits2(int n) {
		int result = 0;
		for (int i = 0; i < 4; i++) {
			result += reverseByte((byte) ((n >>> 8 * i) & 0xFF)); // reverse per byte
			if (i < 3)
				result <<= 8;
		}
		return result;
	}

	private int reverseByte(byte b) {
		Integer value = cache.get(b); // first look up from cache
		if (value != null)
			return value;
		value = 0;
		// reverse by bit
		for (int i = 0; i < 8; i++) {
			value += ((b >>> i) & 1);
			if (i < 7)
				value <<= 1;
		}
		cache.put(b, value);
		return value;
	}

	public static void main(String[] args) {
		ReverseBits solution = new ReverseBits();
		Assert.assertTrue(solution.reverseBits(43261596) == solution.reverseBits2(43261596));
	}
}
