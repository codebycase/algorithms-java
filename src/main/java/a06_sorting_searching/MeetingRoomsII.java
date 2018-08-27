package a06_sorting_searching;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...]
 * (si < ei), find the minimum number of conference rooms required.
 * 
 * For example, Given [[0, 30],[5, 10],[15, 20]], return 2.
 * 
 * @author lchen
 *
 */
public class MeetingRoomsII {
	/* solution1: based on sorting */
	public int minMeetingRooms(Interval[] intervals) {
		if (intervals == null || intervals.length == 0)
			return 0;
		int[] starts = new int[intervals.length];
		int[] ends = new int[intervals.length];
		for (int i = 0; i < intervals.length; i++) {
			starts[i] = intervals[i].start;
			ends[i] = intervals[i].end;
		}
		Arrays.sort(starts);
		Arrays.sort(ends);
		int rooms = 0;
		int endsIdx = 0;
		for (int i = 0; i < starts.length; i++) {
			if (starts[i] < ends[endsIdx])
				rooms++;
			else
				endsIdx++;
		}
		return rooms;
	}

	/* solution2: using heap */
	public int minMeetingRooms2(Interval[] intervals) {
		if (intervals == null || intervals.length == 0)
			return 0;

		Arrays.sort(intervals, (a, b) -> (a.start - b.start));
		Queue<Interval> heap = new PriorityQueue<Interval>(intervals.length, (a, b) -> (a.end - b.end));
		heap.offer(intervals[0]);

		for (int i = 1; i < intervals.length; i++) {
			// get the meeting room that finishes earliest
			Interval interval = heap.poll();
			if (intervals[i].start >= interval.end) {
				interval.end = intervals[i].end;
			} else {
				// otherwise, this meeting needs a new room
				heap.offer(intervals[i]);
			}
			// don't forget to put the meeting room back;
			heap.offer(interval);
		}

		return heap.size();
	}

	public List<Interval> merge(List<Interval> intervals) {
		List<Interval> list = new ArrayList<>(); // or linked list
		Collections.sort(intervals, (a, b) -> (a.start - b.start));

		for (Interval interval : intervals) {
			if (list.isEmpty()) {
				list.add(interval);
				continue;
			}
			Interval last = list.get(list.size() - 1);
			if (last.end < interval.start)
				list.add(interval);
			else {
				last.end = Math.max(last.end, interval.end);
			}
		}

		return list;
	}

	private class Interval {
		int start;
		int end;

		public Interval(int s, int e) {
			start = s;
			end = e;
		}
	}

	public static void main(String[] args) {
		MeetingRoomsII solution = new MeetingRoomsII();
		Interval[] intervals = new Interval[3];
		intervals[0] = solution.new Interval(0, 30);
		intervals[1] = solution.new Interval(5, 10);
		intervals[2] = solution.new Interval(15, 20);
		assert solution.minMeetingRooms(intervals) == 2;
		assert solution.minMeetingRooms2(intervals) == 2;
		intervals = new Interval[5];
		intervals[0] = solution.new Interval(2, 15);
		intervals[1] = solution.new Interval(36, 45);
		intervals[2] = solution.new Interval(9, 25);
		intervals[3] = solution.new Interval(16, 23);
		intervals[4] = solution.new Interval(4, 9);
		assert solution.minMeetingRooms(intervals) == 2;
		assert solution.minMeetingRooms2(intervals) == 2;
	}
}
