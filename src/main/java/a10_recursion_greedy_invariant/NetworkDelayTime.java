package a10_recursion_greedy_invariant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * There are N network nodes, labelled 1 to N.
 * 
 * Given times, a list of travel times as directed edges times[i] = (u, v, w), where u is the source
 * node, v is the target node, and w is the time it takes for a signal to travel from source to
 * target.
 * 
 * Now, we send a signal from a certain node K. How long will it take for all nodes to receive the
 * signal? If it is impossible, return -1.
 * 
 * Note: N will be in the range [1, 100]. K will be in the range [1, N]. The length of times will be
 * in the range [1, 6000]. All edges times[i] = (u, v, w) will have 1 <= u, v <= N and 1 <= w <=
 * 100.
 * 
 * @author lchen
 *
 */
public class NetworkDelayTime {
	public int networkDelayTime(int[][] times, int N, int K) {
		Map<Integer, List<int[]>> graph = new HashMap<>();

		for (int[] edge : times) {
			if (!graph.containsKey(edge[0]))
				graph.put(edge[0], new ArrayList<int[]>());
			graph.get(edge[0]).add(new int[] { edge[1], edge[2] });
		}

		PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
		heap.offer(new int[] { 0, K });

		Map<Integer, Integer> distances = new HashMap<>();
		while (!heap.isEmpty()) {
			int[] item = heap.poll();
			int delay = item[0], node = item[1];
			if (distances.containsKey(node))
				continue;
			distances.put(node, delay);
			if (graph.containsKey(node))
				for (int[] edge : graph.get(node)) {
					int vertex = edge[0], delay2 = edge[1];
					if (!distances.containsKey(vertex))
						heap.offer(new int[] { delay + delay2, vertex });
				}
		}

		if (distances.size() != N)
			return -1;
		int answer = 0;
		for (int delay : distances.values())
			answer = Math.max(answer, delay);
		return answer;
	}
}
