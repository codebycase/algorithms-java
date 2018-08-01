package a04_stacks_queues;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Stack;

/**
 * Given n non-negative integers representing the histogram's bar height where the width of each bar
 * is 1, find the area of largest rectangle in the histogram.
 * 
 * @author lchen
 *
 */
public class LargestRectangleInHistogram {
	public static int largestRectangleArea(int[] heights) {
		int len = heights.length;
		Stack<Integer> s = new Stack<Integer>();
		int maxArea = 0;
		for (int i = 0; i <= len; i++) {
			int h = (i == len ? 0 : heights[i]);
			if (s.isEmpty() || h >= heights[s.peek()]) {
				s.push(i);
			} else {
				int tp = s.pop();
				maxArea = Math.max(maxArea, heights[tp] * (s.isEmpty() ? i : i - 1 - s.peek()));
				i--;
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
