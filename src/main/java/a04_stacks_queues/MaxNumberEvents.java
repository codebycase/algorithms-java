package a04_stacks_queues;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class MaxNumberEvents {
  public int maxEvents(int[][] events) {
    Arrays.sort(events, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);

    int count = 0;
    int last = events[events.length - 1][1];
    Queue<Integer> queue = new PriorityQueue<>();

    for (int day = 1, i = 0; day <= last; day++) {
      // Add all the events that start this day;
      while (i < events.length && events[i][0] == day) {
        int end = events[i][1];
        // Address multiple events with the same end day
        last = Math.max(last, end);
        queue.offer(end);
        i++;
      }

      // Remove the events unable to attend
      while (!queue.isEmpty() && queue.peek() < day) {
        queue.poll();
      }

      // Attend only one event per day
      if (!queue.isEmpty() && queue.peek() >= day) {
        count++;
        queue.poll();
      }
    }

    return count;
  }

  public static void main(String[] args) {
    MaxNumberEvents solution = new MaxNumberEvents();
    int result = solution.maxEvents(new int[][] { { 1, 2 }, { 2, 3 }, { 3, 4 }, { 1, 2 } });
    System.out.println(result);
  }

}
