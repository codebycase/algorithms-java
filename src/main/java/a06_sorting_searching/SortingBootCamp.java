package a06_sorting_searching;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import util.Interval;
import util.ListNode;

public class SortingBootCamp {

	/**
	 * Given two arrays, write a function to compute their intersection.
	 * 
	 * Example: Given nums1 = [1, 2, 2, 1], nums2 = [2, 2], return [2].
	 * 
	 * Note: Each element in the result must be unique. The result can be in any order.
	 */
	public static int[] intersection(int[] nums1, int[] nums2) {
		List<Integer> list = new ArrayList<>();
		Arrays.sort(nums1);
		Arrays.sort(nums2);
		int i = 0, j = 0;
		while (i < nums1.length && j < nums2.length) {
			if (nums1[i] == nums2[j] && (i == 0 || nums1[i] != nums1[i - 1])) {
				list.add(nums1[i]);
				i++;
				j++;
			} else if (nums1[i] < nums2[j]) {
				i++;
			} else {
				j++;
			}
		}
		int[] result = new int[list.size()];
		for (int k = 0; k < result.length; k++)
			result[k] = list.get(k);

		return result;
	}

	/**
	 * Write a program which takes an array of positive integers and returns the smallest number
	 * which is not the sum of a subset of elements of the array.
	 * 
	 */
	public static int smallestNonconstructibleValue(int[] A) {
		Arrays.sort(A);
		int maxConstructibleValue = 0;
		for (int a : A) {
			if (a > maxConstructibleValue + 1)
				break;
			maxConstructibleValue += a;
		}
		return maxConstructibleValue + 1;
	}

	/**
	 * Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if
	 * necessary).
	 * 
	 * You may assume that the intervals were initially sorted according to their start times.
	 * 
	 * Example 1: Given intervals [1,3],[6,9], insert and merge [2,5] in as [1,5],[6,9].
	 * 
	 * Example 2: Given [1,2],[3,5],[6,7],[8,10],[12,16], insert and merge [4,9] in as
	 * [1,2],[3,10],[12,16].
	 * 
	 * This is because the new interval [4,9] overlaps with [3,5],[6,7],[8,10].
	 */
	public List<Interval> insertInterval(List<Interval> intervals, Interval newInterval) {
		List<Interval> results = new ArrayList<>();
		int i = 0;

		while (i < intervals.size() && intervals.get(i).end < newInterval.start) {
			results.add(intervals.get(i++));
		}

		while (i < intervals.size() && intervals.get(i).start <= newInterval.end) {
			int start = Math.min(intervals.get(i).start, newInterval.start);
			int end = Math.max(intervals.get(i).end, newInterval.end);
			newInterval = new Interval(start, end);
			i++;
		}
		results.add(newInterval);

		while (i < intervals.size()) {
			results.add(intervals.get(i));
			i++;
		}

		return results;
	}

	/**
	 * Given a collection of intervals, merge all overlapping intervals.
	 * 
	 * For example, Given [1,3],[2,6],[8,10],[15,18], return [1,6],[8,10],[15,18].
	 */
	public List<Interval> mergeOverlappingIntervals(List<Interval> intervals) {
		if (intervals.size() <= 1)
			return intervals;

		Collections.sort(intervals, (a, b) -> (a.start - b.start));

		List<Interval> result = new LinkedList<Interval>();
		int start = intervals.get(0).start;
		int end = intervals.get(0).end;

		for (Interval interval : intervals) {
			if (interval.start <= end) {
				end = Math.max(end, interval.end);
			} else {
				result.add(new Interval(start, end));
				start = interval.start;
				end = interval.end;
			}
		}

		// add the last one
		result.add(new Interval(start, end));

		return result;
	}

	private TreeMap<Integer, Interval> treeMap = new TreeMap<>();

	public List<Interval> addNumberAndReturnDisjointIntervals(int val) {
		if (treeMap.containsKey(val))
			return new ArrayList<>(treeMap.values());
		Integer l = treeMap.lowerKey(val);
		Integer h = treeMap.higherKey(val);
		if (l != null && h != null && treeMap.get(l).end + 1 == val && h == val + 1) {
			treeMap.get(l).end = treeMap.get(h).end;
			treeMap.remove(h);
		} else if (l != null && treeMap.get(l).end + 1 >= val) {
			treeMap.get(l).end = Math.max(treeMap.get(l).end, val);
		} else if (h != null && h == val + 1) {
			treeMap.put(val, new Interval(val, treeMap.get(h).end));
			treeMap.remove(h);
		} else {
			treeMap.put(val, new Interval(val, val));
		}
		return new ArrayList<>(treeMap.values());
	}

	/**
	 * Sort a linked list in O(nlog(n)) time using constant space complexity.
	 */
	public ListNode sortList(ListNode head) {
		if (head == null || head.next == null)
			return head;

		// step 1. cut the list to two halves
		ListNode prev = null, slow = head, fast = head;
		while (fast != null && fast.next != null) {
			prev = slow;
			slow = slow.next;
			fast = fast.next.next;
		}
		prev.next = null;

		// step 2. sort each half
		ListNode l1 = sortList(head);
		ListNode l2 = sortList(slow);

		// step 3. merge l1 and l2
		return merge(l1, l2);

	}

	private ListNode merge(ListNode l1, ListNode l2) {
		if (l1 == null)
			return l2;
		if (l2 == null)
			return l1;
		if (l1.val < l2.val) {
			l1.next = merge(l1.next, l2);
			return l1;
		} else {
			l2.next = merge(l1, l2.next);
			return l2;
		}
	}

	/**
	 * Design an algorithm for computing the salary cap, given existing salaries and the target
	 * payroll.
	 * 
	 * For example, if there were five employees with salaries last year were $90, $30, $100, $40,
	 * and $20, and the target payroll this year is $210, then $60 is a suitable salary cap, since
	 * 60+30+60+40+20=210.
	 *
	 */
	public static double findSalaryCap(int[] currentSalaries, int targetPayroll) {
		Arrays.sort(currentSalaries);
		double unadjustedSalarySum = 0;
		for (int i = 0; i < currentSalaries.length; i++) {
			int adjustedPeople = currentSalaries.length - i;
			double adjustedSalarySum = currentSalaries[i] * adjustedPeople;
			if (unadjustedSalarySum + adjustedSalarySum >= targetPayroll) {
				return (targetPayroll - unadjustedSalarySum) / adjustedPeople;
			}
			unadjustedSalarySum += currentSalaries[i];
		}
		return -1.0;
	}

	class MyCalendar {
		List<int[]> books = new ArrayList<>();

		// O(n)
		public boolean book(int start, int end) {
			for (int[] b : books)
				if (Math.max(b[0], start) < Math.min(b[1], end))
					return false;
			books.add(new int[] { start, end });
			return true;
		}
	}

	class MyCalendar2 {
		private TreeMap<Integer, Integer> treeMap = new TreeMap<>();

		// O(log(n))
		public boolean book(int start, int end) {
			Integer i = treeMap.lowerKey(end);
			if (i != null && i >= start)
				return false;
			i = treeMap.lowerKey(start);
			if (i != null && treeMap.get(i) > start)
				return false;
			treeMap.put(start, end);
			return true;
		}
	}

	class MyCalendarII {
		private List<int[]> books = new ArrayList<>();

		public boolean book(int s, int e) {
			MyCalendar overlaps = new MyCalendar();
			for (int[] b : books)
				if (Math.max(b[0], s) < Math.min(b[1], e)) // overlap exist
					if (!overlaps.book(Math.max(b[0], s), Math.min(b[1], e)))
						return false; // overlaps overlapped
			books.add(new int[] { s, e });
			return true;
		}
	}

	class MyCalendarIII {
		private Map<Integer, Integer> times = new TreeMap<>();

		public int book(int s, int e) {
			times.put(s, times.getOrDefault(s, 0) + 1); // 1 new event will be starting at times[s]
			times.put(e, times.getOrDefault(e, 0) - 1); // 1 new event will be ending at times[e];
			int ongoing = 0, k = 0;
			for (int v : times.values())
				k = Math.max(k, ongoing += v);
			return k;
		}
	}

	public static void main(String[] args) {
		int[] num1 = new int[] { 2, 3, 3, 5, 5, 6, 7, 7, 8, 12 };
		int[] num2 = new int[] { 5, 5, 6, 8, 8, 9, 10, 10 };
		int[] result = intersection(num1, num2);
		assert Arrays.equals(result, new int[] { 5, 6, 8 });
		int[] A = new int[] { 1, 1, 1, 1, 1, 5, 10, 25 };
		assert smallestNonconstructibleValue(A) == 21;
		int[] salaries = new int[] { 90, 30, 100, 40, 20 };
		assert findSalaryCap((salaries), 210) == 60;
	}
}
