package a10_recursion_greedy_invariant;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Stack;

public class HistogramQuestions {
  /**
   * Given n non-negative integers representing the histogram's bar height where the width of each bar
   * is 1, find the area of largest rectangle in the histogram.
   */
  public static int largestRectangleArea(int[] heights) {
    int len = heights.length;
    Stack<Integer> s = new Stack<>();
    int maxArea = 0;
    for (int i = 0; i <= len; i++) {
      int h = (i == len ? 0 : heights[i]);
      if (s.isEmpty() || h >= heights[s.peek()]) {
        s.push(i);
      } else {
        int tp = s.pop();
        maxArea = Math.max(maxArea, heights[tp] * (s.isEmpty() ? i : i - 1 - s.peek()));
        i--; // keep trying until!
      }

    }
    return maxArea;
  }

  /**
   * Write a program which takes as an input an integer array and returns the pair of entries that
   * trap the maximum amount of water.
   */
  public static int maxTrappedWaterInLines(List<Integer> heights) {
    int i = 0, j = heights.size() - 1, maxWater = 0;
    while (i < j) {
      int width = i - j;
      maxWater = Math.max(maxWater, width * Math.min(heights.get(i), heights.get(j)));
      if (heights.get(i) > heights.get(j))
        j--;
      else
        i++;
    }
    return maxWater;
  }

  /**
   * Given n non-negative integers representing an elevation map where the width of each bar is 1,
   * compute how much water it is able to trap after raining.
   * 
   * For example, Given [0,1,0,2,1,0,1,3,2,1,2,1], return 6.
   * 
   */
  public static int computeHistogramVolume(int[] heights) {
    int a = 0;
    int b = heights.length - 1;
    int max = 0;
    int leftMax = 0;
    int rightMax = 0;
    while (a <= b) {
      leftMax = Math.max(leftMax, heights[a]);
      rightMax = Math.max(rightMax, heights[b]);
      if (leftMax < rightMax) {
        max += (leftMax - heights[a]);
        a++;
      } else {
        max += (rightMax - heights[b]);
        b--;
      }
    }
    return max;
  }

  public static int computeHistogramVolume2(int[] heights) {
    Deque<Integer> stack = new ArrayDeque<>();
    int max = 0;
    int current = 0;
    while (current < heights.length) {
      while (!stack.isEmpty() && heights[current] > heights[stack.peek()]) {
        int top = stack.pop();
        if (stack.isEmpty())
          break;
        int distance = current - stack.peek() - 1;
        int height = Math.min(heights[current], heights[stack.peek()]) - heights[top];
        max += height * distance;
      }
      stack.push(current++);
    }
    return max;
  }

  public static int[] pourWaterInHistogram(int[] heights, int units, int index) {
    while (units-- > 0) {
      boolean foundBest = false;
      for (int dir : new int[] { -1, 1 }) {
        // two pointers shit together
        int i = index, j = index + dir, best = index;
        while (0 <= j && j < heights.length && heights[j] <= heights[i]) {
          if (heights[j] < heights[i])
            best = j;
          i = j;
          j += dir;
        }
        // break if found the best
        if (best != index) {
          heights[best]++;
          foundBest = true;
          break;
        }
      }
      // Otherwise pour straight down
      if (!foundBest) {
        heights[index]++;
      }
    }
    return heights;
  }

  public static void main(String[] args) {
    int[] heights = { 0, 0, 4, 0, 0, 6, 0, 0, 3, 0, 8, 0, 2, 0, 5, 2, 0, 3, 0, 0 };
    assert computeHistogramVolume(heights) == 46;
    assert computeHistogramVolume2(heights) == 46;
    heights = new int[] { 2, 1, 1, 2, 1, 2, 2 };
    assert Arrays.toString(pourWaterInHistogram(heights, 4, 3)).equals("[2, 2, 2, 3, 2, 2, 2]");
  }
}
