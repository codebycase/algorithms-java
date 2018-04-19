package a01_fundamentals;

/**
 * Compute the parity of a word
 * 
 * The parity of a binary word is 1 if the number of 1s in the word is odd; otherwise, it is 0. <br>
 * e.g., the parity of 1011 is 1, and the parity of 10001000 is 0.
 * 
 */
public class ParityOfWord {

	// Test the value of each bit while tracking the number of 1s seen so far.
	// Complexity: O(n)
	public short parity1(long x) {
		short result = 0;
		while (x != 0) {
			result ^= (x & 1); // store the number mod 2 since we only care if even or odd
			x >>>= 1;
		}
		return result;
	}

	// x & (x - 1) equals x with its lowest set bit erased.
	// Complexity: O(k) k is the number of 1s.
	public short parity2(long x) {
		short result = 0;
		while (x != 0) {
			result ^= 1;
			x &= (x - 1); // drops the lowest set bit of x
		}
		return result;
	}

	// The parity of (11010111) is the same as parity of (1101) XORed with (0111) which is (1010).
	// Complexity: O(log(n))
	public short parity3(long x) {
		x ^= x >>> 32;
		x ^= x >>> 16;
		x ^= x >>> 8;
		x ^= x >>> 4;
		x ^= x >>> 2;
		x ^= x >>> 1;
		return (short) (x & 0x1); // extract the last bit
	}

	// Especially for a long stream of words, like to compute CRC, we can cache precomputed parity.
	// Complexity: O(n/L)
	public short parity4(long x) {
		final int MASK_SIZE = 16;
		final int BIT_MASK = 0xFFFF;
		// feed in the precomputed parity
		final short[] precomputedParity = new short[10];
		return (short) (precomputedParity[(int) ((x >>> (3 * MASK_SIZE)) & BIT_MASK)]
				^ precomputedParity[(int) ((x >>> (2 * MASK_SIZE)) & BIT_MASK)]
				^ precomputedParity[(int) ((x >>> (MASK_SIZE)) & BIT_MASK)] 
				^ precomputedParity[(int) (x & BIT_MASK)]);
	}

}
