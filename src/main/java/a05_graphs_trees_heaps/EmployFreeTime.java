package a05_graphs_trees_heaps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import util.Interval;

/**
 * https://leetcode.com/problems/employee-free-time/
 * 
 * @author lchen676
 *
 */
public class EmployFreeTime {
  // Time complexity: nlog(k), space complexity: k
  public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
    Queue<int[]> queue = new PriorityQueue<>((a, b) -> schedule.get(a[0]).get(a[1]).start - schedule.get(b[0]).get(b[1]).start);
    for (int i = 0; i < schedule.size(); i++) {
      queue.add(new int[] { i, 0 });
    }
    List<Interval> result = new ArrayList<>();
    int prevEnd = schedule.get(queue.peek()[0]).get(queue.peek()[1]).start;
    while (!queue.isEmpty()) {
      int[] index = queue.poll();
      Interval interval = schedule.get(index[0]).get(index[1]);
      if (interval.start > prevEnd) {
        result.add(new Interval(prevEnd, interval.start));
      }
      prevEnd = Math.max(prevEnd, interval.end);
      if (schedule.get(index[0]).size() > index[1] + 1) {
        queue.add(new int[] { index[0], index[1] + 1 });
      }
    }
    return result;
  }

  // Merge intervals first, then find out gaps
  public List<Interval> employeeFreeTime2(List<List<Interval>> schedule) {
    List<Interval> res = new ArrayList<>();
    List<Interval> common = new ArrayList<>();
    List<Interval> schedules = new ArrayList<>();
    for (List<Interval> intervals : schedule) {
      for (Interval interval : intervals) {
        schedules.add(interval);
      }
    }
    Collections.sort(schedules, (a, b) -> Integer.compare(a.start, b.start));
    common.add(schedules.get(0));
    for (int i = 1; i < schedules.size(); i++) {
      if (schedules.get(i).start <= common.get(common.size() - 1).end) {
        common.get(common.size() - 1).end = Math.max(schedules.get(i).end, common.get(common.size() - 1).end);
      } else {
        common.add(schedules.get(i));
      }
    }
    for (int i = 1; i < common.size(); i++) {
      res.add(new Interval(common.get(i - 1).end, common.get(i).start));
    }
    return res;
  }
}
