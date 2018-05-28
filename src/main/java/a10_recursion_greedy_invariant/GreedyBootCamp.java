package a10_recursion_greedy_invariant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

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

	public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
		List<List<int[]>> graph = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			graph.add(new ArrayList<>());
		}
		for (int[] flight : flights) {
			graph.get(flight[0]).add(flight);
		}

		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (a[1] - b[1]));
		// city, cost, stop
		pq.add(new int[] { src, 0, -1 });

		while (!pq.isEmpty()) {
			int[] curt = pq.poll();
			int city = curt[0];
			int cost = curt[1];
			int stop = curt[2] + 1;

			if (city == dst) {
				return curt[1];
			}

			for (int[] flight : graph.get(city)) {
				if (stop > K)
					continue;
				pq.add(new int[] { flight[1], flight[2] + cost, stop });
			}
		}

		return -1;
	}

	public static void main(String[] args) {
		List<Integer> serviceTimes = Arrays.asList(2, 5, 1, 3);
		assert minimumTotalWaitingTime(serviceTimes) == 10;
	}

}
