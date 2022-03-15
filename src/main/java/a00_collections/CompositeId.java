package a00_collections;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * 
 * In the case, to design a MySQL sharding approach. You might use a 64 bit ID which contains 16 bit
 * shard ID, 10 bits type ID, and 36 bit local ID.
 * 
 * <pre>
 * ID = (shard ID << 46) | (type ID << 36) | (local ID<<0)
 * Given a ID 241294492511762325
 * Shard ID = (241294492511762325 >> 46) & 0xFFFF = 3429
 * Type ID  = (241294492511762325 >> 36) & 0x3FF = 1
 * Local ID = (241294492511762325 >>  0) & 0xFFFFFFFFF = 7075733
 * </pre>
 *
 */
public class CompositeId {
  /**
   * 16 + 10 + 36 = 62 bits in total!
   * 
   * @param shardId contains 16 bits
   * @param typeId  contains 10 bits
   * @param localId contains 36 bits
   * @return 64 bits ID
   */
  public long encodeId(long shardId, long typeId, long localId) {
    return shardId << (10 + 36) | typeId << 36 | localId;
  }

  /**
   * Use mask bits to clear, then set new value
   * 
   * @param id     encoded id
   * @param typeId new type id
   * @return updated id
   */
  public long updateTypeId(long id, long typeId) {
    long mask = (1 << 10) - 1; // All ones
    id &= ~(mask << 36); // Clear bits
    id |= typeId << 36; // Set bits
    return id;
  }

  /**
   * @param id
   * @return shardId, typeId, localId
   */
  public long[] decodeId(long id) {
    long[] result = new long[3];
    result[0] = (id >> 46) & 0xFFFF; // 1111,1111,1111,1111
    result[1] = (id >> 36) & 0x3FF; // 11,1111,1111
    result[2] = id & ((1 << 36) - 1); // 0xFFFFFFFFF exceeds int!
    return result;
  }

  public String printBits(long id) {
    StringBuilder b = new StringBuilder();
    for (int i = Long.SIZE - 1; i >= 0; i--) {
      b.append((id & (1L << i)) != 0 ? '1' : '0');
    }
    return b.toString();
  }

  public static void main(String[] args) {
    CompositeId solution = new CompositeId();
    long id = solution.encodeId(2, 7, 15);
    assertEquals("0000000000000000100000000111000000000000000000000000000000001111", solution.printBits(id));
    assertArrayEquals(new long[] { 2, 7, 15 }, solution.decodeId(id));
    id = solution.updateTypeId(id, 8);
    assertEquals("0000000000000000100000001000000000000000000000000000000000001111", solution.printBits(id));
    assertArrayEquals(new long[] { 2, 8, 15 }, solution.decodeId(id));
  }
}
