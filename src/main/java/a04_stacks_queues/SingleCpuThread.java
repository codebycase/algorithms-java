package a04_stacks_queues;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class SingleCpuThread {
  public int[] getOrder(int[][] tasks) {
    int n = tasks.length;
    int[] ans = new int[n];
    int[][] extTasks = new int[n][3];
    for (int i = 0; i < n; i++) {
      extTasks[i][0] = i;
      extTasks[i][1] = tasks[i][0];
      extTasks[i][2] = tasks[i][1];
    }
    Arrays.sort(extTasks, (a, b) -> a[1] - b[1]);
    Queue<int[]> queue = new PriorityQueue<int[]>((a, b) -> a[2] == b[2] ? a[0] - b[0] : a[2] - b[2]);
    int time = 0;
    int ansIdx = 0;
    int taskIdx = 0;
    while (ansIdx < n) {
      while (taskIdx < n && extTasks[taskIdx][1] <= time) {
        queue.offer(extTasks[taskIdx++]);
      }
      if (queue.isEmpty()) {
        time = extTasks[taskIdx][1];
        continue;
      }
      int[] bestFit = queue.poll();
      ans[ansIdx++] = bestFit[0];
      time += bestFit[2];
    }
    return ans;
  }
}
