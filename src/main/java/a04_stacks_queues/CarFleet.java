package a04_stacks_queues;

import java.util.Arrays;
import java.util.Stack;

public class CarFleet {
  /**
   * https://leetcode.com/problems/car-fleet/
   */
  public int carFleet(int target, int[] position, int[] speed) {
    int n = position.length;
    int[][] cars = new int[n][2];

    for (int i = 0; i < n; i++) {
      cars[i] = new int[] { position[i], speed[i] };
    }

    Arrays.sort(cars, (a, b) -> a[0] - b[0]);
    
    double timeLast = (target - cars[n - 1][0]) * 1.0 / cars[n - 1][1];

    Stack<Double> stack = new Stack<>();
    stack.push(timeLast);

    for (int i = n - 2; i >= 0; i--) {
      double currTime = (target - cars[i][0]) * 1.0 / cars[i][1];
      if (stack.peek() < currTime) {
        stack.push(currTime);
      }
    }

    return stack.size();
  }
}
