package a10_recursion_greedy_invariant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import util.Interval;

public class GreedyBootCamp {
	public static int minimumTotalWaitingTime(List<Integer> serviceTimes) {
		Collections.sort(serviceTimes);
		int totalWaitingTime = 0;
		for (int i = 0; i < serviceTimes.size(); i++) {
			// exclude the last one!
			int numRemainingQueries = serviceTimes.size() - (i + 1);
			totalWaitingTime += serviceTimes.get(i) * numRemainingQueries;
		}
		return totalWaitingTime;
	}

	public static Integer findMinimumVisits(List<Interval> intervals) {
		Collections.sort(intervals, (a, b) -> (a.right - b.right));
		int numVisits = 0;
		int lastVisitTime = 0;
		for (Interval interval : intervals) {
			if (interval.left > lastVisitTime) {
				lastVisitTime = interval.right;
				numVisits++;
			}
		}
		return numVisits;
	}

	public static void main(String[] args) {
		List<Integer> serviceTimes = Arrays.asList(2, 5, 1, 3);
		assert minimumTotalWaitingTime(serviceTimes) == 10;
	}

}
