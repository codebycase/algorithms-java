package a02_arrays_strings;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

// https://leetcode.com/problems/minimum-number-of-flips-to-convert-binary-matrix-to-zero-matrix/
// O(m * n * 2 ^ (m * n)), Space: O(2 ^ (m * n)).
public class MinimumFlips {
  /**
   */
  public int minFlips(int[][] mat) {
    int[] d = { 0, 0, 1, 0, -1, 0 };
    int start = 0, m = mat.length, n = mat[0].length;
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        start |= mat[i][j] << (i * n + j);
      }
    }

    Queue<Integer> queue = new LinkedList<>();
    queue.offer(start);
    Set<Integer> seen = new HashSet<>();
    seen.add(start);

    int step = 0;
    while (!queue.isEmpty()) {
      int size = queue.size();
      for (int s = 0; s < size; s++) {
        int curr = queue.poll();
        if (curr == 0)
          return step;

        for (int i = 0; i < m; i++) {
          for (int j = 0; j < n; j++) {
            int next = curr;
            for (int k = 0; k < 5; k++) {
              int r = i + d[k], c = j + d[k + 1];
              if (r >= 0 && r < m && c >= 0 && c < n) {
                next ^= 1 << (r * n + c);
              }
            }
            if (seen.add(next)) {
              queue.offer(next);
            }
          }
        }
      }
      step++;
    }

    return -1;
  }
}
