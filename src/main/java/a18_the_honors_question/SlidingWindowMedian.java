package a18_the_honors_question;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * The median is the middle value in an ordered integer list. If the size of the list is even, there
 * is no middle value. So the median is the mean of the two middle values.
 * 
 * For examples, if arr = [2,3,4], the median is 3. For examples, if arr = [1,2,3,4], the median is
 * (2 + 3) / 2 = 2.5. You are given an integer array nums and an integer k. There is a sliding
 * window of size k which is moving from the very left of the array to the very right. You can only
 * see the k numbers in the window. Each time the sliding window moves right by one position.
 * 
 * Return the median array for each window in the original array. Answers within 10-5 of the actual
 * value will be accepted.
 * 
 * 
 * <pre>
 * Example 1:

 * Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
 * Output: [1.00000,-1.00000,-1.00000,3.00000,5.00000,6.00000]
 * Explanation: 
 * Window position                Median
 * ---------------                -----
 *[1  3  -1] -3  5  3  6  7        1
 * 1 [3  -1  -3] 5  3  6  7       -1
 * 1  3 [-1  -3  5] 3  6  7       -1
 * 1  3  -1 [-3  5  3] 6  7        3
 * 1  3  -1  -3 [5  3  6] 7        5
 * 1  3  -1  -3  5 [3  6  7]       6
 * </pre>
 *
 * Solution: Two Heaps (Lazy Removal); <br>
 * Time complexity: O(2⋅nlogk)+O(n−k)≈O(nlogk) <br>
 * Space complexity: O(k)+O(n)≈O(n) extra linear space.
 */
public class SlidingWindowMedian {
  public double[] medianSlidingWindow(int[] nums, int k) {
    Queue<Integer> large = new PriorityQueue<>((a, b) -> nums[a] == nums[b] ? Integer.compare(a, b) : Integer.compare(nums[a], nums[b]));
    Queue<Integer> small = new PriorityQueue<>((a, b) -> nums[a] == nums[b] ? Integer.compare(a, b) : Integer.compare(nums[b], nums[a]));
    double[] ans = new double[nums.length - k + 1];
    int balance = 0;
    int i = 0;
    while (i < nums.length) {
      if (large.isEmpty() || nums[i] >= nums[large.peek()]) {
        large.offer(i);
        balance++;
      } else {
        small.offer(i);
        balance--;
      }
      i++;

      while (balance > 1 || (!large.isEmpty() && large.peek() < i - k)) {
        int min = large.poll();
        if (min >= i - k) {
          small.offer(min);
          balance -= 2;
        }
      }

      while (balance < 0 || (!small.isEmpty() && small.peek() < i - k)) {
        int max = small.poll();
        if (max >= i - k) {
          large.offer(max);
          balance += 2;
        }
      }

      if (i - k >= 0) {
        ans[i - k] = k % 2 == 0 ? ((double) nums[small.peek()] + (double) nums[large.peek()]) / 2 : (double) nums[large.peek()];

        if (!small.isEmpty() && i - k == small.peek()) {
          small.poll();
          balance++;
        } else if (i - k == large.peek()) {
          large.poll();
          balance--;
        } else if (nums[i - k] >= nums[large.peek()]) {
          balance--;
        } else {
          balance++;
        }
      }

    }

    return ans;
  }
}
