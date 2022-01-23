package a04_stacks_queues;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class CarFleet {
  public int carFleet(int target, int[] position, int[] speed) {

    int lgt = position.length;
    Map<Integer, Integer> map = new HashMap<>();

    for (int i = 0; i < lgt; i++) {
      map.put(position[i], speed[i]);
    }

    Arrays.sort(position);

    Stack<Double> stack = new Stack<>();

    double time_last = (target - position[lgt - 1]) * 1.0 / map.get(position[lgt - 1]);

    stack.push(time_last);
    for (int i = lgt - 2; i >= 0; i--) {
      double top = stack.pop();
      double curr_time = (target - position[i]) * 1.0 / map.get(position[i]);

      if (top >= curr_time) {
        stack.push(top);
      } else {
        stack.push(top);
        stack.push(curr_time);
      }

    }

    return stack.size();
  }
}
