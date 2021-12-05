package a10_recursion_greedy_invariant;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Design a hit counter which counts the number of hits received in the past 5 minutes (i.e., the
 * past 300 seconds).
 * 
 * Your system should accept a timestamp parameter (in seconds granularity), and you may assume that
 * calls are being made to the system in chronological order (i.e., timestamp is monotonically
 * increasing). Several hits may arrive roughly at the same time.
 * 
 *
 */
public class HitCounter {
  private int total;
  private Deque<int[]> hits;

  /** Initialize your data structure here. */
  public HitCounter() {
    total = 0;
    hits = new LinkedList<int[]>();
  }

  /**
   * Record a hit.
   * 
   * @param timestamp - The current timestamp (in seconds granularity).
   */
  public void hit(int timestamp) {
    if (hits.isEmpty() || hits.getLast()[0] != timestamp) {
      hits.add(new int[] { timestamp, 1 });
    } else {
      hits.add(new int[] { timestamp, hits.removeLast()[1] + 1 });
    }
    total++;
  }

  /**
   * Return the number of hits in the past 5 minutes.
   * 
   * @param timestamp - The current timestamp (in seconds granularity).
   */
  public int getHits(int timestamp) {
    while (!hits.isEmpty() && timestamp - hits.getFirst()[0] >= 300) {
      total -= hits.removeFirst()[1];
    }
    return total;
  }
}
