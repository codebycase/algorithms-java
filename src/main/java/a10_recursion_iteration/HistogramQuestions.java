package a10_recursion_iteration;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Stack;

public class HistogramQuestions {
	/**
	 * Given n non-negative integers representing the histogram's bar height where the width of each
	 * bar is 1, find the area of largest rectangle in the histogram.
	 */
	public int largestRectangleArea(int[] heights) {
		Stack<Integer> stack = new Stack<>();
		stack.push(-1);
		int maxArea = 0;
		for (int i = 0; i < heights.length; ++i) {
			while (stack.peek() != -1 && heights[stack.peek()] >= heights[i]) {
				maxArea = Math.max(maxArea, heights[stack.pop()] * (i - stack.peek() - 1));
			}
			stack.push(i);
		}
		while (stack.peek() != -1)
			maxArea = Math.max(maxArea, heights[stack.pop()] * (heights.length - stack.peek() - 1));
		return maxArea;
	}

	/**
	 * Write a program which takes as an input an integer array and returns the pair of entries that
	 * trap the maximum amount of water.
	 */
	public int maxTrappedWaterInLines(List<Integer> heights) {
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

	public static void main(String[] args) {
		int[] heights = { 0, 0, 4, 0, 0, 6, 0, 0, 3, 0, 8, 0, 2, 0, 5, 2, 0, 3, 0, 0 };
		assert computeHistogramVolume(heights) == 46;
		assert computeHistogramVolume2(heights) == 46;
	}
}
