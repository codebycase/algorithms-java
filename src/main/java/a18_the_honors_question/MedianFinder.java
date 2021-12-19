package a18_the_honors_question;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * The median is the middle value in an ordered integer list. If the size of the list is even, there
 * is no middle value and the median is the mean of the two middle values.
 * 
 * For example, for arr = [2,3,4], the median is 3. For example, for arr = [2,3], the median is (2 +
 * 3) / 2 = 2.5. Implement the MedianFinder class:
 * 
 * MedianFinder() initializes the MedianFinder object. void addNum(int num) adds the integer num
 * from the data stream to the data structure. double findMedian() returns the median of all
 * elements so far. Answers within 10-5 of the actual answer will be accepted.
 *
 */
public class MedianFinder {
  private Queue<Integer> small; // ascending order
  private Queue<Integer> large; // descending order

  public MedianFinder() {
    small = new PriorityQueue<>();
    large = new PriorityQueue<>(Collections.reverseOrder());
  }

  public void addNum(int num) {
    if (small.size() > 0 && num > small.peek()) {
      small.offer(num);
    } else {
      large.offer(num);
    }
    if (large.size() - small.size() == 2) {
      small.offer(large.poll());
    } else if (small.size() - large.size() == 2) {
      large.offer(small.poll());
    }
  }

  public double findMedian() {
    if (large.size() > small.size()) {
      return (double) large.peek();
    } else if (small.size() > large.size()) {
      return (double) small.peek();
    } else {
      return (large.peek() + small.peek()) / 2.0;
    }
  }
}
