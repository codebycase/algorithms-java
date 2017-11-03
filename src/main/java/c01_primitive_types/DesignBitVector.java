package c01_primitive_types;

/**
 * <p>
 * Bit Vector is a perfect sample to demonstrate the common bit tasks: Sizing, Shifting, Getting and Setting.
 * </p>
 * 
 * <p>
 * An array of int can be used to deal with array of bits. Assuming size of int to be 4 bytes, when we talk about an int, we are dealing with 32 bits.
 * Say we have int A[10], means we are working on 10 * 4 * 8 = 320 bits.
 * </p>
 * 
 * @author lchen
 *
 */
public class DesignBitVector {
	private static final int INT_SIZE = 32; // 4 bytes = 4 * 8 bits
	private int length;
	private int[] vector;

	public DesignBitVector(int length) {
		this.length = length;
		if (length % INT_SIZE == 0)
			vector = new int[length / INT_SIZE];
		else
			vector = new int[length / INT_SIZE + 1];
	}

	public int length() {
		return length;
	}

	public boolean get(int i) {
		if (i < 0 || i >= length)
			throw new ArrayIndexOutOfBoundsException(i);
		return (vector[i / INT_SIZE] & (1 << (i % INT_SIZE))) == 1;
	}

	public void set(int i, boolean flag) {
		if (i < 0 || i >= length)
			throw new ArrayIndexOutOfBoundsException(i);
		if (flag)
			vector[i / INT_SIZE] |= 1 << (i % INT_SIZE); // mask like: 1000
		else
			vector[i / INT_SIZE] &= ~(1 << (i % INT_SIZE)); // mask like: 0111
	}

	public void print() {
		for (int v : vector) {
			for (int i = 0; i < INT_SIZE; i++) {
				System.out.print((v >> i & 1) - 0);
			}
		}
		System.out.println();
	}

	public static void main(String[] args) {
		DesignBitVector bitVector = new DesignBitVector(10);
		bitVector.print();
		bitVector.set(1, true);
		bitVector.set(3, true);
		bitVector.set(5, true);
		bitVector.print();
		bitVector.set(1, false);
		bitVector.set(3, true);
		bitVector.set(5, false);
		bitVector.print();
		// bitVector.get(100); // out of range!
	}

}
