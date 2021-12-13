package a02_arrays_strings;

public class SellColoredBalls {
  public int maxProfit(int[] inventory, int orders) {
    int max = 0;
    for (int in : inventory) {
      max = Math.max(max, in);
    }

    int lo = 0, hi = max;
    while (lo < hi) {
      int mid = lo + (hi - lo) / 2;
      if (getBallCnt(inventory, mid, orders) > orders) {
        lo = mid + 1;
      } else {
        hi = mid;
      }
    }

    int remainMax = lo;
    long result = 0L;
    for (int cnt : inventory) {
      if (cnt <= remainMax)
        continue;
      result += (long) (cnt + remainMax + 1) * (long) (cnt - remainMax) / 2;
      orders -= cnt - remainMax;
    }
    result += (long) remainMax * (long) orders;

    return (int) (result % 1000000007);
  }

  private int getBallCnt(int[] inventory, int remainMax, int total) {
    int res = 0;
    for (int cnt : inventory) {
      if (cnt <= remainMax)
        continue;
      res += (cnt - remainMax);
      if (res > total)
        break;
    }
    return res;
  }
}
