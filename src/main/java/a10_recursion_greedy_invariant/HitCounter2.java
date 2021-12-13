package a10_recursion_greedy_invariant;

public class HitCounter2 {
  private int total;
  private int window;
  private int[][] hits;

  public HitCounter2() {
    total = 0;
    window = 5 * 60; // 5 mins window
    hits = new int[window][2];
  }

  public void hit(int timestamp) {
    int i = timestamp % hits.length;
    if (hits[i][0] != timestamp) {
      purge(i, timestamp);
    }
    hits[i][1]++;
    total++;
  }

  public int getHits(int timestamp) {
    for (int i = 0; i < hits.length; i++) {
      if (hits[i][0] != 0 && timestamp - hits[i][0] >= window) {
        purge(i, timestamp);
      }
    }
    return total;
  }

  private void purge(int i, int timestamp) {
    total -= hits[i][1];
    hits[i][0] = timestamp;
    hits[i][1] = 0;
  }
}
