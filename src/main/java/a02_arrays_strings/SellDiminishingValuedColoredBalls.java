package a02_arrays_strings;

import java.util.Arrays;

/**
 * Sell Diminishing-Valued Colored Balls
 * 
 * https://leetcode.com/problems/sell-diminishing-valued-colored-balls/
 */
public class SellDiminishingValuedColoredBalls {
  public int maxProfit(int[] inventory, int orders) {
    int lo = 0, hi = Arrays.stream(inventory).max().getAsInt();
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

    return (int) (result % (long) (1e9 + 7));
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
