package a00_collections;

import static org.junit.Assert.assertEquals;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class SlidingWindow {
  /**
   * Given an unsorted array of integers, find the length of longest continuous increasing subsequence
   * (subarray).
   * 
   * For example, if A = [1,3,5,4,7], The longest continuous increasing subsequence is [1,3,5], its
   * length is 3.
   * 
   * Solution:
   * 
   * For the continuous subarray issue, we can first consider using sliding window. As below, we can
   * use an anchor to mark the start of a new increasing subsequence at nums[i].
   */
  public int[] longestContinuousIncreasingSubarray(int[] nums) {
    int start = 0, end = 0;
    int max = 0, anchor = 0; // anchor is slow pointer
    for (int i = 0; i < nums.length; i++) {
      if (i > 0 && nums[i - 1] >= nums[i])
        anchor = i;
      if (max < i - anchor + 1) {
        max = i - anchor + 1;
        start = anchor;
        end = i;
      }
    }
    return new int[] { start, end };
  }

  /**
   * Given a string, find the length of the longest substring without repeating characters.
   * 
   * Examples:
   * 
   * Given "abcabcbb", the answer is "abc", which the length is 3.
   * 
   */
  // Use a map to track position easily
  public int lengthOfLongestSubstring(String s) {
    Map<Character, Integer> map = new HashMap<Character, Integer>();
    // Integer[] map = new Integer[256]; // Or use an array!
    int max = 0, left = 0, right = 0;
    while (right < s.length()) {
      char c = s.charAt(right);
      if (map.containsKey(c)) {
        left = Math.max(left, map.get(c) + 1);
      }
      map.put(c, right);
      max = Math.max(max, right - left + 1);
      right++;
    }
    return max;
  }

  // Use an array to achieve efficiency
  public int lengthOfLongestSubstring2(String s) {
    boolean[] seen = new boolean[256];
    char[] arr = s.toCharArray();
    int max = 0, left = 0, right = 0;
    // outer loop to increase window
    while (right < arr.length) {
      char c = arr[right];
      if (!seen[c]) {
        seen[c] = true;
      } else {
        max = Math.max(right - left, max);
        // inner loop to decrease window
        while (arr[right] != arr[left]) {
          char c2 = arr[left];
          seen[c2] = false;
          left++;
        }
        left++;
      }
      right++;
    }
    max = Math.max(right - left, max);
    return max;
  }

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

        // Lazy removal of an outgoing number
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

  // Use double linked list
  public class HitCounter {
    private int total;
    private int window;
    private Deque<int[]> hits;

    public HitCounter() {
      total = 0;
      window = 5 * 60; // 5 mins window
      hits = new LinkedList<int[]>();
    }

    public void hit(int timestamp) {
      if (hits.isEmpty() || hits.getLast()[0] != timestamp) {
        hits.add(new int[] { timestamp, 1 });
      } else {
        hits.getLast()[1]++;
        // hits.add(new int[] { timestamp, hits.removeLast()[1] + 1 });
      }
      // Prevent from growing too much
      if (hits.size() > window) {
        purge(timestamp);
      }
      total++;
    }

    public int getHits(int timestamp) {
      purge(timestamp);
      return total;
    }

    private void purge(int timestamp) {
      while (!hits.isEmpty() && timestamp - hits.getFirst()[0] >= window) {
        total -= hits.removeFirst()[1];
      }
    }
  }

  // Use array rotation
  public class HitCounter2 {
    private int total;
    private int window;
    private int[][] hits;

    public HitCounter2() {
      total = 0;
      window = 5 * 60; // 5 mins window
      hits = new int[window][2];
    }

    public void hit(int timestamp) {
      int i = timestamp % hits.length;
      if (hits[i][0] != timestamp) {
        purge(i, timestamp);
      }
      hits[i][1]++;
      total++;
    }

    public int getHits(int timestamp) {
      for (int i = 0; i < hits.length; i++) {
        if (hits[i][0] != 0 && timestamp - hits[i][0] >= window) {
          purge(i, timestamp);
        }
      }
      return total;
    }

    private void purge(int i, int timestamp) {
      total -= hits[i][1];
      hits[i][0] = timestamp;
      hits[i][1] = 0;
    }
  }

  public static void main(String[] args) {
    SlidingWindow solution = new SlidingWindow();
    assertEquals(1, solution.lengthOfLongestSubstring("bbbbb"));
    assertEquals(3, solution.lengthOfLongestSubstring("abcabcbb"));
    assertEquals(1, solution.lengthOfLongestSubstring2("bbbbb"));
    assertEquals(3, solution.lengthOfLongestSubstring2("abcabcbb"));

  }
}
