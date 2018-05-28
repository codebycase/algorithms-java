package a10_recursion_greedy_invariant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

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
		List<List<int[]>> graph = new ArrayList<>();
		for (int i = 0; i <= N; i++) {
			graph.add(new ArrayList<>());
		}
		for (int[] edge : times) {
			graph.get(edge[0]).add(new int[] { edge[1], edge[2] });
		}

		Queue<int[]> heap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
		heap.offer(new int[] { K, 0 }); // node -> distance

		// distance of shortest s -> v path
		Map<Integer, Integer> distances = new HashMap<>();
		while (!heap.isEmpty()) {
			int[] item = heap.poll();
			int node = item[0], delay = item[1];
			if (distances.containsKey(node))
				continue;
			distances.put(node, delay);
			for (int[] edge : graph.get(node)) {
				int node2 = edge[0], delay2 = edge[1];
				if (!distances.containsKey(node2))
					heap.offer(new int[] { node2, delay + delay2 });
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
