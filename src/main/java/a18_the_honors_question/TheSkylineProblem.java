package a18_the_honors_question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * A city's skyline is the outer contour of the silhouette formed by all the buildings in that city
 * when viewed from a distance. Now suppose you are given the locations and height of all the
 * buildings as shown on a cityscape photo (Figure A), write a program to output the skyline formed
 * by these buildings collectively (Figure B).
 * 
 * 
 * The geometric information of each building is represented by a triplet of integers [Li, Ri, Hi],
 * where Li and Ri are the x coordinates of the left and right edge of the ith building,
 * respectively, and Hi is its height. It is guaranteed that 0 �� Li, Ri �� INT_MAX, 0 < Hi ��
 * INT_MAX, and Ri - Li > 0. You may assume all buildings are perfect rectangles grounded on an
 * absolutely flat surface at height 0.
 * 
 * For instance, the dimensions of all buildings in Figure A are recorded as: [ [2 9 10], [3 7 15],
 * [5 12 12], [15 20 10], [19 24 8] ] .
 * 
 * The output is a list of "key points" (red dots in Figure B) in the format of [ [x1,y1], [x2, y2],
 * [x3, y3], ... ] that uniquely defines a skyline. A key point is the left endpoint of a horizontal
 * line segment. Note that the last key point, where the rightmost building ends, is merely used to
 * mark the termination of the skyline, and always has zero height. Also, the ground in between any
 * two adjacent buildings should be considered part of the skyline contour.
 * 
 * For instance, the skyline in Figure B should be represented as:[ [2 10], [3 15], [7 12], [12 0],
 * [15 10], [20 8], [24, 0] ].
 * 
 * Notes:
 * 
 * <pre>
The number of buildings in any input list is guaranteed to be in the range [0, 10000].
The input list is already sorted in ascending order by the left x position Li.
The output list must be sorted by the x position.
There must be no consecutive horizontal lines of equal height in the output skyline. For instance, [...[2 3], [4 5], [7 5], [11 5], [12 7]...] is not acceptable; the three lines of height 5 should be merged into one in the final output as such: [...[2 3], [4 5], [12 7], ...]
 * </pre>
 * 
 * @author lchen
 *
 */
public class TheSkylineProblem {
	public List<int[]> getSkyline(int[][] buildings) {
		List<int[]> result = new ArrayList<>();
		List<int[]> heights = new ArrayList<>();
		for (int[] building : buildings) {
			heights.add(new int[] { building[0], -building[2] });
			heights.add(new int[] { building[1], building[2] });
		}
		Collections.sort(heights, (a, b) -> (a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]));
		Queue<Integer> queue = new PriorityQueue<>((a, b) -> (b - a));
		queue.offer(0);
		int prev = 0;
		for (int[] height : heights) {
			if (height[1] < 0)
				queue.offer(-height[1]);
			else
				queue.remove(height[1]);
			int curr = queue.peek();
			if (curr != prev) {
				result.add(new int[] { height[0], curr });
				prev = curr;
			}
		}
		return result;
	}

	// use tree map
	public List<int[]> getSkyline2(int[][] buildings) {
		int minLeft = Integer.MAX_VALUE, maxRight = Integer.MIN_VALUE;
		for (int[] building : buildings) {
			minLeft = Math.min(minLeft, building[0]);
			maxRight = Math.max(maxRight, building[1]);
		}
		int[] heights = new int[maxRight - minLeft + 1];
		for (int[] building : buildings) {
			for (int i = building[0]; i <= building[1]; i++) {
				heights[i - minLeft] = Math.max(heights[i - minLeft], building[2]);
			}
		}
		List<int[]> result = new ArrayList<>();
		int left = 0;
		for (int i = 1; i < heights.length; i++) {
			if (heights[i] != heights[i - 1]) {
				result.add(new int[] { left + minLeft, i - 1 + minLeft, heights[i - 1] });
				left = i;
			}
		}
		result.add(new int[] { left + minLeft, maxRight, heights[heights.length - 1] });
		return result;
	}

	public int maxIncreaseKeepingSkyline(int[][] grid) {
		int n = grid.length;
		int[] col = new int[n], row = new int[n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				row[i] = Math.max(row[i], grid[i][j]);
				col[j] = Math.max(col[j], grid[i][j]);
			}
		}
		int result = 0;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				result += Math.min(row[i], col[j]) - grid[i][j];
		return result;
	}
}
