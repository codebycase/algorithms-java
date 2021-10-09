package a18_the_honors_question;

import java.util.HashMap;
import java.util.Map;

public class CanIWin {
  public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
    if (desiredTotal <= maxChoosableInteger) {
      return true;
    }
    if (maxChoosableInteger * (1 + maxChoosableInteger) / 2.0 < desiredTotal) {
      return false;
    }
    return canIWin(0, new Boolean[1 << maxChoosableInteger], desiredTotal, maxChoosableInteger);
  }

  // State is the bitmap representation of the all picked/chosen integers
  // dp[state] represents whether the current player can win the game at this state
  private boolean canIWin(int state, Boolean[] dp, int desiredTotal, int maxChoosableInteger) {
    if (dp[state] != null) {
      return dp[state];
    }
    for (int i = 1; i <= maxChoosableInteger; i++) {
      int current = 1 << (i - 1);
      // 0 means i is not used
      if ((current & state) == 0) {
        // check whether this leads to a win:
        // 1. i is greater than the desired total
        // 2. the other player can't win after the current player picks i
        if (i >= desiredTotal || !canIWin(state | current, dp, desiredTotal - i, maxChoosableInteger)) {
          dp[state] = true;
          return dp[state];
        }
      }
    }
    dp[state] = false;
    return dp[state];
  }

  public boolean canIWin2(int maxChoosableInteger, int desiredTotal) {
    // verify the input data
    int maxTotal = (1 + maxChoosableInteger) * maxChoosableInteger / 2;
    if (maxTotal < desiredTotal)
      return false;
    if (desiredTotal <= 0)
      return true;

    return helper(desiredTotal, new boolean[maxChoosableInteger + 1], new HashMap<>());
  }

  public boolean helper(int desiredTotal, boolean[] used, Map<Integer, Boolean> map) {
    if (desiredTotal <= 0)
      return false;
    // treat boolean[] as bitMap and transform to a key
    int key = 0;
    for (boolean b : used) {
      key <<= 1;
      if (b)
        key |= 1;
    }
    if (!map.containsKey(key)) {
      boolean canIWin = false;
      // try every unchosen number as next step
      for (int i = 1; i < used.length; i++) {
        if (!used[i]) {
          used[i] = true;
          // check whether this lead to a win (means the other player will lose)
          canIWin = !helper(desiredTotal - i, used, map);
          used[i] = false;
          if (canIWin)
            break;
        }
      }
      map.put(key, canIWin);
    }
    return map.get(key);
  }
}
