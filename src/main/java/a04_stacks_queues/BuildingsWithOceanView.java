package a04_stacks_queues;

import java.util.Deque;
import java.util.LinkedList;

public class BuildingsWithOceanView {
  // Count increasing heights backward
  public int[] findBuildings(int[] heights) {
    int n = heights.length, max = 0, count = 0;

    // count all valid heights
    for (int i = n - 1; i >= 0; i--) {
      if (heights[i] > max) {
        max = heights[i];
        count++;
      }
    }

    max = 0; // reset
    // back fill result
    int[] result = new int[count];
    for (int i = n - 1; i >= 0; i--) {
      if (heights[i] > max) {
        max = heights[i];
        result[--count] = i;
      }
    }

    return result;
  }

  // Use monotonic queue to track
  public int[] findBuildings2(int[] heights) {
    Deque<Integer> deque = new LinkedList<>();

    for (int i = 0; i < heights.length; i++) {
      while (!deque.isEmpty() && heights[deque.peekLast()] <= heights[i]) {
        deque.pollLast();
      }
      deque.addLast(i);
    }

    int[] result = new int[deque.size()];
    int i = 0;
    while (!deque.isEmpty()) {
      result[i++] = deque.pollFirst();
    }

    return result;
  }
}
