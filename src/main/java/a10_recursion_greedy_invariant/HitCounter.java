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

// Use double linked list
public class HitCounter {
  private int total;
  private int window;
  private Deque<int[]> hits;

  public HitCounter() {
    total = 0;
    window = 5 * 60; // 5 mins window
    hits = new LinkedList<int[]>();
  }

  public void hit(int timestamp) {
    if (hits.isEmpty() || hits.getLast()[0] != timestamp) {
      hits.add(new int[] { timestamp, 1 });
    } else {
      hits.getLast()[1]++;
      // hits.add(new int[] { timestamp, hits.removeLast()[1] + 1 });
    }
    // Prevent from growing too much
    if (hits.size() > window) {
      purge(timestamp);
    }
    total++;
  }

  public int getHits(int timestamp) {
    purge(timestamp);
    return total;
  }

  private void purge(int timestamp) {
    while (!hits.isEmpty() && timestamp - hits.getFirst()[0] >= window) {
      total -= hits.removeFirst()[1];
    }
  }
}
