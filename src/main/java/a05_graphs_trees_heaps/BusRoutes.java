package a05_graphs_trees_heaps;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import util.Vertex;

/**
 * We have a list of bus routes. Each routes[i] is a bus route that the i-th bus repeats forever.
 * For example if routes[0] = [1, 5, 7], this means that the first bus (0-th indexed) travels in the
 * sequence 1->5->7->1->5->7->1->... forever.
 * 
 * We start at bus stop S (initially not on a bus), and we want to go to bus stop T. Travelling by
 * buses only, what is the least number of buses we must take to reach our destination? Return -1 if
 * it is not possible.
 * 
 * <pre>
Example:
Input: 
routes = [[1, 2, 7], [3, 6, 7]]
S = 1
T = 6
Output: 2
Explanation: 
The best strategy is take the first bus to the bus stop 7, then take the second bus to the bus stop 6.
 * </pre>
 * 
 * @author lchen
 *
 */
public class BusRoutes {
	/**
	 * Instead of thinking of the stops as vertex (of a graph), think of the buses as nodes. We want to
	 * take the least number of buses, which is a shortest path problem, conducive to using a
	 * breadth-first search.
	 */
	public int numBusesToDestination(int[][] routes, int S, int T) {
		if (S == T)
			return 0;
		int N = routes.length;

		List<List<Integer>> graph = new ArrayList<>(N);
		for (int i = 0; i < N; i++) {
			Arrays.sort(routes[i]);
			graph.add(new ArrayList<>());
		}

		// two buses are connected if they share at least one bus stop.
		for (int i = 0; i < N - 1; i++) {
			for (int j = i + 1; j < N; j++) {
				if (intersect(routes[i], routes[j])) {
					graph.get(i).add(j);
					graph.get(j).add(i);
				}
			}
		}

		// breadth first search with queue
		Set<Integer> visited = new HashSet<>();
		Set<Integer> targets = new HashSet<>();
		Queue<Vertex> queue = new ArrayDeque<>();

		for (int i = 0; i < N; i++) {
			// all source buses through S stop
			if (Arrays.binarySearch(routes[i], S) >= 0) {
				queue.offer(new Vertex(i, 0));
				visited.add(i);
			}
			// all target buses through T stop
			if (Arrays.binarySearch(routes[i], T) >= 0)
				targets.add(i);
		}
		
		while (!queue.isEmpty()) {
			Vertex vertex = queue.poll();
			if (targets.contains(vertex.id))
				return vertex.depth + 1;
			for (Integer neighbor : graph.get(vertex.id)) {
				if (!visited.contains(neighbor)) {
					queue.offer(new Vertex(neighbor, vertex.depth + 1));
					visited.add(neighbor);
				}
			}
		}

		return -1;
	}

	private boolean intersect(int[] routeA, int[] routeB) {
		int i = 0, j = 0;
		while (i < routeA.length && j < routeB.length) {
			if (routeA[i] == routeB[j])
				return true;
			else if (routeA[i] < routeB[j])
				i++;
			else
				j++;
		}
		return false;
	}

	public static void main(String[] args) {
		BusRoutes solution = new BusRoutes();
		int[][] routes = { { 1, 2, 7 }, { 3, 6, 7 }, { 6, 4, 5 } };
		assert solution.numBusesToDestination(routes, 1, 5) == 3;
	}

}
