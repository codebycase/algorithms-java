package a18_the_honors_question;

import java.util.Deque;
import java.util.LinkedList;

/**
 * <pre>
Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position.

For example,
Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.

Window position                Max
---------------               -----
[1  3  -1] -3  5  3  6  7       3
 1 [3  -1  -3] 5  3  6  7       3
 1  3 [-1  -3  5] 3  6  7       5
 1  3  -1 [-3  5  3] 6  7       5
 1  3  -1  -3 [5  3  6] 7       6
 1  3  -1  -3  5 [3  6  7]      7
Therefore, return the max sliding window as [3,3,5,5,6,7].

Note: 
You may assume k is always valid, ie: 1 ≤ k ≤ input array's size for non-empty array.

Follow up:
Could you solve it in linear time?
 * </pre>
 * 
 * @author lchen
 * @category Hard
 *
 */
public class SlidingWindowMaximum {
	public int[] maxSlidingWindow(int[] nums, int k) {
		if (nums.length == 0 || k <= 0)
			return new int[0];
		int[] result = new int[nums.length - k + 1];
		Deque<Integer> queue = new LinkedList<>();
		for (int i = 0; i < nums.length; i++) {
			// discard if the element is out of the k size window
			while (!queue.isEmpty() && queue.peek() < i - k + 1) {
				queue.poll();
			}
			// discard elements smaller than nums[i] from the tail
			while (!queue.isEmpty() && nums[queue.peekLast()] < nums[i]) {
				queue.pollLast();
			}
			queue.offer(i);
			if (i >= k - 1) {
				result[i - k + 1] = nums[queue.peek()];
			}
		}
		return result;
	}
}
