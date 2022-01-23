package a06_sorting_searching;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StrangePrinter {
  public int strangePrinter(String s) {
    return dfs(s, new HashMap<String, Integer>());
  }

  private int dfs(String s, Map<String, Integer> memo) {
    if (s.length() < 2)
      return s.length();

    if (memo.containsKey(s))
      return memo.get(s);

    char chr = s.charAt(0);
    int idx = 0;
    while (idx < s.length() && s.charAt(idx) == chr) {
      idx++;
    }
    int best = 1 + dfs(s.substring(idx), memo);
    for (int i = idx; i < s.length(); i++) {
      if (s.charAt(i) == chr) {
        best = Math.min(best, dfs(s.substring(idx, i), memo) + dfs(s.substring(i), memo));
        while (i < s.length() - 1 && s.charAt(i + 1) == chr) {
          i++;
        }
      }
    }
    memo.put(s, best);
    return best;
  }

  public int strangePrinter2(String s) {
    int len = s.length();
    if (len < 2)
      return len;

    int[] cur = new int[26];
    int[] next = new int[len];

    Arrays.fill(cur, -1);
    Arrays.fill(next, -1);
    cur[s.charAt(0) - 'a'] = 0;
    int pos = 1;

    for (int i = 1; i < len; ++i) {
      if (s.charAt(i) == s.charAt(i - 1))
        continue;

      int idx = s.charAt(i) - 'a';
      if (cur[idx] != -1) {
        next[cur[idx]] = pos;
      }
      cur[idx] = pos++;
    }

    int[][] dp = new int[pos][pos];
    for (int l = pos - 1; 0 <= l; --l) {
      Arrays.fill(dp[l], 200);
      for (int m = l; m < pos; ++m) {
        if (m == l) {
          dp[l][m] = 1;
        } else {
          dp[l][m] = Math.min(dp[l][m], dp[l][m - 1] + 1);
        }

        for (int r = next[m]; r != -1; r = next[r]) {
          dp[l][r] = Math.min(dp[l][r], dp[l][m] + dp[m + 1][r - 1]);
        }
      }
    }

    return dp[0][pos - 1];

  }
}
