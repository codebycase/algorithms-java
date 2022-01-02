package a04_stacks_queues;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * Given n non-negative integers representing the histogram's bar height where the width of each bar
 * is 1, find the area of largest rectangle in the histogram.
 * 
 * @author lchen
 *
 */
public class LargestRectangleInHistogram {
  public static int largestRectangleArea(int[] heights) {
    Deque<Integer> stack = new ArrayDeque<>();
    int maxArea = 0;
    stack.add(-1); // Add a dummy index
    for (int i = 0; i <= heights.length; i++) {
      int height = (i == heights.length ? 0 : heights[i]);
      if (stack.peek() == -1 || height >= heights[stack.peek()]) {
        stack.push(i);
      } else {
        int pillarIndex = stack.pop();
        maxArea = Math.max(maxArea, heights[pillarIndex] * (i - 1 - stack.peek()));
        i--; // Achieve to compare current height with previous heights as far as it can go!
      }
    }
    return maxArea;
  }

  public static int largestRectangleArea2(int[] heights) {
    Deque<Integer> stack = new ArrayDeque<>();
    int maxArea = 0;
    for (int i = 0; i <= heights.length; i++) {
      int height = (i == heights.length ? 0 : heights[i]);
      if (stack.isEmpty() || height >= heights[stack.peek()]) {
        stack.push(i);
      } else {
        int barIndex = stack.pop();
        maxArea = Math.max(maxArea, heights[barIndex] * (stack.isEmpty() ? i : i - 1 - stack.peek()));
        i--; // Until we hit a stack which is equal or smaller than the current bar
      }
    }
    return maxArea;
  }

  public static int largestRectangleArea(List<Integer> heights) {
    Deque<Integer> pillarIndices = new ArrayDeque<>();
    int maxArea = 0;
    // By iterationg to heights.size() instead of heights.size() - 1, we can uniformly handle
    // the computation for rectangle area here.
    for (int i = 0; i <= heights.size(); i++) {
      while (!pillarIndices.isEmpty() && isNewPillarOrReachEnd(heights, i, pillarIndices.peek())) {
        int height = heights.get(pillarIndices.pop());
        int width = pillarIndices.isEmpty() ? i : i - pillarIndices.peek() - 1;
        maxArea = Math.max(maxArea, height * width);
      }
      pillarIndices.push(i);
    }
    return maxArea;
  }

  private static boolean isNewPillarOrReachEnd(List<Integer> heights, int currIdx, int prevIdx) {
    return currIdx < heights.size() ? heights.get(currIdx) <= heights.get(prevIdx) : true;
  }

  public static void main(String[] args) {
    assert largestRectangleArea(new int[] { 6, 2, 5, 4, 5, 1, 6 }) == 12;
    assert largestRectangleArea(new int[] { 2, 1, 5, 6, 2, 3 }) == 10;
    assert largestRectangleArea(Arrays.asList(6, 2, 5, 4, 5, 1, 6)) == 12;
    assert largestRectangleArea(Arrays.asList(2, 1, 5, 6, 2, 3)) == 10;
  }
}
