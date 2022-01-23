package a06_sorting_searching;

import java.util.List;

public class MinimumTimeDifference {
  public int findMinDifference(List<String> timePoints) {
    int length = 24 * 60;
    boolean[] mark = new boolean[length];
    for (String time : timePoints) {
      String[] t = time.split(":");
      int m = Integer.valueOf(t[0]) * 60 + Integer.valueOf(t[1]);
      if (mark[m]) {
        return 0;
      }
      mark[m] = true;
    }

    int min = Integer.MAX_VALUE;
    int prev = -1, first = -1, last = -1;
    for (int i = 0; i < mark.length; i++) {
      if (mark[i]) {
        if (prev != -1) {
          min = Math.min(min, i - prev);
        }
        if (first == -1) {
          first = i;
        }
        prev = last = i;
      }
    }

    // diff between begin and end time
    min = Math.min(min, (length - last + first));

    return min;
  }
}
