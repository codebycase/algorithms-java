package a04_stacks_queues;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * https://leetcode.com/problems/max-value-of-equation/
 * 
 * @author lchen676
 *
 */
public class MaxValueOfEquation {
  public int findMaxValueOfEquation(int[][] points, int k) {
    Queue<int[]> queue = new PriorityQueue<>((a, b) -> a[0] == b[0] ? a[1] - b[1] : b[0] - a[0]);
    int ans = Integer.MIN_VALUE;
    for (int[] point : points) {
      while (!queue.isEmpty() && point[0] - queue.peek()[1] > k) {
        queue.poll();
      }
      if (!queue.isEmpty()) {
        ans = Math.max(ans, queue.peek()[0] + point[0] + point[1]);
      }
      queue.offer(new int[] { point[1] - point[0], point[0] });
    }
    return ans;
  }
}
