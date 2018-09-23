package a10_recursion_greedy_invariant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

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

	public int candy(int[] ratings) {
		int[] candies = new int[ratings.length];
		Arrays.fill(candies, 1);
		for (int i = 1; i < ratings.length; i++) {
			if (ratings[i] > ratings[i - 1]) {
				candies[i] = candies[i - 1] + 1;
			}
		}
		int sum = candies[ratings.length - 1];
		for (int i = ratings.length - 2; i >= 0; i--) {
			if (ratings[i] > ratings[i + 1]) {
				candies[i] = Math.max(candies[i], candies[i + 1] + 1);
			}
			sum += candies[i];
		}
		return sum;
	}

	public int candy2(int[] ratings) {
		if (ratings == null || ratings.length == 0)
			return 0;
		int start = 0, sum = 0, len = ratings.length;
		while (start < len) {
			if (start + 1 < len && ratings[start] == ratings[start + 1]) {
				sum += 1;
				start++;
				continue;
			}
			// left means the left part of the mountain,right means the right part of the mountain
			int left = 0, right = 0;
			// climbing up
			while (start + 1 < len && ratings[start] < ratings[start + 1]) {
				start++;
				left++;
			}
			// falling down
			while (start + 1 < len && ratings[start] > ratings[start + 1]) {
				start++;
				right++;
			}
			// break for flat point
			if (left == 0 && right == 0) {
				sum += 1;
				break;
			}
			// calculate for mountain
			int max = Math.max(left, right) + 1;
			int leftSum = (1 + left) * left / 2;
			int rightSum = (1 + right) * right / 2 - 1;
			sum += max + leftSum + rightSum;
		}
		return sum;
	}

	public boolean canCross(int[] stones) {
		int[][] memo = new int[stones.length][stones.length];
		for (int[] row : memo) {
			Arrays.fill(row, -1);
		}
		return canCross(stones, 0, 0, memo) == 1;
	}

	public int canCross(int[] stones, int index, int jumpsize, int[][] memo) {
		if (memo[index][jumpsize] >= 0) {
			return memo[index][jumpsize];
		}
		int ind1 = Arrays.binarySearch(stones, index + 1, stones.length, stones[index] + jumpsize);
		if (ind1 >= 0 && canCross(stones, ind1, jumpsize, memo) == 1) {
			memo[index][jumpsize] = 1;
			return 1;
		}
		int ind2 = Arrays.binarySearch(stones, index + 1, stones.length, stones[index] + jumpsize - 1);
		if (ind2 >= 0 && canCross(stones, ind2, jumpsize - 1, memo) == 1) {
			memo[index][jumpsize - 1] = 1;
			return 1;
		}
		int ind3 = Arrays.binarySearch(stones, index + 1, stones.length, stones[index] + jumpsize + 1);
		if (ind3 >= 0 && canCross(stones, ind3, jumpsize + 1, memo) == 1) {
			memo[index][jumpsize + 1] = 1;
			return 1;
		}
		memo[index][jumpsize] = ((index == stones.length - 1) ? 1 : 0);
		return memo[index][jumpsize];
	}

	public boolean canCross2(int[] stones) {
		Map<Integer, Set<Integer>> map = new HashMap<>();
		for (int i = 0; i < stones.length; i++) {
			map.put(stones[i], new HashSet<Integer>());
		}
		map.get(0).add(0);
		for (int i = 0; i < stones.length; i++) {
			// for each stone, calculate next stones it can reach
			for (int k : map.get(stones[i])) {
				for (int step = k - 1; step <= k + 1; step++) {
					if (step > 0 && map.containsKey(stones[i] + step)) {
						map.get(stones[i] + step).add(step);
					}
				}
			}
		}
		return map.get(stones[stones.length - 1]).size() > 0;
	}

	public static void main(String[] args) {
		List<Integer> serviceTimes = Arrays.asList(2, 5, 1, 3);
		assert minimumTotalWaitingTime(serviceTimes) == 10;
	}

}
