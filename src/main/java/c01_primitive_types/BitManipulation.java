package c01_primitive_types;

/**
 * <h2>A few bit manipulation samples:</h2>
 * 
 * <pre>
 * 0110 + 0110 = 1100
 * 0100 * 0011 = 4 * 0011 = 0011 << 2 = 1100
 * 1101 ^ (~1101) = 1101 ^ 0010 = 1111 // a ^ (~a) = 1
 * </pre>
 * 
 * <h2>To get the negation of a numer in two's complement.</h2> <br>
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
 * UTF-8, the most widely used by websites, uses one byte for the first 128 code points, and <b>up
 * to 4 bytes</b> for other characters. The first 128 Unicode code points are the ASCII characters;
 * so an ASCII text is a UTF-8 text.
 * </p>
 * <p>
 * The same character converted to UTF-8 becomes the byte squence {@code BF BB BF}. The Unicode
 * Standard allows that the BOM "can serve as signature for UTF-8 encoded text where the character
 * set is unmarked".
 * </p>
 * 
 * @author lchen
 *
 */
public class BitManipulation {
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

}
