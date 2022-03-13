package a00_collections;

public class BitFunctions {
  public boolean getBit(int num, int i) {
    return (num & (1 << i)) != 0;
  }

  public int setBit(int num, int i) {
    return num | (1 << i);
  }

  public int clearBit(int num, int i) {
    return num & ~(1 << i);
    // clear all bits from the most significant bit through i (inclusive)
    // return num & ((1 << i) - 1);
    // clear all bits from i through 0 (inclusive)
    // return num & ((-1 << i + 1)); // NOTE: a sequence of 1 is -1
  }

  public int updateBit(int num, int i, boolean bitIs1) {
    int value = bitIs1 ? 1 : 0;
    int mask = ~(1 << i); // to clear bit
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

  public int reverseBits(int n) {
    int result = 0;
    for (int i = 0; i < 32; i++) {
      result += n & 1;
      n >>>= 1; // must do unsigned shift
      if (i < 31) // for last digit, don't shift!
        result <<= 1;
    }
    return result;
  }

  public int countBits(long x) {
    int count = 0;
    while (x != 0) {
      x &= (x - 1); // clear the loweset set bit
      count++;
    }
    return count;
  }
}
